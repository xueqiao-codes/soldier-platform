package org.soldier.platform.route_finder_test;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.route.RouteAgent;

public class TestMain {
	public static void main(String[] args) {
		if (0 != RouteAgent.Init()) {
			System.err.println("route agent init failed!");
			return ;
		}
		
		int serviceKey = Integer.parseInt(args[0]);
		String[] ipList = StringUtils.split(args[1], ",");
		
		int totalCount = 100000000;
		long startTimestamp = System.currentTimeMillis();
		for (int index = 0; index < totalCount; ++index) {
			RouteAgent.GetRouteIp(serviceKey, "", 0);
		}
		System.out.println(Thread.currentThread().getId() + " end! " + (System.currentTimeMillis() - startTimestamp) + "ms"
				+ " every single time=" + ((double)(System.currentTimeMillis() - startTimestamp))/totalCount + "ms");
		
//		for (int index = 0; index < 16; ++index) {
//			new TestThread(serviceKey, ipList).start();
//		}
//		
//		try {
//			Thread.sleep(10000000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	private static class TestThread extends Thread {
		private String[] ipList;
		private int serviceKey;
		
		public TestThread(int serviceKey, String[] ipList) {
			this.serviceKey = serviceKey;
			this.ipList = ipList;
		}
		
		@Override
		public void run() {
			long startTimestamp = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getId() + " start! " + startTimestamp);
			int start = 1;
			int totalCount = 100000000;
			for (int index = 0; index < totalCount; ++index,++start) {
				if (!ipList[start % ipList.length].equals(RouteAgent.GetRouteIp(serviceKey, "", 0))) {
					System.out.println("unexpected !");
				}
//				if (index % 10000 == 0) {
//					System.out.println("Gone " + index);
//				}
			}
			System.out.println(Thread.currentThread().getId() + " end! " + (System.currentTimeMillis() - startTimestamp) + "ms"
					+ " every single time=" + ((double)(System.currentTimeMillis() - startTimestamp))/totalCount + "ms");
		}
	}
	
}
