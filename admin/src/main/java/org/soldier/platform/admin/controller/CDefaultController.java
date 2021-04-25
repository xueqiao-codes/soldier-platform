package org.soldier.platform.admin.controller;

import java.util.Map;

public class CDefaultController extends CController {
	private static final CDefaultController sInstance = new CDefaultController();
	
	public static CDefaultController getInstance() {
		return sInstance;
	}
	
	@Override
	public int doModel(Map<Object, Object> dataModel) {
		return 200;
	}

}
