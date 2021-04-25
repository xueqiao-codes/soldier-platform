package org.soldier.platform.admin.web.controller;

import java.net.URLEncoder;

import org.soldier.platform.admin.data.UserAccess;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.web_framework.CTemplateController;

public class WebAuthController extends CTemplateController {
	@Override
	public void process() throws Exception {		
		if (this.baseUrl.startsWith("http://localhost")) {
			super.process();
			return ;
		}
		
		if (!UserAccess.isEnabled()) {
			super.process();
			return ;
		}
		String[] domainSplits = getServerName().split("\\.");
		int startIndex = domainSplits.length - 3;
		if (startIndex < 0) {
			startIndex = 0;
		}
		
		StringBuffer oaDomainBuffer = new StringBuffer(64);
		for (int index = startIndex; index < domainSplits.length; ++index) {
			if (index != startIndex) {
				oaDomainBuffer.append(".");
			}
			if (index == domainSplits.length - 1) {
				oaDomainBuffer.append(domainSplits[index].split(":")[0]);
			} else {
				oaDomainBuffer.append(domainSplits[index]);
			}
		}
		
		StringBuffer fromBuffer = new StringBuffer(128);
		fromBuffer.append(getRequestURL());
		if (getQueryString() != null && !getQueryString().isEmpty()) {
			fromBuffer.append("?");
			fromBuffer.append(getQueryString());
		}
		String loginUrl =  "http://" + oaDomainBuffer + "/oa/Access/show?from=" +
				URLEncoder.encode(fromBuffer.toString() , "UTF-8");

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
