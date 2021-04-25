package org.soldier.platform.route.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.route.dao.client.RouteDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class RouteDaoTest {
	
	public static void addRoute() {
		RouteDaoStub stub = new RouteDaoStub();
		
		RouteInfo info = new RouteInfo();
		info.setServiceKey(20);
		info.setServiceName("route_dao");
		
		List<Long> ipList = new ArrayList<Long>();
		ipList.add(NetHelper.AddrNet("10.0.0.5"));
		info.setIpList(ipList);
		info.setDesc("路由服务");
		
		try {
			stub.addRoute(RandomUtils.nextInt(), 1000, info);
			
			System.out.println("Add Success");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateRoute() {
		RouteDaoStub stub = new RouteDaoStub();
		
		RouteInfo info = new RouteInfo();
		info.setServiceKey(20);
		List<Long> ipList = new ArrayList<Long>();
		ipList.add(NetHelper.AddrNet("10.0.0.5"));
		ipList.add(NetHelper.AddrNet("10.0.0.6"));
		info.setIpList(ipList);
		info.setDesc("路由服务呵呵");
		
		try {
			stub.updateRoute(RandomUtils.nextInt(), 1000, info);
			System.out.println("Update Success");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void queryRoute() {
		RouteDaoStub stub = new RouteDaoStub();
		
		QueryRouteOption option = new QueryRouteOption();
		try {
			RouteInfoList result = stub.queryRouteInfoList(RandomUtils.nextInt(), 
					1000, 0, 10, option);
			System.out.println(result);
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void syncRoute() {
		RouteDaoStub stub = new RouteDaoStub();
		
		try {
			stub.syncRoute(RandomUtils.nextInt(), 1000, null);
			System.out.println("sync success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getLastRouteVersion() {
		RouteDaoStub stub = new RouteDaoStub();
		
		try {
			System.out.println(stub.getLastRouteVersion(RandomUtils.nextInt(), 1000));
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getAllSimpleRouteInfo() {
		RouteDaoStub stub = new RouteDaoStub();
		
		try {
			List<SimpleRouteInfo> simpleRouteInfoList = 
					stub.getAllSimpleRouteInfo(RandomUtils.nextInt(), 3000);
			
			System.out.println(simpleRouteInfoList);
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteRoute() {
		try {
			new RouteDaoStub().deleteRoute(RandomUtils.nextInt(), 1500, 1999);
			System.out.println("delete success");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		addRoute();
//		updateRoute();
		queryRoute();

//		syncRoute();
//		getAllSimpleRouteInfo();
//		getLastRouteVersion();
		
//		deleteRoute();
	}
}
