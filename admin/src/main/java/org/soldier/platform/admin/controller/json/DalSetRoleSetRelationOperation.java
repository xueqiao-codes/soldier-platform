package org.soldier.platform.admin.controller.json;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.RoleRelatedOption;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.TypeInSet;
import org.soldier.platform.dal_set.dao.client.DalSetDaoAsyncStub;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.AsyncCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class DalSetRoleSetRelationOperation extends CJsonAjaxOpController {
	private RoleSetRelation checkRelation() throws AjaxOpException {
		RoleSetRelation relation = new RoleSetRelation();
		
		String roleName = parameter("roleName", "").trim();
		if (roleName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名未选择");
		}
		relation.setRoleName(roleName);
		
		int setIndex = parameter("setIndex", -1);
		if (setIndex < 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"SET索引错误");
		}
		relation.setSetIndex(setIndex);
		
		TypeInSet typeInSet = TypeInSet.valueOf(parameter("typeInSet", "").trim());
		if (typeInSet == null) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "错误的部署类型");
		}
		relation.setTypeInSet(typeInSet);
		
		String hostName = parameter("hostName", "").trim();
		if (hostName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"主机名为选择");
		}
		relation.setHostName(hostName);
		
		return relation;
	}
	
	@Override
	protected void doAdd() throws AjaxOpException, ErrorInfo, TException {
		RoleSetRelation relation = checkRelation();
		int weight = parameter("weight", -1);
		if (weight <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"权重值错误");
		}
		relation.setWeight(weight);
		
		QueryDalSetRoleOption queryRoleOption = new QueryDalSetRoleOption();
		queryRoleOption.setRoleName(relation.getRoleName());
		
		QueryDalSetHostOption queryHostOption = new QueryDalSetHostOption();
		queryHostOption.setName(relation.getHostName());
		
		QueryRoleSetRelationOption queryRoleSetRelatioOptionn = new QueryRoleSetRelationOption();
		queryRoleSetRelatioOptionn.setRoleRelatedOption(new RoleRelatedOption(relation.getRoleName()));
		
		AsyncCallback<DalSetRoleList> queryRoleListCallback = new AsyncCallback<DalSetRoleList>();
		AsyncCallback<DalSetHostList> queryHostListCallback = new AsyncCallback<DalSetHostList>();
		AsyncCallback<RoleSetRelationList> queryRoleSetRelationCallback 
				= new AsyncCallback<RoleSetRelationList>();
		
		AsyncCallRunner runner = new AsyncCallRunner();
		DalSetDaoAsyncStub asyncStub = new DalSetDaoAsyncStub();
		
		runner.start();
		asyncStub.add_queryDalSetRolesCall(runner, RandomUtils.nextInt(), 
				2000, 0, 10, queryRoleOption, queryRoleListCallback);
		asyncStub.add_queryDalSetHostsCall(runner, RandomUtils.nextInt(),
				2000, 0, 10, queryHostOption, queryHostListCallback);
		asyncStub.add_queryRoleSetRelationsCall(runner,
				RandomUtils.nextInt(), 2000, 0, Integer.MAX_VALUE, 
				queryRoleSetRelatioOptionn, queryRoleSetRelationCallback);
		runner.run(3000);
		
		if (queryRoleListCallback.isError() || queryHostListCallback.isError() 
				|| queryRoleSetRelationCallback.isError()) {
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (queryRoleListCallback.getResponseData().getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"角色名称不存在");
		}
		if (queryHostListCallback.getResponseData().getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"主机名称不存在");
		}
		
		List<RoleSetRelation> allSetRelationList = queryRoleSetRelationCallback.getResponseData().getResultList();
		int maxSetIndex = -1;
		for (RoleSetRelation oldRelation : allSetRelationList) {
			if (oldRelation.getSetIndex() > maxSetIndex) {
				maxSetIndex = oldRelation.getSetIndex();
			}
			if (relation.getHostName().equals(oldRelation.getHostName())) {
				throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
						"角色中存在相同的实例");
			}
			if (relation.getSetIndex() == oldRelation.getSetIndex()) {
				if (relation.getTypeInSet()== TypeInSet.Master 
					&& oldRelation.getTypeInSet() == TypeInSet.Master) {
					throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
							"重复的Master部署");
				}
			}
		}
		if (relation.getSetIndex() > (maxSetIndex + 1)) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"SET索引过大");
		}
		
		new DalSetDaoStub().addRoleSetRelation(RandomUtils.nextInt(), 1500, relation);
	}

	@Override
	protected void doUpdate() throws AjaxOpException, ErrorInfo, TException {
		RoleSetRelation relation = checkRelation();
		
		int weight = parameter("weight", -1);
		if (weight <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"权重值错误");
		}
		relation.setWeight(weight);
		
		new DalSetDaoStub().updateRoleSetRelation(RandomUtils.nextInt(),
				1500, relation);
	}
	
	@Override
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		new DalSetDaoStub().deleteRoleSetRelation(RandomUtils.nextInt(), 1500, checkRelation());
	}

}
