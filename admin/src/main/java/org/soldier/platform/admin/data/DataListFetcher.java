package org.soldier.platform.admin.data;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.client.RouteDaoStub;

public class DataListFetcher {
	public static List<DalSetRole> getAllRoleList() {
		DalSetDaoStub stub = new DalSetDaoStub();
		DalSetRoleList roleList = null;
		try {
			roleList = stub.queryDalSetRoles(
					RandomUtils.nextInt(), 2000, 0, Integer.MAX_VALUE, null);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return new ArrayList<DalSetRole>();
		}
		return roleList.getResultList();
	}
	
	public static List<DalSetHost> getAllHostList() {
		DalSetDaoStub stub = new DalSetDaoStub();
		DalSetHostList hostList = null;
		try {
			hostList = stub.queryDalSetHosts(RandomUtils.nextInt(), 
					2000, 0, Integer.MAX_VALUE, null);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return new ArrayList<DalSetHost>();
		}
		return hostList.getResultList();
	}
	
	public static List<RouteInfo> getAllRouteList() {
		RouteDaoStub stub = new RouteDaoStub();
		RouteInfoList routeList = null;
		
		try {
			routeList = stub.queryRouteInfoList(
					RandomUtils.nextInt(), 3000, 0, 200, new QueryRouteOption());
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return new ArrayList<RouteInfo>();
		}
		
		return routeList.getResultList();
	}
	
	public static List<DalSetUser> getAllUserList() {
		DalSetDaoStub stub = new DalSetDaoStub();
		DalSetUserList userList = null;
		
		try {
			userList = stub.queryDalSetUsers(
					RandomUtils.nextInt(), 3000, 0, Integer.MAX_VALUE, null);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return new ArrayList<DalSetUser>();
		}
		return userList.getResultList();
	}
}
