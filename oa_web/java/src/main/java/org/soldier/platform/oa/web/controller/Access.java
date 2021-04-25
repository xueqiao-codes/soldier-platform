package org.soldier.platform.oa.web.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.oa.user.oa_userConstants;
import org.soldier.platform.oa.user.ao.LoginResult;
import org.soldier.platform.oa.user.ao.OaUserErrorCode;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.CTemplateController;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeOuter;

public class Access extends CTemplateController {
	public void show() throws Exception {
		String from = parameter("from", "").trim();
		if (from.contains("\"") || from.startsWith("javascript:")) {
			showError(403, "error parameter for from");
			return ;
		}
 		
		put("from", from);
		render("login.html");
	}
	
	public static class CLoginResult extends ErrorResult {
		private String userName;
		private int userId;
		private String userSecretKey;
		private String falconSig;
		
		public CLoginResult() {
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getUserSecretKey() {
			return userSecretKey;
		}

		public void setUserSecretKey(String userSecretKey) {
			this.userSecretKey = userSecretKey;
		}

		public String getFalconSig() {
			return falconSig;
		}

		public void setFalconSig(String falconSig) {
			this.falconSig = falconSig;
		}
	}
	
	public void login() throws Exception {
		CLoginResult result = new CLoginResult();
		doLogin(result);
		echoJson(result);
	}
	
	private void doLogin(CLoginResult result) throws Exception {
		String userName = parameter("userName", "").trim();
		String userPassword = parameter("userPassword", "").trim();
		
		if (userName.isEmpty() || userPassword.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("用户名或密码不能为空");
			return ;
		}
		
		OaUserAoStub stub = new OaUserAoStub();
		LoginResult loginResult = null;
		
		try {
			loginResult = stub.login(
				RandomUtils.nextInt(), 1500, userName, userPassword);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == OaUserErrorCode.UserNameOrPasswordError.getValue()) {
				result.setErrorCode(err.getErrorCode())
					  .setErrorMsg("用户名或者密码错误");
			} else {
				result.setErrorCode(ErrorCodeOuter.SERVER_BUSY.getErrorCode())
					  .setErrorMsg(ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
			}
			return ;
		}
		
		result.setUserId(loginResult.getUserId());
		result.setUserName(loginResult.getUserName());
		result.setUserSecretKey(loginResult.getSecretKey());
		if (loginResult.getFalconSig() != null) {
			result.setFalconSig(loginResult.getFalconSig());
		} else {
			result.setFalconSig("");
		}
	}
	
	public void logout() throws Exception {
	}
}
