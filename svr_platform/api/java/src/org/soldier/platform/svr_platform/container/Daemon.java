package org.soldier.platform.svr_platform.container;

import java.util.Properties;

public class Daemon {
	static Properties sDaemonProperties = null;
	public static final Properties getProperties(){
		return sDaemonProperties;
	}
}
