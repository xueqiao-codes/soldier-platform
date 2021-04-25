package org.soldier.platform.machine.server.persistance;

import java.util.Arrays;
import java.util.List;

import org.soldier.platform.falcon.api.data.req.UpdateGraphReq;
import org.soldier.platform.falcon.api.data.resp.GraphListResp;
import org.soldier.platform.falcon.api.data.resp.GraphResp;
import org.soldier.platform.falcon.api.data.resp.HostGroupInfoResp;
import org.soldier.platform.falcon.api.data.resp.HostGroupResp;
import org.soldier.platform.falcon.api.data.resp.HostResp;
import org.soldier.platform.falcon.api.data.resp.IdResp;
import org.soldier.platform.falcon.api.data.resp.ScreenResp;
import org.soldier.platform.falcon.api.data.resp.TemplatesOfHostGroupResp;
import org.soldier.platform.falcon.api.method.graph.CreateGraphMethod;
import org.soldier.platform.falcon.api.method.graph.UpdateGraphMethod;
import org.soldier.platform.falcon.api.method.hostgroup.CreateHostGroupMethod;
import org.soldier.platform.falcon.api.method.screen.CreateDashboardScreenMethod;
import org.soldier.platform.machine.Machine;

public class MachineUpdateMonitor extends MachineMonitor {

	private static final String GRAPH_CPU_NAME = "CPU监控";
	private static final String GRAPH_MEM_NAME = "内存监控";
	private static final String GRAPH_DF_NAME = "磁盘监控";
	private static final String GRAPH_NET_NAME = "网络监控";
	
	private ScreenResp screen;
	
	private GraphResp graphCPU = null;
	private GraphResp graphMEM = null;
	private GraphResp graphDF = null;
	private GraphResp graphNET = null;
	
	public MachineUpdateMonitor(Machine opMachine) throws Exception {
		super(opMachine);
	}
	
	public void update() throws Exception{
		// 添加machine.${hostName}的host group, 同时将机器绑定
		if (super.machineSingleHostGroupId < 0) {
			super.machineSingleHostGroupId = createHostGroup(super.getMachineSingleHostGroupName());
		}
		HostGroupInfoResp hostGroupInfo = getHostGroupInfoById(super.machineSingleHostGroupId);
		bindHostsToHostGroup(hostGroupInfo, super.machine.getHostName());
		
		// 绑定基础的报警规则，如果已经有继承重写，则无视
		TemplatesOfHostGroupResp templatesInfo = getTemplatesofHostGroup(super.machineSingleHostGroupId);
		if (!super.isTemplateExistsInHostGroup(super.machineBasicAlarmTemplate, templatesInfo)) {
			super.bindTemplateToHostGroup(super.machineBasicAlarmTemplate.getId(), super.machineSingleHostGroupId);
		}
		
		// machine.all中添加host
		HostGroupInfoResp machineAllHostGroupInfo = getHostGroupInfoById(super.machineAllHostGroupId);
		if (machineAllHostGroupInfo.getHosts() == null || machineAllHostGroupInfo.getHosts().isEmpty()) {
			super.addHostsToHostGroup(machineAllHostGroupInfo, super.machine.getHostName());
		} else {
			boolean existed = false;
			for (HostResp host : machineAllHostGroupInfo.getHosts()) {
				if (host.getHostname().equals(super.machine.getHostName())) {
					existed = true;
					break;
				}
			}
			if (!existed) {
				super.addHostsToHostGroup(machineAllHostGroupInfo, super.machine.getHostName());
			}
		}
		
		updateScreen();
	}
	
