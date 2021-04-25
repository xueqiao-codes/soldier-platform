package org.soldier.watcher.file;

import java.io.File;

public interface IFileWatcherListener {
	public void onHandleFileChanged(File filePath);
}
