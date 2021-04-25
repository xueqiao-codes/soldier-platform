package org.soldier.platform.svr_platform.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.soldier.base.Assert;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;

/**
 *  服务运行的容器类
 * @author Xairy
 */
public class ServiceContainer extends BaseServiceContainer {
	private static void printHelp() {
		System.out.println("java " + ServiceContainer.class.getName() 
				+ " service.properties[config]");
	}
	
	private static File servicePropertiesFile;
	
	public int getServicePort() {
		return ServiceFinderFactory.getServiceFinder().getServicePort(serviceKey);
	}
		
	public static void main(String[] args) {
		if(args.length < 1) {
			printHelp();
			System.exit(1);
		}
		
		InputStream in = null;
		servicePropertiesFile = null;
		try {
			servicePropertiesFile = new File(args[0]);
			in = new FileInputStream(servicePropertiesFile);
		} catch (FileNotFoundException e) {
			System.err.println("CONFIG OPEN FAILED, " + e.getMessage());
			System.exit(2);
		}
		
		Properties serviceProperties = new Properties();
		try {
			serviceProperties.load(in);
			in.close();
		} catch (IOException e) {
			System.err.println("LOAD CONFIG " + args[0] + " ERR, "
					+ e.getMessage());
			System.exit(3);
		}
		
		String deployDir = servicePropertiesFile.getParentFile().getAbsolutePath();
		serviceProperties.put("_deployDir_", deployDir);
		System.out.println("_deployDir_=" + serviceProperties.getProperty("_deployDir_"));
		
		ServiceContainer container = new ServiceContainer();
		try {
			container.start(serviceProperties);
		} catch(Exception e) {
			AppLog.e(e.getMessage(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected AbstractServerArgs createArgs(Properties properties) throws Exception {
		TNonblockingServerSocket socket = null;
		String bindAddress = properties.getProperty("bindAddress");
		if (bindAddress == null || bindAddress.isEmpty()) {
			socket = new TNonblockingServerSocket(getServicePort());
		} else {
			System.out.println("bindAddress=" + bindAddress);
			socket = new TNonblockingServerSocket(new InetSocketAddress(bindAddress, getServicePort()));
		}
		THsHaServer.Args serverArgs = new THsHaServer.Args(socket);
		serverArgs.workerThreads(Integer.valueOf(
				properties.getProperty("workerNum", "10")));
		System.out.println("workerNum=" + serverArgs.getWorkerThreads());
		return serverArgs;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected TServer createServer(AbstractServerArgs args) {
		return new THsHaServer((THsHaServer.Args)args);
	}

	@Override
	protected void willStartService() {
		// 在/data/applog目录下建立部署名称一致的log目录
		String serviceName = servicePropertiesFile.getParentFile().getName();
		
		String logModule = "service/" + serviceName;
		System.out.println("Init AppLog module " + logModule);
		AppLog.init(logModule);
		Assert.SetLogger(Logger.getLogger(Assert.class));
		
	}

	@Override
	protected boolean willServe() {
		System.out.println("Service start on port " + getServicePort());
		
		Map<String, String> keepAliveTags = new HashMap<String, String>();
		keepAliveTags.put("servicekey", String.valueOf(getServiceKey()));
		
		AttrReporterFactory.thirtySecs().keep(
				AttrReporterFactory.thirtySecs().requireKey("service.server.keepalive", keepAliveTags), 1);
		
		AppLogStream.redirectSystemPrint();
		return true;
	}

}
