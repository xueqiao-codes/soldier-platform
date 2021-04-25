package org.soldier.platform.route;

import org.soldier.base.OSHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.route.impl.RouteFinderByProperties;
import org.soldier.platform.route.impl.RouteFinderBySo;

public class RouteAgent {
	private static boolean bInit = false;
	public final static int SERVICE_CALL_OK = 0;
	public final static int SERVICE_CALL_FAILED = 1;
	
	private static IRouteFinder sFinder;
	
	public static synchronized int Init(){
		if(bInit){
			return 0;
		}
		if(OSHelper.isWindows()){
			// C:\ConfigAgent.properties解析属性
			sFinder = new RouteFinderByProperties("C:\\RouteAgent.properties");
			bInit = true;
			return 0;
		} else if (OSHelper.isMac()) {
			sFinder = new RouteFinderByProperties("/etc/RouteAgent.properties");
			bInit = true;
			return 0;
		} else {
			try {
				sFinder = new RouteFinderBySo();
			} catch (Exception e) {
				AppLog.e(e.getMessage(), e);
				return -1;
			}
		}
		
		bInit = true;
		return 0;
	}
	
	public static synchronized String GetRouteIp(
			long serviceKey, final String methodName, long routeKey){
		return sFinder.GetRouteIp(serviceKey, methodName, routeKey);
	}
	
	public static synchronized void updateCallInfo(
			long serviceKey, final String methodName, final String ip, int callResult) {
		sFinder.updateCallInfo(serviceKey, methodName, ip, callResult);
	}
	
}
