package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetHostData extends CPageController {
	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) {
		QueryDalSetHostOption option = new QueryDalSetHostOption();
		
		String hostName = parameter("hostName", "").trim();
		if (!hostName.isEmpty()) {
			option.setName(hostName);
		}
		String hostDomain = parameter("hostDomain", "").trim();
		if (!hostDomain.isEmpty()) {
			option.setDomain(hostDomain);
		}
		int hostPort = parameter("hostPort", 0);
		if (hostPort > 0) {
			option.setPort(hostPort);
		}
		String desc = parameter("remark", "").trim();
		if (!desc.isEmpty()) {
			option.setDesc(desc);
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		try {
			dataModel.put("itemsResult", 
					stub.queryDalSetHosts(RandomUtils.nextInt(),
							1500, pageIndex, pageSize, option));
		} catch(Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		
		return 200;
	}


}
