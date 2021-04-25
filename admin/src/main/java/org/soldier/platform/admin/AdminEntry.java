package org.soldier.platform.admin;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.admin.controller.CDefaultController;
import org.soldier.platform.admin.controller.CJsController;
import org.soldier.platform.admin.data.UserAccess;
import org.soldier.platform.admin.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.freemarker.TemplateUtil;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AdminEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, CController> controllerMap = new HashMap<String, CController>();
	
    public AdminEntry() {        
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
			throws ServletException, IOException{
		try{
			_doProcess(request, response);
		} catch(Exception e){
			AppLog.e(e.getMessage(), e);
		}
	}
	
	private void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		AppLog.d("request.getRequestURL()=" + request.getRequestURL());
//		AppLog.d("request.getContextPath()=" + request.getContextPath());
//		AppLog.d("request.getServletPath()=" + request.getServletPath());
		String requestHost = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getRequestURI().length());
		String servletUrl = requestHost + request.getContextPath();
		String baseUrl = servletUrl + "/admin";
//		AppLog.d("servletUrl=" + servletUrl);
//		AppLog.d("baseUrl=" + baseUrl);
		
		String controllerPath = request.getRequestURL().substring(baseUrl.length());
//		AppLog.d("controllerPath=" + controllerPath);
		
//		Enumeration <String> enumer = request.getHeaderNames();
//        while(enumer.hasMoreElements()){
//            String key = enumer.nextElement();
//            System.out.println("HEADER " + key+" == "+request.getHeader(key));
//        }
//		
		String xForwardedProtocol = request.getHeader("X-Forwarded-Proto");
        if (xForwardedProtocol != null && xForwardedProtocol.equalsIgnoreCase("https")) {
            baseUrl = baseUrl.replaceFirst("http://", "https://");
            servletUrl = servletUrl.replaceFirst("http://", "https://");
        }
        if (AppLog.debugEnabled()) {
            AppLog.d("X-Forwarded-Proto=" + xForwardedProtocol 
                    + ", X-Forwarded-For=" + request.getHeader("X-Forwarded-For")
                    + ", X-Real-IP=" +request.getHeader("X-Real-IP")
                    + ", baseUrl=" +  baseUrl + ", servletUrl=" + servletUrl);
        }
		
		CController controller = getController(controllerPath);
		if (controller == null) {
			controller = CDefaultController.getInstance(); 
		}
		
		Session session = new Session(request, response);
		String templateName = controller.getTemplatePath(controllerPath);
		Template ftl = TemplateUtil.getInstance().getTemplate(templateName);
		if(ftl == null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return ;
		}
		ftl.setEncoding("UTF-8");
		
		Map<Object, Object> dataModel = new HashMap<Object, Object>();
		
		dataModel.put("static_url", servletUrl);
		dataModel.put("base_url", baseUrl);
		dataModel.put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		int responseCode = 200;
		if(controller != null){
			controller.beginRequest(session);
			
			if (controller.needCheckSession() && UserAccess.isEnabled() && !baseUrl.startsWith("http://localhost")) {
				String[] domainSplits = request.getServerName().split("\\.");
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
				fromBuffer.append(request.getRequestURL());
				if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
					fromBuffer.append("?");
					fromBuffer.append(request.getQueryString());
				}
				String loginUrl =  "http://" + oaDomainBuffer + "/oa/Access/show?from=" +
						URLEncoder.encode(fromBuffer.toString() , "UTF-8");
				
				dataModel.put("loginUrl", loginUrl);
				
				if (!controller.checkSession()) {
					response.sendRedirect(loginUrl);
					return ;
				}
			}
			
			try {
				responseCode = controller.runModel(dataModel);
			} finally {
				controller.endRequest();
			}
		}
		if(responseCode == 200){
			response.setContentType(getContextType(controllerPath));
			try {
				ftl.process(dataModel, response.getWriter());
				response.getWriter().flush();
			} catch (TemplateException e) {
				AppLog.e(e.getMessage(), e);
				response.sendError(503);
			}
		} else if(responseCode == 302){
			response.sendRedirect((String) dataModel.get(ModelKey._RedirectUrl));
		} else {
			System.out.println("send Error");
			response.sendError(responseCode);
		}
	}
	
	private String getContextType(String controllerPath) {
		int index = 1;
		while(index < controllerPath.length()) {
			if (controllerPath.charAt(index) == '/') {
				break;
			}
			++index;
		}
		String format = controllerPath.substring(1, index);
		if (format.equalsIgnoreCase("json")) {
			return "application/json;charset=utf-8";
		} else if (format.equalsIgnoreCase("html")) {
			return "text/html;charset=utf-8";
		} else if (format.equals("js")) {
			return "application/x-javascript;charset=utf-8";
		}
		return "";
	}
	
	private synchronized CController getController(String controllerPath){
		if(controllerMap.containsKey(controllerPath)){
			return controllerMap.get(controllerPath);
		}
		
		if (controllerPath.endsWith(".js")) {
			return CJsController.getInstance();
		}
		
//		AppLog.d("getControllerPath " + controllerPath);
		String controllerName = StringUtils.replace(controllerPath, "/", ".");
		
		CController controller = null;
		try {
			controller =  (CController)Class.forName(
							getClass().getPackage().getName() + ".controller"
							+ controllerName).newInstance();
		} catch (ClassNotFoundException e) {
			
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
		}
		controllerMap.put(controllerName, controller);
		
		return controller;
	}

	@Override
	public void destroy() {
		controllerMap.clear();
	}
}
