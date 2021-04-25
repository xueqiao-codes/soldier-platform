package org.soldier.platform.machine.server.persistance;

import java.util.ArrayList;
import java.util.List;

import org.soldier.platform.falcon.api.data.resp.GraphListResp;
import org.soldier.platform.falcon.api.data.resp.GraphResp;
import org.soldier.platform.falcon.api.data.resp.HostGroupInfoResp;
import org.soldier.platform.falcon.api.data.resp.HostResp;
import org.soldier.platform.falcon.api.data.resp.MessageResp;
import org.soldier.platform.falcon.api.data.resp.ScreenResp;
import org.soldier.platform.falcon.api.method.hostgroup.DeleteHostGroupMethod;
import org.soldier.platform.falcon.api.method.screen.DeleteDashboardScreenMethod;
import org.soldier.platform.machine.Machine;

public class MachineDeleteMonitor extends MachineMonitor {

	public MachineDeleteMonitor(Machine opMachine) throws Exception {
		super(opMachine);
	}
	
	public void delete() throws Exception {
		ScreenResp screen = screenId(super.getScreens(super.machineTopScreen.getId()), super.machine.getHostName());
		if (screen != null) {
			GraphListResp graphList = super.getScreenGraphs(screen.getId());
			if (graphList.getGraphList() != null) {
				for (GraphResp graph : graphList.getGraphList()) {
					super.deleteGraph(graph.getGraphId());
				}
			}
			deleteScreen(screen.getId());
		}
		
		// 移除machine.all中对于Host的绑定
		HostGroupInfoResp machineAllHostGroupInfo = getHostGroupInfoById(super.machineAllHostGroupId);
		removeHostFromHostGroup(machineAllHostGroupInfo, super.machine.getHostName());
		
		// 移除群组中Host的绑定
		if (super.machineSingleHostGroupId >= 0) {
			deleteHostGroup(super.machineSingleHostGroupId);
		}
	}
	
	protected void removeHostFromHostGroup(HostGroupInfoResp hostGroupInfo, String removeHost) throws Exception {
		if (hostGroupInfo.getHosts() != null) {
			boolean existed = false;
			List<String> removedHosts = new ArrayList<String>();
			for (HostResp host : hostGroupInfo.getHosts()) {
				if (!host.getHostname().equals(removeHost)) {
					removedHosts.add(host.getHostname());
				} else {
					existed = true;
				}
			}
			if (existed) {
				super.bindHostsToHostGroup(hostGroupInfo, (String[])removedHosts.toArray(new String[removedHosts.size()]));
			}
		}
	}
	
	protected void deleteHostGroup(int hostGroupId) throws Exception {
		DeleteHostGroupMethod method = new DeleteHostGroupMethod();
		method.setServerName("root");
		method.setHostGroupId(hostGroupId);
		
		MessageResp resp = method.call(MessageResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("delete hostgroup failed, http status=" + resp.getHttpStatusCode());
		}
	}
	
	protected void deleteScreen(int screenId) throws Exception {
		DeleteDashboardScreenMethod method = new DeleteDashboardScreenMethod();
		method.setServerName("root");
		method.getReq().setScreenId(screenId);
		
		MessageResp resp = method.call(MessageResp.class);
		if (resp.getHttpStatusCode() != 200) {
			throw new Exception("delete screen failed, http status=" + resp.getHttpStatusCode());
		}
	}

}
