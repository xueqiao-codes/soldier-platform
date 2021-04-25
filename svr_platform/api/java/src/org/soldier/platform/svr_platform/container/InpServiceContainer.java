package org.soldier.platform.svr_platform.container;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

import org.apache.thrift.server.TServer;
import org.soldier.platform.svr_platform.thrift.InpServer;
import org.soldier.platform.svr_platform.thrift.InpServerTransport;

/**
 *  服务运行的容器类
 * @author Xairy
 */
public class InpServiceContainer extends BaseServiceContainer{
	private java.util.concurrent.ExecutorService executorService;
	private InpServer inpServer;
	private boolean willServe = true;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected TServer.AbstractServerArgs createArgs(Properties properties) {
		InpServerTransport transport = new InpServerTransport(serviceKey);
		InpServer.Args serverArgs = new InpServer.Args(transport);
		int workerNumber = Integer.valueOf(
				properties.getProperty("workerNum", "5"));
		serverArgs.minWorkerThreads(1);
		serverArgs.maxWorkerThreads(workerNumber);
		if (executorService != null) {
			serverArgs.executorService = executorService;
		} else {
			System.out.println("workerNum=" + workerNumber);
		}
		return serverArgs;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected TServer createServer(TServer.AbstractServerArgs args) {
		inpServer = new InpServer((InpServer.Args)args);
		return inpServer;
	}

	@Override
	protected void willStartService() {
		
	}

	@Override
	protected boolean willServe() {
		if (willServe) {
			System.out.println("Service Started for Key=" + serviceKey);
		}
		return willServe;
	}	
	
	
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public void setServeAfterStart(boolean willServe) {
		this.willServe = willServe;
	}
	
	public InpServer getServer() {
		return inpServer;
	}
	
	@Override
	public void destroy() {
		if (inpServer != null ) {
			inpServer.stop();
		}
		super.destroy();
	}
}
