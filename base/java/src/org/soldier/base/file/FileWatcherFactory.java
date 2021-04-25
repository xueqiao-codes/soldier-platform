package org.soldier.base.file;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.soldier.base.LibraryLoader;
import org.soldier.base.OSHelper;
import org.soldier.base.PathService;

public class FileWatcherFactory {
	private static ScheduledExecutorService sFileWatcherecutorService;
	public static IFileWatcher getFileWatcher() {
		if (OSHelper.isWindows()) {
			LibraryLoader.addSearchPath("C:\\");
		} else {
			LibraryLoader.addSearchPath(PathService.getSoldierHome() + "/lib");
		}
		
		synchronized(FileWatcherFactory.class) {
			if (sFileWatcherecutorService == null) {
				sFileWatcherecutorService = Executors.newScheduledThreadPool(3);
			}
		}
		
		return new JNotifyFileWatcher(sFileWatcherecutorService);
	}
}
