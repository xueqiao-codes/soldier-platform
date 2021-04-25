package org.soldier.platform.web_framework.freemarker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class HttpParam implements TemplateMethodModelEx  {
	private HttpServletRequest request;
	
	public HttpParam(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() < 1) {
			return null;
		}
		
		String key = arguments.get(0).toString();
		if (key == null || key.isEmpty()) {
			return null;
		}
		
		return request.getParameter(key);
	}

}
