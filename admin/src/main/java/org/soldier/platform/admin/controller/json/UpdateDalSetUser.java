package org.soldier.platform.admin.controller.json;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class UpdateDalSetUser extends CJsonAjaxOpController {
	private String checkUserKey() throws AjaxOpException {
		String userKey = parameter("userId", "").trim();
		if (userKey.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"用户唯一标识参数未填");
		}
		return userKey;
	}
	
	private String checkUserName() throws AjaxOpException {
		String userName = parameter("userName", "").trim();
		if (userName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"用户名参数未填");
		}
		return userName;
	}
	
	private String checkDesc() throws AjaxOpException {
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"备注长度不得小于6");
		}
		return desc;
	}
	
	
	@Override
	protected void doAdd() throws AjaxOpException {
		String userKey = checkUserKey();
		String userName = checkUserName();
		String desc = checkDesc();
		
		String password = parameter("userPasswd", "").trim();
		if (password.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"密码长度不得小于6");
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryDalSetUserOption option = new QueryDalSetUserOption();
		option.setKey(userKey);
		
		DalSetUserList resultList = null;
		try {
			resultList = stub.queryDalSetUsers(RandomUtils.nextInt(), 2000, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"重复的唯一标识");
		}
		
		DalSetUser user = new DalSetUser();
		user.setKey(userKey);
		user.setName(userName);
		user.setPassword(password);
		user.setDesc(desc);
		
		try {
			stub.addDalSetUser(RandomUtils.nextInt(), 1500, user);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}

	@Override
	protected void doUpdate() throws AjaxOpException {
		String userKey = checkUserKey();
		String userName = checkUserName();
		String desc = checkDesc();
		
		String password = parameter("userPasswd", "").trim();
		if (!password.isEmpty() && password.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"密码长度不得小于6");
		}
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryDalSetUserOption option = new QueryDalSetUserOption();
		option.setKey(userKey);
		
		DalSetUserList resultList = null;
		try {
			resultList = stub.queryDalSetUsers(RandomUtils.nextInt(), 2000, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"唯一表示记录未找到");
		}
		
		DalSetUser user = new DalSetUser();
		user.setKey(userKey);
		user.setName(userName);
		if (!password.isEmpty()) {
			user.setPassword(password);
		}
		user.setDesc(desc);
		
		try {
			stub.updateDalSetUser(RandomUtils.nextInt(), 1500, user);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}
	
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		String userKey = checkUserKey();
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryRoleServiceRelationOption option = new QueryRoleServiceRelationOption();
		option.setUserKey(userKey);
		RoleServiceRelationList relationList = 
				stub.queryRoleServiceRelations(RandomUtils.nextInt(), 2000, 0, 10, option);
		if (relationList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"该用户仍然被用于关联服务中，不能删除");
		}
		
		stub.deleteDalSetUser(RandomUtils.nextInt(), 1500, userKey);
	}

}
