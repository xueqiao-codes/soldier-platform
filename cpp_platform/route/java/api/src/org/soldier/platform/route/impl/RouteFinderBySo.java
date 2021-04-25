package org.soldier.platform.route.impl;

import java.lang.reflect.Method;

import org.soldier.base.PathService;
import org.soldier.platform.route.IRouteFinder;

public class RouteFinderBySo implements IRouteFinder {
	private Class<?> implClass; 
	private Object implInstance;
	private Method implGetRouteIpMethod;
	private Method implUpdateCallInfoMethod;
	
	public RouteFinderBySo() throws Exception {
		String implClassName = getClass().getPackage().getName() + ".RouteFinderBySoImpl";
//		System.out.println("RouteFinderBySo load class " + implClassName);
		implClass = getClass().getClassLoader().loadClass(implClassName);
		
		implInstance = implClass.getMethod("getInstance", String.class).invoke(null, 
				PathService.getSoldierHome() + "/route_agent/lib/libjroute_agent.so");
		
		implGetRouteIpMethod = implClass.getDeclaredMethod(
				"GetRouteIp", long.class, String.class, long.class);
		implGetRouteIpMethod.setAccessible(true);
		
		implUpdateCallInfoMethod = implClass.getDeclaredMethod(
				"updateCallInfo", long.class, String.class, String.class, int.class);
		implUpdateCallInfoMethod.setAccessible(true);
	}
	
	@Override
	public String GetRouteIp(long serviceKey, String methodName, long routeKey) {
		try {
			return (String) implGetRouteIpMethod.invoke(implInstance,
					serviceKey, methodName, routeKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateCallInfo(long serviceKey, String methodName, String ip,
			int callResult) {
		try {
			implUpdateCallInfoMethod.invoke(implInstance, serviceKey, methodName, ip, callResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
