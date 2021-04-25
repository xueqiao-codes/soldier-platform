package org.soldier.platform.web.config.dao.server.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import org.soldier.platform.web.config.dao.server.WebConfigDaoAdaptor;
import org.soldier.platform.web.config.dao.ConfigType;
import org.soldier.platform.web.config.dao.DeployType;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.WebConfigList;

import com.antiy.error_code.ErrorCodeInner;

public class WebConfigDaoHandler extends WebConfigDaoAdaptor {
	private String roleWebConfigName;
	
	@Override
	public int InitApp(Properties props) {
		roleWebConfigName = props.getProperty("roleWebConfigName", "platform");

		System.out.println("roleWebConfigName=" + roleWebConfigName);
		
		try {
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
				
		return 0;
	}
	
	private String getWebConfigTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(
					roleWebConfigName, "tweb_config_info", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getWebNginxTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleWebConfigName, "tweb_nginx_config", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private void fillSqlQueryBuilder(SqlQueryBuilder builder, 
			QueryWebConfigOption option) throws ErrorInfo {
		builder.addFields("Fweb_project_name", "Fdeploy_type", "Fip_list",
				"Fport", "Fdomain_list", "Findex_path", "Fdesc", "Fserver_options", "Flocation_options",
				"Fhttps_cert_name", "Fdisable_http", "Fcreate_timestamp", "Flastmodify_timestamp");
		builder.addTables(getWebConfigTableName());
		if (option != null) {
			if(option.isSetWebProjectName() && !option.getWebProjectName().trim().isEmpty()) {
				builder.addFieldCondition(ConditionType.AND,
						"Fweb_project_name=?", option.getWebProjectName().trim());
			}
			if(option.isSetType()){
				builder.addFieldCondition(ConditionType.AND,
						"Fdeploy_type=?", option.getType().getValue());
			}
			if (option.isSetDesc() && !option.getDesc().trim().isEmpty()) {
				builder.addFieldCondition(ConditionType.AND,
						"Fdesc like ?", "%" + option.getDesc().trim() + "%");
			}
			if (option.isSetPort()) {
				builder.addFieldCondition(ConditionType.AND,
						"Fport=?", option.getPort());
			}
			if (option.isSetBackend() && !option.getBackend().trim().isEmpty()) {
				builder.addFieldCondition(ConditionType.AND, 
						"Fip_list like ?", "%" + option.backend.trim() + "%");
			}
			if (option.isSetDomain() && !option.getDomain().trim().isEmpty()) {
				builder.addFieldCondition(ConditionType.AND,
						"Fdomain_list like ?",  "%" + option.getDomain().trim() + "%");
			}
		}
	}
	
	private WebConfig webConfigFromRS(ResultSet rs) throws SQLException {
		WebConfig config = new WebConfig();
		config.setWebProjectName(rs.getString("Fweb_project_name"));
		config.setDeployType(DeployType.findByValue(rs.getInt("Fdeploy_type")));
		
		String ipListStr = rs.getString("Fip_list");
		config.setIpList(IpListUtil.strToIpList(ipListStr));
		config.setBackendList(Arrays.asList(StringUtils.split(ipListStr, ",")));
		config.setPort(rs.getInt("Fport"));
		
		List<String> domainList = new ArrayList<String>();
		
		String[] rsDomainArray = StringUtils.split(rs.getString("Fdomain_list"), ",");
		if (rsDomainArray != null) {
			for (String domain : rsDomainArray) {
				domainList.add(domain);
			}
		}
		config.setDomainList(domainList);
		
		config.setIndexPath(rs.getString("Findex_path"));
		config.setDesc(rs.getString("Fdesc"));
		config.setServerOptions(rs.getString("Fserver_options"));
		config.setLocationOptions(rs.getString("Flocation_options"));
		config.setHttpsCertName(rs.getString("Fhttps_cert_name"));
		config.setDisableHttp(0 != rs.getByte("Fdisable_http"));
		
		config.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		config.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return config;
	}
	
	private void fillPreparedFields(PreparedFields preparedFields, WebConfig config) {
		if (config.isSetDeployType()) {
			preparedFields.addInt("Fdeploy_type", config.getDeployType().getValue());
		}
		if (config.isSetDomainList()) {
			preparedFields.addString("Fdomain_list", StringUtils.join(config.getDomainList(), ","));
		}
		if (config.isSetDesc()) {
			preparedFields.addString("Fdesc", config.getDesc());
		}
		if (config.isSetIndexPath()) {
			preparedFields.addString("Findex_path", config.getIndexPath());
		}
		if (config.isSetBackendList()) {
		    preparedFields.addString("Fip_list", StringUtils.join(config.getBackendList(), ","));
		} else {
		    if (config.isSetIpList()) {
		        preparedFields.addString("Fip_list", IpListUtil.ipListToStr(config.getIpList()));
		    }
		}
		if (config.isSetPort()) {
			preparedFields.addInt("Fport", config.getPort());
		}
		if (config.isSetServerOptions()) {
			preparedFields.addString("Fserver_options", config.getServerOptions());
		}
		if (config.isSetLocationOptions()) {
			preparedFields.addString("Flocation_options", config.getLocationOptions());
		}
		if (config.isSetDisableHttp()) {
		    preparedFields.addByte("Fdisable_http", config.isDisableHttp() ? (byte)1: (byte)0);
		}
		if (config.isSetHttpsCertName()) {
		    preparedFields.addString("Fhttps_cert_name", config.getHttpsCertName().trim());
		}
	}
	
	@Override
	protected WebConfigList queryConfigByPage(TServiceCntl oCntl,
			QueryWebConfigOption option, int pageIndex, int pageSize)
			throws ErrorInfo, TException {
		if (pageIndex < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageIndex should not < 0");
		}
		if (pageSize <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageSize should not <= 0");
		}
		
		SqlQueryBuilder sqlBuilder = new SqlQueryBuilder();
		fillSqlQueryBuilder(sqlBuilder, option);
		sqlBuilder.setOrder(OrderType.ASC, "Fweb_project_name");
		sqlBuilder.setPage(pageIndex, pageSize);
		
		WebConfigList result = new WebConfigList();
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleWebConfigName, oCntl.getDalSetServiceName(), true, 0));
		
		try {
			result.setTotalCount(
					runner.query(sqlBuilder.getTotalCountSql(), 
							new ScalarHandler<Long>(), 
							sqlBuilder.getParameterList()).intValue());
			
			result.setConfigList(runner.query(
					sqlBuilder.getItemsSql(), 
					new AbstractListHandler<WebConfig>() {
						@Override
						protected WebConfig handleRow(ResultSet rs) throws SQLException {
							return webConfigFromRS(rs);
						}
					},
					sqlBuilder.getParameterList()));
			
			return result;
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}

	@Override
	protected List<WebConfig> queryConfig(TServiceCntl oCntl,
			QueryWebConfigOption option)
			throws ErrorInfo, TException {
		SqlQueryBuilder sqlBuilder = new SqlQueryBuilder();
		fillSqlQueryBuilder(sqlBuilder, option);
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleWebConfigName, oCntl.getDalSetServiceName(), true, 0));
		
