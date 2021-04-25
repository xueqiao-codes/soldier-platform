package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;

public class Login extends CController {
	@Override
	public boolean needCheckSession() {
		return false;
	}
	
	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		String from = parameter("from", "");
		if (from.isEmpty()) {
			dataModel.put("from", dataModel.get("base_url").toString() + "/html/Welcome");
		} else {
			dataModel.put("from", from);
		}
		return 200;
	}

}
