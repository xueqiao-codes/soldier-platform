package org.soldier.platform.oa.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.Md5;
import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.oa.user.OaUserPage;
import org.soldier.platform.oa.user.QueryUserOption;
import org.soldier.platform.oa.user.oa_userConstants;
import org.soldier.platform.oa.user.ao.OaUserErrorCode;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeOuter;

public class UserAdmin extends OaAuthController {
	private static final String DEFAULT_PASSWORD = "w123456w";
	
	@Override
	public void beforeMethod(String methodName) throws Throwable {
		String userName = getUserName();
		if (!userName.equals("admin")) {
			die("only admin has the access!");
		}
		super.beforeMethod(methodName);
	}
	
	public void show() throws Exception {
		OaUserAoStub stub = new OaUserAoStub();
		QueryUserOption option = new QueryUserOption();
		
		int pageIndex = parameter("pageNo", 1);
		int pageSize = parameter("pageSize", 20);
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		if (pageSize <= 0) {
			pageSize = 20;
		}
		String userName = parameter("userName", "").trim();
		if (!userName.isEmpty()) {
			option.setUserName(userName);
		}
		int userId = parameter("userId", 0);
		if (userId > 0) {
			option.setUserId(userId);
		}
		
		OaUserPage page = stub.queryUserByPage(RandomUtils.nextInt(),
				3000, option, pageIndex - 1, pageSize);
		for (OaUser user : page.getPageRecords()) {
			user.setUserPassword(Md5.toMD5(user.getUserPassword()));
		}
		
		put("userList", page);
		
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		put("pageIndex", pageIndex);
		put("pageSize", pageSize);
		put("toUrl", baseUrl + "/" + this.getClass().getSimpleName() + "/show");
		put("defaultPassword", DEFAULT_PASSWORD);
		
		Map<String, String> queryOptionsMap = new HashMap<String, String>();
		queryOptionsMap.put("userName", userName);
		queryOptionsMap.put("userId", String.valueOf(userId));
		put("queryOptions", queryOptionsMap);
		
		render("user.html");
	}
	
	public void register() throws Exception {
		ErrorResult result = new ErrorResult();
		doRegister(result);
		echoJson(result);
	}
	
	private void doRegister(ErrorResult result) throws Exception {
		String userName = parameter("userName", "").trim();
		String userNickName = parameter("userNickName", "").trim();
		if (userName.isEmpty() || userNickName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("用户登录名和用户姓名未填写完整");
			return ;
		}
		if (userName.length() > oa_userConstants.MAX_USER_NAME_LENGTH) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("用户登录名过长");
			return ;
		}
		if (userNickName.length() > oa_userConstants.MAX_NICK_NAME_LENGTH) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("用户姓名过长");
			return ;
		}
		
		OaUserAoStub stub = new OaUserAoStub();
		OaUser user = new OaUser();
		user.setUserName(userName);
		user.setUserNickName(userNickName);
		user.setUserPassword(DEFAULT_PASSWORD);
		try {
			stub.registerUser(userName.hashCode(), 2000, user, getUserName());
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == OaUserErrorCode.UserNameAlreadyExist.getValue()) {
				result.setErrorCode(err.getErrorCode())
					  .setErrorMsg("用户名已经存在");
				return ;
			}
			throw err;
		}
	}
	
	public void updateUser() throws Exception {
		ErrorResult result = new ErrorResult();
		doUpdateUser(result);
		echoJson(result);
	}

	private void doUpdateUser(ErrorResult result) throws Exception {
		OaUser user = new OaUser();
		
		int userId = parameter("userId", 0);
		if (userId <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("请选择正确的用户");
			return ;
		}
		user.setUserId(userId);
		
		String userNickName = parameter("userNickName", "");
		if (!userNickName.isEmpty()) {
			if (userNickName.length() > oa_userConstants.MAX_NICK_NAME_LENGTH) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
					  .setErrorMsg("用户姓名过长");
				return ;
			}
			user.setUserNickName(userNickName);
		}
		
		String userPassword = parameter("userPassword", "");
		if (!userPassword.isEmpty()) {
			if (userPassword.length() > oa_userConstants.MAX_PASSWORD_LENGTH) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  	  .setErrorMsg("密码过长");
				return ;
			}
			user.setUserPassword(userPassword);
		}
		
		try {
			new OaUserAoStub().updateUser(userId, 1500, user);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == OaUserErrorCode.UserNotExist.getValue()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  	  	  .setErrorMsg("用户不存在");
				return ;
			}
			throw err;
		}
	}
}
