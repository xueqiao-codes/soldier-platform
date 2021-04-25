package org.soldier.platform.svr_platform.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.thrift.PipeManager;
import org.soldier.platform.svr_platform.thrift.ProxyInpServer;

public class MultiInpServiceContainerInSingleThread extends
		MultiInpServiceContainer {
	private Map<String, InpServiceContainer> servicesMap;
	private ReentrantLock operationLock;
	private ProxyInpServer proxyServer;
	private ProxyServerThread serverThread;
	
	public MultiInpServiceContainerInSingleThread() {
		proxyServer = new ProxyInpServer();
		servicesMap = new HashMap<String, InpServiceContainer>();
		operationLock = new ReentrantLock();
	}

	@Override
	public void doLoadServices(List<InpService> serviceList) {
		operationLock.lock();
		if (serverThread == null) {
			serverThread = new ProxyServerThread();
			serverThread.start();
		}
		for (InpService service : serviceList) {
			if (service.getServiceName() == null || service.getServiceName().isEmpty()) {
				continue ;
			}
			InpServiceContainer container = servicesMap.get(service.getServiceName());
			if (container != null ){
				System.out.println("service " + service.getServiceName() + " has started!");
				continue ;
			}
			
			container = new InpServiceContainer();
			container.setServeAfterStart(false);
			
			Properties props = service.getServiceProperties();
			if (props == null) {
				props = new Properties();
			}
			props.put("Adaptor-Class", service.getServiceClass());
			container.setExecutorService(service.getExecutorService());
			
			try {
				container.start(props);
				proxyServer.registerServer(container.getServiceKey(), container.getServer());
				servicesMap.put(service.getServiceName(), container);
				System.out.println("register " + service.getServiceName() + " for InpServer");
			} catch (Exception e) {
				AppLog.e(e.getMessage(), e);
			}
		}
		operationLock.unlock();
	}

	@Override
	public void destory() {
		operationLock.lock();
		serverThread.stopServe();
		serverThread = null;
		for (Entry<String, InpServiceContainer> entry : servicesMap.entrySet()) {
			entry.getValue().destroy();
		}
		servicesMap.clear();
		operationLock.unlock();
		PipeManager.getInstance().destory();
	}
	
	private class ProxyServerThread extends Thread {
		@Override
		public void run() {
			setName("ProxyInpServer");
			proxyServer.serve();
			AppLog.i("ProxyInpServe end!");
		}
		
		public void stopServe () {
			proxyServer.stop();
			interrupt();
		}
		
	}

}
