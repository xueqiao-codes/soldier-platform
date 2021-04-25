package org.soldier.platform.db_helper;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

public abstract class DBUpdatorHelperBase<T> implements IDBUpdateHelper<T> {
	
	private DataSource dataSource;
	private Connection conn;
	
	protected DBUpdatorHelperBase(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	protected abstract void onPreparedDataBefore() throws ErrorInfo, Exception;
	protected abstract void onPrepareDataFinished() throws ErrorInfo, Exception;
	protected abstract void onUpdateFinished() throws ErrorInfo, Exception;
	protected void onFinally() throws Exception {}
	protected void onException() {}
	
	@Override
	public IDBUpdateHelper<T> execute() throws ErrorInfo {
		try {
			conn = dataSource.getConnection();
			try {
				onPreparedDataBefore();
				onPrepareData();
				onPrepareDataFinished();
				onUpdate();
				onUpdateFinished();
			} finally {
				onFinally();
			}
		} catch (ErrorInfo ei) {
			onException();
			throw ei;
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			onException();
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
					, ErrorCodeInner.SERVER_INNER_ERROR.getErrorMsg());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return this;
	}
}
