package org.soldier.platform.dal_set;

/**
 *  Db用户的描述
 * @author Tencent
 */
public class DbUser {
	private String userId = "";     // 用户唯一ID，同名不同密码可以是不同的用户ID 
	private String userName = "";
	private String userPasswd = "";
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPasswd() {
		return userPasswd;
	}
	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}
}
