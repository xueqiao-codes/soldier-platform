package org.soldier.platform.svr_platform.client;

class InpServiceFinder implements IServiceFinder {

	@Override
	public String getServiceIp(int serviceKey, String methodName, long routeKey)
			throws ServiceException {
		return "";
	}

	@Override
	public int getServicePort(int serviceKey) {
		return 0;
	}

	@Override
	public void updateCallInfo(int serviceKey, String methodName, String ip,
			Exception e) {
	}

}
