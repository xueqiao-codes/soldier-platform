package org.soldier.base.file;

public class FileWatcherException extends Exception {
	private static final long serialVersionUID = -1136589238750878372L;
	
	private int errorCode = 0;
	
	public FileWatcherException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
