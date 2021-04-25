package org.soldier.platform.oa.user.ao.server.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.falcon.api.data.resp.MessageResp;
import org.soldier.platform.falcon.api.data.resp.UserResp;
import org.soldier.platform.falcon.api.method.ChangeUserPasswordMethod;
import org.soldier.platform.falcon.api.method.CreateUserMethod;
import org.soldier.platform.falcon.api.method.GetUserInfoByNameMethod;
import org.soldier.platform.oa.user.OaUser;

public class OpenFalconUser {
	public static void repairUser(OaUser oaUser) throws Exception {
		GetUserInfoByNameMethod userInfoMethod = new GetUserInfoByNameMethod();
		userInfoMethod.setServerName("oa_user_ao");
		userInfoMethod.getReq().setName(oaUser.getUserName());
	
		UserResp userResp = userInfoMethod.call(UserResp.class);
		if (userResp.getHttpStatusCode() == 200) {
			if (userResp.getId() > 0) {
				// 用户存在，把密码重置为和OA一致
				ChangeUserPasswordMethod changePasswordMethod = new ChangeUserPasswordMethod();
				changePasswordMethod.setServerName("root");
				changePasswordMethod.getReq().setUserId(userResp.getId());
				changePasswordMethod.getReq().setPassword(oaUser.getUserPassword());
				
				MessageResp changedPasswordResp = changePasswordMethod.call(MessageResp.class);
				if (changedPasswordResp.getHttpStatusCode() == 200) {
					AppLog.i("change " + oaUser.getUserName() + " for open-falcon success!");
				} else {
					AppLog.e("change " + oaUser.getUserName() + " for open-falcon, http status=" 
								+ changedPasswordResp.getHttpStatusCode());
				}
			}
		} else if (userResp.getHttpStatusCode() >= 400 && userResp.getHttpStatusCode() < 500){
			CreateUserMethod createUserMethod = new CreateUserMethod();
			createUserMethod.setServerName("root");
			createUserMethod.getReq().setName(oaUser.getUserName());
			createUserMethod.getReq().setCnname(oaUser.getUserNickName());
			createUserMethod.getReq().setPassword(oaUser.getUserPassword());
			createUserMethod.getReq().setEmail(oaUser.getUserName() + "@initemail.com");
			
			UserResp createUserResp = createUserMethod.call(UserResp.class);
			if (createUserResp.getHttpStatusCode() == 200) {
				AppLog.i("create user " + oaUser.getUserName() + " for open-falcon success!");
			} else {
				AppLog.e("create user " + oaUser.getUserName() + " for open-falcon failed, http status="
							+ createUserResp.getHttpStatusCode());
			}
		}
	}
}