		try {
			return runner.query(
					sqlBuilder.getItemsSql(), 
					new AbstractListHandler<WebConfig>() {
						@Override
						protected WebConfig handleRow(ResultSet rs) throws SQLException {
							return webConfigFromRS(rs);
						}
					},
					sqlBuilder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void addWebConfig(TServiceCntl oCntl, WebConfig config)
			throws ErrorInfo, TException {
		new WebConfigProccessor(config).checkAndProcess4Add();
		
		PreparedFields preparedFields = new PreparedFields();
		preparedFields.addString("Fweb_project_name", config.getWebProjectName());
		fillPreparedFields(preparedFields, config);
		preparedFields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		preparedFields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer sqlBuilder = new StringBuffer(128);
		sqlBuilder.append("INSERT INTO ");
		sqlBuilder.append(getWebConfigTableName());
		sqlBuilder.append(" SET ");
		sqlBuilder.append(preparedFields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleWebConfigName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(sqlBuilder.toString(), preparedFields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void deleteWebConfig(TServiceCntl oCntl, String webProjectName)
			throws ErrorInfo, TException {
		if (webProjectName == null || webProjectName.trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"webProjectName should be set and not empty");
		}
		
		StringBuffer sqlBuffer = new StringBuffer(128);
		sqlBuffer.append("DELETE FROM ");
		sqlBuffer.append(getWebConfigTableName());
		sqlBuffer.append(" WHERE Fweb_project_name=?");
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleWebConfigName, oCntl.getDalSetServiceName(), false, 0));
		try {
			int rs = runner.update(sqlBuffer.toString(), webProjectName);
			if (rs <= 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	
	@Override
	protected void updateWebConfig(TServiceCntl oCntl, WebConfig config)
			throws ErrorInfo, TException {
		new WebConfigProccessor(config).checkAndProcess4Update();
		
		PreparedFields preparedFields = new PreparedFields();
		fillPreparedFields(preparedFields, config);
		preparedFields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer sqlBuffer = new StringBuffer(128);
		sqlBuffer.append("UPDATE ");
		sqlBuffer.append(getWebConfigTableName());
		sqlBuffer.append(" SET ");
		sqlBuffer.append(preparedFields.getPreparedSql());
		sqlBuffer.append(" WHERE Fweb_project_name=?");
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleWebConfigName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			int rs = runner.update(sqlBuffer.toString(), 
					ArrayUtils.add(preparedFields.getParameters(), config.getWebProjectName()));
			if (rs <= 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
	}

	@Override
	protected void updateNginxConfig(TServiceCntl oCntl, String config, ConfigType type)
			throws ErrorInfo, TException {
		if (config == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"config should not be null");
		}
		int fidValue = (type == null) ? ConfigType.AllConfig.getValue() : type.getValue();
		
		PreparedFields preparedFields = new PreparedFields();
		preparedFields.addString("Fconfig", config);
		preparedFields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer sqlBuffer = new StringBuffer(128);
		sqlBuffer.append("UPDATE ");
		sqlBuffer.append(getWebNginxTableName());
		sqlBuffer.append(" SET Fversion=Fversion+1,");
		sqlBuffer.append(preparedFields.getPreparedSql());
		sqlBuffer.append(" WHERE Fid=" + fidValue);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleWebConfigName, oCntl.getDalSetServiceName(), false, 0));
		try {
			int rs = runner.update(sqlBuffer.toString(), preparedFields.getParameters());
			if (rs <= 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
	}

	@Override
	protected int getLastVersion(TServiceCntl oCntl, ConfigType type) throws ErrorInfo,TException {
		int fidValue = (type == null) ? ConfigType.AllConfig.getValue() : type.getValue();
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		builder.addFields("Fversion");
		builder.addTables(getWebNginxTableName());
		builder.addFieldCondition(ConditionType.AND, "Fid=?", fidValue);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleWebConfigName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(builder.getItemsSql(), 
					new ScalarHandler<Long>(), builder.getParameterList()).intValue();
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}

	@Override
	protected String getLastestNginxConfig(TServiceCntl oCntl, ConfigType type)
			throws ErrorInfo, TException {
		int fidValue = (type == null) ? ConfigType.AllConfig.getValue() : type.getValue();
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		builder.addFields("Fconfig");
		builder.addTables(getWebNginxTableName());
		builder.addFieldCondition(ConditionType.AND, "Fid=?", fidValue);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleWebConfigName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(builder.getItemsSql(), new ScalarHandler<String>(), builder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}

	@Override
	public void destroy() {
	}
	
}
