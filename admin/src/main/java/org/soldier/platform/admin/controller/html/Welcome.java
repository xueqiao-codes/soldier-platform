package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;


public class Welcome extends CController {
//	@Override
//	public boolean needCheckSession() {
//		return false;
//	}
	
	@Override
	public int doModel(Map<Object, Object> dataModel) {
		return 200;
	}

}
