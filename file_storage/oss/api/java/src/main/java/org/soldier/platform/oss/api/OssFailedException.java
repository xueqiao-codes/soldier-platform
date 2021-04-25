package org.soldier.platform.oss.api;

public class OssFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OssFailedException(String msg) {
		super(msg);
	}
	
	public OssFailedException(Throwable e) {
		super(e);
	}
	
	public OssFailedException(String msg, Throwable e) {
		super(msg, e);
	}

}
