package org.soldier.platform.web_framework.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.soldier.base.logger.AppLog;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtil {
	private static TemplateUtil sInstance;
	public static TemplateUtil getInstance() {
		if (sInstance == null) {
			synchronized(TemplateUtil.class) {
				if (sInstance == null) {
					sInstance = new TemplateUtil();
				}
			}
		}
		return sInstance;
	}
	
	private static String templatesPath = "";
	public static void setTemplatesPath(String path) {
		templatesPath = path;
	}
	
	private Configuration templatesCfg;
	
	protected TemplateUtil() {
		templatesCfg = new Configuration();
        try {
        	templatesCfg.setDirectoryForTemplateLoading(new File(templatesPath));
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
		}
        templatesCfg.setDefaultEncoding("UTF-8");
        templatesCfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	public void showTemplate(String templatePath, 
			Map<Object, Object> dataModel,
			Writer writer) throws IOException, TemplateException {
		Template ftl = templatesCfg.getTemplate(templatePath);
		ftl.process(dataModel, writer);
	}
	
	
	public String showTemplate(String templatePath, Map<Object, Object> dataModel) 
			throws IOException, TemplateException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Template ftl = templatesCfg.getTemplate(templatePath);
		OutputStreamWriter writer = new OutputStreamWriter(baos);
		try {
			ftl.process(dataModel, writer);
			return baos.toString();
		} finally {
			writer.close();
		}
	}
	
	public Template getTemplate(String templatePath) throws IOException {
		return templatesCfg.getTemplate(templatePath);
	}
}
