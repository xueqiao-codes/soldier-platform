package org.soldier.platform.admin.web.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.web.config.dao.ConfigType;
import org.soldier.platform.web.config.dao.client.WebConfigDaoStub;
import org.soldier.platform.web_framework.CTemplateController;

public class WebConfigData extends CTemplateController {
	public static class NginxConfig {
		private int version ;
		private String config;
		
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
		
		public String getConfig() {
			return config;
		}
		public void setConfig(String config) {
			this.config = config;
		}
	}
	
	public void getLastestNginxConfig() throws Exception {
		NginxConfig config = new NginxConfig();
		doGetLastestNginxConfig(config);
		echoJson(config);
	}
	
	private void doGetLastestNginxConfig(NginxConfig config) throws Exception {
		int version = parameter("version", 0);
		
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		config.setVersion(stub.getLastVersion(RandomUtils.nextInt(), 1500, ConfigType.AllConfig));
		if (config.getVersion() <= version) {
			return ;
		}
		
		config.setConfig(stub.getLastestNginxConfig(RandomUtils.nextInt(), 5000, ConfigType.AllConfig));
	}
}
