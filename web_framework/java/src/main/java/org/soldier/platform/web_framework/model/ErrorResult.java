package org.soldier.platform.web_framework.model;

public class ErrorResult {
	private int errorCode;
	private String errorMsg;
	
	public ErrorResult() {
		setErrorCode(0);
		setErrorMsg("");
	}
	
	public ErrorResult(int errorCode, String errorMsg) {
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public ErrorResult setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public ErrorResult setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		return this;
	}
	
	
}
