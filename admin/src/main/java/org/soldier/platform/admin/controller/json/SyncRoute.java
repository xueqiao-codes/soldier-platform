package org.soldier.platform.admin.controller.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.NetHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.client.RouteDaoStub;
import org.soldier.platform.web_framework.util.GsonFactory;

import com.antiy.error_code.ErrorCodeOuter;

public class SyncRoute extends CJsonAjaxOpController {

	@Override
	protected void doAdd() throws AjaxOpException {

	}
	
	public static class RouteList {
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
	
	public static class RouteItem {
		private int serviceKey;
		private String ipList;
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

	@Override
	protected void doUpdate() throws AjaxOpException {
		RouteDaoStub stub = new RouteDaoStub();
		
		try {
			RouteInfoList routeInfoList = 
					stub.queryRouteInfoList(RandomUtils.nextInt(), 500, 0, 10000,  new QueryRouteOption());
			
			List<RouteItem> routeItemList = new ArrayList<RouteItem>(routeInfoList.getResultList().size());
			for (RouteInfo info : routeInfoList.getResultList()) {
				RouteItem item = new RouteItem();
				item.setServiceKey(info.getServiceKey());
				item.setIpList(NetHelper.IpList2Str(info.getIpList(), ","));
				item.setRouteType(info.getRouteType().getValue());
				routeItemList.add(item);
			}
			RouteList routeList = new RouteList();
			routeList.setTotalCount(routeInfoList.getResultListSize());
			routeList.setItems(routeItemList);
			
			stub.syncRoute(RandomUtils.nextInt(), 1500, GsonFactory.getGson().toJson(routeList));
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}

}
