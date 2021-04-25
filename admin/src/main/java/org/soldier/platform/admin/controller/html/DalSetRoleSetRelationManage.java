package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.admin.data.DataListFetcher;

public class DalSetRoleSetRelationManage extends CController {
	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		dataModel.put("roles", DataListFetcher.getAllRoleList());
		dataModel.put("hosts", DataListFetcher.getAllHostList());
		return 200;
	}

}
