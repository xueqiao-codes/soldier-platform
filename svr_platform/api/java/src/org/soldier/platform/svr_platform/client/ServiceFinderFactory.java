package org.soldier.platform.svr_platform.client;

public class ServiceFinderFactory {
	private static IServiceFinder sServiceFinder;
	
	public static IServiceFinder getServiceFinder() {
		if (sServiceFinder == null) {
			synchronized (ServiceFinderFactory.class) {
				if (sServiceFinder == null) {
					sServiceFinder = new RAServiceFinder();
				}
			}
		}
		
		return sServiceFinder;
	}
	
	/**
	 * @param serviceFinder
	 */
	public static void setServiceFinder(final IServiceFinder serviceFinder){
		sServiceFinder = serviceFinder;
	}
}
