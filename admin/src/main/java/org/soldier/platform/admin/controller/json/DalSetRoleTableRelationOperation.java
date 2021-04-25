package org.soldier.platform.admin.controller.json;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoAsyncStub;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.AsyncCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class DalSetRoleTableRelationOperation extends CJsonAjaxOpController {
	private RoleTableRelation checkRelation() throws AjaxOpException {
		RoleTableRelation result = new RoleTableRelation();
		result.setRoleName(parameter("roleName", "").trim());
		if(result.getRoleName().isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名未填写");
		}
		result.setTablePrefixName(parameter("tablePrefixName", "").trim());
		if (result.getTablePrefixName().isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"表名前缀未填写");
		}
		return result;
	}
	
	@Override
	protected void doAdd() throws AjaxOpException, ErrorInfo, TException {
		RoleTableRelation relation = checkRelation();
		
		QueryDalSetRoleOption queryRoleOption = new QueryDalSetRoleOption();
		queryRoleOption.setRoleName(relation.getRoleName());
		
		QueryDalSetTableOption queryTableOption = new QueryDalSetTableOption();
		queryTableOption.setPrefixName(relation.getTablePrefixName());
		
		QueryRoleTableRelationOption queryRoleTableRelation = new QueryRoleTableRelationOption();
		queryRoleTableRelation.setRoleName(relation.getRoleName());
		queryRoleTableRelation.setTablePrefixName(relation.getTablePrefixName());
		
		
		AsyncCallRunner runner = new AsyncCallRunner();
		DalSetDaoAsyncStub asyncStub = new DalSetDaoAsyncStub();
		
		AsyncCallback<DalSetRoleList> queryRoleListCallback = new AsyncCallback<DalSetRoleList>();
		AsyncCallback<DalSetTableList> queryTableListCallback = new AsyncCallback<DalSetTableList>();
		AsyncCallback<RoleTableRelationList> queryRoleTableRelationCallback 
				= new AsyncCallback<RoleTableRelationList>();
		
		runner.start();
		asyncStub.add_queryDalSetRolesCall(runner,
				relation.getRoleName().hashCode(), 2000, 0, 10, queryRoleOption,
				queryRoleListCallback);
		asyncStub.add_queryDalSetTablesCall(runner, 
				relation.getTablePrefixName().hashCode(), 2000, 0, 10, queryTableOption,
				queryTableListCallback);
		asyncStub.add_queryTableRoleRelationsCall(runner, 
				RandomUtils.nextInt(), 2000, 0, 10, queryRoleTableRelation, 
				queryRoleTableRelationCallback);
		runner.run(3000);
		
		
		if (queryRoleListCallback.isError() || queryTableListCallback.isError() 
			 || queryRoleTableRelationCallback.isError()) {
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (queryRoleListCallback.getResponseData().getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名不存在");
		}
		if (queryTableListCallback.getResponseData().getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"表名前缀不存在");
		}
		if (queryRoleTableRelationCallback.getResponseData().getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"关联关系已经存在");
		}
		
		new DalSetDaoStub().addTableRoleRelation(
				relation.getRoleName().hashCode(), 1500, relation);
	}

	@Override
	protected void doUpdate() throws AjaxOpException, ErrorInfo, TException {
		throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "更新操作不需要");
	}
	
	@Override 
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		DalSetDaoStub stub = new DalSetDaoStub();
		stub.deleteTableRoleRelation(RandomUtils.nextInt(), 1500, checkRelation());
	}

}
