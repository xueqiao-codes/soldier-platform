package org.soldier.platform.dal_set;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DalSetDataSource implements DataSource {
	private final String serviceName;
	private final String roleName;
	private final long setKey;
	private final boolean isReadOnly;
	
	public DalSetDataSource(final String roleName,
							final String serviceName,
							final boolean isReadOnly,
							final long setKey){
		this.serviceName = serviceName;
		this.roleName = roleName;
		this.setKey = setKey;
		this.isReadOnly = isReadOnly;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return DalSetProxy.getInstance().getConnection(roleName, serviceName, isReadOnly, setKey);
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		throw new SQLException("Should not use this function");
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		throw new SQLException("Unsupported getLoginTimeout");
	}

	//@Override is not supported in lower java version than 1.7
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
