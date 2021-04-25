package org.soldier.platform.dal_set_agent_daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.soldier.base.logger.AppLog;

public class DalSetConfigUpdateRunner {
	private File versionFile;
	private File dalSetConfigFile;
	private DalSetConfigAccess dataAccess;
	
	public DalSetConfigUpdateRunner(String versionFilePath,
			String dalSetConfigFilePath,
			String requestUrl) throws Exception {
		versionFile = new File(versionFilePath);
		if (!versionFile.exists()) {
			versionFile.createNewFile();
		}
		
		dalSetConfigFile = new File(dalSetConfigFilePath);
		if (!dalSetConfigFile.exists()) {
			dalSetConfigFile.createNewFile();
		}
		
		dataAccess = new DalSetConfigAccess(requestUrl);
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
	
	private void writeDalSetConfigFile(String dalSetConfig) throws Exception {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(dalSetConfigFile));
			writer.write(dalSetConfig);
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
			
			DalSetConfigResult result = dataAccess.getDalSetConfig(version);
			if (result == null) {
				return false;
			}
			if (result.getDalSetConfig() == null || result.getDalSetConfig().isEmpty()) {
				AppLog.d("No Update!");
				return false;
			}
			writeDalSetConfigFile(result.getDalSetConfig());
			writeVersionToFile(result.getVersion());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
