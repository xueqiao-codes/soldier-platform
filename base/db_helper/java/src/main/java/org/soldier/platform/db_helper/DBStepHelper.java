package org.soldier.platform.db_helper;

import javax.sql.DataSource;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public abstract class DBStepHelper<T> extends DBUpdatorHelperBase<T> {

	public DBStepHelper(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected void onPreparedDataBefore() throws ErrorInfo, Exception {
	}

	@Override
	protected void onPrepareDataFinished() throws ErrorInfo, Exception {
		
	}

	@Override
	protected void onUpdateFinished() throws ErrorInfo, Exception {
	}

	@Override
	protected void onFinally() throws Exception {
	}
}
