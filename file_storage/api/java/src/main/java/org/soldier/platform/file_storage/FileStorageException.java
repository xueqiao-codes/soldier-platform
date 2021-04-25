package org.soldier.platform.file_storage;

public class FileStorageException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public FileStorageException(String msg) {
		super(msg);
	}
	
	public FileStorageException(Throwable e) {
		super(e);
	}
	
	public FileStorageException(String msg, Throwable e) {
		super(msg, e);
	}

}
