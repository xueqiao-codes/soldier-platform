package org.soldier.platform.svr_platform.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.soldier.platform.svr_platform.thrift.InpTransportManager;
import org.soldier.platform.svr_platform.thrift.InpTransportManager.ConnectQueueMode;
import org.soldier.platform.svr_platform.thrift.PipeManager;

public class MultiInpServiceContainerInMultiThread extends MultiInpServiceContainer {
	private Map<String, InpServiceThread> servicesMap;
	private ReentrantLock operationLock;
	
	public MultiInpServiceContainerInMultiThread() {
		servicesMap = new HashMap<String, InpServiceThread>();
		operationLock = new ReentrantLock();
		InpTransportManager.setConnectQueueMode(ConnectQueueMode.MultiThreadInpServerMode);
	}

	@Override
	public void doLoadServices(List<InpService> serviceList) {
		operationLock.lock();
		for (InpService service : serviceList) {
			if (service.getServiceName() == null || service.getServiceName().isEmpty()) {
				continue ;
			}
			InpServiceThread thread = servicesMap.get(service.getServiceName());
			if (thread != null && thread.isAlive()){
				System.out.println("service " + service.getServiceName() + " has started!");
				continue ;
			}
			if (service.getServiceClass() != null 
				&& !service.getServiceClass().isEmpty()) {
				thread = new InpServiceThread(service);
				thread.start();
				servicesMap.put(service.getServiceName(), thread);
			}
		}
		operationLock.unlock();
	}
	
	@Override
	public void destory() {
		operationLock.lock();
		for (Entry<String, InpServiceThread> entry : servicesMap.entrySet()) {
			entry.getValue().stopServe();
		}
		servicesMap.clear();
		operationLock.unlock();
		PipeManager.getInstance().destory();
	}
	
	private static class InpServiceThread extends Thread {
		private InpService service;
		private InpServiceContainer container;
//		private boolean isDestroyCalled;
		
		public InpServiceThread(InpService service) {
			this.service = service;
			this.setName(service.getServiceName());
//			this.isDestroyCalled = false;
		}
		
		@Override
		public void run() {
			Properties props = service.getServiceProperties();
			if (props == null) {
				props = new Properties();
			}
			props.put("Adaptor-Class", service.getServiceClass());
			container = new InpServiceContainer();
			container.setExecutorService(service.getExecutorService());
			
			try {
				container.start(props);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println("MultiInpServiceContainer destroyed! Service Stoped service " +
//					service.getServiceName() + ", isDestroyCalled=" + isDestroyCalled);
		}
		
		public void stopServe() {
			//isDestroyCalled = true;
			if (container != null) {
				container.destroy();
			}
			this.interrupt();
		}
	}
}
