package org.soldier.platform.admin.controller.json;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DbType;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleRelatedOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoAsyncStub;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.AsyncCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class DalSetRoleOperation extends CJsonAjaxOpController {
	private String checkRoleName() throws AjaxOpException {
		String roleName = parameter("roleName", "").trim();
		if (roleName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名未填写");
		}
		return roleName;
	}
	
	private String checkDbName() throws AjaxOpException {
		String dbName = parameter("dbName", "").trim();
		if (dbName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"db名称未填写");
		}
		return dbName;
	}
	
	private DbType checkDbType() throws AjaxOpException {
		String dbType = parameter("dbType", "").trim();
		if (dbType.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"db类型未选择");
		}
		DbType resultType = DbType.valueOf(dbType);
		if (resultType == null) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"db类型值错误");
		}
		return resultType;
	}
	
	private String checkDesc() throws AjaxOpException {
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"备注长度过短, 不得少于6个字符");
		}
		return desc;
	}
	
	
	@Override
	protected void doAdd() throws AjaxOpException, ErrorInfo, TException {
		DalSetRole role = new DalSetRole();
		role.setRoleName(checkRoleName());
		role.setDbName(checkDbName());
		role.setDbType(checkDbType());
		role.setDesc(checkDesc());
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryDalSetRoleOption option = new QueryDalSetRoleOption();
		option.setRoleName(role.getRoleName());
		
		DalSetRoleList roleList = stub.queryDalSetRoles(role.getRoleName().hashCode(), 
				2000, 0, 10, option);
		if (roleList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名称已经存在");
		}
		
		stub.addDalSetRole(role.getRoleName().hashCode(), 1500, role);
	}

	@Override
	protected void doUpdate() throws AjaxOpException, ErrorInfo, TException {
		DalSetRole role = new DalSetRole();
		role.setRoleName(checkRoleName());
		role.setDbName(checkDbName());
		role.setDbType(checkDbType());
		role.setDesc(checkDesc());
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryDalSetRoleOption option = new QueryDalSetRoleOption();
		option.setRoleName(role.getRoleName());
		
		DalSetRoleList roleList = stub.queryDalSetRoles(role.getRoleName().hashCode(), 
				2000, 0, 10, option);
		if (roleList.getTotalNum() == 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"更新的角色名称不存在");
		}
		
		stub.updateDalSetRole(role.getRoleName().hashCode(), 1500, role);

	}
	
	@Override
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		String roleName = checkRoleName();
		
		QueryRoleTableRelationOption queryRoleTableRelationOption 
				= new QueryRoleTableRelationOption();
		queryRoleTableRelationOption.setRoleName(roleName);
		
		QueryRoleSetRelationOption queryRoleSetRelationOption
				= new QueryRoleSetRelationOption();
		queryRoleSetRelationOption.setRoleRelatedOption(new RoleRelatedOption(roleName));
		
		QueryRoleServiceRelationOption queryRoleServiceRelationOption
				= new QueryRoleServiceRelationOption();
		queryRoleServiceRelationOption.setRoleName(roleName);
		
		AsyncCallback<RoleTableRelationList> queryRoleTableRelationCallback
				= new AsyncCallback<RoleTableRelationList>();
		AsyncCallback<RoleSetRelationList> queryRoleSetRelationCallback
				= new AsyncCallback<RoleSetRelationList>();
		AsyncCallback<RoleServiceRelationList> queryRoleServiceRelationCallback
				= new AsyncCallback<RoleServiceRelationList>();
		
		AsyncCallRunner runner = new AsyncCallRunner();
		DalSetDaoAsyncStub asyncStub = new DalSetDaoAsyncStub();
		runner.start();
		asyncStub.add_queryTableRoleRelationsCall(runner,
				RandomUtils.nextInt(), 2000, 0, 10,
				queryRoleTableRelationOption, queryRoleTableRelationCallback);
		asyncStub.add_queryRoleSetRelationsCall(runner,
				RandomUtils.nextInt(), 2000, 0, 10, queryRoleSetRelationOption, queryRoleSetRelationCallback);
		asyncStub.add_queryRoleServiceRelationsCall(runner, 
				RandomUtils.nextInt(), 2000, 0, 10, 
				queryRoleServiceRelationOption, queryRoleServiceRelationCallback);
		runner.run(3000);
		
		if (queryRoleTableRelationCallback.isError() || queryRoleSetRelationCallback.isError()
				|| queryRoleServiceRelationCallback.isError()) {
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (queryRoleTableRelationCallback.getResponseData().getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"该角色具备表关联，无法删除");
		}
		if (queryRoleSetRelationCallback.getResponseData().getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"该角色具备SET部署，无法删除");
		}
		if (queryRoleServiceRelationCallback.getResponseData().getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"该角色具备服务关联关系，无法删除");
		}
		
		new DalSetDaoStub().deleteDalSetRole(RandomUtils.nextInt(),
				1500, roleName);
		
	}

}
