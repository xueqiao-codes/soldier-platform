package org.soldier.platform.svr_platform.client;


/**
 * 服务查找器
 */
public interface IServiceFinder {
	public String getServiceIp(final int serviceKey, 
			final String methodName, 
			final long routeKey) throws ServiceException;
	
	public int getServicePort(final int serviceKey);
	
	public void updateCallInfo(
			int serviceKey, final String methodName, final String ip, Exception e);
}
