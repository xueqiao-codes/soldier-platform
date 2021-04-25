package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.RoleRelatedOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetRoleSetRelationData extends CPageController {
	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) throws Exception {
		QueryRoleSetRelationOption option = new QueryRoleSetRelationOption();
		DalSetDaoStub stub = new DalSetDaoStub();
		
		String roleName = parameter("roleName", "").trim();
		if (!roleName.isEmpty()) {
			option.setRoleRelatedOption(new RoleRelatedOption(roleName));
		}
		
		dataModel.put("itemsResult", 
				stub.queryRoleSetRelations(RandomUtils.nextInt(), 
						3000, pageIndex, pageSize, option));
		return 200;
	}

}
