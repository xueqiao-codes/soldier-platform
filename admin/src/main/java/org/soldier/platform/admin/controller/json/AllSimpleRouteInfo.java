package org.soldier.platform.admin.controller.json;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.admin.freemarker.IpListConverter;
import org.soldier.platform.route.dao.SimpleRouteInfo;
import org.soldier.platform.route.dao.client.RouteDaoStub;

public class AllSimpleRouteInfo extends CController {
	
	@Override
	public int doModel(Map<Object, Object> dataModel) {
		int version = parameter("version", 0);
		
		int lastRouteVersion = 0;
		RouteDaoStub stub = new RouteDaoStub();
		try {
			lastRouteVersion = stub.getLastRouteVersion(RandomUtils.nextInt(), 1500);
			dataModel.put("version", lastRouteVersion);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		
		if (lastRouteVersion > version) {
			try {
				dataModel.put("items", stub.getAllSimpleRouteInfo(RandomUtils.nextInt(), 3000));
			} catch (Exception e) {
				AppLog.e(e.getMessage(), e);
				return 503;
			}
		} else {
			dataModel.put("items", new ArrayList<SimpleRouteInfo>());
		}
		dataModel.put("ipList2Str",  new IpListConverter(","));
		
		return 200;
	}
	
	@Override
	public boolean needCheckSession() {
		return false;
	}

}
