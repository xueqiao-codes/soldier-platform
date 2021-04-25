package org.soldier.platform.web_framework;

import java.lang.reflect.Method;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.web_framework.freemarker.TemplateUtil;
import org.soldier.platform.web_framework.statics.fis.FisConfiguration;

/**
 *  listener for web framework
 * @author wileywang
 */
public abstract class WebContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		AppLogStream.restoreSystemPrint();
		AppLog.destroy();
		tryDestroyFileStorage();
		SvrContainer.destory();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent context) {
		String loggerName = getLoggerName();
		if (loggerName != null && !loggerName.trim().isEmpty()) {
			AppLog.init("web/" + loggerName.trim());
		}
		AppLogStream.redirectSystemPrint();
		
		TemplateUtil.setTemplatesPath(context.getServletContext().getRealPath("META-INF/templates")); 
		FisConfiguration.getInstance().addMapJsonFromFile(context.getServletContext().getRealPath("WEB-INF/map.json"));
	}
	
	private void tryDestroyFileStorage() {
		try {
			Class<?> fileStorageClazz = Class.forName("org.soldier.platform.file_storage.FileStorageFactory");
			try {
				Method instanceMethod = fileStorageClazz.getMethod("getInstance");
				Object instance = instanceMethod.invoke(null);
				
				Method destroyMethod = fileStorageClazz.getMethod("destroy");
				destroyMethod.invoke(instance);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} catch (ClassNotFoundException e) {
			AppLog.d("FileStorage is not used!");
		}
	}
	
	public abstract String getLoggerName();
}
