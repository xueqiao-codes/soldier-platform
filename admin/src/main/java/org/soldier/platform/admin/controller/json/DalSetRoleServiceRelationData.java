package org.soldier.platform.admin.controller.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.admin.data.DataListFetcher;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.route.dao.RouteInfo;

public class DalSetRoleServiceRelationData extends CPageController {

	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) throws Exception {
		DalSetDaoStub dalSetStub = new DalSetDaoStub();
		QueryRoleServiceRelationOption option = new QueryRoleServiceRelationOption();
		
		int serviceKey = parameter("serviceKey", -1);
		if (serviceKey > 0) {
			option.setServiceKey(serviceKey);
		}
		String roleName = parameter("roleName", "").trim();
		if (!roleName.isEmpty()) {
			option.setRoleName(roleName);
		}
		String interfaceName = parameter("interfaceName", "").trim();
		if (!interfaceName.isEmpty()) {
			if (interfaceName.equals("*")) {
				option.setInterfaceName("");
			} else {
				option.setInterfaceName(interfaceName);
			}
		}
		
		Map<String, RouteInfo> routeMap = new HashMap<String, RouteInfo>();
		List<RouteInfo> routeList = DataListFetcher.getAllRouteList();
		for (RouteInfo route : routeList) {
			routeMap.put(String.valueOf(route.getServiceKey()), route);
		}
		dataModel.put("routeMap", routeMap);
		
		dataModel.put("itemsResult", 
				dalSetStub.queryRoleServiceRelations(RandomUtils.nextInt(),
						3000, pageIndex, pageSize, option));
		
		return 200;
	}

}
