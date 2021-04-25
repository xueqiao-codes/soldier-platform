package org.soldier.platform.admin.web.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.route.dao.client.RouteDaoStub;
import org.soldier.platform.web_framework.CTemplateController;

public class RouteConfigData extends CTemplateController {
	public static class RouteConfig {
		private int version;
		private String config;
		
		public int getVersion() {
			return this.version;
		}
		
		public void setVersion(int version) {
			this.version = version;
		}
		
		public String getConfig() {
			return this.config;
		}
		
		public void setConfig(String config) {
			this.config = config;
		}
	}
	
	
	public void getLastestRouteConfig() throws Exception {
		RouteConfig config = new RouteConfig();
		doGetLastestRouteConfig(config);
		echoJson(config);
	}
	
	private void doGetLastestRouteConfig(RouteConfig config) throws Exception {
		int version = parameter("version", 0);
		
		RouteDaoStub stub = new RouteDaoStub();
		
		config.setVersion(stub.getLastRouteVersion(RandomUtils.nextInt(), 1500));
		if (config.getVersion() <= version) {
			return ;
		}
		
		config.setConfig(stub.getLastestRouteConfig(RandomUtils.nextInt(), 5000));
	}
}
