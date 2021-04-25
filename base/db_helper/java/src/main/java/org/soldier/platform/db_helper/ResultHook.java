package org.soldier.platform.db_helper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author wileywang
 *
 * @param <T>
 */
public interface ResultHook<T> {
	public void handle(T obj, ResultSet rs) throws SQLException;
}
