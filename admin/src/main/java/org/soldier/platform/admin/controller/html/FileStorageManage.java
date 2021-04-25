package org.soldier.platform.admin.controller.html;

import java.util.Map;

import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.file_storage_info.dao.AccessAttribute;

public class FileStorageManage extends CController {

	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		dataModel.put("supportAccessAttributes", AccessAttribute.values());
		return 200;
	}

}
