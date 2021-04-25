package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.route.dao.RouteType;

public class RouteManage extends CController {

	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		dataModel.put("routeTypes", RouteType.values());
		return 200;
	}

}
