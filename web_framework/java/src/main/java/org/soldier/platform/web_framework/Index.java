package org.soldier.platform.web_framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String indexName;
	private String controllerPackage;
	private String staticUrl;
	
	@Override
	public void init() {
		indexName = getInitParameter("IndexName");
		if (indexName == null) {
			indexName = "index";
		}
		
		controllerPackage = getInitParameter("ControllerPackage");
		if (controllerPackage == null) {
			controllerPackage = getClass().getPackage().getName() + ".controller";
		}
		
		staticUrl = getInitParameter("StaticUrl");
		
		AppLog.d("IndexName=" + indexName +", ControllerPackage=" + controllerPackage 
		        + ", StaticUrl=" + staticUrl);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
		
	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestHost = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getRequestURI().length());
		String servletUrl = requestHost + request.getContextPath();
		String baseUrl = servletUrl + "/" + indexName;
		
		String[] paths = StringUtils.split( request.getRequestURL().substring(baseUrl.length()), '/');
		if (paths.length < 1) {
			response.sendError(404);
			return ;
		}
		
		CController controller = newController(paths[0]);
		if (controller == null) {
			response.sendError(404);
			return ;
		}
		
		String xForwardedProtocol = request.getHeader("X-Forwarded-Proto");
		if (xForwardedProtocol != null && xForwardedProtocol.equalsIgnoreCase("https")) {
			baseUrl = baseUrl.replaceFirst("http://", "https://");
			servletUrl = servletUrl.replaceFirst("http://", "https://");
		}
		if (AppLog.debugEnabled()) {
			AppLog.d("X-Forwarded-Proto=" + xForwardedProtocol + ", baseUrl=" + baseUrl + ", servletUrl=" + servletUrl);
		}
		
		controller.setBaseUrl(baseUrl);
		controller.setContextUrl(servletUrl);
		if (staticUrl == null) {
			controller.setStaticUrl(servletUrl);
		} else {
			controller.setStaticUrl(staticUrl);
		}
		controller.setRequest(request, response);
		controller.setPaths(paths);
		controller.run();
	}
	
	
	private CController newController(String controller){
//		AppLog.d("controller=" + controller);
		try {
			return (CController)Class.forName(
					controllerPackage + "."+ controller).newInstance();
		} catch (ClassNotFoundException e) {
			AppLog.i(e.getMessage());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
		}
		return null;
	}

}
