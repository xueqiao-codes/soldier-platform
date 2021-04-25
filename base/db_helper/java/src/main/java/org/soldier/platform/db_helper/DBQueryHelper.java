package org.soldier.platform.db_helper;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

public abstract class DBQueryHelper <T> {
	
	private DataSource dataSource;
	
	public DBQueryHelper(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	protected abstract T onQuery(Connection conn) throws Exception;
	
	public T query() throws ErrorInfo {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return onQuery(conn);
		} catch (ErrorInfo e) {
		    throw e;
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
					, "Server Inner Error!" + e.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
}
