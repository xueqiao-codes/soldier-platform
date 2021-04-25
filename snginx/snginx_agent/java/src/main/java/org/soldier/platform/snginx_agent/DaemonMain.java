package org.soldier.platform.snginx_agent;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.PathService;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;

public class DaemonMain {
	private final static String AgentInstallDirName= "snginx";
	
	protected static void checkSingleInstance() {
		String appId = "snginx_agent";
		try {
			JUnique.acquireLock(appId);
		} catch (AlreadyLockedException e) {
			System.err.println("process is already started!");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		AppLog.init("snginx_agent");
		
		if (args.length < 1) {
			System.err.println("please input the config.properties path");
			System.exit(1);
		}
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(args[0])));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
		
		String serverUrl = properties.getProperty("serverUrl", "").trim();
		if (serverUrl.isEmpty()) {
			System.err.println("please configure the serverUrl parameters");
			System.exit(3);
		}
		
		int timeDelaySeconds = Integer.valueOf(properties.getProperty("timeDelaySeconds", "15").trim());
		System.out.println("serverUrl=" + serverUrl);
		System.out.println("timeDelaySeconds=" + timeDelaySeconds);
		
		checkSingleInstance();
		
		String snginxDataDir = System.getenv("SNGINX_DATA_DIR");
		if (StringUtils.isEmpty(snginxDataDir)) {
		    snginxDataDir = PathService.getSoldierHome() + "/" + AgentInstallDirName + "/data";
		}
		
		NginxUpdateRunner runner = null;
		try {
			runner = new NginxUpdateRunner(
					snginxDataDir + "/version", 
					snginxDataDir + "/web_servers.conf",
					serverUrl);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(5);
		}
		INginxConfigLoader loader = null;
		
		if ("true".equalsIgnoreCase(System.getenv("SNGINX_LOAD_BY_HTTP"))) {
		    loader = new HttpReqNginxConfigLoader();
		} else {
		    loader = new SNginxCommandConfigLoader(
                    PathService.getSoldierHome() + "/" + AgentInstallDirName + "/snginx");
		}
		
		AppLog.i("snginx_agent started!");
		AppLogStream.redirectSystemPrint();
		while (true) {
			if (runner.runOnce()) {
				try {
				    AppLog.i("config changed, notify nginx to reload...");
					loader.load();
				} catch (Exception e) {
					AppLog.e(e.getMessage(), e);
				}
			}
			try {
                Thread.sleep(timeDelaySeconds * 1000);
            } catch (InterruptedException e) {}
		}
	}
}
