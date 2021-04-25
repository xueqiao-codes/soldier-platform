package org.soldier.platform.svr_platform.client;

import org.apache.thrift.TException;
import org.soldier.platform.route.RouteAgent;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

class RAServiceFinder implements IServiceFinder {
	public RAServiceFinder() {
		if (0 != RouteAgent.Init()) {
			throw new RuntimeException("ConfigAgent Init Failed");
		}
	}
	
	@Override
	public String getServiceIp(int serviceKey, 
			final String methodName, long routeKey) throws ServiceException {
		String ip = RouteAgent.GetRouteIp(serviceKey, methodName, routeKey);
		if (ip == null || ip.isEmpty()) {
			throw new ServiceException("No ServiceIp Found");
		}
		return ip;
	}

	@Override
	public int getServicePort(int serviceKey) {
		return 10000 + serviceKey;
	}

	@Override
	public void updateCallInfo(int serviceKey, String methodName, String ip, Exception e) {
		if (e == null || (e instanceof ErrorInfo)) {
			RouteAgent.updateCallInfo(serviceKey, methodName, ip, RouteAgent.SERVICE_CALL_OK);
		} else if (e instanceof TException){
			RouteAgent.updateCallInfo(serviceKey, methodName, ip, RouteAgent.SERVICE_CALL_FAILED);
		}
	}
	
}
