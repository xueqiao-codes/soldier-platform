package org.soldier.platform.dal_set_agent_daemon;

public class DalSetConfigResult {
	private int version;
	private String dalSetConfig;
	
	public DalSetConfigResult() {
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDalSetConfig() {
		return dalSetConfig;
	}

	public void setDalSetConfig(String dalSetConfig) {
		this.dalSetConfig = dalSetConfig;
	}
	
	public void print() {
		System.out.println("version=" + version);
		System.out.println("dalSetConfig=" + dalSetConfig);
	}
}
