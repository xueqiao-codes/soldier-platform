package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;

public class DalSetTableData extends CPageController {

	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) {
		QueryDalSetTableOption option = new QueryDalSetTableOption();
		
		String prefixName = parameter("prefixName", "").trim();
		if (!prefixName.isEmpty()) {
			option.setPrefixName(prefixName);
		}
		String desc = parameter("remark", "").trim();
		if (!desc.isEmpty()) {
			option.setDesc(desc);
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		
		try {
			dataModel.put("itemsResult", 
					stub.queryDalSetTables(RandomUtils.nextInt(),
							2000, pageIndex, pageSize, option));
		} catch(Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		
		return 200;
	}

}
