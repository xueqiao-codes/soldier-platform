package org.soldier.platform.db_helper;

import java.sql.ResultSet;

public interface RS2O<T> {
	public T fromResultSet(ResultSet rs) throws Exception ;
}
