package org.soldier.platform.admin.data;

import org.soldier.platform.web_framework.model.ErrorResult;

public class SyncNginxConfigResult extends ErrorResult {
	private String log = "";

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
}
