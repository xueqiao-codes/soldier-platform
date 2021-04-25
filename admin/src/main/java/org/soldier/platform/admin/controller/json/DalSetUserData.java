package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetUserData extends CPageController {

	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) {
		QueryDalSetUserOption option = new QueryDalSetUserOption();
		
		String userKey = parameter("queryUserId", "").trim();
		if (!userKey.isEmpty()) {
			option.setKey(userKey);
		}
		String userName = parameter("queryUserName").trim();
		if (!userName.isEmpty()) {
			option.setName(userName);
		}
		String userDesc = parameter("queryRemark").trim();
		if (!userDesc.isEmpty()) {
			option.setDesc(userDesc);
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		try {
			dataModel.put("itemsResult", 
					stub.queryDalSetUsers(RandomUtils.nextInt(),
							2000, pageIndex, pageSize, option));
		} catch(Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		
		return 200;
	}

}
