package org.soldier.platform.svr_platform.thrift;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;
import org.soldier.base.Assert;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.thrift.InpTransportManager.ConnectQueueMode;


public class ProxyInpServer {
	private Map<Long, InpServer> proxyMap;
	private boolean stopped;

	public ProxyInpServer() {
		proxyMap = new ConcurrentHashMap<Long, InpServer>();
		stopped = true;
		InpTransportManager.setConnectQueueMode(ConnectQueueMode.SingleThreadInpServerMode);
	}
	
	public void registerServer(long requestKey, InpServer server) {
		proxyMap.put(requestKey, server);
		InpTransportManager.getInstance().getConnectQueue().registerKey(requestKey, 0);
	}

	public void serve() {
		stopped = false;
		while(!stopped) {
			try {
				InpConnectInfo request = InpTransportManager.getInstance().getConnectQueue()
						.poll(10, TimeUnit.SECONDS);
				if (request != null) {
					InpSocket inpSocket = new InpSocket(
							request.isClientBlocking(), 
							request.getInChannel(), request.getOutChannel());
					InpServer server = proxyMap.get(request.getRequestKey());
					if (server == null) {
						AppLog.e("No InpServer for requestKey=" + request.getRequestKey());
						inpSocket.closeThorough();
					} else {
						while (true) {
							int rejectCount = 0;
							try {
								server.handleRequest(inpSocket);
								break;
							} catch(RejectedExecutionException e) {
								++rejectCount;
								if (stopped) {
									break;
								}
								AppLog.e("InpServer Reject request count=" + rejectCount 
										+ ",requestKey=" + request.getRequestKey(), e);
								if (rejectCount >= 5) {
									inpSocket.closeThorough();
									break;
								}
								try {
									TimeUnit.MILLISECONDS.sleep(100);
								} catch (InterruptedException ie) {
									AppLog.w("Interrupted while waiting to place client on"
											+ " executor queue.");
									Thread.currentThread().interrupt();
									break;
								}
							}
						}
					}
				} 
			} catch (TTransportException e) {
				AppLog.e(e.getMessage(), e);
			} catch (InterruptedException e) {
			}
		}
		proxyMap.clear();
	}
	
	public void stop() {
		stopped = true;
		for (java.util.Map.Entry<Long, InpServer> entry : proxyMap.entrySet()) {
			entry.getValue().close();
		}
	}

}
