package org.soldier.platform.web_framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.web_framework.freemarker.HttpParam;
import org.soldier.platform.web_framework.freemarker.TemplateUtil;
import org.soldier.platform.web_framework.statics.fis.freemarker.FisResTemplate;

import freemarker.template.TemplateException;

public class CTemplateController extends CMethodController {
	private Map<Object, Object> dataModel = new HashMap<Object, Object>();
	
	protected void put(String key, Object value) {
		dataModel.put(key, value);
	}
	
	protected void render(String template) throws IOException, TemplateException {
		if (template.endsWith(".html") || template.endsWith(".htm")) {
			setContentType("text/html;charset=utf-8");
		} else if (template.endsWith(".js")) {
			setContentType("application/x-javascript;charset=utf-8");
		} else if (template.endsWith("json")) {
			setContentType("application/json;charset=utf-8");
		}
		TemplateUtil.getInstance().showTemplate(template, dataModel, output());
	}
	
	protected String showTemplate(String templatePath) throws IOException, TemplateException {
		return TemplateUtil.getInstance().showTemplate(templatePath, dataModel);
	}
	
	@Override
	protected void showError(int errorCode){
		showError(errorCode, "错误码" + Integer.toString(errorCode));
	}
	
	@Override
	protected void showError(int errorCode, String errorMsg) {
		put("static_url", contextUrl);
		
		put("errorCode", errorCode);
		put("errorMsg", errorMsg);
		put("fisRes", new FisResTemplate());
		try {
			setStatus(errorCode);
			render("error_page.html");
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
		} 
	}
	
	@Override
	protected void show404(String errorMsg) {
		showError(404, errorMsg);
	}
	
	@Override
	protected void beforeMethod(String methodName) throws Throwable {
		put("static_url", staticUrl);
		put("base_url", baseUrl);
		put("context_url", contextUrl);
		put("fisRes", new FisResTemplate());
		put("param", new HttpParam(getServletRequest()));
	}

}
