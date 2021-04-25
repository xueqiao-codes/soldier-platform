package org.soldier.platform.admin.controller.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetView extends CController {
	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		Map<String, Map<String, List<RoleSetRelation>>> roleRelationsMap
			= new HashMap<String, Map<String, List<RoleSetRelation> > >();
		DalSetDaoStub stub = new DalSetDaoStub();
		
		RoleSetRelationList roleSetRelationList = stub.queryRoleSetRelations(
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null);
		for (RoleSetRelation relation : roleSetRelationList.getResultList()) {
			Map<String, List<RoleSetRelation>> setRelations = roleRelationsMap.get(relation.getRoleName());
			if (setRelations == null) {
				setRelations = new HashMap<String, List<RoleSetRelation>>();
				roleRelationsMap.put(relation.getRoleName(), setRelations);
			}
			List<RoleSetRelation> relationListInSet = setRelations.get(String.valueOf(relation.getSetIndex()));
			if (relationListInSet == null) {
				relationListInSet = new ArrayList<RoleSetRelation>();
				setRelations.put(String.valueOf(relation.getSetIndex()), relationListInSet);
			}
			relationListInSet.add(relation);
		}
		
		dataModel.put("roleSetRelations", roleRelationsMap);
		
		Map<String, DalSetHost> hostsMap = new HashMap<String, DalSetHost>(); 
		DalSetHostList hostsList = stub.queryDalSetHosts(
				RandomUtils.nextInt(), 3000, 0, 
				Integer.MAX_VALUE, null);
		for (DalSetHost host : hostsList.getResultList()) {
			hostsMap.put(host.getName(), host);
		}
		dataModel.put("hosts", hostsMap);
		
		
		return 200;
	}
		
}
