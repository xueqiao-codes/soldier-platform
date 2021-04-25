package org.soldier.platform.route;

public interface IRouteFinder {
	public String GetRouteIp(
			long serviceKey, final String methodName, long routeKey);
	
	public void updateCallInfo(
			long serviceKey, final String methodName, final String ip, int callResult);
}
