package org.soldier.platform.svr_platform.container;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

public class InpService {
	private String serviceName;
	private String serviceClass;
	private int serviceKey;
	private Properties serviceProperties;
	private ExecutorService executorService;
	
	public InpService() {
		serviceProperties = new Properties();
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	
	public Properties getServiceProperties() {
		return serviceProperties;
	}
	public void setServiceProperties(Properties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}
	
	public ExecutorService getExecutorService() {
		return executorService;
	}
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public int getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(int serviceKey) {
		this.serviceKey = serviceKey;
	}
	
}
