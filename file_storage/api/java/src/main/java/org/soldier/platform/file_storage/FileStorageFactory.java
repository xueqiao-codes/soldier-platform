package org.soldier.platform.file_storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.file_storage.impl.FileStorageByDao;
import org.soldier.platform.file_storage.impl.FileStorageByOss;


public class FileStorageFactory {
	private static FileStorageFactory sInstance;
	public static FileStorageFactory getInstance() {
		if (sInstance == null) {
			synchronized(FileStorageFactory.class) {
				if (sInstance == null) {
					sInstance = new FileStorageFactory();
				}
			}
		}
		return sInstance;
	}
	
	private Map<String, FileStorage> storageMap;
	private ScheduledExecutorService executorService;
	
	protected FileStorageFactory() {
		storageMap = new ConcurrentHashMap<String, FileStorage>();
	}
	
	/**
	 * 获取分布式文件存储的句柄
	 * @param storageKey 文件存储的Key, 填写配置中心申请的内容
	 * @return 对应的文件存储句柄
	 * @throws FileStorageException
	 */
	public synchronized FileStorage getFileStorage(String storageKey) {
		if (storageKey == null || storageKey.isEmpty()) {
			return null;
		}
		
		if (executorService == null) {
			executorService = new ScheduledThreadPoolExecutor(1);
		}
		
		FileStorage storage = storageMap.get(storageKey);
		if (storage == null) {
			storage = createStorage(storageKey, executorService);
			storageMap.put(storageKey, storage);
			
			AppLog.i("create file storage for key=" + storageKey + ", storageType="
			        + storage.getClass().getSimpleName());
		}
		return storage;
	}
	
	public void destroy() {
		storageMap.clear();
		if (executorService != null) {
			executorService.shutdownNow();
		}
	}
	
	
	private FileStorage createStorage(String storageKey, ScheduledExecutorService executorService) {
	    String envTypeValue = System.getenv("FILE_STORAGE_TYPE");
	    if (envTypeValue != null && "DAO".equalsIgnoreCase(envTypeValue)) {
	        return new FileStorageByDao(storageKey, executorService);
	    }	    
	    return new FileStorageByOss(storageKey, executorService);
	}
	
}
