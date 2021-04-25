package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetRoleData extends CPageController {
	@Override
	protected int doPageModel(Map<Object, Object> dataModel,
			int pageIndex,
			int pageSize) throws Exception {
		QueryDalSetRoleOption option = new QueryDalSetRoleOption();
		
		String roleName = parameter("roleName", "").trim();
		if (!roleName.isEmpty()) {
			option.setRoleName(roleName);
		}
		String dbName = parameter("dbName", "").trim();
		if (!dbName.isEmpty()) {
			option.setDbName(dbName);
		}
		String desc = parameter("remark", "").trim();
		if (!desc.isEmpty()) {
			option.setDesc(desc);
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		dataModel.put("itemsResult", 
					stub.queryDalSetRoles(RandomUtils.nextInt(), 
							3000, pageIndex, pageSize, option));
		
		return 200;
	}

}
