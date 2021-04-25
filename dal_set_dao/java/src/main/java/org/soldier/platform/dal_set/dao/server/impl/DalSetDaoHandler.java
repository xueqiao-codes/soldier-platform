package org.soldier.platform.dal_set.dao.server.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.soldier.base.AES;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.DbType;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.ServiceRelatedType;
import org.soldier.platform.dal_set.dao.TypeInSet;
import org.soldier.platform.dal_set.dao.server.DalSetDaoAdaptor;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

public class DalSetDaoHandler extends DalSetDaoAdaptor {
	private String roleName;
	
	private String secretKey = "NOT_SHOW_PASSWD#"; // DalSet Use This, Do not modify this
	
	@Override
	public int InitApp(Properties props) {
		roleName = props.getProperty("roleName", "platform");
		
		try {
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		AES.encrypt("load fast", secretKey);
		
		return 0;
	}
	
	private String getDalSetHostTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_host", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getDalSetUserTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_user", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getDalSetTableTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_table", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getDalSetRoleTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_role", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getDalSetRoleTableRelationTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_role_table_relation", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getDalSetRoleSetRelationTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_role_set_relation", 0);
		} catch(SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getDalSetRoleServiceRelationTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_role_service_relation", 0);
		} catch(SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private DalSetHost hostFromResultSet(ResultSet rs) throws SQLException {
		DalSetHost result = new DalSetHost();
		result.setName(rs.getString("Fhost_name"));
		result.setDomain(rs.getString("Fhost_domain"));
		result.setPort(rs.getInt("Fhost_port"));
		result.setDesc(rs.getString("Fhost_desc"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return result;
	}
	
	private DalSetUser userFromResultSet(ResultSet rs) throws SQLException {
		DalSetUser result = new DalSetUser();
		result.setKey(rs.getString("Fuser_key"));
		result.setName(rs.getString("Fuser_name"));
		result.setPlainPassword(rs.getString("Fuser_password"));
		result.setPassword(AES.encrypt(result.getPlainPassword(), secretKey));
		result.setDesc(rs.getString("Fuser_desc"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return result;
	}
	
	private DalSetTable tableFromResultSet(ResultSet rs) throws SQLException {
		DalSetTable result = new DalSetTable();
		result.setPrefixName(rs.getString("Ftable_prefix_name"));
		result.setSliceNum(rs.getInt("Ftable_slice_num"));
		result.setFillZero(rs.getBoolean("Ftable_fill_zero"));
		result.setDesc(rs.getString("Ftable_desc"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return result;
	}
	
	private DalSetRole roleFromResultSet(ResultSet rs) throws SQLException {
		DalSetRole result = new DalSetRole();
		result.setRoleName(rs.getString("Frole_name"));
		result.setDbName(rs.getString("Fdb_name"));
		result.setDbType(DbType.findByValue(rs.getInt("Fdb_type")));
		result.setDesc(rs.getString("Fdesc"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return result;
	}
	
	private RoleTableRelation roleTableRelationFromResultSet(ResultSet rs) throws SQLException {
		RoleTableRelation result = new RoleTableRelation();
		result.setRoleName(rs.getString("Frole_name"));
		result.setTablePrefixName(rs.getString("Ftable_prefix_name"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		return result;
	}
	
	private RoleSetRelation roleSetRelationFromResultSet(ResultSet rs) throws SQLException {
		RoleSetRelation result = new RoleSetRelation();
		result.setHostName(rs.getString("Fhost_name"));
		result.setTypeInSet(TypeInSet.findByValue(rs.getInt("Ftype_in_set")));
		result.setSetIndex(rs.getInt("Fset_index"));
		result.setWeight(rs.getInt("Fweight"));
		result.setRoleName(rs.getString("Frole_name"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return result;
	}
	
	private RoleServiceRelation roleServiceRelationFromResultSet(ResultSet rs) throws SQLException {
		RoleServiceRelation result = new RoleServiceRelation();
		result.setServiceKey(rs.getInt("Fservice_key"));
		result.setInterfaceName(rs.getString("Finterface_name"));
		result.setRoleName(rs.getString("Frole_name"));
		result.setUserKey(rs.getString("Fuser_key"));
		result.setRelatedType(ServiceRelatedType.findByValue(rs.getInt("Frelated_type")));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return result;
	}
	
	private void checkPageParameters(int pageIndex, int pageSize) throws ErrorInfo {
		if (pageIndex < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "pageIndex error, can not < 0");
		}
		if (pageSize <= 0 ) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "pageSize error, can not <= 0");
		}
	}

	@Override
	protected DalSetHostList queryDalSetHosts(TServiceCntl oCntl,
			int pageIndex, int pageSize,
			QueryDalSetHostOption option)
			throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.addFields("Fhost_name", "Fhost_domain", "Fhost_port",
				"Fhost_desc", "Fcreate_timestamp", "Flastmodify_timestamp");
		builder.addTables(getDalSetHostTableName());
		
		if (option != null) {
			if (option.isSetName()) {
				builder.addFieldCondition(ConditionType.AND, "Fhost_name=?", option.getName());
			} else {
				if (option.isSetDomain()) {
					builder.addFieldCondition(ConditionType.AND, 
						"Fhost_domain=?", option.getDomain());
				}
				if (option.isSetPort()) {
					builder.addFieldCondition(ConditionType.AND,
						"Fhost_port=?", option.getPort());
				}
				if (option.isSetDesc() && !option.getDesc().trim().isEmpty()) {
					builder.addFieldCondition(ConditionType.AND,
							"Fhost_desc like ?", "%" + option.getDesc().trim() + "%");
				}
			}
		}
		builder.setOrder(OrderType.ASC, "Fhost_name");
		builder.setPage(pageIndex, pageSize);
		
		DalSetHostList result = new DalSetHostList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(runner.query(
					builder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					builder.getParameterList()).intValue());
			
			result.setResultList(runner.query(builder.getItemsSql(),
					new AbstractListHandler<DalSetHost>() {
						@Override
						protected DalSetHost handleRow(ResultSet rs) throws SQLException {
							return hostFromResultSet(rs);
						}
					}, builder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}
	
	private void checkHost(DalSetHost host) throws ErrorInfo {
		if (host == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"host should not be null");
		}
	}
	
	private void checkHostName(DalSetHost host) throws ErrorInfo {
		if (!host.isSetName() || host.getName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"host is not right!");
		}
	}
	
	private void checkDomain(DalSetHost host) throws ErrorInfo {
		if (!host.isSetDomain() || host.getDomain().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"domain is not right!");
		}
	}
	
	private void checkPort(DalSetHost host) throws ErrorInfo {
		if (host.getPort() <= 0 || host.getPort() > 65535) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"port is not right, must 0 < port <= 65535");
		}
	}
	
	@Override
	protected void addDalSetHost(TServiceCntl oCntl, DalSetHost host)
			throws ErrorInfo, TException {
		checkHost(host);
		checkHostName(host);
		checkDomain(host);
		checkPort(host);
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetHostTableName());
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Fhost_name", host.getName());
		fields.addString("Fhost_domain", host.getDomain());
		fields.addShort("Fhost_port", (short)(host.getPort()));
		if (host.isSetDesc()) {
			fields.addString("Fhost_desc", host.getDesc());
		}
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		addSqlBuffer.append(" SET ");
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateDalSetHost(TServiceCntl oCntl, DalSetHost host)
			throws ErrorInfo, TException {
		checkHost(host);
		checkHostName(host);
		if (host.isSetDomain()) {
			checkDomain(host);
		}
		if (host.isSetPort()) {
			checkPort(host);
		}
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getDalSetHostTableName());
		
		PreparedFields fields = new PreparedFields();
		if (host.isSetDomain()) {
			fields.addString("Fhost_domain", host.getDomain());
		}
		if (host.isSetPort()) {
			fields.addShort("Fhost_port", (short)(host.getPort()));
		}
		if (host.isSetDesc()) {
			fields.addString("Fhost_desc", host.getDesc());
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fhost_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(updateSqlBuffer.toString(), 
					ArrayUtils.add(fields.getParameters(), host.getName()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	
	@Override
	protected void deleteDalSetHost(TServiceCntl oCntl, String hostKey)
			throws ErrorInfo, TException {
		if (hostKey == null || hostKey.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"hostKey must set and not be empty");
		}
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetHostTableName());
		deleteSqlBuffer.append(" WHERE Fhost_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			int count = runner.update(deleteSqlBuffer.toString(), hostKey);
			if (count == 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						"no such hostKey");
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	protected DalSetUserList queryDalSetUsers(TServiceCntl oCntl,
			int pageIndex, int pageSize, QueryDalSetUserOption option)
			throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Fuser_key", "Fuser_name", "Fuser_password"
				, "Fuser_desc", "Fcreate_timestamp", "Flastmodify_timestamp");
		queryBuilder.addTables(this.getDalSetUserTableName());
		
		if (option != null) {
			if (option.isSetKey()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Fuser_key = ?", option.getKey());
			} else {
				if (option.isSetName()) {
					queryBuilder.addFieldCondition(ConditionType.AND,
							"Fuser_name=?", option.getName());
				}
				if (option.isSetDesc()) {
				queryBuilder.addFieldCondition(ConditionType.AND, 
					"Fuser_desc like ?", "%" + option.getDesc() + "%");
				}
			}
		}
		
		queryBuilder.setOrder(OrderType.ASC, "Fuser_key");
		queryBuilder.setPage(pageIndex, pageSize);
		
		DalSetUserList result = new DalSetUserList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(
					runner.query(queryBuilder.getTotalCountSql(), 
							new ScalarHandler<Long>(), 
							queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(queryBuilder.getItemsSql(), 
					new AbstractListHandler<DalSetUser>() {
						@Override
						protected DalSetUser handleRow(ResultSet rs) throws SQLException {
							return userFromResultSet(rs);
						}
			}, queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}
	
	private void checkUser(DalSetUser user) throws ErrorInfo {
		if (user == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"user should not be null");
		}
	}
	
	private void checkUserKey(DalSetUser user) throws ErrorInfo {
		if (!user.isSetKey() || user.getKey().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"user's key is not right!");
		}
	}
	
	private void checkUserName(DalSetUser user) throws ErrorInfo {
		if (!user.isSetName() || user.getName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"user's name is not right!");
		}
	}
	
	private void checkUserPassword(DalSetUser user) throws ErrorInfo {
		if (!user.isSetPassword() || user.getPassword().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"user's password is not right!");
		}
	}
	
	@Override
	protected void addDalSetUser(TServiceCntl oCntl, DalSetUser user)
			throws ErrorInfo, TException {
		checkUser(user);
		checkUserKey(user);
		checkUserName(user);
		checkUserPassword(user);
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetUserTableName());
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Fuser_key", user.getKey());
		fields.addString("Fuser_name", user.getName());
		fields.addString("Fuser_password", user.getPassword());
		if (user.isSetDesc()) {
			fields.addString("Fuser_desc", user.getDesc());
		}
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		addSqlBuffer.append(" SET ");
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateDalSetUser(TServiceCntl oCntl, DalSetUser user)
			throws ErrorInfo, TException {
		checkUser(user);
		checkUserKey(user);
		if (user.isSetName()) {
			checkUserName(user);
		}
		if (user.isSetPassword()) {
			checkUserPassword(user);
		}
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getDalSetUserTableName());
		PreparedFields fields = new PreparedFields();
		if (user.isSetName()) {
			fields.addString("Fuser_name", user.getName());
		}
		if (user.isSetPassword()) {
			fields.addString("Fuser_password", user.getPassword());
		}
		if (user.isSetDesc()) {
			fields.addString("Fuser_desc", user.getDesc());
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fuser_key=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(updateSqlBuffer.toString(),
					ArrayUtils.add(fields.getParameters(), user.getKey()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	

	@Override
	protected void deleteDalSetUser(TServiceCntl oCntl, String userKey)
			throws ErrorInfo, TException {
		if (userKey == null || userKey.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userKey must be set and not be empty!");
		}
		
		StringBuffer deleteSqlBuffer = new StringBuffer(256);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetUserTableName());
		deleteSqlBuffer.append(" WHERE Fuser_key=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			int count = runner.update(deleteSqlBuffer.toString(), userKey);
			if (count == 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						"no such userKey");
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected DalSetTableList queryDalSetTables(TServiceCntl oCntl,
			int pageIndex, int pageSize, QueryDalSetTableOption option)
			throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Ftable_prefix_name", "Ftable_slice_num", 
				"Ftable_fill_zero", "Ftable_desc", "Fcreate_timestamp",
				"Flastmodify_timestamp");
		queryBuilder.addTables(getDalSetTableTableName());
		
		if (option != null) {
			if (option.isSetPrefixName()) {
				queryBuilder.addFieldCondition(
					ConditionType.AND, "Ftable_prefix_name=?", option.getPrefixName());
			} else {
				if (option.isSetDesc() && !option.getDesc().trim().isEmpty()) {
					queryBuilder.addFieldCondition(
						ConditionType.AND, "Ftable_desc like ?", "%" + option.getDesc() + "%");
				}
			}
		}
	
		queryBuilder.setOrder(OrderType.ASC, "Ftable_prefix_name");
		queryBuilder.setPage(pageIndex, pageSize);
		
		DalSetTableList result = new DalSetTableList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(runner.query(
					queryBuilder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(queryBuilder.getItemsSql(),
					new AbstractListHandler<DalSetTable>() {
						@Override
						protected DalSetTable handleRow(ResultSet rs) throws SQLException {
							return tableFromResultSet(rs);
						}
					}, queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}
	
	private void checkTable(DalSetTable table) throws ErrorInfo {
		if (table == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"table should not be null");
		}
	}
	
	private void checkTablePrefixName(DalSetTable table) throws ErrorInfo {
		if (!table.isSetPrefixName() || table.getPrefixName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"prefixName is wrong!");
		}
	}
	
	private void checkTableSliceNum(DalSetTable table) throws ErrorInfo {
		if (!table.isSetSliceNum() || table.getSliceNum() < 0 || table.getSliceNum() >= 65535) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"sliceNum must set 0 <= sliceNum < 65535");
		}
	}

	@Override
	protected void addDalSetTable(TServiceCntl oCntl, DalSetTable table)
			throws ErrorInfo, TException {
		checkTable(table);
		checkTablePrefixName(table);
		checkTableSliceNum(table);
		if (table.getSliceNum() != 0 && !table.isSetFillZero()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"fillZero must be set");
		}
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetTableTableName());
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Ftable_prefix_name", table.getPrefixName());
		fields.addShort("Ftable_slice_num", (short)table.getSliceNum());
		fields.addInt("Ftable_fill_zero", table.isFillZero() ? 1 : 0);
		if (table.isSetDesc()) {
			fields.addString("Ftable_desc", table.getDesc());
		}
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		addSqlBuffer.append(" SET ");
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateDalSetTable(TServiceCntl oCntl, DalSetTable table)
			throws ErrorInfo, TException {
		checkTable(table);
		checkTablePrefixName(table);
		if (table.isSetSliceNum()) {
			checkTableSliceNum(table);
		}
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getDalSetTableTableName());
		PreparedFields fields = new PreparedFields();
		if (table.isSetSliceNum()) {
			fields.addShort("Ftable_slice_num", (short)table.getSliceNum());
		}
		if (table.isSetFillZero()) {
			fields.addInt("Ftable_fill_zero", table.isFillZero() ? 1 : 0);
		}
		if (table.isSetDesc()) {
			fields.addString("Ftable_desc", table.getDesc());
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Ftable_prefix_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(updateSqlBuffer.toString(),
					ArrayUtils.add(fields.getParameters(), table.getPrefixName()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	
	@Override
	protected void deleteDalSetTable(TServiceCntl oCntl,
			String tablePrefixName) throws ErrorInfo, TException {
		if (tablePrefixName == null || tablePrefixName.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"tablePrefixName should set and not be empty");
		}
		
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetTableTableName());
		deleteSqlBuffer.append(" WHERE Ftable_prefix_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			int count = runner.update(deleteSqlBuffer.toString(), tablePrefixName);
			if (count == 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						"no such userKey");
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
		
	}

	@Override
	protected DalSetRoleList queryDalSetRoles(TServiceCntl oCntl,
			int pageIndex, int pageSize, QueryDalSetRoleOption option)
			throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Frole_name", "Fdb_name", "Fdb_type",
				"Fdesc", "Fcreate_timestamp", "Flastmodify_timestamp");
		queryBuilder.addTables(getDalSetRoleTableName());
		
		if (option != null) {
			if (option.isSetRoleName()) {
				queryBuilder.addFieldCondition(
					ConditionType.AND, "Frole_name=?", option.getRoleName());
			} else {
				if (option.isSetDbName()) {
					queryBuilder.addFieldCondition(ConditionType.AND, 
							"Fdb_name=?", option.getDbName());
				} 
				if (option.isSetDesc() && !option.getDesc().trim().isEmpty()) {
					queryBuilder.addFieldCondition(ConditionType.AND, 
							"Fdesc like ?", "%" + option.getDesc().trim() + "%");
				}
			}
		}
		
		queryBuilder.setOrder(OrderType.ASC, "Frole_name");
		queryBuilder.setPage(pageIndex, pageSize);
		
		DalSetRoleList result = new DalSetRoleList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(runner.query(
					queryBuilder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(queryBuilder.getItemsSql(),
					new AbstractListHandler<DalSetRole>() {
						@Override
						protected DalSetRole handleRow(ResultSet rs) throws SQLException {
							return roleFromResultSet(rs);
						}
					}, queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}
	
	private void checkRole(DalSetRole role) throws ErrorInfo {
		if (role == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"role should not be null!");
		}
	}
	
	private void checkRoleName(DalSetRole role) throws ErrorInfo {
		if (!role.isSetRoleName() || role.getRoleName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"roleName is not set right!");
		}
	}
	
	private void checkDbName(DalSetRole role) throws ErrorInfo {
		if (!role.isSetDbName() || role.getDbName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"dbName is not set right!");
		}
	}
	
	@Override
	protected void addDalSetRole(TServiceCntl oCntl, DalSetRole role)
			throws ErrorInfo, TException {
		checkRole(role);
		checkRoleName(role);
		checkDbName(role);
		if (!role.isSetDbType()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"dbType is not set");
		}
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetRoleTableName());
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Frole_name", role.getRoleName());
		fields.addString("Fdb_name", role.getDbName());
		fields.addShort("Fdb_type", (short)(role.getDbType().getValue()));
		if (role.isSetDesc()) {
			fields.addString("Fdesc", role.getDesc());
		}
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		addSqlBuffer.append(" SET ");
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateDalSetRole(TServiceCntl oCntl, DalSetRole role)
			throws ErrorInfo, TException {
		checkRole(role);
		checkRoleName(role);
		if (role.isSetDbName()) {
			checkDbName(role);
		}
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getDalSetRoleTableName());
		PreparedFields fields = new PreparedFields();
		if (role.isSetDbName()) {
			fields.addString("Fdb_name", role.getDbName());
		}
		if (role.isSetDbType()) {
			fields.addShort("Fdb_type", (short)(role.getDbType().getValue()));
		}
		if (role.isSetDesc()) {
			fields.addString("Fdesc", role.getDesc());
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Frole_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(updateSqlBuffer.toString(),
					ArrayUtils.add(fields.getParameters(), role.getRoleName()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void deleteDalSetRole(TServiceCntl oCntl,
			String deleteRoleName) throws ErrorInfo, TException {
		if (deleteRoleName == null || deleteRoleName.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userKey must be set and not be empty!");
		}
		
		StringBuffer deleteSqlBuffer = new StringBuffer(256);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetRoleTableName());
		deleteSqlBuffer.append(" WHERE Frole_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			int count = runner.update(deleteSqlBuffer.toString(), deleteRoleName);
			if (count == 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						"no such roleName");
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
	}
	
	private void checkRoleTableRelation(RoleTableRelation relation) throws ErrorInfo {
		if (relation == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"relation should not be null");
		}
		if (relation.getRoleName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"roleName is not right");
		}
		if (relation.getTablePrefixName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"tablePrefixName is not right");
		}
	}

	@Override
	protected void addTableRoleRelation(TServiceCntl oCntl,
			RoleTableRelation relation) throws ErrorInfo, TException {
		checkRoleTableRelation(relation);
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetRoleTableRelationTableName());
		addSqlBuffer.append(" SET ");
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Frole_name", relation.getRoleName());
		fields.addString("Ftable_prefix_name", relation.getTablePrefixName());
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void deleteTableRoleRelation(TServiceCntl oCntl,
			RoleTableRelation relation) throws ErrorInfo, TException {
		checkRoleTableRelation(relation);
		
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetRoleTableRelationTableName());
		deleteSqlBuffer.append(" WHERE Frole_name=? AND Ftable_prefix_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(deleteSqlBuffer.toString(), 
					relation.getRoleName(), relation.getTablePrefixName());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected RoleTableRelationList queryTableRoleRelations(TServiceCntl oCntl,
			int pageIndex, int pageSize, QueryRoleTableRelationOption option)
			throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Frole_name", "Ftable_prefix_name", "Fcreate_timestamp");
		queryBuilder.addTables(getDalSetRoleTableRelationTableName());
		
		queryBuilder.setOrder(OrderType.ASC, "Frole_name");
		if (option != null) {
			if (option.isSetRoleName()) {
				queryBuilder.addFieldCondition(ConditionType.AND, 
						"Frole_name=?", option.getRoleName());
			}
			if (option.isSetTablePrefixName()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Ftable_prefix_name=?", option.getTablePrefixName());
				queryBuilder.setOrder(OrderType.ASC, "Ftable_prefix_name");
			}
		}
		queryBuilder.setPage(pageIndex, pageSize);
		
		RoleTableRelationList result = new RoleTableRelationList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(runner.query(
					queryBuilder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(queryBuilder.getItemsSql(),
					new AbstractListHandler<RoleTableRelation>() {
						@Override
						protected RoleTableRelation handleRow(ResultSet rs) throws SQLException {
							return roleTableRelationFromResultSet(rs);
						}
					}, queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}

	@Override
	protected RoleSetRelationList queryRoleSetRelations(TServiceCntl oCntl,
			int pageIndex, int pageSize, QueryRoleSetRelationOption option)
			throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Fhost_name", "Ftype_in_set", "Fset_index",
				"Fweight", "Frole_name", "Fcreate_timestamp", "Flastmodify_timestamp");
		queryBuilder.addTables(getDalSetRoleSetRelationTableName());
		
		queryBuilder.setOrder(OrderType.ASC, "Frole_name");
		if (option != null) {
			if (option.isSetHostName()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Fhost_name=?", option.getHostName());
			}
			if (option.isSetRoleRelatedOption()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Frole_name=?", option.getRoleRelatedOption().getRoleName());
				if (option.getRoleRelatedOption().isSetSetIndex()) {
					queryBuilder.addFieldCondition(ConditionType.AND,
						"Fset_index=?", option.getRoleRelatedOption().getSetIndex());
				}
				queryBuilder.setOrder(OrderType.ASC, "Fset_index");
			}
		}
		queryBuilder.setPage(pageIndex, pageSize);
		
		RoleSetRelationList result = new RoleSetRelationList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(runner.query(
					queryBuilder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(queryBuilder.getItemsSql(),
					new AbstractListHandler<RoleSetRelation>() {
						@Override
						protected RoleSetRelation handleRow(ResultSet rs) throws SQLException {
							return roleSetRelationFromResultSet(rs);
						}
					}, queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}
	
	private void checkRoleSetRelation(RoleSetRelation relation) throws ErrorInfo {
		if (relation == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"relation should not be null");
		}
		if (relation.getHostName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"hostName should not be empty");
		}
		if (relation.getRoleName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"roleName should not be empty");
		}
		if (relation.getSetIndex() < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"setIndex should not < 0");
		}
	}
	
	private void checkRoleSetRelationWeight(RoleSetRelation relation) throws ErrorInfo {
		if (relation.getWeight() < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"weight should not < 0");
		}
	}
	
	@Override
	protected void addRoleSetRelation(TServiceCntl oCntl,
			RoleSetRelation relation) throws ErrorInfo, TException {
		checkRoleSetRelation(relation);
		if (!relation.isSetTypeInSet()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"typeInSet should be set");
		}
		int weight = 1;
		if (relation.isSetWeight()) {
			checkRoleSetRelationWeight(relation);
			weight = relation.getWeight();
		}
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetRoleSetRelationTableName());
		addSqlBuffer.append(" SET ");
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Frole_name", relation.getRoleName());
		fields.addString("Fhost_name", relation.getHostName());
		fields.addInt("Ftype_in_set", relation.getTypeInSet().getValue());
		fields.addInt("Fset_index", relation.getSetIndex());
		fields.addInt("Fweight", weight);
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void deleteRoleSetRelation(TServiceCntl oCntl,
			RoleSetRelation relation) throws ErrorInfo, TException {
		checkRoleSetRelation(relation);
		
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetRoleSetRelationTableName());
		deleteSqlBuffer.append(" WHERE Fhost_name=? AND Fset_index=? AND Frole_name=? ");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(deleteSqlBuffer.toString(), 
					relation.getHostName(), relation.getSetIndex(),
					relation.getRoleName());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateRoleSetRelation(TServiceCntl oCntl,
			RoleSetRelation relation) throws ErrorInfo, TException {
		checkRoleSetRelation(relation);
		
		if (relation.isSetWeight()) {
			checkRoleSetRelationWeight(relation);
			
			StringBuffer updateSqlBuffer = new StringBuffer(128);
			updateSqlBuffer.append("UPDATE ");
			updateSqlBuffer.append(getDalSetRoleSetRelationTableName());
			updateSqlBuffer.append(" SET ");
			updateSqlBuffer.append(" Fweight=?");
			updateSqlBuffer.append(",Flastmodify_timestamp=?");
			updateSqlBuffer.append(" WHERE Fhost_name=? AND Fset_index=? AND Frole_name=?");
			
			QueryRunner runner = new QueryRunner(
					new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
			try {
				runner.update(updateSqlBuffer.toString(), 
						relation.getWeight(), (int)(System.currentTimeMillis()/1000),
						relation.getHostName(), relation.getSetIndex(),
						relation.getRoleName());
			} catch (SQLException e) {
				AppLog.e(e.getMessage(), e);
				throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
						ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
			}
		}
	}

	@Override
	protected RoleServiceRelationList queryRoleServiceRelations(
			TServiceCntl oCntl, int pageIndex, int pageSize,
			QueryRoleServiceRelationOption option) throws ErrorInfo, TException {
		checkPageParameters(pageIndex, pageSize);
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Fservice_key", "Finterface_name", "Frole_name",
				"Fuser_key", "Frelated_type", "Fcreate_timestamp", "Flastmodify_timestamp");
		queryBuilder.addTables(getDalSetRoleServiceRelationTableName());
		
		queryBuilder.setOrder(OrderType.ASC, "Fservice_key");
		if (option != null) {
			if (option.isSetServiceKey()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Fservice_key=?", option.getServiceKey());
				queryBuilder.setOrder(OrderType.ASC, "Finterface_name");
			}
			if (option.isSetInterfaceName()) {
				queryBuilder.addFieldCondition(ConditionType.AND,
						"Finterface_name=?", option.getInterfaceName());
			}
			if (option.isSetRoleName()) {
				queryBuilder.addFieldCondition(ConditionType.AND, 
						"Frole_name=?", option.getRoleName());
			}
			if (option.isSetUserKey()) {
				queryBuilder.addFieldCondition(ConditionType.AND, 
						"Fuser_key=?", option.getUserKey());
			}
		}
		queryBuilder.setPage(pageIndex, pageSize);
		
		RoleServiceRelationList result = new RoleServiceRelationList();
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalNum(runner.query(
					queryBuilder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(queryBuilder.getItemsSql(),
					new AbstractListHandler<RoleServiceRelation>() {
						@Override
						protected RoleServiceRelation handleRow(ResultSet rs) throws SQLException {
							return roleServiceRelationFromResultSet(rs);
						}
					}, queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
		
		return result;
	}

	private void checkRoleServiceRelation(RoleServiceRelation relation) throws ErrorInfo {
		if (relation == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"relation should not be null");
		}
		if (relation.getRoleName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"roleName should not be empty");
		}
		if (relation.getServiceKey() < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"serviceKey should not < 0");
		}
		if (0 == relation.getServiceKey() && relation.getInterfaceName().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"interfaceName should not be empty when serviceKey is 0");
		}
	}
	
	@Override
	protected void addRoleServiceRelation(TServiceCntl oCntl,
			RoleServiceRelation relation) throws ErrorInfo, TException {
		checkRoleServiceRelation(relation);
		
		if (!relation.isSetRelatedType()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"relatedType should be set");
		}
		if (!relation.isSetUserKey() || relation.getUserKey().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userKey should be set and not be empty");
		}
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getDalSetRoleServiceRelationTableName());
		addSqlBuffer.append(" SET ");
		
		PreparedFields fields = new PreparedFields();
		fields.addInt("Fservice_key", relation.getServiceKey());
		fields.addString("Finterface_name", relation.getInterfaceName());
		fields.addString("Frole_name", relation.getRoleName());
		fields.addString("Fuser_key", relation.getUserKey());
		fields.addInt("Frelated_type", relation.getRelatedType().getValue());
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateRoleServiceRelation(TServiceCntl oCntl,
			RoleServiceRelation relation) throws ErrorInfo, TException {
		checkRoleServiceRelation(relation);
		
		PreparedFields fields = new PreparedFields();
		if (relation.isSetRelatedType()) {
			fields.addInt("Frelated_type", relation.getRelatedType().getValue());
		}
		if (relation.isSetUserKey()) {
			fields.addString("Fuser_key", relation.getUserKey());
		}
		if (fields.getSize() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"no fields to update");
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getDalSetRoleServiceRelationTableName());
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fservice_key=? AND Finterface_name=? AND Frole_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(updateSqlBuffer.toString(), 
					ArrayUtils.addAll(fields.getParameters(),
							new Object[]{
								relation.getServiceKey(), 
								relation.getInterfaceName(),
								relation.getRoleName()
					}));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void deleteRoleServiceRelation(TServiceCntl oCntl,
			RoleServiceRelation relation) throws ErrorInfo, TException {
		checkRoleServiceRelation(relation);
		
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getDalSetRoleServiceRelationTableName());
		deleteSqlBuffer.append("  WHERE Fservice_key=? AND Finterface_name=? AND Frole_name=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(deleteSqlBuffer.toString(), 
						  relation.getServiceKey(), 
						  relation.getInterfaceName(),
						  relation.getRoleName());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	
	private String getDalSetDescriptionTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tdal_set_description", 0);
		} catch(SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}

	@Override
	protected void updateDalSetXml(TServiceCntl oCntl, String xml)
			throws ErrorInfo, TException {
		if (xml == null || xml.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"xml should not be null or empty");
		}
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getDalSetDescriptionTableName());
		updateSqlBuffer.append(" SET Fversion=Fversion+1,Fxml=?,Flastmodify_timestamp=?");
		updateSqlBuffer.append(" WHERE Fid=1");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(updateSqlBuffer.toString(), 
					xml, (int)(System.currentTimeMillis() / 1000));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg()); 
		}
	}

	@Override
	protected int getLastVersion(TServiceCntl oCntl) throws ErrorInfo,
			TException {
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		builder.addFields("Fversion");
		builder.addTables(getDalSetDescriptionTableName());
		builder.addFieldCondition(ConditionType.AND, "Fid=?", 1);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(
					builder.getItemsSql(), 
					new ScalarHandler<Long>(), 
					builder.getParameterList()).intValue();
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}

	@Override
	protected String getLastXml(TServiceCntl oCntl) throws ErrorInfo,
			TException {
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		builder.addFields("Fxml");
		builder.addTables(getDalSetDescriptionTableName());
		builder.addFieldCondition(ConditionType.AND, "Fid=?", 1);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(
					builder.getItemsSql(), 
					new ScalarHandler<String>(), 
					builder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}
}
