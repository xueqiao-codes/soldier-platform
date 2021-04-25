package org.soldier.platform.db_helper.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.soldier.platform.db_helper.ResultHook;
import org.soldier.platform.db_helper.protocols.FMapping;

public class FMHandler<T> {
	protected D2OHandlerByMapping<T> handler;
	protected ResultHook<T> hook;
	
	public FMHandler(Class<T> clazz) {
		handler = new D2OHandlerByMapping<T>(clazz, new FMapping());
	}
	
	public FMHandler(Class<T> clazz, ResultHook<T> hook) {
		this(clazz);
		this.hook = hook;
	}
	
	protected T getResult(ResultSet rs) throws SQLException {
		T obj = handler.toObject(rs);
		if (hook != null) {
			hook.handle(obj, rs);
		}
		return obj;
	}
}
