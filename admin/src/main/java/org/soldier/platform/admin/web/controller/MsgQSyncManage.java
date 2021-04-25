package org.soldier.platform.admin.web.controller;

import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeOuter;

public class MsgQSyncManage extends WebAuthController {
	
	public static class MsgQSyncResult extends ErrorResult {
		private String log = "";

		public String getLog() {
			return log;
		}

		public void setLog(String log) {
			this.log = log;
		}
	}
	
	public void showMsgQSync() throws Exception {
		render("msgq/MsgQSync.html");
	}
	
	
	private void doSyncConfigIncrement(MsgQSyncResult result) {
		
	}
	
	private void doSyncConfigAll(MsgQSyncResult result) {
		
	}
	
	public void syncConfig() throws Exception {
		MsgQSyncResult result = new MsgQSyncResult();
		String mode = parameter("mode", "").trim();
		if (mode.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("mode parameter is empty");
			echoJson(result);
			return ;
		}
		
		if (mode.equals("increment")) {
			doSyncConfigIncrement(result);
		} else if (mode.equals("all")) {
			doSyncConfigAll(result);
		} else {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("mode parameter error");
			echoJson(result);
			return ;
		}
		
		echoJson(result);
	}
}
