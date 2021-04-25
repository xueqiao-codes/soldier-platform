package org.soldier.platform.route;

import java.util.List;

public class RouteList {
	private int totalCount;
	private List<RouteItem> items;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public List<RouteItem> getItems() {
		return items;
	}
	public void setItems(List<RouteItem> items) {
		this.items = items;
	}
	
	
}
