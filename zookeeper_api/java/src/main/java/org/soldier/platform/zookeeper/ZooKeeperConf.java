package org.soldier.platform.zookeeper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.soldier.base.logger.AppLog;

import com.google.gson.Gson;

public class ZooKeeperConf {
	public static class ZooKeeperEntry {
		private String key;
		private String connectString;
		private int sessionTimeout;
		private String qconfSuffix;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getConnectString() {
			return connectString;
		}
		public void setConnectString(String connectString) {
			this.connectString = connectString;
		}
		
		public int getSessionTimeout() {
			return sessionTimeout;
		}
		public void setSessionTimeout(int sessionTimeout) {
			this.sessionTimeout = sessionTimeout;
		}
		
		public String getQconfSuffix() {
			return qconfSuffix;
		}
		public void setQconfSuffix(String qconfSuffix) {
			this.qconfSuffix = qconfSuffix;
		}
	}
	
	private List<ZooKeeperEntry> zookeepers = new ArrayList<ZooKeeperEntry>();
	private Map<String, ZooKeeperEntry> indexMap = new HashMap<String, ZooKeeperEntry>();
	
	private ZooKeeperConf() {
	}
	
	private void buildIndex() {
		indexMap.clear();
		for (ZooKeeperEntry entry : zookeepers) {
			if (entry.getKey() != null && !entry.getKey().isEmpty()) {
				indexMap.put(entry.getKey(), entry);
			}
		}
	}
	
	public List<ZooKeeperEntry> getEntries() {
		return zookeepers;
	}
	
	public ZooKeeperEntry getEntry(String key) {
		return indexMap.get(key);
	}
	
	public static ZooKeeperConf fromJson(File jsonFile) {
		ZooKeeperConf conf = null;
		int retryCount = 3;
		while((retryCount--) >= 0) {
			FileReader reader = null;
			try {
				reader = new FileReader(jsonFile);
				conf = new Gson().fromJson(reader, ZooKeeperConf.class);
				break;
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
					}
				}
			}
		}
		
		if (conf != null) {
			conf.buildIndex();
		}
		
		return conf;
	}
	
}
