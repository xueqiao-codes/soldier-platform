package org.soldier.platform.admin.data;

import org.soldier.base.AES;
import org.soldier.base.Md5;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.model.LoginInfo;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;

public class UserAccess {
	private static String secretKey = "soldierSecret123";
	
	private static String getAdminName() {
		return "admin";
	}
	
	private static String getAdminPassword() {
		return "soldier_platform";
	}
	
	private static String getAdminSession() {
		return "sdfsdfiwer01239sfksdf90123l01239";
	}
	
	public static void init() {
		AES.encrypt("run fast", secretKey);
	}
	
	public static boolean isEnabled() {
		return !"disabled".equals(System.getenv("PLATFORM_WEB_USERACCESS"));
	}
	
	public static boolean isSessionValid(int userId, String userName, String secretKey) {
		try {
			return new OaUserAoStub().checkSession(userId, 1500, userId, userName, secretKey);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return false;
		}
	}
	
	public static LoginInfo login(String userName, String password) {
		if (!isPasswordValid(userName, password)) {
			return null;
		}
		
		LoginInfo info = new LoginInfo();
		info.setSecretKey(secretKey);
		info.setSession(AES.encrypt(getAdminSession(), secretKey));
		
		return info;
	}
	
	private static boolean isPasswordValid(String userName, String password) {
		if (userName.equals(getAdminName())) {
			if (Md5.toMD5(getAdminPassword()).equals(password)) {
				return true;
			}
		}
		return false;
	}
}
