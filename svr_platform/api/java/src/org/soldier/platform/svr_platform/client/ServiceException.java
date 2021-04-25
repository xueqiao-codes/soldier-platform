package org.soldier.platform.svr_platform.client;

/**
 *  服务异常描述
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ServiceException(String msg) {
		super(msg);
	}
}
