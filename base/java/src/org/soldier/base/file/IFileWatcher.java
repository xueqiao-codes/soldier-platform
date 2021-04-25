package org.soldier.base.file;

public interface IFileWatcher {
	public static final int FILE_CREATED = 1;
	public static final int FILE_DELETED = 2;
	public static final int FILE_MODIFIED = 4;
	public static final int FILE_RENAMED = 8;
	public static final int FILE_ANY = 
			FILE_CREATED | FILE_DELETED | FILE_MODIFIED | FILE_RENAMED;
	
	public int addWatch(final String path, 
			int watchTypeMask, boolean watchSubTree, IFileListener listener)
			throws FileWatcherException;
	
	public void removeWatch(int watchID);
}
