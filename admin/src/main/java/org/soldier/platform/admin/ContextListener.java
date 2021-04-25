package org.soldier.platform.admin;

import javax.servlet.ServletContextEvent;

import org.soldier.platform.admin.data.UserAccess;
import org.soldier.platform.web_framework.WebContextListener;

public class ContextListener extends WebContextListener  {
	@Override
	public void contextInitialized(ServletContextEvent context) {
		super.contextInitialized(context);
        UserAccess.init();
	}

	@Override
	public String getLoggerName() {
		return "platform_web_admin";
	}

}
