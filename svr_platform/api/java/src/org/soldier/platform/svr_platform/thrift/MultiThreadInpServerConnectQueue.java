package org.soldier.platform.svr_platform.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.TTransportException;

public class MultiThreadInpServerConnectQueue implements IConnectQueue {
	private MapQueue<Long, InpConnectInfo> requestQueues;
	
	
	public MultiThreadInpServerConnectQueue() {
		requestQueues = new MapQueue<Long, InpConnectInfo>();
	}
	
	@Override
	public void add(long key, InpConnectInfo info) throws TTransportException {
		requestQueues.add(key, info);
	}

	@Override
	public void registerKey(long key, int num) {
		requestQueues.registerKey(key, num);
	}

	@Override
	public InpConnectInfo poll(long key) throws TTransportException {
		return requestQueues.poll(key);
	}

	@Override
	public InpConnectInfo poll(long key, long timeout, TimeUnit unit)
			throws InterruptedException, TTransportException {
		return requestQueues.poll(key, timeout, unit);
	}

	@Override
	public InpConnectInfo poll(long timeout, TimeUnit unit)
			throws InterruptedException, TTransportException {
		throw new TTransportException("Not Supported!");
	}

	@Override
	public InpConnectInfo poll() throws TTransportException {
		throw new TTransportException("Not Supported!");
	}

}
