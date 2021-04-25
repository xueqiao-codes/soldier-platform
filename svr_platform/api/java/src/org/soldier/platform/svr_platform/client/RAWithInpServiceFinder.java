package org.soldier.platform.svr_platform.client;

import org.soldier.platform.svr_platform.comm.SvrConfiguration;

public class RAWithInpServiceFinder implements IServiceFinder {
	private RAServiceFinder routeAgentServiceFinder;
	private InpServiceFinder inpServiceFinder;
	
	public RAWithInpServiceFinder() {
		routeAgentServiceFinder = new RAServiceFinder();
		inpServiceFinder = new InpServiceFinder();
	}
	
	@Override
	public String getServiceIp(int serviceKey, String methodName, long routeKey) throws ServiceException {
		if (SvrConfiguration.isServiceInProcess(serviceKey)) {
			return inpServiceFinder.getServiceIp(serviceKey, methodName, routeKey);
		}
		return routeAgentServiceFinder.getServiceIp(serviceKey, methodName, routeKey);
	}

	@Override
	public int getServicePort(int serviceKey) {
		if (SvrConfiguration.isServiceInProcess(serviceKey)) {
			return inpServiceFinder.getServicePort(serviceKey);
		}
		return routeAgentServiceFinder.getServicePort(serviceKey);
	}

	@Override
	public void updateCallInfo(int serviceKey, String methodName, String ip, Exception e) {
		if (SvrConfiguration.isServiceInProcess(serviceKey)) {
			inpServiceFinder.updateCallInfo(serviceKey, methodName, ip, e);
		} else {
			routeAgentServiceFinder.updateCallInfo(serviceKey, methodName, ip, e);
		}
	}

}
