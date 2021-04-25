package org.soldier.platform.db_helper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface D2OHandler <T> {
	public void prepare(ResultSet rs) throws SQLException;
	
	public T toObject(ResultSet rs) throws SQLException;
	
	public void finish();
}
