package org.soldier.platform.svr_platform.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.TTransportException;

public interface IConnectQueue {
	public void add(long key, InpConnectInfo info) throws TTransportException;
	public void registerKey(long key, int num);
	
	public InpConnectInfo poll(long key) throws TTransportException;
	
	public InpConnectInfo poll(long key, long timeout, TimeUnit unit) 
			throws InterruptedException, TTransportException;
	
	public InpConnectInfo poll(long timeout, TimeUnit unit)
			throws InterruptedException, TTransportException;
	
	public InpConnectInfo poll() throws TTransportException;
}
