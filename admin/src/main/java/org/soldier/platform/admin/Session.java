package org.soldier.platform.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Session {
	private HttpServletRequest httpRequest;
	private HttpServletResponse httpResponse;
	private String opUserName;
	
	public Session(HttpServletRequest request, HttpServletResponse response) {
		this.httpRequest = request;
		this.httpResponse = response;
	}
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
	
	public String getOpUserName() {
		return opUserName;
	}
	
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
}