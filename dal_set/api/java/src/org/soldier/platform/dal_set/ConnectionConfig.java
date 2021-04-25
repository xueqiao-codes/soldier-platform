package org.soldier.platform.dal_set;

import java.util.Map;

import org.apache.log4j.Logger;

public abstract class ConnectionConfig {
	private static Logger logger = Logger.getLogger(ConnectionConfig.class);
	
	protected String host = "";
	protected int port = 3306;
	protected String userName = "";
	protected String passwd = "";
	protected String dbName = "";
	
	abstract public String getDbType();
	abstract public String getDriverClassName();
	abstract public Map<String, String> getOptions();
	abstract public String getJDBCUrl();
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer(128);
		buffer.append(getJDBCUrl());
		buffer.append(":userName=");
		buffer.append(this.userName);
		buffer.append("&passwd=");
		buffer.append(this.passwd);
		return buffer.toString();
	}
	
	public boolean CheckConfig(){
		if(this.userName == null || this.userName.isEmpty()){
			logger.error("userName should not be null or empty");
			return false;
		}
		if(this.passwd == null){
			logger.error("passwd should not be null");
			return false;
		}
		if(this.host == null || this.host.isEmpty()){
			logger.error("host should not be null or empty");
			return false;
		}
		if(this.dbName == null || this.dbName.isEmpty()){
			logger.error("dbName should not be null or empty");
			return false;
		}
		if(this.port <= 0 || this.port > 65532){
			logger.error("Illegal port value " + this.port);
			return false;
		}
		return true;
	}
}
	
