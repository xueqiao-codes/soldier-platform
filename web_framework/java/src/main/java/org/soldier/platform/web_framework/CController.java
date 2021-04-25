package org.soldier.platform.web_framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.util.Json;
import org.soldier.platform.web_framework.exceptions.ExitException;
import org.soldier.platform.web_framework.util.GsonFactory;

/**
 *  公共模版Model类
 */
public abstract class CController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	protected String baseUrl;
	protected String contextUrl;
	protected String staticUrl;
	private String[] paths;
	
	public void setRequest(HttpServletRequest request, 
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public void setPaths(String[] paths) {
		this.paths = paths;
	}
	
	protected String getPath(int index) {
		if (index < 0 || index >= paths.length) {
			return null;
		}
		return paths[index];
	}
	
	protected String getPathString() {
		return StringUtils.join(paths, '/');
	}
	
	public void setBaseUrl(String url) {
		this.baseUrl = url;
	}
	
	public void setContextUrl(String url) {
		this.contextUrl = url;
	}
	
	public void setStaticUrl(String url) {
		this.staticUrl = url;
	}
	
	protected void setStatus(int statusCode) {
		this.response.setStatus(statusCode);
	}
	
	protected void showError(int errorCode) {
		try {
			response.sendError(errorCode);
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
	protected void showError(int errorCode, String msg) {
		try {
			response.sendError(errorCode, msg);
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
	protected void show404() throws IOException {
		show404("404, Page Not Found");
	}
	
	protected void show404(String msg) throws IOException {
		response.sendError(404, msg);
	}
	
	protected void showUserError(String errorMsg) {
		showError(501, errorMsg);
	}
	
	protected void sendRedirect(String url) throws IOException {
		if (url.startsWith("http")) {
			response.sendRedirect(url);
			return ;
		}
		
		if (url.startsWith("/")) {
			response.sendRedirect(baseUrl + url);
		} else {
			response.sendRedirect(baseUrl + "/" + url);
		}
	}
	
	protected void echo(String content) throws IOException {
		response.getWriter().print(content);
		response.getWriter().print("\n");
	}
	
	protected void echoQuietly(String content) {
		try {
			echo(content);
		} catch (IOException e) {
			AppLog.w(e.getMessage(), e);
		}
	}
	
	protected HttpServletRequest getServletRequest() {
		return request;
	}
	
	protected HttpServletResponse getServletResponse() {
		return response;
	}
	
	protected void echoJson(Object obj) throws IOException {
		setContentType("application/json;charset=utf-8");
		response.getWriter().write(GsonFactory.getGson().toJson(obj));
	}
	
	protected void echoJson(TBase<?, ?> baseObject) throws IOException {
		setContentType("application/json;charset=utf-8");
		response.getWriter().write(Json.toJson(baseObject));
	}
	
	protected void print(String content) throws IOException {
		response.getWriter().print(content);
	}
	
	protected PrintWriter output() throws IOException {
		return response.getWriter();
	}
	
	protected ServletOutputStream outputStream() throws IOException {
		return response.getOutputStream();
	}
	
	protected void setContentType(String contentType) {
		response.setContentType(contentType);
	}
	
	protected void transfer(CController target) {
		target.request = this.request;
		target.response = this.response;
		target.baseUrl = this.baseUrl;
		target.contextUrl = this.contextUrl;
		target.staticUrl = this.staticUrl;
		target.paths = this.paths;
	}
	
	protected abstract void process() throws Exception;
	
	public void run() {
		try {
			process();
		} catch (ExitException e) {
			echoQuietly(e.getMessage());
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getClass() == ExitException.class) {
				echoQuietly(e.getCause().getMessage());
			} else {
				AppLog.e(e.getMessage(), e);
				showError(503, "抱歉服务器繁忙");
			}
		} 
	}
		
	protected String parameter(final String key){
		return parameter(key, "");
	}
	
	protected String parameter(final String key, final String defaultValue){
		String[] parameters = request.getParameterValues(key);
		if(parameters == null || parameters.length < 1){
			return defaultValue;
		}
		return parameters[0];
	}
	
	protected int parameter(final String key, final int defaultValue){
		try{
			return Integer.parseInt(parameter(key, String.valueOf(defaultValue)));
		} catch (NumberFormatException e){
			AppLog.i("GET Key " + key + " Format Error");
			return defaultValue;
		}
	}
	
	protected long parameter(final String key, final long defaultValue){
		try{
			return Long.parseLong(parameter(key, String.valueOf(defaultValue)));
		} catch (NumberFormatException e){
			AppLog.i("GET Key " + key + " Format Error");
			return defaultValue;
		}
	}
	
	protected String[] parameterArray(final String key){
		String[] parameters = request.getParameterValues(key);
		if(parameters == null){
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		return parameters;
	}
	
	protected String getQueryString() {
		return request.getQueryString();
	}
	
	protected void addCookie(String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		if (path != null && !path.isEmpty()) {
			cookie.setPath(path);
		}
		response.addCookie(cookie);
	}
	
	protected String getCookie(String name, String defaultValue) {
		Cookie cookie = getCookie(name);
		if (cookie == null) {
			return defaultValue; 
		}
		return cookie.getValue();
	}
	
	protected Cookie getCookie(String name) {
		Cookie[] cookies = request.getCookies();
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
	
	protected void setResponseHeader(String name, String value) {
		response.setHeader(name, value);
	}
	
	protected String getRequestHeader(String name) {
		return request.getHeader(name);
	}
	
	protected String getUserAgent() {
		return getRequestHeader("user-agent");
	}
	
	protected String getRequestURL() {
		return request.getRequestURL().toString();
	}
	
	protected String getScheme() {
		return request.getScheme();
	}
	
	protected String getServerName() {
		return request.getServerName();
	}
	
	protected int getServerPort() {
		return request.getServerPort();
	}
	
	protected void die(String msg) {
		setContentType("text/plain;charset=utf-8");
		throw new ExitException(msg);
	}
	
	@SuppressWarnings("unchecked")
	protected List<FileItem> getFileItemList() throws FileUploadException {
		List<FileItem> fileItemList = new ArrayList<FileItem>();
		if	(ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload upload = new ServletFileUpload(factory);
			Iterator<FileItem> items = upload.parseRequest(request).iterator();// 将表单数据全部赋值给items
			while (items.hasNext()) {// while循环迭代items，得到所有的表单数据
				FileItem item = items.next();
				fileItemList.add(item);
			}
		}
		
		return fileItemList;
	}
}
