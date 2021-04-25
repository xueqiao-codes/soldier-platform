package org.soldier.platform.svr_platform.thrift;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.TTransportException;

public class SingleThreadInpServerConnectionQueue implements IConnectQueue {
	private BlockingQueue<InpConnectInfo> requestQueue;
	private Map<Long, Integer> registerInformation;
	
	public SingleThreadInpServerConnectionQueue() {
		requestQueue = new LinkedBlockingQueue<InpConnectInfo>();
		registerInformation = new ConcurrentHashMap<Long, Integer>();
	}
	
	@Override
	public void add(long key, InpConnectInfo info) throws TTransportException {
		if (registerInformation.get(key) != null) {
			requestQueue.add(info);
		} else {
			throw new TTransportException("Service for " + key + " is not registered!");
		}
	}

	@Override
	public void registerKey(long key, int num) {
		registerInformation.put(key, num);
	}

	@Override
	public InpConnectInfo poll(long key) throws TTransportException {
		throw new TTransportException("Not Supported!");
	}

	@Override
	public InpConnectInfo poll(long key, long timeout, TimeUnit unit)
			throws InterruptedException, TTransportException {
		throw new TTransportException("Not Supported!");
	}

	@Override
	public InpConnectInfo poll(long timeout, TimeUnit unit)
			throws InterruptedException, TTransportException {
		return requestQueue.poll(timeout, unit);
	}

	@Override
	public InpConnectInfo poll() throws TTransportException {
		InpConnectInfo result = null;
		while (true) {
			try {
				if (result != null) {
					break;
				}
				result = requestQueue.poll(10000000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {}
		}
		return result;
	}

}
