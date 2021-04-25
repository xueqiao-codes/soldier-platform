package org.soldier.platform.admin.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.Session;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;

/**
 *  公共模版Model类
 */
public abstract class CController {
	private static final ThreadLocal<Session> httpSessionThreadLocal
		= new ThreadLocal<Session>();
	
	protected String baseUrl;
	
	
	/**
	 * Model生成
	 * @param request     Http请求参数
	 * @param dataModel   返回的dataModel
	 * @return Http状态码 
	 *         返回200正常结合模版后输出
	 *         返回302进行重定向, dataModel中存放 
	 *         		dataModel.put(ModelKey._RedirectUrl, redirectUrl);
	 */
	public abstract int doModel(Map<Object, Object> dataModel) throws Exception;
	
	public int runModel(Map<Object, Object> dataModel) {
		try {
			int result = doModel(dataModel);
			if (result == 0) {
				return 200;
			}
			return result;
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
	}
		
	/**
	 * 是否需要检查登录Session
	 * @return
	 */
	public boolean needCheckSession() {
		return true;
	}
	
	public int init(HttpServlet servlet){
		return 0;
	}

	private HttpServletRequest getHttpRequest() {
		return httpSessionThreadLocal.get().getHttpRequest();
	}
	
	private HttpServletResponse getHttpResponse() {
		return httpSessionThreadLocal.get().getHttpResponse();
	}
	
	public void beginRequest(Session session) {
		httpSessionThreadLocal.set(session);
	}
	
	public void endRequest() {
		httpSessionThreadLocal.set(null);
	}

	protected String parameter(final String key){
		return parameter(key, "");
	}
	
	protected String parameter(final String key,
						final String defaultValue){
		String[] parameters = getHttpRequest().getParameterValues(key);
		if(parameters == null || parameters.length < 1){
			return defaultValue;
		}
		return parameters[0];
	}
	
	protected int parameter(final String key, final int defaultValue){
		try{
			return Integer.parseInt(parameter(key, String.valueOf(defaultValue)));
		} catch (NumberFormatException e){
			return defaultValue;
		}
	}
	
	protected long parameter(final String key, final long defaultValue){
		try{
			return Long.parseLong(parameter(key, String.valueOf(defaultValue)));
		} catch (NumberFormatException e){
			return defaultValue;
		}
	}
	
	protected String[] parameterArray(final String key){
		String[] parameters = getHttpRequest().getParameterValues(key);
		if(parameters == null){
			return emptyStringArray;
		}
		return parameters;
	}
	
	protected void addCookie(Cookie cookie) {
		getHttpResponse().addCookie(cookie);
	}
	
	protected String getOpUserName() {
		return httpSessionThreadLocal.get().getOpUserName();
	}
	
	public String getTemplatePath(String controllerPath) {
		return controllerPath + ".ftl";
	}
	
	protected String getCookie(String name, String defaultValue) {
		Cookie cookie = getCookie(name);
		if (cookie == null) {
			return defaultValue; 
		}
		return cookie.getValue();
	}
	
	protected Cookie getCookie(String name) {
		Cookie[] cookies = httpSessionThreadLocal.get().getHttpRequest().getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (name.equals(cookie.getName()))
				return cookie;
		}
		return null;
	}
	
	public int getUserId() {
		try {
			return Integer.parseInt(getCookie("oa_user_id", "0"));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public String getUserName() {
		return getCookie("oa_user_name", "");
	}
	
	public String getSecretKey() {
		return getCookie("oa_user_secret", "");
	}
	
	public boolean checkSession() {
		int userId = getUserId();
		String userName = getUserName();
		String secretKey = getSecretKey();
		if (userId <= 0 || userName.isEmpty() || secretKey.isEmpty()) {
			return false;
		}
		
		try {
			return new OaUserAoStub().checkSession(userId, 2000, userId, userName,
					secretKey);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return false;
		}
	}
	
	private static final String[] emptyStringArray = new String[0];
}
