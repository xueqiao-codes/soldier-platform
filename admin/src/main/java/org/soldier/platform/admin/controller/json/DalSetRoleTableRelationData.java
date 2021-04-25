package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetRoleTableRelationData extends CPageController {

	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) throws Exception {
		QueryRoleTableRelationOption option = new QueryRoleTableRelationOption();
		
		String roleName = parameter("roleName", "").trim();
		if (!roleName.isEmpty()) {
			option.setRoleName(roleName);
		}
		String tablePrefixName = parameter("tablePrefixName", "").trim();
		if (!tablePrefixName.isEmpty()) {
			option.setTablePrefixName(tablePrefixName);
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		dataModel.put("itemsResult",
				stub.queryTableRoleRelations(RandomUtils.nextInt(),
						200, pageIndex, pageSize, option));
		
		return 200;
	}

}
