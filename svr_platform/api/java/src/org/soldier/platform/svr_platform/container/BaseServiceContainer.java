package org.soldier.platform.svr_platform.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;

public abstract class BaseServiceContainer {
	protected int serviceKey;
	protected Object serviceAdaptorImpl;
	
	public int getServiceKey() {
		return serviceKey;
	}
	
	@SuppressWarnings("rawtypes")
	protected abstract AbstractServerArgs createArgs(Properties properties) throws Exception;
	@SuppressWarnings("rawtypes")
	protected abstract TServer createServer(AbstractServerArgs args);
	protected abstract void willStartService();
	protected abstract boolean willServe();
	
	public void start(Properties serviceProperties) throws Exception {
		String serviceAdpatorClassName = serviceProperties.getProperty("Adaptor-Class");
		if (serviceAdpatorClassName == null || serviceAdpatorClassName.isEmpty()) {
			throw new Exception("No Adaptor-Class properties");
		}
		
		String packageName = null;
		String serviceName = null;
		Class<?> serviceAdaptorClass = null;
		Class<?> serviceClass = null;
		Class<?> serviceVaraibleClass = null;
		Class<?> serviceAdaptorImplClass = null;
		serviceAdaptorClass = Class.forName(serviceAdpatorClassName);
		if(!serviceAdaptorClass.getSimpleName().endsWith("Adaptor")){
			throw new Exception(serviceAdaptorClass.getName() 
						+ " is not correct for service");
		}
		packageName = serviceAdaptorClass.getName().substring(0, 
				serviceAdpatorClassName.length() - ".server.".length() 
				- serviceAdaptorClass.getSimpleName().length());
		serviceName = serviceAdaptorClass.getSimpleName().substring(0, 
				serviceAdaptorClass.getSimpleName().length() - "Adaptor".length()); 
		serviceClass = Class.forName(packageName + "." + serviceName);
		serviceVaraibleClass = Class.forName(packageName + "." + serviceName + "Variable");
			
		String serviceAdaptorImplClassName = serviceProperties.getProperty("Adaptor-Class-Impl");
		if(serviceAdaptorImplClassName == null || serviceAdaptorImplClassName.isEmpty()) {
			serviceAdaptorImplClassName = packageName 
					+ ".server.impl." + serviceName + "Handler";
		}
		serviceAdaptorImplClass = Class.forName(serviceAdaptorImplClassName);
		
		willStartService();
		
		SvrConfiguration.setLogItemMaxLength(512);
		
		startService(serviceAdaptorClass, 
				serviceClass,
				serviceVaraibleClass, 
				serviceAdaptorImplClass,
				serviceProperties);
	}
	
	private void startService(
			final Class<?> serviceAdaptorClass,
			final Class<?> serviceClass,
			final Class<?> serviceVariableClass,
			final Class<?> serviceAdaptorImplClass,
			Properties serviceProperties
			) throws Exception {
		serviceKey = serviceVariableClass.getDeclaredField("serviceKey").getInt(null);;
			
		Class<?> processorClass = 
				Class.forName(serviceClass.getName() + "$Processor");
		Class<?> processorIFaceClass = 
				Class.forName(serviceClass.getName() + "$Iface");
		Constructor<?> processorConstructor = 
				processorClass.getConstructor(processorIFaceClass);
			
		Object serviceAdaptorImpl = serviceAdaptorImplClass.newInstance();
			// 调用初始化函数
		Method InitAppMethod = serviceAdaptorImplClass.getMethod("InitApp", Properties.class);
		int result = Integer.class.cast(
				InitAppMethod.invoke(serviceAdaptorImpl, serviceProperties)).intValue();
		if(result != 0 ) {
			serviceAdaptorImpl = null;
			throw new Exception("InitApp Failed, result=" + result);
		}
			
		@SuppressWarnings("unchecked")
		AbstractServerArgs<Args> serverArgs = createArgs(serviceProperties);
		serverArgs.protocolFactory(new TCompactProtocol.Factory());
		serverArgs.transportFactory(new TFramedTransport.Factory());
		
		IfaceProxy faceProxy = new IfaceProxy(serviceAdaptorImpl);
		faceProxy.setServiceKey(serviceKey);
		serverArgs.processorFactory(new TProcessorFactory(
				(TProcessor) processorConstructor.newInstance(
						Proxy.newProxyInstance(serviceAdaptorImplClass.getClassLoader(),
								new Class[]{processorIFaceClass},
								faceProxy))));
		TServer server = createServer(serverArgs);
		if (willServe()) {
			server.serve();
		}
	}
	
	public void destroy() {
		if (serviceAdaptorImpl != null) {
			try {
				Method method = serviceAdaptorImpl.getClass().getMethod("destroy");
				method.invoke(serviceAdaptorImpl);
			} catch (Exception e) {
				AppLog.e("destroy instance failed", e);
			} 
		}
	}
}
