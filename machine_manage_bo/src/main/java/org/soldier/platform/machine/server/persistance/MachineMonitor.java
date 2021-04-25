package org.soldier.platform.machine.server.persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.falcon.api.data.resp.BindTplResp;
import org.soldier.platform.falcon.api.data.resp.GraphListResp;
import org.soldier.platform.falcon.api.data.resp.HostGroupInfoResp;
import org.soldier.platform.falcon.api.data.resp.HostGroupListResp;
import org.soldier.platform.falcon.api.data.resp.HostResp;
import org.soldier.platform.falcon.api.data.resp.IdResp;
import org.soldier.platform.falcon.api.data.resp.MessageResp;
import org.soldier.platform.falcon.api.data.resp.ScreenResp;
import org.soldier.platform.falcon.api.data.resp.ScreensListResp;
import org.soldier.platform.falcon.api.data.resp.TemplateListResp;
import org.soldier.platform.falcon.api.data.resp.TemplateResp;
import org.soldier.platform.falcon.api.data.resp.TemplatesOfHostGroupResp;
import org.soldier.platform.falcon.api.method.graph.DeleteGraphMethod;
import org.soldier.platform.falcon.api.method.graph.GetGraphsMethod;
import org.soldier.platform.falcon.api.method.hostgroup.BindTplMethod;
import org.soldier.platform.falcon.api.method.hostgroup.GetHostGroupInfoByIdMethod;
import org.soldier.platform.falcon.api.method.hostgroup.GetTemplatesOfHostGroupMethod;
import org.soldier.platform.falcon.api.method.hostgroup.HostGroupListMethod;
import org.soldier.platform.falcon.api.method.hostgroup.ReplaceHostsToHostGroupMethod;
import org.soldier.platform.falcon.api.method.screen.GetDashboardScreensMethod;
import org.soldier.platform.falcon.api.method.template.TemplateListMethod;
import org.soldier.platform.machine.Machine;

public class MachineMonitor {
	
	protected static final String TEMPLATE_MACHINE_BASIC = "alarm.machine.common.basic";
	protected static final String MACHINE_ALL_GROUP_NAME = "machine.all";
	
	protected static final String MACHINE_SCREEN_PARENT_NAME = "机器监控";
	
	
	protected Machine machine;
	protected int machineSingleHostGroupId = -1;
	protected int machineAllHostGroupId = -1;
	protected ScreenResp machineTopScreen;
	protected TemplateResp machineBasicAlarmTemplate = null;
	
	protected MachineMonitor(Machine opMachine)  throws Exception {
		this.machine = opMachine;
		this.machineSingleHostGroupId = getHostGroupId(getMachineSingleHostGroupName());
		this.machineAllHostGroupId = getHostGroupId(MACHINE_ALL_GROUP_NAME);
		this.machineBasicAlarmTemplate = getTemplate(TEMPLATE_MACHINE_BASIC);
		this.machineTopScreen = screenId(getScreens(0), MACHINE_SCREEN_PARENT_NAME);
		if (machineTopScreen == null) {
			throw new Exception(MACHINE_SCREEN_PARENT_NAME + " is not existed");
		}
		
		AppLog.i("hostName=" + machine.getHostName() + ", machineSingleHostGroupId=" + machineSingleHostGroupId
				+ ", machineAllHostGroupId=" + machineAllHostGroupId + ", machineBasicAlarmTemplate" 
				+ ", machineBasicAlarmTemplate=" +  machineBasicAlarmTemplate);
	}
	
	protected String getMachineSingleHostGroupName() {
		return "machine." + machine.getHostName();
	}
	
	protected ScreenResp screenId(ScreensListResp screens, String name) {
		if (screens == null || screens.getScreenList().isEmpty()) {
			return null;
		}
		
		for (ScreenResp resp : screens.getScreenList()) {
			if (resp.getName().equals(name)) {
				return resp;
			}
		}
		return null;
	}
	
	protected ScreensListResp getScreens(int pid) throws Exception {
		GetDashboardScreensMethod method = new GetDashboardScreensMethod();
		
		method.setServerName("root");
		method.getReq().setPid(pid);
		
		ScreensListResp resp = method.call(ScreensListResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("getScreens failed, http status=" + resp.getHttpStatusCode());
		}
		return resp;
	}
	
	protected int getHostGroupId(String hostGroupName) throws Exception {
		HostGroupListMethod hostGroupList = new HostGroupListMethod();
		hostGroupList.setServerName("root");
		hostGroupList.setQuery(getWholeWordReg(hostGroupName));
		
		HostGroupListResp listResp = hostGroupList.call(HostGroupListResp.class);
		if (listResp.getHttpStatusCode() != 200) {
			throw new Exception("get host group list failed, http status=" + listResp.getHttpStatusCode());
		} 
		
		if (listResp.getHostGroupList() == null || listResp.getHostGroupList().isEmpty()) {
			return -1;
		}
		if (listResp.getHostGroupList().size() > 1) {
			throw new Exception("duplicate host group for " + hostGroupName);
		}
		return listResp.getHostGroupList().get(0).getId();
	}
	
