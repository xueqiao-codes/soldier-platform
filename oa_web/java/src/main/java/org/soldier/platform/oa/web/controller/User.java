package org.soldier.platform.oa.web.controller;

import java.util.List;

import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.oa.user.QueryUserOption;
import org.soldier.platform.oa.user.oa_userConstants;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeOuter;

public class User extends OaAuthController {
	public void passwordPage () throws Exception {
		render("password_page.html");
	}
	
	public void changePassword() throws Exception {
		ErrorResult result = new ErrorResult();
		doChangePassword(result);
		echoJson(result);
	}
	
	private void doChangePassword(ErrorResult result) throws Exception {
		String orginalPassoword = parameter("orginalPassword", "").trim();
		if (orginalPassoword.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("必须填写原始密码");
			return ;
		}
		String newPassword = parameter("newPassword", "").trim();
		if (newPassword.length() < 8) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("密码长度不得小于8位");
			return ;
		}
		
		OaUserAoStub stub = new OaUserAoStub();
		QueryUserOption option = new QueryUserOption();
		option.setUserId(getUserId());
		
		List<OaUser> userList = stub.queryUser(getUserId(), 1500, option);
		if (userList.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("用户不存在");
			return ;
		}
		OaUser user = userList.get(0);
		
		if (!user.getUserPassword().equals(orginalPassoword.substring(0, oa_userConstants.MAX_PASSWORD_LENGTH))) {
			result.setErrorCode(ErrorCodeOuter.USERNAME_OR_PASSED_ERROR.getErrorCode())
				  .setErrorMsg("密码错误");
			return ;
		}
		
		OaUser newUser = new OaUser();
		newUser.setUserId(getUserId());
		newUser.setUserPassword(newPassword.substring(0, oa_userConstants.MAX_PASSWORD_LENGTH));
		stub.updateUser(getUserId(), 1500, newUser);
	}
}
