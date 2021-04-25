package org.soldier.platform.db_helper;

import javax.sql.DataSource;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public abstract class DBTransactionHelper<T> extends DBUpdatorHelperBase<T> {
	
	public DBTransactionHelper(DataSource dataSource) {
		super(dataSource);
	}

	private boolean success = false;
	
	@Override
	protected void onPreparedDataBefore() throws ErrorInfo, Exception {
		getConnection().setAutoCommit(false);
	}
	
	@Override
	protected void onPrepareDataFinished() {
	}
	
	protected void onCommitFinished() throws Exception {
	}
	protected void onRollbackFinished() throws Exception {
	}
	protected void onTransactionOperationException() throws Exception {
	}

	@Override
	protected void onUpdateFinished() throws ErrorInfo, Exception {
		success = true;
	}
	
	protected void onFinally() throws Exception {
		try {
			if (success) {
				getConnection().commit();
			} else {
				getConnection().rollback();
			}
		} catch (Throwable e) {
			onTransactionOperationException();
			return ;
		}
		
		if (success) {
			onCommitFinished();
		} else {
			onRollbackFinished();
		}
	}

}
