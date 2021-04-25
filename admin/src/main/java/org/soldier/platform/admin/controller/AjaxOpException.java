package org.soldier.platform.admin.controller;

public class AjaxOpException extends Exception{
	private static final long serialVersionUID = 1L;
	private int errorCode;
	
	public AjaxOpException(int errorCode){
		this.errorCode = errorCode;
	}
	
	public AjaxOpException(int errorCode, String errorMsg){
		super(errorMsg);
		this.errorCode = errorCode;
		
	}
	
	public int getErrorCode(){
		return this.errorCode;
	}
}
