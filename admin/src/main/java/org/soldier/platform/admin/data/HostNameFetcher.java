package org.soldier.platform.admin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.machine.KeyType;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.machine.client.MachineManageBoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class HostNameFetcher {
	
	private Map<String, Machine> ipHosts = new HashMap<String, Machine>();
	
	public HostNameFetcher(Set<String> ips) throws ErrorInfo, TException {
		if (ips.isEmpty()) {
			return ;
		}
		
		List<String> addrs = new ArrayList<String>();
		for(String ip : ips) {
			addrs.add(ip);
		}
		
		MachineManageBoStub  machineStub = new MachineManageBoStub();
		QueryMachineOption option = new QueryMachineOption();
		option.setHostInnerIPS(addrs);
		option.setKeyType(KeyType.KEY_HOSTINNER_IP);
		MachineList machineList = machineStub.queryMachineList(RandomUtils.nextInt(), 2000, option, 0, ips.size());
		if (machineList.getMachinesMap() != null) {
			ipHosts = machineList.getMachinesMap();
		}
		
		Machine localHostMachine = new Machine();
		localHostMachine.setHostName("localhost");
		localHostMachine.setHostInnerIP("127.0.0.1");
		ipHosts.put("127.0.0.1", localHostMachine);
	}
	
	public Map<String, Machine> getIPHosts() {
		return ipHosts;
	}
}
