package org.soldier.platform.route_agent_daemon;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.PathService;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.platform.route.TickWorker;

public class DaemonMain {
	private final static String RouteAgentInstallDirName= "route_agent";
	
	private final static String routeAgentToolPath 
		=  PathService.getSoldierHome() + "/" + RouteAgentInstallDirName + "/bin/route_agent_tool2";
	
	public static void loadData(String dataPath) {
		AppLog.d("load data " + dataPath);
		
		ProcessBuilder builder = new ProcessBuilder(routeAgentToolPath);
		builder.command(routeAgentToolPath, "load", dataPath);
		try {
			Process process = builder.start();
			
			int result = 0;
			try {
				result = process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (result != 0) {
				AppLog.e(StringUtils.join(builder.command().toArray(), " ") + " failed! result=" + result);
				System.exit(-1);
			}
			
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
			System.exit(-1);
		}
	}
	
	protected static void checkSingleInstance() {
		String appId = "route_agent_daemon";
		try {
			JUnique.acquireLock(appId);
		} catch (AlreadyLockedException e) {
			System.err.println("process is already started!");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		AppLog.init("route_agent_daemon");
		AppLog.d("route_agent_daemon started begin!");
		
		boolean disableTickWorker = "1".equalsIgnoreCase(
		        System.getenv("DISABLE_TICK_WORKER"));
		
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
		
		String dataPath = PathService.getSoldierHome() + "/" + RouteAgentInstallDirName + "/data/route2.data";
		RouteConfig initRouteConfig = new RouteConfig();
		
		RouteUpdateRunner runner = null;
		TickWorker tickWorker = null;
		try {
			runner = new RouteUpdateRunner(
					PathService.getSoldierHome() + "/" + RouteAgentInstallDirName + "/data/version", 
					dataPath,
					serverUrl);
			LibraryLoader.init();
			
			int version = runner.readVersionFromFile();
			if (version > 0) {
				loadData(dataPath);
				initRouteConfig.setVersion(version);
				initRouteConfig.setConfig(FileUtils.readFileToString(new File(dataPath), "UTF-8"));
			}
			
			if (!disableTickWorker) {
			    tickWorker = new TickWorker(initRouteConfig);
			    runner.getEventBus().register(tickWorker);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(5);
		}
		
		AppLogStream.redirectSystemPrint();
		
		if (!disableTickWorker) {
		    tickWorker.startWork();
		}
		while (true) {
			if (runner.runOnce()) {
				try {
					loadData(dataPath);
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
