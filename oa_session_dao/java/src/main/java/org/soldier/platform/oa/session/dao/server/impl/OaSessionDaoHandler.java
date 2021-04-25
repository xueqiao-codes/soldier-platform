package org.soldier.platform.oa.session.dao.server.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.oa.session.dao.TSession;
import org.soldier.platform.oa.session.dao.server.OaSessionDaoAdaptor;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

public class OaSessionDaoHandler extends OaSessionDaoAdaptor {
	private String roleName;
	private int expireSeconds;
	
	@Override
	public int InitApp(Properties props) {
		roleName = props.getProperty("roleName", "role_oa_session");
		expireSeconds = Integer.parseInt(props.getProperty("expireSeconds", "1800"));
		
		System.out.println("roleName=" + roleName);
		System.out.println("expireSecons=" + expireSeconds);
		
		try {
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}
	
	private String getSessionTableName(int userId) throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "toa_user_session", userId);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	@Override
	protected void updateSession(TServiceCntl oCntl, TSession session)
			throws ErrorInfo, TException {
		if (session == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"session should not be null");
		}
		if (!session.isSetUserId() || session.getUserId() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userId should not <= 0");
		}
		if (!session.isSetSessionKey()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"sessionKey shoud set");
		}
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Fsession_key", session.getSessionKey());
		if (session.isSetUserName()) {
            fields.addString("Fuser_name", session.getUserName());
        } else {
            fields.addString("Fuser_name", "");
        }
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("INSERT INTO ");
		updateSqlBuffer.append(getSessionTableName(session.getUserId()));
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(", Fuser_id=");
		updateSqlBuffer.append(session.getUserId());
		updateSqlBuffer.append(" ON DUPLICATE KEY UPDATE ");
		updateSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, session.getUserId()));
		
		try {
			runner.update(updateSqlBuffer.toString(), 
					ArrayUtils.addAll(fields.getParameters(), fields.getParameters()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected List<TSession> getSession(TServiceCntl oCntl, int userId, String userName)
			throws ErrorInfo, TException {
		if (userId <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userId should not <= 0");
		}
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.addFields("Fuser_id", "Fsession_key");
		builder.addTables(getSessionTableName(userId));
		builder.addFieldCondition(ConditionType.AND, "Fuser_id=?", userId);
		if (StringUtils.isNotEmpty(userName)) {
		    builder.addFieldCondition(ConditionType.AND, "Fuser_name=?", userName);
		}
		builder.addFieldCondition(ConditionType.AND, "Flastmodify_timestamp > (UNIX_TIMESTAMP(NOW()) - ?)", expireSeconds);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, userId));
		
		try {
			return runner.query(builder.getItemsSql(), new AbstractListHandler<TSession>(){
				@Override
				protected TSession handleRow(ResultSet rs) throws SQLException {
					TSession result = new TSession();
					result.setUserId(rs.getInt("Fuser_id"));
					result.setSessionKey(rs.getString("Fsession_key"));
					return result;
				}
			}, builder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void deleteSession(TServiceCntl oCntl, int userId, String userName)
			throws ErrorInfo, TException {
		if (userId <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userId should not <= 0");
		}
		
		StringBuffer deleteSqlBuffer = new StringBuffer(128);
		deleteSqlBuffer.append("DELETE FROM ");
		deleteSqlBuffer.append(getSessionTableName(userId));
		deleteSqlBuffer.append(" WHERE Fuser_id=?");
		if (StringUtils.isNotEmpty(userName)) {
		    deleteSqlBuffer.append(" AND Fuser_name=?");
		}
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, userId));
		
		try {
		    if (StringUtils.isNotEmpty(userName)) {
		        runner.update(deleteSqlBuffer.toString(), userId, userName);
		    } else {
		        runner.update(deleteSqlBuffer.toString(), userId);
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
}
