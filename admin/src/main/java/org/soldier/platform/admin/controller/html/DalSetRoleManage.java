package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;

public class DalSetRoleManage extends CController {
	@Override
	public int doModel(Map<Object, Object> dataModel) {
		dataModel.put("supportDbTypes", org.soldier.platform.dal_set.dao.DbType.values());
		
		return 200;
	}

}
