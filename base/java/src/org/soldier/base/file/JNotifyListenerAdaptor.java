package org.soldier.base.file;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.contentobjects.jnotify.JNotifyListener;

class JNotifyListenerAdaptor implements JNotifyListener {
	private IFileListener listener;
	
	private ScheduledExecutorService executorService;
	private Map<String, FileModifyNotificationTask> futureExecutorMap;
	private Lock lock = new ReentrantLock();
	
	private class FileModifyNotificationTask implements Runnable {
		private int watchId;
		private String rootPath;
		private String name;
		private boolean isRemoved;
		
		public void setRemoved(boolean removed) {
			this.isRemoved = removed;
		}
		
		public FileModifyNotificationTask(int watchId, 
				String rootPath, String name) {
			this.watchId = watchId;
			this.rootPath = rootPath;
			this.name = name;
			this.isRemoved = false;
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer(32);
			buffer.append(watchId);
			buffer.append("_");
			buffer.append(rootPath);
			buffer.append("/");
			buffer.append(name);
			return buffer.toString();
		}
		
		@Override
		public void run() {
			lock.lock();
			if (isRemoved) {
				lock.unlock();
				return ;
			}
			
			futureExecutorMap.remove(this.toString());
			lock.unlock();
			listener.fileModified(watchId, rootPath, name);
		}
		
	}
	
	public JNotifyListenerAdaptor(IFileListener listener, 
			ScheduledExecutorService executorService) {
		this.listener = listener;
		this.executorService = executorService;
		this.futureExecutorMap = new HashMap<String, FileModifyNotificationTask>(5);
	}
	
	@Override
	public void fileCreated(int watchId, String rootPath, String name) {
		listener.fileCreated(watchId, rootPath, name);
	}

	@Override
	public void fileDeleted(int watchId, String rootPath, String name) {
		listener.fileDeleted(watchId, rootPath, name);
	}

	@Override
	public void fileModified(int watchId, String rootPath, String name) {
		FileModifyNotificationTask newTask = 
				new FileModifyNotificationTask(watchId, rootPath, name);
		String key = newTask.toString();
		
		lock.lock();
		FileModifyNotificationTask oldTask =  futureExecutorMap.get(key);
		if (oldTask != null) {
			oldTask.setRemoved(true);
		} 
		futureExecutorMap.put(key, newTask);
		lock.unlock();
		
		executorService.schedule(newTask, 20, TimeUnit.MILLISECONDS);
	}

	@Override
	public void fileRenamed(int watchId, String rootPath, String oldName, String newName) {
		listener.fileRenamed(watchId, rootPath, oldName, newName);
	}


}
