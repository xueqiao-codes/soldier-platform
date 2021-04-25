package org.soldier.platform.admin.controller.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.NetHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.CPageController;
import org.soldier.platform.admin.data.HostNameFetcher;
import org.soldier.platform.admin.freemarker.IpListConverter;
import org.soldier.platform.admin.freemarker.StringListConverter;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.client.MachineManageBoStub;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.client.RouteDaoStub;

public class RouteInfoData extends CPageController {

	public static class RouteEntry {
		private RouteInfo routeInfo;
		private List<String> routeHostNameOrIPs = new ArrayList<String>();
		
		public RouteInfo getRouteInfo() {
			return routeInfo;
		}
		public void setRouteInfo(RouteInfo route) {
			this.routeInfo = route;
		}
		
		public List<String> getRouteHostNameOrIPs() {
			return routeHostNameOrIPs;
		}
		public void setRouteHostNameOrIPs(List<String> routeHostNameOrIPs) {
			this.routeHostNameOrIPs = routeHostNameOrIPs;
		}
	}
	
	public static class RouteResults {
		private int totalCount;
		private List<RouteEntry> resultList = new ArrayList<RouteEntry>();
		
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		
		public List<RouteEntry> getResultList() {
			return resultList;
		}
		public void setResultList(List<RouteEntry> resultList) {
			this.resultList = resultList;
		}
	}
	
	@Override
	protected int doPageModel(Map<Object, Object> dataModel, int pageIndex,
			int pageSize) {
		QueryRouteOption option = new QueryRouteOption();
		
		int serviceKey = parameter("cmdnum", -1);
		if (serviceKey >= 0) {
			option.setServiceKey(serviceKey);
		}
		String serviceName = parameter("serviceName", "").trim();
		if (!serviceName.isEmpty()) {
			option.setServiceName(serviceName);
		}
		String ip = parameter("ip", "").trim();
		if (!ip.isEmpty()) {
			option.setIp(ip);
		}
		String desc = parameter("remark", "");
		if (!desc.isEmpty()) {
			option.setDesc(desc);
		}
		
		RouteDaoStub routeStub = new RouteDaoStub();
		
		dataModel.put("ipList2Str",  new IpListConverter(","));
		dataModel.put("dotListStr", new StringListConverter(","));
		dataModel.put("adminList2Str", new StringListConverter(","));
		try {
			RouteInfoList routeInfoList = routeStub.queryRouteInfoList(RandomUtils.nextInt(),
					1500, pageIndex, pageSize, option);
			
			Set<String> queryIPS = new HashSet<String>();
			
			RouteResults itemsResult = new RouteResults();
			itemsResult.setTotalCount(routeInfoList.getTotalCount());
			
			for (RouteInfo r : routeInfoList.getResultList()) {
				for (Long ipValue : r.getIpList()) {
					queryIPS.add(NetHelper.NetAddr(ipValue));
				}
			}
			Map<String, Machine> ipHosts = new HostNameFetcher(queryIPS).getIPHosts();
			for (RouteInfo r : routeInfoList.getResultList()) {
				RouteEntry e = new RouteEntry();
				e.setRouteInfo(r);
				for (Long i : r.getIpList()) {
					String iStr = NetHelper.NetAddr(i);
					if (ipHosts.containsKey(iStr)) {
						e.getRouteHostNameOrIPs().add(ipHosts.get(iStr).getHostName());
					} else {
						e.getRouteHostNameOrIPs().add(iStr);
					}
				}
				itemsResult.getResultList().add(e);
			}
			
			
			dataModel.put("itemsResult", itemsResult);
//			RouteInfoList routeList = new RouteInfoList();
//			routeList.setTotalCount(2);
//			
//			RouteInfo info1 = new RouteInfo();
//			info1.setServiceKey(1000);
//			info1.setServiceName("haha");
//			info1.setIpList(new ArrayList<Long>());
//			info1.setDesc("haha");
//			info1.setCreateTimestamp((int)(System.currentTimeMillis() / 1000));
//			info1.setLastmodifyTimestamp((int)(System.currentTimeMillis() / 1000));
//			
//			routeList.setResultList(new ArrayList<RouteInfo>());
//			routeList.getResultList().add(info1);
//			routeList.getResultList().add(info1);
//			
//			dataModel.put("itemsResult", routeList);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return 503;
		}
		return 200;
	}

}
