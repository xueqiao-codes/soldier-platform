package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.RoleRelatedOption;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetRoleSetRelationInputInfo extends CController {
	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		String roleName = parameter("roleName", "").trim();
		if (roleName.isEmpty()) {
			throw new Exception("roleName is not set");
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryRoleSetRelationOption option = new QueryRoleSetRelationOption();
		option.setRoleRelatedOption(new RoleRelatedOption(roleName));
		
		RoleSetRelationList relationList = 
				stub.queryRoleSetRelations(RandomUtils.nextInt(), 2000, 0, Integer.MAX_VALUE, option);
		
		int maxSetIndex = -1;
		for (RoleSetRelation relation : relationList.getResultList()) {
			if (relation.getSetIndex() > maxSetIndex) {
				maxSetIndex = relation.getSetIndex();
			}
		}
		
		dataModel.put("maxSetIndex", maxSetIndex + 1);
		
		return 200;
	}

}
