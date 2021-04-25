package org.soldier.platform.route_agent_daemon;

public class RouteConfig {
	private int version;
	private String config;
	
	public int getVersion() {
		return this.version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getConfig() {
		return this.config;
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
	
	public void print() {
		System.out.println("version=" + version);
		if (config != null) {
			System.out.println("config=" + config);
		}
	}
	
}
