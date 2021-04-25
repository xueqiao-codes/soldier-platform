package org.soldier.platform.admin.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.NetHelper;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.MachineManageBoError;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.machine.client.MachineManageBoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeOuter;

public class MachineManage extends WebAuthController {
	
	public void show() throws Exception {
		render("machine/main.html");
	}
	
	public static class MachineListResultData {
		private List<Machine> data = new ArrayList<Machine>();

		public List<Machine> getData() {
			return data;
		}

		public void setData(List<Machine> data) {
			this.data = data;
		}
	}
	
	public void select2MachineList() throws Exception {
		String query = parameter("query", "").trim();
		int limit = parameter("limit", 10);
		if (limit < 0) {
			limit = 10;
		}
		
		MachineManageBoStub stub = new MachineManageBoStub();
		QueryMachineOption option = new QueryMachineOption();
		option.setHostNamePartical(query);
		
		MachineList machineList = stub.queryMachineList(RandomUtils.nextInt(), 3000, option, 0, limit);
		TreeMap<String, Machine> orderMap = new TreeMap<String, Machine>();
		
		for (Entry<String, Machine> e : machineList.getMachinesMap().entrySet()) {
			orderMap.put(e.getKey(), e.getValue());
		}
		
		MachineListResultData resultList = new MachineListResultData();
		for (Entry<String, Machine> orderE : orderMap.entrySet()) {
			resultList.getData().add(orderE.getValue());
		}
		
		if ("localhost".contains(query)) {
			Machine localhostMachine = new Machine();
			localhostMachine.setHostName("localhost");
			localhostMachine.setHostInnerIP("127.0.0.1");
			resultList.getData().add(localhostMachine);
		}
		
		echoJson(resultList);
	}
	
	public void listMachines() throws Exception {
		int pageIndex = parameter("page", 1) ;
		if(pageIndex > 1){
			--pageIndex;
		} else {
			pageIndex = 0;
		}
		int pageSize = parameter("rp", 20);
		if(pageSize <= 0){
			pageSize = 20;
		}
		
		MachineManageBoStub stub = new MachineManageBoStub();
		QueryMachineOption option = new QueryMachineOption();
		
		String hostNameInput = parameter("hostName", "").trim();
		if (!hostNameInput.isEmpty()) {
			option.setHostNamePartical(hostNameInput);
		}
		
		String hostNameWhole = parameter("hostNameWhole", "").trim();
		if (!hostNameWhole.isEmpty()) {
			option.setHostNames(Arrays.asList(hostNameWhole));
		}
		
		String hostInnerIPInput = parameter("hostInnerIP", "").trim();
		if (!hostInnerIPInput.isEmpty()) {
			option.setHostInnerIPS(Arrays.asList(hostInnerIPInput));
		}
		
		String hostAdminInput = parameter("hostAdmin", "").trim();
		if (!hostAdminInput.isEmpty()) {
			option.setHostAdmin(hostAdminInput);
		}
		
		String remarkInput = parameter("remark", "").trim();
		if (!remarkInput.isEmpty()) {
			option.setHostDesc(remarkInput);
		}
		
		MachineList resultMachineList 
			= stub.queryMachineList(RandomUtils.nextInt(), 3000, option, pageIndex, pageSize);
		TreeMap<String, Machine> orderMap = new TreeMap<String, Machine>();
		
		for (Entry<String, Machine> e : resultMachineList.getMachinesMap().entrySet()) {
			orderMap.put(e.getKey(), e.getValue());
		}
		
		resultMachineList.setMachinesMap(orderMap);
		
		put("pageIndex", pageIndex);
		put("itemsResult", resultMachineList);
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("machine/json/MachineInfoData.json");
	}
	
