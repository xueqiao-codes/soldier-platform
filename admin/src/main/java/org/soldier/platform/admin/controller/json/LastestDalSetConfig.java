package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class LastestDalSetConfig extends CController {

	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		int version = parameter("version", 0);
		
		int lastRouteVersion = 0;
		DalSetDaoStub stub = new DalSetDaoStub();
		try {
			lastRouteVersion = stub.getLastVersion(RandomUtils.nextInt(), 1500);
			dataModel.put("version", lastRouteVersion);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		
		if (lastRouteVersion > version) {
			try {
				dataModel.put("dalSetConfig", Base64.encodeBase64String(
						stub.getLastXml(RandomUtils.nextInt(), 3000).getBytes()));
			} catch (Exception e) {
				AppLog.e(e.getMessage(), e);
				return 503;
			}
		} else {
			dataModel.put("dalSetConfig", "");
		}
		
		return 200;
	}
	
	@Override
	public boolean needCheckSession() {
		return false;
	}

}
