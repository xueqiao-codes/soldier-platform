package org.soldier.platform.admin.controller;

import java.util.Map;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public abstract class CAjaxController extends CController {

	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		try {
			doAjax(dataModel);
			
			dataModel.put("errorCode", 0);
			dataModel.put("errorMsg", "success");
		} catch (AjaxOpException e) {
			dataModel.put("errorCode", e.getErrorCode());
			dataModel.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			dataModel.put("errorCode", ErrorCodeOuter.SERVER_BUSY.getErrorCode());
			dataModel.put("errorMsg", ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		return 200;
	}
	
	protected abstract void doAjax(Map<Object, Object> dataModel) throws AjaxOpException, ErrorInfo, TException;

}