	public void deleteMachine() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteMachine(result);
		echoJson(result);
	}
	
	public void updateMachineMonitor() throws Exception {
		ErrorResult result = new ErrorResult();
		doUpdateMachineMonitor(result);
		echoJson(result);
	}
	
	private void doUpdateMachineMonitor(ErrorResult result) throws Exception {
		String hostNameInput = parameter("hostName", "").trim();
		if (hostNameInput.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("no hostName parameter");
			return ;
		}
		
		MachineManageBoStub stub = new MachineManageBoStub();
		try {
			stub.updateMachineRelatedMonitor(0, 5000, hostNameInput);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == MachineManageBoError.ERROR_NO_SUCH_MACHINE.getValue()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("机器不存在");
				return ;
			}
			throw err;
		}
	}
	
	private void doDeleteMachine(ErrorResult result) throws Exception {
		String hostNameInput = parameter("hostName", "").trim();
		if (hostNameInput.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("no hostName parameter");
			return ;
		}
		
		MachineManageBoStub stub = new MachineManageBoStub();
		try {
			stub.deleteMachine(RandomUtils.nextInt(), 3000, hostNameInput);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == MachineManageBoError.ERROR_NO_SUCH_MACHINE.getValue()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("机器不存在");
				return ;
			}
			throw err;
		}
	}
	
	public void opMachine() throws Exception{
		ErrorResult result = new ErrorResult();
		doOpMachine(result);
		echoJson(result);
	}
	
	private Machine checkMachine(ErrorResult result) throws Exception {
		Machine m = new Machine();
		
		String hostNameInput = parameter("hostName", "").trim();
		if (hostNameInput.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须输入机器名称");
			return null;
		}
		m.setHostName(hostNameInput);
		
		String hostInnerIPInput = parameter("hostInnerIP", "").trim();
		if (hostInnerIPInput.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须输入机器地址");
			return null;
		}
		if (-1 == NetHelper.AddrNet(hostInnerIPInput)) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("机器地址输入错误");
			return null;
		}
		m.setHostInnerIP(hostInnerIPInput);
		
		String hostAdminInput = parameter("hostAdmin", "").trim();
		if (hostAdminInput.isEmpty()) {
			hostAdminInput = super.getUserName();
		}
		if (hostAdminInput.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("机器负责人必须填写");
			return null;
		}
		m.setHostAdmin(hostAdminInput);  
		
		String hostDescInput = parameter("remark", "").trim();
		if (hostDescInput.length() < 6) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("备注不得少于6个字");
			return null;
		}
		m.setHostDesc(hostDescInput);
		
		return m;
	}
	
	private void doOpMachine(ErrorResult result) throws Exception{
		Machine machine = checkMachine(result);
		if (machine == null) {
			return ;
		}
		
		String op = parameter("op", "").trim();
		if (op.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("no op parameter");
			return ;
		}
		
		if (op.equals("add")) {
			doAddMachine(machine, result);
		} else if (op.equals("update")) {
			doUpdateMachine(machine, result);
		} else {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("op parameter error");
		}
	}
	
	private void doAddMachine(Machine machine, ErrorResult result) throws Exception {
		MachineManageBoStub stub = new MachineManageBoStub();
		
		try {
			stub.addMachine(RandomUtils.nextInt(), 3000, machine);
		} catch (ErrorInfo e) {
			if (e.getErrorCode() == MachineManageBoError.ERROR_DUPLICATE_HOSTINNER_IP.getValue()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("重复的机器IP");
				return ;
			}
			if (e.getErrorCode() == MachineManageBoError.ERROR_DUPLICATE_HOSTNAME.getValue()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("重复的机器名称");
				return ;
			}
			throw e;
		} 
	}
	
	private void doUpdateMachine(Machine machine, ErrorResult result) throws Exception{
		MachineManageBoStub stub = new MachineManageBoStub();
		
		try {
			stub.updateMachine(RandomUtils.nextInt(), 3000, machine);
		} catch (ErrorInfo e) {
			if (e.getErrorCode() == MachineManageBoError.ERROR_DUPLICATE_HOSTINNER_IP.getValue()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("重复的机器IP");
				return ;
			}
			throw e;
		} 
	}
}
