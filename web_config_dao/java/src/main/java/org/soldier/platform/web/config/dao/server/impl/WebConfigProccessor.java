package org.soldier.platform.web.config.dao.server.impl;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web.config.dao.WebConfig;

import com.antiy.error_code.ErrorCodeInner;

public class WebConfigProccessor {
	private WebConfig config;
	
	public WebConfigProccessor(WebConfig config) throws ErrorInfo {
		if (config == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"config should not be null");
		}
		this.config = config;
	}
	
	private void checkIpListIfSet() throws ErrorInfo {
		if (config.getIpList().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"ipList is empty");
		}
		
		for (Long ip : config.getIpList()) {
			if (0l == ip) {
				throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
						"ipList containes 0");
			}
		}
	}
	
	private void checkIpList() throws ErrorInfo {
		if (!config.isSetIpList()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"ipList should be set");
		}
		
		checkIpListIfSet();
	}
	
	private void checkWebProjectName() throws ErrorInfo {
		if (!config.isSetWebProjectName()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"webProjectName should be set");
		}
		checkWebProjectNameIfSet();
	}
	
	private void checkWebProjectNameIfSet() throws ErrorInfo {
		if (config.getWebProjectName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"webProjectName should not be empty");
		}
		config.setWebProjectName(config.getWebProjectName().trim());
	}
	
	private void checkDeployType() throws ErrorInfo {
		if (!config.isSetDeployType()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"deployType should be set");
		}
	}
	
	private void checkPort() throws ErrorInfo {
		if (!config.isSetPort()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"port should be set");
		}
		checkPortIfSet();
	}
	
	private void checkPortIfSet() throws ErrorInfo {
		if (config.getPort() <= 0 || config.getPort() > 65535) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"port should not <=0 or > 65535");
		}
	}
	
	public void checkAndProcess4Add() throws ErrorInfo {
		checkWebProjectName();
		if (!config.isSetBackendList() || config.getBackendList().isEmpty()) {
		    checkIpList();
		}
		checkDeployType();
		checkPort();
	}
	
	public void checkAndProcess4Update() throws ErrorInfo {
		checkWebProjectName();
		if (config.isSetIpList()) {
			checkIpListIfSet();
		}
		if (config.isSetPort()) {
			checkPortIfSet();
		}
	}
}
