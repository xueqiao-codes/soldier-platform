package org.soldier.platform.route;

public class RouteItem {
	private int serviceKey;
	private String ipList; // dot seperated
	private int routeType;
	
	
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
	
	public int getRouteType() {
		return routeType;
	}
	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}
	
	
	
	
}
