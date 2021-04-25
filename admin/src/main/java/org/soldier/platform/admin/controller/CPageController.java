package org.soldier.platform.admin.controller;

import java.util.Map;

public abstract class CPageController extends CController {

	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		int pageIndex = parameter("page", 1) ;
		if(pageIndex > 1){
			--pageIndex;
		} else {
			pageIndex = 0;
		}
		int pageSize = parameter("rp", 20);
		if(pageSize <= 0){
			pageSize = 20;
		}
		dataModel.put("pageIndex", pageIndex);
		dataModel.put("pageSize", pageSize);
		return doPageModel(dataModel, pageIndex, pageSize);
	}
	
	protected abstract int doPageModel(Map<Object, Object> dataModel, int pageIndex, int pageSize)
		throws Exception; 
}
