package org.soldier.platform.web.mitty;

public class Variables {
	public static String getMittyHome() {
		return System.getProperty("mitty.home");
	}
	
	public static String getWebAppTempDir() {
		return System.getProperty("mitty.webapp.tmpdir");
	}
}
