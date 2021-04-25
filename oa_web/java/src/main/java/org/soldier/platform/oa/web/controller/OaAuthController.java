package org.soldier.platform.oa.web.controller;

import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.web_framework.CTemplateController;

public class OaAuthController extends CTemplateController {
	@Override
	public void process() throws Exception {
		if (baseUrl.startsWith("http://localhost")) {
			super.process();
			return ;
		}
		
		String loginUrl = baseUrl + "/Access/show";

		int userId = getUserId();
		String userName = getUserName();
		String secretKey = getSecretKey();
		if (userId <= 0 || userName.isEmpty() || secretKey.isEmpty()) {
			sendRedirect(loginUrl);
			return;
		}
		
		
		if (!new OaUserAoStub().checkSession(userId, 2000, userId, userName,
				secretKey)) {
			sendRedirect(loginUrl);
			return;
		}
		
		super.process();
	}
	
	protected int getUserId() {
		try {
			return Integer.parseInt(getCookie("oa_user_id", "0"));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	protected String getUserName() {
		return getCookie("oa_user_name", "");
	}
	
	protected String getSecretKey() {
		return getCookie("oa_user_secret", "");
	}
}
