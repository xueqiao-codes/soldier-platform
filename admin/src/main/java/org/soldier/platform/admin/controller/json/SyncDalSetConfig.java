package org.soldier.platform.admin.controller.json;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.admin.data.DalSetXmlBuilder;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoAsyncStub;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.AsyncCallback;

import com.antiy.error_code.ErrorCodeOuter;

public class SyncDalSetConfig extends CController {
	@Override
	public int doModel(Map<Object, Object> dataModel) throws Exception {
		String dalSetConfig = "";
		try {
			dalSetConfig = doProcess();
			
			dataModel.put("errorCode", 0);
			dataModel.put("errorMsg", "Success");
		} catch (ConfigException e) {
			dataModel.put("errorCode", 1000);
			dataModel.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			dataModel.put("errorCode", ErrorCodeOuter.SERVER_BUSY.getErrorCode());
			dataModel.put("errorMsg", ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		dataModel.put("dalSetConfig", Base64.encodeBase64String(dalSetConfig.getBytes("UTF-8")));
		
		return 200;
	}
	
	private static class ConfigException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public ConfigException(String msg) {
			super(msg);
		}
		
	}
	
	public String doProcess() throws ConfigException,Exception {
		AsyncCallback<DalSetHostList> hostListCallback = new AsyncCallback<DalSetHostList>();
		AsyncCallback<DalSetUserList> userListCallback = new AsyncCallback<DalSetUserList>();
		AsyncCallback<DalSetTableList> tableListCallback = new AsyncCallback<DalSetTableList>();
		AsyncCallback<DalSetRoleList> roleListCallback = new AsyncCallback<DalSetRoleList>();
		AsyncCallback<RoleTableRelationList> roleTableRelationListCallback 
				= new AsyncCallback<RoleTableRelationList>();
		AsyncCallback<RoleSetRelationList> roleSetRelationListCallback 
				= new AsyncCallback<RoleSetRelationList>();
		AsyncCallback<RoleServiceRelationList> roleServiceRelationListCallback
				= new AsyncCallback<RoleServiceRelationList>();
		
		DalSetDaoAsyncStub asyncStub = new DalSetDaoAsyncStub();
		AsyncCallRunner runner = new AsyncCallRunner();
		runner.start();
		asyncStub.add_queryDalSetHostsCall(runner,
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, hostListCallback);
		asyncStub.add_queryDalSetUsersCall(runner,
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, userListCallback);
		asyncStub.add_queryDalSetTablesCall(runner,
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, tableListCallback);
		asyncStub.add_queryDalSetRolesCall(runner,
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, roleListCallback);
		asyncStub.add_queryTableRoleRelationsCall(runner,
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, roleTableRelationListCallback);
		asyncStub.add_queryRoleSetRelationsCall(runner, 
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, roleSetRelationListCallback);
		asyncStub.add_queryRoleServiceRelationsCall(runner,
				RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null, roleServiceRelationListCallback);
		runner.run(5000);
		
		if (hostListCallback.isError() || userListCallback.isError() 
				|| tableListCallback.isError() || roleListCallback.isError()
				|| roleTableRelationListCallback.isError() || roleSetRelationListCallback.isError()
				|| roleServiceRelationListCallback.isError()) {
			throw new Exception("AsyncCall Is Not All Success");
		}
		
		DalSetXmlBuilder builder = new DalSetXmlBuilder();
		builder.setHostsList(hostListCallback.getResponseData().getResultList());
		builder.setUsersList(userListCallback.getResponseData().getResultList());
		builder.setTablesList(tableListCallback.getResponseData().getResultList());
		builder.setRolesList(roleListCallback.getResponseData().getResultList());
		builder.setRoleTableRelationList(roleTableRelationListCallback.getResponseData().getResultList());
		builder.setRoleSetRelationList(roleSetRelationListCallback.getResponseData().getResultList());
		builder.setRoleServiceRelationList(roleServiceRelationListCallback.getResponseData().getResultList());
		
		builder.setHasDescription(false);
		String xmlConfiguration = builder.getXml(true, true);
		
		try {
			DalSetProxy.getInstance().testXmlConfiguration(
				new ByteArrayInputStream(xmlConfiguration.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new ConfigException(e.getMessage());
		}
		
		new DalSetDaoStub().updateDalSetXml(RandomUtils.nextInt(), 2000, 
				xmlConfiguration);
		
		builder.setHasDescription(true);
		return builder.getXml(false, true);
	}
	

}
