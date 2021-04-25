package org.soldier.platform.file_storage_info.dao.server.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.file_storage_info.dao.server.FileStorageInfoDaoAdaptor;
import org.soldier.platform.file_storage_info.dao.AccessAttribute;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.StorageInfo;
import org.soldier.platform.file_storage_info.dao.StorageInfoList;

import com.antiy.error_code.ErrorCodeInner;

public class FileStorageInfoDaoHandler extends FileStorageInfoDaoAdaptor {
	private String roleName;
	private IDomainProvider domainProvider;
	
	@Override
	public int InitApp(Properties props) {
		roleName = props.getProperty("roleName", "file_storage").trim();
		System.out.println("roleName=" + roleName);
		
		try {
			domainProvider = new AliyunOssDomainProvider();
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return -1;
		}
		
		return 0;
	}
	
	private String getFileStorageTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tfile_storage_info", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private StorageInfo fromResultSet(ResultSet rs) throws SQLException {
		StorageInfo info = new StorageInfo();
		info.setStorageKey(rs.getString("Fstorage_key"));
		info.setAccessAttribute(AccessAttribute.findByValue(rs.getInt("Faccess_attribute")));
		info.setDomain(rs.getString("Fdomain"));
		info.setDesc(rs.getString("Fdesc"));
		info.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		info.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return info;
	}
	
	private void checkStorage(StorageInfo storageInfo) throws ErrorInfo {
		if (storageInfo == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"storageInfo should not be null");
		}
		if (!storageInfo.isSetStorageKey() || storageInfo.getStorageKey().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"storageKey should not be empty");
		}
	}

	@Override
	protected void addStorage(TServiceCntl oCntl, StorageInfo storageInfo)
			throws ErrorInfo, TException {
		checkStorage(storageInfo);
		
		StringBuffer insertSqlBuffer = new StringBuffer(128);
		insertSqlBuffer.append("INSERT INTO ");
		insertSqlBuffer.append(getFileStorageTableName());
		insertSqlBuffer.append(" SET ");
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Fstorage_key", storageInfo.getStorageKey());
		if (storageInfo.isSetAccessAttribute()) {
			fields.addInt("Faccess_attribute", storageInfo.getAccessAttribute().getValue());
		} else {
			fields.addInt("Faccess_attribute", AccessAttribute.PublicRead.getValue());
		}
		if (storageInfo.isSetDomain() && !storageInfo.getDomain().isEmpty()) {
			fields.addString("Fdomain", storageInfo.getDomain());
		} else {
			fields.addString("Fdomain", domainProvider.getDefaultDomain(storageInfo.getStorageKey()));
		}
		if (storageInfo.isSetDesc()) {
			fields.addString("Fdesc", storageInfo.getDesc());
		}
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
		
		insertSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(insertSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected StorageInfoList queryStorageList(TServiceCntl oCntl,
			int pageIndex, int pageSize,
			QueryStorageInfoListOption option) throws ErrorInfo, TException {
		if (pageIndex < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageIndex should not < 0");
		}
		if (pageSize <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageSize should not <= 0");
		}
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		
		queryBuilder.addFields("Fstorage_key", "Faccess_attribute", 
				"Fdomain", "Fdesc", "Fcreate_timestamp", "Flastmodify_timestamp");
		queryBuilder.addTables(getFileStorageTableName());
		queryBuilder.setOrder(OrderType.ASC, "Fstorage_key");
		queryBuilder.setPage(pageIndex, pageSize);
		
		if (option != null) {
			if (option.isSetStorageKey()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Fstorage_key=?", option.getStorageKey());
			}
			if (option.isSetDesc()) {
				queryBuilder.addFieldCondition(ConditionType.AND, 
						"Fdesc like ?", "%" + option.getDesc() + "%");
			}
		}
		
		StorageInfoList resultList = new StorageInfoList();
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			resultList.setTotalNum(
					runner.query(queryBuilder.getTotalCountSql(), 
							new ScalarHandler<Long>(), 
							queryBuilder.getParameterList()).intValue());
			
			resultList.setResultList(runner.query(
					queryBuilder.getItemsSql(), 
					new AbstractListHandler<StorageInfo>() {
						@Override
						protected StorageInfo handleRow(ResultSet rs) throws SQLException {
							return fromResultSet(rs);
						}
					},
					queryBuilder.getParameterList()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		return resultList;
	}

	@Override
	public void destroy() {
	}

	@Override
	protected void deleteStorage(TServiceCntl oCntl, String storageKey)
			throws ErrorInfo, TException {
		if (storageKey == null || storageKey.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"storageKey should not be empty");
		}
		
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getFileStorageTableName());
		deleteSqlBuffer.append(" WHERE Fstorage_key=?");
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(deleteSqlBuffer.toString(), storageKey);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateStorage(TServiceCntl oCntl, StorageInfo storageInfo)
			throws ErrorInfo, TException {
		checkStorage(storageInfo);
		
		PreparedFields fields = new PreparedFields();
		if (storageInfo.isSetAccessAttribute()) {
			fields.addInt("Faccess_attribute", storageInfo.getAccessAttribute().getValue());
		}
		if (storageInfo.isSetDomain() && !storageInfo.getDomain().isEmpty()) {
			fields.addString("Fdomain", storageInfo.getDomain());
		}
		if (storageInfo.isSetDesc()) {
			fields.addString("Fdesc", storageInfo.getDesc());
		}
		if (fields.getSize() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"no fields to update");
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getFileStorageTableName());
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fstorage_key=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(updateSqlBuffer.toString(),
					ArrayUtils.add(fields.getParameters(), storageInfo.getStorageKey()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
	}
}
