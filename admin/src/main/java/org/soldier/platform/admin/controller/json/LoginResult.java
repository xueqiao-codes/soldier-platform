package org.soldier.platform.admin.controller.json;

import java.util.Map;

import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CAjaxController;
import org.soldier.platform.admin.data.UserAccess;
import org.soldier.platform.admin.model.LoginInfo;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class LoginResult extends CAjaxController {
	@Override
	protected void doAjax(Map<Object, Object> dataModel)
			throws AjaxOpException, ErrorInfo, TException {
		String userName = parameter("username", "").trim();
		if (userName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"请填写用户名");
		}
		String password = parameter("password", "").trim();
		if (password.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"请填写密码");
		}
		
		LoginInfo info = UserAccess.login(userName, password);
		if (info == null) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"用户名或者密码错误");
		}
		dataModel.put("session", info.getSession());
	}
	
	@Override
	public boolean needCheckSession() {
		return false;
	}
}