	protected HostGroupInfoResp getHostGroupInfoById(int hostGroupId) throws Exception {
		GetHostGroupInfoByIdMethod method = new GetHostGroupInfoByIdMethod();
		
		method.setServerName("root");
		method.setHostGroupId(hostGroupId);
		
		HostGroupInfoResp resp = method.call(HostGroupInfoResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("get host group info failed, http status=" + resp.getHttpStatusCode());
		}
		return resp;
	}
	
	protected void bindHostsToHostGroup(HostGroupInfoResp hostGroupInfo, String... hosts) throws Exception {
		ReplaceHostsToHostGroupMethod method = new ReplaceHostsToHostGroupMethod();
		method.setServerName("root");
			
		method.getReq().setHostgroupId(hostGroupInfo.getHostgroup().getId());
		method.getReq().setHosts(Arrays.asList(hosts));
			
		MessageResp resp = method.call(MessageResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("bind hosts failed, http status=" + resp.getHttpStatusCode());
		}
	}
	
	protected void addHostsToHostGroup(HostGroupInfoResp hostGroupInfo, String... newHosts) throws Exception {
		ReplaceHostsToHostGroupMethod method = new ReplaceHostsToHostGroupMethod();
		method.setServerName("root");
		
		method.getReq().setHostgroupId(hostGroupInfo.getHostgroup().getId());
		List<String> hostsName = new ArrayList<String>();
		if (hostGroupInfo.getHosts() != null) {
			for (HostResp host : hostGroupInfo.getHosts()) {
				hostsName.add(host.getHostname());
			}
		}
		for (String newHost : newHosts) {
			hostsName.add(newHost);
		}
		method.getReq().setHosts(hostsName);
		
		MessageResp resp = method.call(MessageResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("bind hosts failed, http status=" + resp.getHttpStatusCode());
		}
	}
	
	protected boolean isTemplateExistsInHostGroup(TemplateResp checkTemplate, TemplatesOfHostGroupResp templatesInfo) {
		if (templatesInfo.getTemplates() == null || templatesInfo.getTemplates().isEmpty()) {
			return false;
		}
		
		for (TemplateResp template : templatesInfo.getTemplates()) {
			if (template.getId() == checkTemplate.getId()) {
				return true;
			}
			if (template.getParentId() == checkTemplate.getId()) {
				return true;
			}
		}
		
		return false;
	}
	
	protected TemplateResp getTemplate(String templateName) throws Exception {
		TemplateListMethod method = new TemplateListMethod();
		
		method.setServerName("root");
		method.setQuery(getWholeWordReg(templateName));
		TemplateListResp resp = method.call(TemplateListResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("get machine basic template failed, http status=" + resp.getHttpStatusCode());
		}
		if (resp.getTemplateList() == null
			|| resp.getTemplateList().size() != 1 
			|| !templateName.equals(resp.getTemplateList().get(0).getTemplate().getTplName())) {
			throw new Exception("template " + templateName + " is not found!");
		}
		return resp.getTemplateList().get(0).getTemplate();
	}
	
	protected TemplatesOfHostGroupResp getTemplatesofHostGroup(int hostGroupId) throws Exception {
		GetTemplatesOfHostGroupMethod method = new GetTemplatesOfHostGroupMethod();
		
		method.setServerName("root");
		method.setHostgroupId(hostGroupId);
		
		TemplatesOfHostGroupResp resp = method.call(TemplatesOfHostGroupResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("get templates of host group failed, http status=" + resp.getHttpStatusCode());
		}
		return resp;
	}
	
	protected void bindTemplateToHostGroup(int tplId, int groupId) throws Exception {
		BindTplMethod method = new BindTplMethod();
		
		method.setServerName("root");
		method.getReq().setTplId(tplId);
		method.getReq().setGrpId(groupId);
		
		BindTplResp resp = method.call(BindTplResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("bind basic tpl failed, http status=" + resp.getHttpStatusCode());
		}
	}
	
	protected GraphListResp getScreenGraphs(int screenId) throws Exception {
		GetGraphsMethod method = new GetGraphsMethod();
		
		method.setServerName("root");
		method.setScreenId(screenId);
		
		GraphListResp resp = method.call(GraphListResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("get graph list failed, http status=" + resp.getHttpStatusCode());
		}
		return resp;
	}
	
	protected void deleteGraph(int graphId) throws Exception {
		DeleteGraphMethod method = new DeleteGraphMethod();
		
		method.setServerName("root");
		method.setGraphId(graphId);
		
		IdResp resp = method.call(IdResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("delete graph failed, http status=" + resp.getHttpStatusCode());
		}
	}
	
	public static String getWholeWordReg(String query) {
		return ("^" + query + "$").replace(".", "\\.");
	}
	
}
