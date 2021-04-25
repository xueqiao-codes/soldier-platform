package org.soldier.platform.oa.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.oa.user.ao.ECheckResult;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.web_framework.CController;

/**
 *  主要用于外部鉴权，可以用于nginx的auth_request等
 * @author Xairy
 */
public class Auth extends CController {
	
	@Override
	protected void process() throws Exception {
		int userId = getUserId();
		String userName = getUserName();
		String secretKey = getSecretKey();
		if (userId <= 0 || userName.isEmpty() || secretKey.isEmpty()) {
			super.getServletResponse().sendError(401);
			return;
		}
		
		Set<String> groups = getGroups();
		if (groups == null || groups.isEmpty()) {
		    if (!new OaUserAoStub().checkSession(userId, 2000, userId, userName,
		            secretKey)) {
		        super.getServletResponse().sendError(401);
		        return;
		    }
		} else {
		    ECheckResult checkResult = new OaUserAoStub()
		            .checkSessionAndGroups(userId, userName, secretKey, groups);
		    if (checkResult == ECheckResult.SESSION_INVALID) {
		        super.getServletResponse().sendError(401, "Need Login!");
		        return ;
		    } else if (checkResult == ECheckResult.GROUP_INVALID) {
		        super.getServletResponse().sendError(403, "Forbidden, No Permisssion!");
		        return ;
		    }
		}
		
		getServletResponse().setHeader("X-WEBAUTH-USER", userName);
		getServletResponse().setStatus(200);
		
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
	
	protected Set<String> getGroups() {
	    String groupsParameter = parameter("groups", "");
	    Set<String> resultSet = new HashSet<String>();
	    for (String group : StringUtils.split(groupsParameter, '|')) {
	        resultSet.add(group);
	    }
	    return resultSet;
	}
}
