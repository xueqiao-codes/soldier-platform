package org.soldier.platform.admin.controller.json;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class UpdateDalSetHost extends CJsonAjaxOpController {
	private String checkHostName() throws AjaxOpException {
		String hostName = parameter("hostName", "").trim();
		if (hostName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"实例机名称错误");
		}
		return hostName;
	}
	
	private String checkDomain() throws AjaxOpException {
		String domain = parameter("hostDomain", "").trim();
		if (domain.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"域名或者IP未填写");
		}
		return domain;
	}
	
	private int checkPort() throws AjaxOpException {
		int port = parameter("hostPort", 0);
		if (port <= 0 || port >= 65535) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"端口错误, 必须介于1~65535之间");
		}
		return port;
	}
	
	private String checkDesc() throws AjaxOpException {
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"备注错误，不少于6个字符");
		}
		return desc;
	}
	
	private void checkHostNameUnique(String hostName) throws AjaxOpException {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		QueryDalSetHostOption option = new QueryDalSetHostOption();
		option.setName(hostName);
		DalSetHostList resultList = null;
		try {
			resultList = stub.queryDalSetHosts(RandomUtils.nextInt(), 
					1500, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					hostName + "是重复的实例名称");
		}
	}
	
	private void checkMachine(String domain, int port) throws AjaxOpException {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		QueryDalSetHostOption option = new QueryDalSetHostOption();
		option.setDomain(domain);
		option.setPort(port);
		DalSetHostList resultList = null;
		try {
			resultList = stub.queryDalSetHosts(RandomUtils.nextInt(), 
					1500, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					domain + ":" + port + ",重复的部署实例，参考"
							+ resultList.getResultList().get(0).getName());
		}
	}
	
	@Override
	protected void doAdd() throws AjaxOpException {
		String hostName = checkHostName();
		String domain = checkDomain();
		int port = checkPort();
		String desc = checkDesc();
		
		checkHostNameUnique(hostName);
		checkMachine(domain, port);
	
		DalSetDaoStub stub = new DalSetDaoStub();
		DalSetHost host = new DalSetHost();
		host.setName(hostName);
		host.setDomain(domain);
		host.setPort(port);
		host.setDesc(desc);
		
		try {
			stub.addDalSetHost(RandomUtils.nextInt(), 1500, host);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		
	}

	@Override
	protected void doUpdate() throws AjaxOpException {
		String hostName = checkHostName();
		String domain = checkDomain();
		int port = checkPort();
		String desc = checkDesc();
		
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryDalSetHostOption option = new QueryDalSetHostOption();
		option.setName(hostName);
		DalSetHostList resultList = null;
		try {
			resultList = stub.queryDalSetHosts(RandomUtils.nextInt(), 
					1500, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		if (resultList.getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					hostName + "不存在");
		}
		
		DalSetHost oldHost = resultList.getResultList().get(0);
		if (!oldHost.getDomain().equals(domain) || oldHost.getPort() != port) {
			checkMachine(domain, port);
		}
		
		DalSetHost newHost = new DalSetHost();
		newHost.setName(hostName);
		newHost.setDomain(domain);
		newHost.setPort(port);
		newHost.setDesc(desc);
		
		try {
			stub.updateDalSetHost(RandomUtils.nextInt(), 1500, newHost);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}
	
	@Override
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		String hostName = checkHostName();
		DalSetDaoStub stub = new DalSetDaoStub();
		
		QueryRoleSetRelationOption roleSetRelationOption = new QueryRoleSetRelationOption();
		roleSetRelationOption.setHostName(hostName);
		RoleSetRelationList hostList = 
				stub.queryRoleSetRelations(RandomUtils.nextInt(), 2000, 0, 10, roleSetRelationOption);
		if (hostList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"部署中存在此实例机，无法删除");
		}
		
		stub.deleteDalSetHost(RandomUtils.nextInt(), 2000, hostName);
	}
	
}
