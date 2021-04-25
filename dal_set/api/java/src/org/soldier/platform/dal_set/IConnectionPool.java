package org.soldier.platform.dal_set;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {
	/**
	 * 从连接池中获取DB连接
	 * @param config DB连接的配置
	 * @return 连接的实例
	 * @throws CarSQLException
	 */
	public Connection getConnection(
			final ConnectionConfig config)throws SQLException;
	
	/**
	 *  清理连接池
	 */
	public void destory();
}
