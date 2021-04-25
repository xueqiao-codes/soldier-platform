package org.soldier.watcher.file;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.OSHelper;
import org.soldier.watcher.file.swig.FileWatcherSwig;

public abstract class FileWatcherModule {
	private static FileWatcherModule sInstance;
	
	public static FileWatcherModule Instance() {
		if (sInstance == null) {
			synchronized(FileWatcherModule.class) {
				if (sInstance == null) {
					if (OSHelper.isWindows()) {
						sInstance = new FileWatcherModuleWin();
					} else {
						sInstance = new FileWatcherModuleLinux();
					}
				}
			}
		}
		return sInstance;
	}
	
	public void initNativeLog(String logModuleName) {
	    if (StringUtils.isNotEmpty(logModuleName)) {
	        FileWatcherSwig.initNativeLogger(logModuleName);
	    }
	}
	
	public abstract void addWatchFile(final File file, IFileWatcherListener listener);
	public abstract void removeWatchFile(final File file);
	
	
	private static class FileWatcherModuleWin extends FileWatcherModule {
		@Override
		public void addWatchFile(final File file, IFileWatcherListener listener) {
		}

		@Override
		public void removeWatchFile(final File file) {
		}
	}
	
	private static class FileWatcherModuleLinux extends FileWatcherModule implements IFileWatcherListener {
		private ConcurrentHashMap<String, FileWatcherHolder> mListenerMap;
		private FileWatcherHolder mGlobalListener;
		
		@Override
		public void onHandleFileChanged(File file) {
			FileWatcherHolder holder = mListenerMap.get(file.getAbsolutePath());
			if (holder != null) {
				holder.onFileChanged(file.getAbsolutePath());
			}
		}
		
		public FileWatcherModuleLinux() {
			LibraryLoader.init();
			Runtime.getRuntime().addShutdownHook(new Thread() {  
	            public void run() {  
	                FileWatcherSwig.destroy();
	            }  
	        });  
			
			mListenerMap = new ConcurrentHashMap<String, FileWatcherHolder>();
			mGlobalListener = new FileWatcherHolder(this);
			FileWatcherSwig.init(mGlobalListener);
		}

		@Override
		public void addWatchFile(File file, IFileWatcherListener listener) {
			if (listener == null) {
				throw new IllegalArgumentException("listener should not be null");
			}
			if (file == null) {
				throw new IllegalArgumentException("file should not be null");
			}
			if (!file.exists() && !file.getAbsolutePath().startsWith("/data/configs/qconf/")) {
				throw new IllegalArgumentException(file.getAbsolutePath() + " is not existed!");
			}
			if (mListenerMap.containsKey(file.getAbsolutePath())) {
				throw new IllegalArgumentException(file.getAbsolutePath() + " is already been watched!");
			}
			
			FileWatcherSwig.addWatchFile(file.getAbsolutePath());
			mListenerMap.put(file.getAbsolutePath(), new FileWatcherHolder(listener));
		}

		@Override
		public void removeWatchFile(File file) {
			FileWatcherSwig.removeWatchFile(file.getAbsolutePath());
			mListenerMap.remove(file.getAbsolutePath());
		}

	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("please input the watch file path");
			System.exit(1);
		}
		
		for (int index = 0; index < args.length; ++index) {
			FileWatcherModule.Instance().addWatchFile(new File(args[index]), new IFileWatcherListener() {
				@Override
				public void onHandleFileChanged(File file) {
					System.out.println(file.getAbsolutePath() + " changed!");
				}
			});
		}
		
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
