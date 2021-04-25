package org.soldier.platform.snginx_agent;

public class NginxConfig {
	private int version ;
	private String config;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	
	public void print() {
		System.out.println("version=" + version);
		System.out.println("config=" + config);
	}
}
