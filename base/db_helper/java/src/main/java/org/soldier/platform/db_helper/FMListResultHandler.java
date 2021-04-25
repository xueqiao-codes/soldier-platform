package org.soldier.platform.db_helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.soldier.platform.db_helper.handler.FMHandler;

public class FMListResultHandler<T> extends FMHandler<T> implements ResultSetHandler<List<T>>{
	public FMListResultHandler(Class<T> clazz) {
		super(clazz);
	}
	
	public FMListResultHandler(Class<T> clazz, ResultHook<T> hook) {
		super(clazz, hook);
	}

	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		handler.prepare(rs);
		try {
			List<T> rows = new ArrayList<T>();
			while (rs.next()) {
				rows.add(getResult(rs));
			}
			return rows;
		} finally {
			handler.finish();
		}
	}

}
