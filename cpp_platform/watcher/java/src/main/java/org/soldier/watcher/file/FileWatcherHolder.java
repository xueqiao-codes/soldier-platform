package org.soldier.watcher.file;

import java.io.File;

import org.soldier.base.logger.AppLog;
import org.soldier.watcher.file.swig.IFileListener;

public class FileWatcherHolder extends IFileListener {
	private IFileWatcherListener mListener;
	
	public FileWatcherHolder(IFileWatcherListener listener) {
		this.mListener = listener;
	}
	
	@Override
	public void onFileChanged(String file_path) {
	    try {
	    	if (mListener != null) {
	    		mListener.onHandleFileChanged(new File(file_path));
	    	}
	    } catch (Throwable e) {
	    	AppLog.e(e.getMessage(), e);
	    }
	}
}
