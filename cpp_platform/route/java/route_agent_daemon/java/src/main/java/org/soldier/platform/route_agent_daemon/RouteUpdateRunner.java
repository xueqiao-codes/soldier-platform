package org.soldier.platform.route_agent_daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.soldier.base.logger.AppLog;

import com.google.common.eventbus.EventBus;

public class RouteUpdateRunner {
	private File versionFile;
	private File dataFile;
	private RouteDataAccess dataAccess;
	private int lockFd;
	
	private EventBus eventBus;
	
	public RouteUpdateRunner(String versionFilePath, 
			String dataFilePath,
			String requestUrl) throws Exception {
		eventBus = new EventBus();
		
		versionFile = new File(versionFilePath);
		if (!versionFile.exists()) {
			versionFile.createNewFile();
		}
		
		dataFile = new File(dataFilePath);
		if (!dataFile.exists()) {
			dataFile.createNewFile();
		}
		dataAccess = new RouteDataAccess(requestUrl);
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	private void writeVersionToFile(int version) throws Exception {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(versionFile));
			writer.write(String.valueOf(version));
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	public int readVersionFromFile() throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(versionFile));
			String line = reader.readLine();
			if (line != null) {
				return Integer.valueOf(line);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return 0;
	}
	
	private void writeData(RouteConfig config) throws IOException {
		if (0 != writeFile(dataFile.getAbsolutePath(), config.getConfig())) {
			throw new IOException("write data failed to " + dataFile.getAbsolutePath());
		}
	}
	
	public boolean runOnce() {
		try {
			int version = readVersionFromFile();
			
			RouteConfig result = dataAccess.getRouteConfig(version);
			if (result == null) {
				return false;
			}
			if (result.getConfig() == null || result.getConfig().isEmpty()) {
				AppLog.d("No Update!");
				return false;
			}
			writeData(result);
			writeVersionToFile(result.getVersion());
			eventBus.post(result);
			
			AppLog.i("onConfigChannged version=" +  result.getVersion());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private static native int writeFile(String path,
			String content);
}
