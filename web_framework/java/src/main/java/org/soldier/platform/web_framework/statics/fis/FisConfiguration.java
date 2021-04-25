package org.soldier.platform.web_framework.statics.fis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.soldier.base.logger.AppLog;


public class FisConfiguration {
	private static FisConfiguration sInstance;
	
	public static FisConfiguration getInstance() {
		if (sInstance == null) {
			synchronized(FisConfiguration.class) {
				if (sInstance == null) {
					sInstance = new FisConfiguration();
				}
			}
		}
		return sInstance;
	}
	
	private String defaultHost;
	private String defaultPrefixPath;
	private Vector<MapJson> mapJsonList;
	
	private  FisConfiguration() {
		mapJsonList = new Vector<MapJson>();
	}
	
	public void setDefaultHost(String defaultHost) {
		if (defaultHost == null || defaultHost.isEmpty()) {
			this.defaultHost = null;
		}
		if (defaultHost.endsWith("/")) {
			throw new IllegalArgumentException("host should not end with /");
		}
		this.defaultHost = defaultHost;
	}
	
	public String getDefaultHost() {
		return this.defaultHost;
	}
	
	public void addMapJson(MapJson mapJson) {
		mapJsonList.add(mapJson);
	}
	
	public void addMapJsonFromFile(String path) {
		File jsonFile = new File(path);
		if (jsonFile.isFile() && jsonFile.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(jsonFile));
				addMapJson(new MapJson(reader));
			} catch (Throwable e) {
				AppLog.e("File " + path + ", exception " + e.getMessage(), e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						AppLog.e(e.getMessage(), e);
					}
				}
			}
		} else {
			AppLog.e("File " + path + " is not exist for MapJson");
		}
	}
	
	public List<MapJson> getMapJsonList() {
		return mapJsonList;
	}
	
	public void setDefaultPrefixPath(String prefixPath) {
		if (prefixPath == null || prefixPath.isEmpty()) {
			this.defaultPrefixPath = null;
			return ;
		}
		if (!prefixPath.startsWith("/")) {
			throw new IllegalArgumentException("prefixPath should start with /");
		}
		if (prefixPath.endsWith("/")) {
			throw new IllegalArgumentException("prefixPath should not end with /");
		}
		this.defaultPrefixPath = prefixPath;
	}
	
	public String getDefaultPrefixPath() {
		return defaultPrefixPath;
	}
}
