package org.soldier.platform.zookeeper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.zookeeper.IConfProvider.ConfException;
import org.soldier.platform.zookeeper.ZooKeeperConf.ZooKeeperEntry;
import org.soldier.watcher.file.FileWatcherModule;
import org.soldier.watcher.file.IFileWatcherListener;

import com.google.common.eventbus.EventBus;

public class ZooKeeperManagerFactory  implements IFileWatcherListener {
	private static File ZOOKEEPERS_CONF_FILE = new File("/data/configs/qconf/platform/zookeeper_manage/zookeepers");
	private static ZooKeeperManagerFactory INSTANCE;
	
	public static ZooKeeperManagerFactory Global() {
		if (INSTANCE == null) {
			synchronized(ZooKeeperManagerFactory.class) {
				if (INSTANCE == null) {
					INSTANCE = new ZooKeeperManagerFactory();
				}
			}
		}
		return INSTANCE;
	}
	
	private class ZooKeeperManagerConfProvider implements IConfProvider {
		private ZooKeeperEntry entry;
		
		public ZooKeeperManagerConfProvider(ZooKeeperEntry e) {
			this.entry = e;
		}
		
		@Override
		public ZooConf getActiveConf() throws ConfException {
			ZooConf conf = new ZooConf();
			conf.setAddrs(entry.getConnectString());
			conf.setSessionTimeout(entry.getSessionTimeout());
			return conf;
		}
		
		public void setEntry(ZooKeeperEntry e) {
			this.entry = e;
		}
		
		public ZooKeeperEntry getEntry() {
			return this.entry;
		}
	}
	
	private class ZooKeeperManagerHolder {
		private ZooKeeperManagerConfProvider confProvider;
		private ZooKeeperManager manager;
		
		public ZooKeeperManagerHolder(ZooKeeperEntry entry, EventBus eventBus) 
				throws ConfException, IOException {
			confProvider = new ZooKeeperManagerConfProvider(entry);
			manager = new ZooKeeperManager(eventBus, confProvider);
		}
		
		public ZooKeeperManager getManager() {
			return manager;
		}
		
		public void onConfChanged(ZooKeeperEntry entry) {
			confProvider.setEntry(entry);
			manager.onConfChanged();
		}
		
		public ZooKeeperManagerConfProvider getConfProvider() {
			return confProvider;
		}
	}
	
	private volatile ZooKeeperConf currentConf;
	private Map<String, ZooKeeperManagerHolder> managers = new HashMap<String, ZooKeeperManagerHolder>();
	
	private ConcurrentHashMap<String, String> qconfIDCNames = new ConcurrentHashMap<String, String>();
	
	private synchronized void loadConf() {
		ZooKeeperConf newConf = ZooKeeperConf.fromJson(ZOOKEEPERS_CONF_FILE);
		if (newConf == null) {
			return ;
		}
		
		for (Entry<String, ZooKeeperManagerHolder> e : managers.entrySet()) {
			ZooKeeperEntry oldEntry = e.getValue().getConfProvider().getEntry();
			ZooKeeperEntry newEntry = newConf.getEntry(e.getKey());
			if (oldEntry != null && newEntry != null) {
				if (!oldEntry.getConnectString().equalsIgnoreCase(newEntry.getConnectString())) {
					AppLog.w("[NOTICE]ZooKeeper " + e.getKey() + "changed! connectString from "
							+ oldEntry.getConnectString() + " to " + newEntry.getConnectString());
					e.getValue().onConfChanged(newEntry);
				}
			}
		}
		
		for (ZooKeeperEntry newEntry : newConf.getEntries()) {
			String qconfIDCName = buildQConfIDCName(newEntry);
			if (newEntry.getKey() != null && qconfIDCName != null) {
				qconfIDCNames.put(newEntry.getKey(), qconfIDCName);
			}
		}
		
		currentConf = newConf;
	}
	
	private String buildQConfIDCName(ZooKeeperEntry entry) {
		if (entry.getQconfSuffix() == null || entry.getQconfSuffix().isEmpty()) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder(entry.getQconfSuffix().length() + 10);
		builder.append(entry.getQconfSuffix());
		return builder.toString();
	}
	
	private ZooKeeperManagerFactory() {
		FileWatcherModule.Instance().addWatchFile(ZOOKEEPERS_CONF_FILE, this);
		loadConf();
	}
	
	public ZooKeeperManager get(String key) throws ConfException, IOException {
		return get(key, null);
	}
	
	public synchronized ZooKeeperManager get(String key, EventBus eventBus) throws ConfException, IOException {
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("key should not be null or empty");
		}
		
		ZooKeeperManagerHolder holder = managers.get(key);
		if (holder != null) {
			return holder.getManager();
		}
		
		if (currentConf == null){
			throw new ConfException("configuration is not init!");
		}
		ZooKeeperEntry entry = currentConf.getEntry(key);
		if (entry == null) {
			throw new ConfException("no configuration is found for key=" + key);
		}
		holder = new ZooKeeperManagerHolder(entry, eventBus);
		managers.put(key, holder);
		return holder.getManager();
	}
	
	public String getQconfIDCName(String key) throws ConfException {
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("key should not be null or empty");
		}
		
		String qconfIDCName = qconfIDCNames.get(key);
		if (qconfIDCName == null) {
			throw new IllegalArgumentException("configuration is not found for key=" + key);
		}
		
		return qconfIDCName;
	}

	@Override
	public void onHandleFileChanged(File filePath) {
		loadConf();
	}
}
