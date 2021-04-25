package org.soldier.platform.admin.controller.json;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoStub;

import com.antiy.error_code.ErrorCodeOuter;

public class IdMakerOperation extends CJsonAjaxOpController {
	
	private int checkType() throws AjaxOpException {
		int type =  parameter("type", 0);
		if (type <= 0) {
			throw new AjaxOpException(
					ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "类型参数不合法");
		}
		return type;
	}
	
	private int checkId() throws AjaxOpException {
		int id = parameter("id", 0);
		if (id <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "水位参数不合法");
		}
		return id;
	}
	
	private int checkAllocSize() throws AjaxOpException {
		int allocSize = parameter("allocSize", 0);
		if (allocSize <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"每次分配大小参数不合法");
		}
		return allocSize;
	}
	
	private String checkDesc() throws AjaxOpException {
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"备注参数长度不应该小宇6个字符");
		}
		return desc;
	}
	
	@Override
	protected void doAdd() throws AjaxOpException {
		int type = checkType();
		int id = checkId();
		int allocSize = checkAllocSize();
		String desc = checkDesc();
		
		IdMakerDaoStub stub = new IdMakerDaoStub();
		IdMakerQueryOption option = new IdMakerQueryOption();
		IdMakerInfoList resultList = null;
		try {
			option.setType(type);
			resultList = stub.queryIdMakerTypeInfoList(type, 1000, 0, 20, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalCount() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"重复的注册类型");
		}
		
		IdMakerInfo addInfo = new IdMakerInfo();
		addInfo.setType(type);
		addInfo.setId(id);
		addInfo.setAllocSize(allocSize);
		addInfo.setDesc(desc);
		
		try {
			stub.registerType(type, 1000, addInfo);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}

	@Override
	protected void doUpdate() throws AjaxOpException {
		int type = checkType();
		int allocSize = checkAllocSize();
		String desc = checkDesc();
		
		IdMakerDaoStub stub = new IdMakerDaoStub();
		IdMakerQueryOption option = new IdMakerQueryOption();
		IdMakerInfoList resultList = null;
		try {
			option.setType(type);
			resultList = stub.queryIdMakerTypeInfoList(type, 1000, 0, 20, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalCount() == 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"类型不存在");
		}
		
		IdMakerInfo updateInfo = new IdMakerInfo();
		updateInfo.setType(type);
		updateInfo.setAllocSize(allocSize);
		updateInfo.setDesc(desc);
		
		try {
			stub.updateType(type, 1000, updateInfo); 
			 
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}

}
