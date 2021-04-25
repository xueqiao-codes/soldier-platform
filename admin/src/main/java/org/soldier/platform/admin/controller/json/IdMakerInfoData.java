package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoStub;

public class IdMakerInfoData extends CPageController {

	@Override
	public int doPageModel(Map<Object, Object> dataModel, int pageIndex, int pageSize) {
		IdMakerQueryOption option = new IdMakerQueryOption();
		String type = parameter("type");
		if (type != null && !type.trim().isEmpty()) {
			option.setType(Integer.valueOf(type));
		}
		String desc = parameter("remark");
		if (desc != null && !desc.trim().isEmpty()) {
			option.setDesc(desc);
		}
		
		IdMakerDaoStub stub = new IdMakerDaoStub();
		try {
			dataModel.put("itemsResult", 
					stub.queryIdMakerTypeInfoList(RandomUtils.nextInt(),
							2000, pageIndex, pageSize, option));
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		return 200;
	}

}
