package org.soldier.platform.dal_set_agent_daemon;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.PathService;
import org.soldier.base.logger.AppLog;

public class DaemonMain {
	private final static String DalSetAgentInstallDirName= "dal_set";
	
	protected static void checkSingleInstance() {
		String appId = "dal_set_agent_daemon";
		try {
			JUnique.acquireLock(appId);
		} catch (AlreadyLockedException e) {
			System.err.println("process is already started!");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		AppLog.init("dal_set_agent_daemon");
		AppLog.d("dal_set_agent_daemon started begin!");
		
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
		
		String dalSetVersionFilePath = PathService.getSoldierHome() + "/" 
						+ DalSetAgentInstallDirName + "/data/version";
		String dalSetConfigFilePath = PathService.getSoldierHome() + "/" 
						+ DalSetAgentInstallDirName + "/config/dal_set.xml";
		
		DalSetConfigUpdateRunner runner = null;
		try {
			 runner = new DalSetConfigUpdateRunner(
					dalSetVersionFilePath, dalSetConfigFilePath, serverUrl);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(4);
		}
		
		while(true) {
			runner.runOnce();
			
			int sleepSeconds = timeDelaySeconds + (RandomUtils.nextInt() % timeDelaySeconds);
			try {
				Thread.sleep(sleepSeconds * 1000);
			} catch (InterruptedException e) {
			}
		}
	}

}
