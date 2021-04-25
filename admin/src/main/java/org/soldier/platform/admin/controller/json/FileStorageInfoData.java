package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.client.FileStorageInfoDaoStub;

public class FileStorageInfoData extends CPageController {
	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) throws Exception {
		QueryStorageInfoListOption option = new QueryStorageInfoListOption();
		
		String storageKey = parameter("storageKey", "").trim();
		if (!storageKey.isEmpty()) {
			option.setStorageKey(storageKey);
		}
		
		String desc = parameter("remark", "").trim();
		if (!desc.isEmpty()) {
			option.setDesc(desc);
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		try {
			dataModel.put("itemsResult", 
					new FileStorageInfoDaoStub().queryStorageList(
							RandomUtils.nextInt(),
							2000, pageIndex, pageSize, option));
		} catch(Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		
		
		return 200;
	}

}
