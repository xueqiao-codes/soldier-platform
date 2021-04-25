package org.soldier.platform.snginx_agent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.soldier.base.logger.AppLog;

public class NginxUpdateRunner {
	private File versionFile;
	private File configFile;
	private NginxConfigDataAccess dataAccess;
	
	public NginxUpdateRunner(String versionFilePath, 
			String configFilePath,
			String requestUrl) throws Exception {
		versionFile = new File(versionFilePath);
		if (!versionFile.exists()) {
			versionFile.createNewFile();
		}
		
		configFile = new File(configFilePath);
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		dataAccess = new NginxConfigDataAccess(requestUrl);
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
	
	private int readVersionFromFile() throws Exception {
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
	
	private void writeData(NginxConfig config) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(configFile));
			writer.write(config.getConfig());
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	public boolean runOnce() {
		try {
			int version = readVersionFromFile();
			
			NginxConfig result = dataAccess.getNginxConfig(version);
			if (result == null) {
				return false;
			}
			if (result.getConfig() == null || result.getConfig().isEmpty()) {
				AppLog.d("No Update!");
				return false;
			}
			writeData(result);
			writeVersionToFile(result.getVersion());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
}
