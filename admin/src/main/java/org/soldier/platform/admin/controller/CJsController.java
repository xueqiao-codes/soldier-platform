package org.soldier.platform.admin.controller;

import java.util.Map;

public class CJsController extends CController {
	private static CJsController sInstance = new CJsController();
	
	public static CJsController getInstance() {
		return sInstance;
	}
	
	@Override
	public int doModel(Map<Object, Object> dataModel) {
		return 200;
	}
	
	@Override
	public boolean needCheckSession() {
		return false;
	}
	
	@Override
	public String getTemplatePath(String controllerPath) {
		return controllerPath;
	}

}