	private void updateScreen() throws Exception {
		// 监控视图
		screen = screenId(super.getScreens(super.machineTopScreen.getId()), super.machine.getHostName());
		if (screen == null) {
			screen = createScreen(super.machineTopScreen.getId(), super.machine.getHostName());
		} 
		
		// 获取所有的Graphs
		GraphListResp graphList = super.getScreenGraphs(screen.getId());
		if (graphList.getGraphList() != null) {
			for (GraphResp graph : graphList.getGraphList()) {
				boolean shouldDelete = true;
				if (GRAPH_CPU_NAME.equals(graph.getTitle())) {
					if (graphCPU == null) {
						graphCPU = graph;
						shouldDelete = false;
					} 
				} else if (GRAPH_MEM_NAME.equals(graph.getTitle())) {
					if (graphMEM == null) {
						graphMEM = graph;
						shouldDelete = false;
					} 
				} else if (GRAPH_DF_NAME.equals(graph.getTitle())) {
					if (graphDF == null) {
						graphDF = graph;
						shouldDelete = false;
					} 
				} else if (GRAPH_NET_NAME.equals(graph.getTitle())) {
					if (graphNET == null) {
						graphNET = graph;
						shouldDelete = false;
					}
				} 
				if (shouldDelete) {
					deleteGraph(graph.getGraphId());
				}
			}
		}
		
		// CPU Graph
		createOrUpdateMachineGraph((graphCPU != null) ? graphCPU.getGraphId() : -1, GRAPH_CPU_NAME
				, Arrays.asList("cpu.idle", "cpu.iowait", "cpu.irq"
						, "cpu.switches", "cpu.system", "cpu.user"));
		
		// MEM Graph
		createOrUpdateMachineGraph((graphMEM != null) ? graphMEM.getGraphId() : -1 , GRAPH_MEM_NAME
				, Arrays.asList(
				        "mem.memtotal", "mem.memfree", 
				        "mem.memfree.percent", "mem.swapused.percent"));
		
		// DF Graph
		createOrUpdateMachineGraph((graphDF != null ) ? graphDF.getGraphId() : -1, GRAPH_DF_NAME
				, Arrays.asList(
						"df.bytes.free.percent/fstype=ext3,mount=/" 
						, "df.bytes.free.percent/fstype=ext4,mount=/"
						, "disk.io.await/device=vda"
						, "disk.io.read_requests/device=vda"
						, "disk.io.write_requests/device=vda"));
		
		// NET Graph
		createOrUpdateMachineGraph((graphNET != null) ? graphNET.getGraphId() : -1, GRAPH_NET_NAME
				, Arrays.asList(
						"net.if.in.bytes/iface=eth0"
						, "net.if.in.compressed/iface=eth0"
						, "net.if.in.dropped/iface=eth0"
						, "net.if.in.errors/iface=eth0"
						, "net.if.in.fifo.errs/iface=eth0"
						, "net.if.in.frame.errs/iface=eth0"
						, "net.if.in.multicast/iface=eth0"
						, "net.if.in.packets/iface=eth0"
						, "net.if.out.bytes/iface=eth0"
						, "net.if.out.carrier.errs/iface=eth0"
						, "net.if.out.collisions/iface=eth0"
						, "net.if.out.compressed/iface=eth0"
						, "net.if.out.dropped/iface=eth0"
						, "net.if.out.errors/iface=eth0"
						, "net.if.out.fifo.errs/iface=eth0"
						, "net.if.out.packets/iface=eth0"
						, "net.if.total.bytes/iface=eth0"
						, "net.if.total.dropped/iface=eth0"
						, "net.if.total.errors/iface=eth0"
						, "net.if.total.packets/iface=eth0"));
	}
	
	private void createOrUpdateMachineGraph(int graphId, String graphName, List<String> counters) throws Exception {
		UpdateGraphReq req = new UpdateGraphReq();
		req.setGraphId(graphId);
		req.setCounters(counters);
		req.setScreenId(screen.getId());
		req.setTitle(graphName);
		req.setEndpoints(Arrays.asList(machine.getHostName()));
		
		createOrUpdateGraph(req);
	}
	
	private ScreenResp createScreen(int pid, String name) throws Exception {
		CreateDashboardScreenMethod method = new CreateDashboardScreenMethod();
		
		method.setServerName("root");
		method.getReq().setName(name);
		method.getReq().setPid(pid);
		
		ScreenResp screen = method.call(ScreenResp.class);
		if (screen.getHttpStatusCode() != 200) {
			throw new Exception("create screen failed! http status=" + screen.getHttpStatusCode());
		}
		return screen;
	}
	
	private IdResp createOrUpdateGraph(UpdateGraphReq req) throws Exception {
		IdResp resp = null;
		
		if (req.getGraphId() <= 0) {
			CreateGraphMethod createMethod = new CreateGraphMethod();
			
			createMethod.setServerName("root");
			createMethod.getReq().setScreenId(req.getScreenId());
			createMethod.getReq().setCounters(req.getCounters());
			createMethod.getReq().setMethod(req.getMethod());
			createMethod.getReq().setGraphType(req.getGraphType());
			createMethod.getReq().setTitle(req.getTitle());
			createMethod.getReq().setEndpoints(req.getEndpoints());
			createMethod.getReq().setTimespan(req.getTimespan());
			
			resp = createMethod.call(IdResp.class);
		} else {
			UpdateGraphMethod updateMethod = new UpdateGraphMethod();
			
			updateMethod.setServerName("root");
			updateMethod.getReq().setGraphId(req.getGraphId());
			updateMethod.getReq().setScreenId(req.getScreenId());
			updateMethod.getReq().setCounters(req.getCounters());
			updateMethod.getReq().setMethod(req.getMethod());
			updateMethod.getReq().setGraphType(req.getGraphType());
			updateMethod.getReq().setTitle(req.getTitle());
			updateMethod.getReq().setEndpoints(req.getEndpoints());
			updateMethod.getReq().setTimespan(req.getTimespan());
			
			resp = updateMethod.call(IdResp.class);
		}
		
		if(resp.getHttpStatusCode() != 200) {
			throw new Exception("create or update graph " + req.getTitle() + " failed, status=" + resp.getHttpStatusCode());
		}
		return resp;
	}
	
	private int createHostGroup(String hostGroupName) throws Exception{
		CreateHostGroupMethod method = new CreateHostGroupMethod();
		
		method.setServerName("root");
		method.getReq().setName(hostGroupName);
		
		HostGroupResp createResp = method.call(HostGroupResp.class);
		if (createResp.getHttpStatusCode() != 200) {
			throw new Exception("hostGroup create failed, http status=" + createResp.getHttpStatusCode());
		}
		return createResp.getId();
	}

	public ScreenResp getScreen() {
		return screen;
	}


}
