package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;

import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;

public class Top extends CController {
	@Override
	public boolean needCheckSession() {
		return false;
	}
	
	@Override
	public int doModel(Map<Object, Object> dataModel) throws QconfException {
		dataModel.put("environment", Qconf.getConf("platform/environment").toUpperCase());
		dataModel.put("username", getUserName());
		
		return 200;
	}

}
