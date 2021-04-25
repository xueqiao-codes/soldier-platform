package org.soldier.platform.web_framework.exceptions;

public class ExitException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExitException(String msg) {
		super(msg);
	}
	
}
