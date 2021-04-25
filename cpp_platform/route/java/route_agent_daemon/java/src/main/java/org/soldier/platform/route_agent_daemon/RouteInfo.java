package org.soldier.platform.route_agent_daemon;

public class RouteInfo {
	private int serviceKey;
	private String ipList;
	
	public int getServiceKey() {
		return serviceKey;
	}
	public void setServiceKey(int serviceKey) {
		this.serviceKey = serviceKey;
	}
	
	public String getIpList() {
		return ipList;
	}
	public void setIpList(String ipList) {
		this.ipList = ipList;
	}
}
