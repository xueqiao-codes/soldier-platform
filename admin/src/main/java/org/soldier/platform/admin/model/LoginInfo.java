package org.soldier.platform.admin.model;

public class LoginInfo {
	private String session = "";
	private String secretKey = "";
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
