package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.admin.data.DataListFetcher;
import org.soldier.platform.dal_set.dao.ServiceRelatedType;

public class DalSetRoleServiceRelationManage extends CController {
	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		dataModel.put("roles", DataListFetcher.getAllRoleList());
		dataModel.put("users", DataListFetcher.getAllUserList());
		dataModel.put("routes", DataListFetcher.getAllRouteList());
		dataModel.put("relatedTypes", ServiceRelatedType.values());
		return 200;
	}

}
