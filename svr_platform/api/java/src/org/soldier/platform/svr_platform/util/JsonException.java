package org.soldier.platform.svr_platform.util;

public class JsonException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public JsonException(String msg) {
		super(msg);
	}

	public JsonException(String msg, Throwable cause) {
	    super(msg, cause);
	}

	public JsonException(Throwable cause) {
	    super(cause);
	}
}
