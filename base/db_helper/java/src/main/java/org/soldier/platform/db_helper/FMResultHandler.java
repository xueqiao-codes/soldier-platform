package org.soldier.platform.db_helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.soldier.platform.db_helper.handler.FMHandler;

public class FMResultHandler<T> extends FMHandler<T> implements ResultSetHandler<T>{
	public FMResultHandler(Class<T> clazz) {
		super(clazz);
	}
	
	public FMResultHandler(Class<T> clazz, ResultHook<T> hook) {
		super(clazz, hook);
	}

	@Override
	public T handle(ResultSet rs) throws SQLException {
		handler.prepare(rs);
		try {
			return getResult(rs);
		} finally {
			handler.finish();
		}
	}

}
