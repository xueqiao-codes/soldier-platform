package org.soldier.platform.admin.web.controller;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.client.WebConfigDaoStub;
import org.soldier.platform.web_framework.CTemplateController;

import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;

public class Host extends CTemplateController {
	private void echoHost(String host) throws Exception {
		String machine = getEntryMachine(host);
		if (machine == null || machine.isEmpty()) {
			return ;
		}
		echo(machine + " " + host);
	}
	
	public void show() throws Exception {
		WebConfigDaoStub stub  = new WebConfigDaoStub();
		
		List<WebConfig> configList = stub.queryConfig(RandomUtils.nextInt(), 5000, new QueryWebConfigOption());
		
		setContentType("text/plain;charset=utf-8");
		echo(Qconf.getConf("platform/web_admin/host/maven.server") + " maven.server");
		
		for (WebConfig config : configList) {
			for (String host : config.getDomainList()) {
				echoHost(host);
			}
		}
		
		echo(Qconf.getConf("platform/web_admin/host/extra"));
//		for (String host : StaticProjects.get()) {
//			echoHost(host);
//		}
	}
	
	private String getEntryMachine(String host) throws QconfException {
		return Qconf.getConf("platform/web_admin/host/entry");
	}
}
