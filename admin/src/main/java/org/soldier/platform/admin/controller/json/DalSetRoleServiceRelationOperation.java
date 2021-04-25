package org.soldier.platform.admin.controller.json;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.ServiceRelatedType;
import org.soldier.platform.dal_set.dao.client.DalSetDaoAsyncStub;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.client.RouteDaoAsyncStub;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.AsyncCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class DalSetRoleServiceRelationOperation extends CJsonAjaxOpController {
	private RoleServiceRelation checkRelation() throws AjaxOpException {
		RoleServiceRelation relation = new RoleServiceRelation();
		
		int serviceKey = parameter("serviceKey", -1);
		if (serviceKey < 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"服务命令字未正确填写");
		}
		relation.setServiceKey(serviceKey);
		
		String interfaceName = parameter("interfaceName", "").trim();
		if (interfaceName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"接口名称未填写");
		}
		if (interfaceName.equals("*")) {
			relation.setInterfaceName("");
		} else {
			relation.setInterfaceName(interfaceName);
		}
		if (relation.getInterfaceName().isEmpty() && relation.getServiceKey() == 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"服务命令字为空的情况下，接口名称不能通配为*");
		}
		
		String roleName = parameter("roleName", "").trim();
		if (roleName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名称不能为空");
		}
		relation.setRoleName(roleName);
		
		return relation;
	}
	
	private String getUserKey() {
		return parameter("userKey", "").trim();
	}
	
	private ServiceRelatedType getRelatedType() {
		return ServiceRelatedType.valueOf(parameter("relatedType", "").trim());
	}
	
	@Override
	protected void doAdd() throws AjaxOpException, ErrorInfo, TException {
		RoleServiceRelation relation = checkRelation();
		
		String userKey = getUserKey();
		if (userKey.isEmpty()) {
			throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"关联用户必须填写");
		}
		relation.setUserKey(userKey);
		
		ServiceRelatedType relatedType = getRelatedType();
		if (relatedType == null) {
			throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"请选择正确的部署关系");
		}
		relation.setRelatedType(relatedType);
		
		QueryRouteOption queryRouteOption = new QueryRouteOption();
		if (relation.getServiceKey() > 0) {
			queryRouteOption.setServiceKey(relation.getServiceKey());
		}
		
		QueryDalSetRoleOption queryDalSetRoleOption = new QueryDalSetRoleOption();
		queryDalSetRoleOption.setRoleName(relation.getRoleName());
		
		QueryDalSetUserOption queryDalSetUserOption = new QueryDalSetUserOption();
		queryDalSetUserOption.setKey(relation.getUserKey());
		
		QueryRoleServiceRelationOption queryRoleServiceRelationOption 
				= new QueryRoleServiceRelationOption();
		queryRoleServiceRelationOption.setServiceKey(relation.getServiceKey());
		queryRoleServiceRelationOption.setInterfaceName(relation.getInterfaceName());
		queryRoleServiceRelationOption.setRoleName(relation.getRoleName());
		
		AsyncCallback<RouteInfoList> queryRouteListCallback = new AsyncCallback<RouteInfoList>();
		AsyncCallback<DalSetRoleList> queryRoleListCallback = new AsyncCallback<DalSetRoleList>();
		AsyncCallback<DalSetUserList> queryUserListCallback = new AsyncCallback<DalSetUserList>();
		AsyncCallback<RoleServiceRelationList> queryRoleServiceRelationListCallback 
				= new AsyncCallback<RoleServiceRelationList>();
		
		RouteDaoAsyncStub routeAsyncStub = new RouteDaoAsyncStub();
		DalSetDaoAsyncStub dalSetAsyncStub = new DalSetDaoAsyncStub();
		AsyncCallRunner runner = new AsyncCallRunner();
		runner.start();
		if (relation.getServiceKey() > 0) {
			routeAsyncStub.add_queryRouteInfoListCall(
				runner, relation.getServiceKey(),
				2000, 0, 10, queryRouteOption, queryRouteListCallback);
		}
		dalSetAsyncStub.add_queryDalSetRolesCall(runner,
				relation.getRoleName().hashCode(), 2000, 0, 10, 
				queryDalSetRoleOption, queryRoleListCallback);
		dalSetAsyncStub.add_queryDalSetUsersCall(runner, 
				relation.getUserKey().hashCode(), 2000, 0, 10, 
				queryDalSetUserOption, queryUserListCallback);
		dalSetAsyncStub.add_queryRoleServiceRelationsCall(runner,
				relation.getRoleName().hashCode(),
				2000, 0, 10, queryRoleServiceRelationOption, queryRoleServiceRelationListCallback);
		runner.run(3000);
		
		if (queryRouteListCallback.isError() || queryRoleListCallback.isError() 
				|| queryUserListCallback.isError() || queryRoleServiceRelationListCallback.isError()) {
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		
		if (relation.getServiceKey() > 0 && queryRouteListCallback.getResponseData().getTotalCount() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"服务命令字不存在");
		}
		if (queryRoleListCallback.getResponseData().getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名不存在");
		}
		if (queryUserListCallback.getResponseData().getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"用户Key不存在");
		}
		if (queryRoleServiceRelationListCallback.getResponseData().getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"关联关系已经存在");
		}
		
		new DalSetDaoStub().addRoleServiceRelation(
				relation.getRoleName().hashCode(), 2000, relation);
	}

	@Override
	protected void doUpdate() throws AjaxOpException, ErrorInfo, TException {
		RoleServiceRelation relation = checkRelation();
		DalSetDaoStub stub = new DalSetDaoStub();
		
		QueryRoleServiceRelationOption queryRoleServiceRelationOption 
			= new QueryRoleServiceRelationOption();
		queryRoleServiceRelationOption.setServiceKey(relation.getServiceKey());
		queryRoleServiceRelationOption.setInterfaceName(relation.getInterfaceName());
		queryRoleServiceRelationOption.setRoleName(relation.getRoleName());
		
		RoleServiceRelationList relationList = stub.queryRoleServiceRelations(relation.getRoleName().hashCode(),
				2000, 0, 10, queryRoleServiceRelationOption);
		if (relationList.getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"不存在该关联关系");
		}
		RoleServiceRelation oldRelation = relationList.getResultList().get(0);
		
		boolean hasChanged = false;
		String userKey = getUserKey();
		if (!userKey.isEmpty() && !userKey.equals(oldRelation.getUserKey())) {
			relation.setUserKey(userKey);
			hasChanged = true;
		}
		ServiceRelatedType relatedType = getRelatedType();
		if (relatedType != null && oldRelation.getRelatedType() != relatedType) {
			relation.setRelatedType(relatedType);
			hasChanged = true;
		}
		if (!hasChanged) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"并未修改任何字段");
		}
		
		stub.updateRoleServiceRelation(relation.getRoleName().hashCode(), 2000, relation);
	}
	
	@Override
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		RoleServiceRelation relation = checkRelation();
		new DalSetDaoStub().deleteRoleServiceRelation(RandomUtils.nextInt(), 2000, relation);
	}

}
