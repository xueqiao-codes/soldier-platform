package org.soldier.platform.oa.web;

import org.soldier.platform.web_framework.WebContextListener;

public class OaWebContextListener extends WebContextListener {

	@Override
	public String getLoggerName() {
		return "oa_web";
	}

}
