package org.soldier.platform.thriftgen;

import org.soldier.platform.web_framework.WebContextListener;

public class ContextListener extends WebContextListener {

	@Override
	public String getLoggerName() {
		return "thrift-generator";
	}

}
