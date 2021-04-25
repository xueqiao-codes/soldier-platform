package org.soldier.base.file;

import java.util.concurrent.ScheduledExecutorService;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;

class JNotifyFileWatcher implements IFileWatcher {
	private ScheduledExecutorService executorService;
	public JNotifyFileWatcher(ScheduledExecutorService executorService) {
		this.executorService = executorService;
	}
	
	@Override
	public int addWatch(String path, 
			int watchTypeMask, 
			boolean watchSubTree,
			IFileListener listener) throws FileWatcherException {
		int jnotifyMask = 0;
		if ((watchTypeMask & FILE_CREATED) > 0) {
				jnotifyMask |= JNotify.FILE_CREATED;
		} 
		if ((watchTypeMask & FILE_DELETED) > 0) {
			jnotifyMask |= JNotify.FILE_DELETED;
		}
		if ((watchTypeMask & FILE_MODIFIED) > 0) {
			jnotifyMask |= JNotify.FILE_MODIFIED;
		}
		if ((watchTypeMask & FILE_RENAMED) > 0) {
			jnotifyMask |= JNotify.FILE_RENAMED;
		}
		try {
			return JNotify.addWatch(path, jnotifyMask, watchSubTree, 
					new JNotifyListenerAdaptor(listener, executorService));
		} catch (JNotifyException e) {
			throw new FileWatcherException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public void removeWatch(int watchID) {
		try {
			JNotify.removeWatch(watchID);
		} catch (JNotifyException e) {
			e.printStackTrace();
		}
	}

}
