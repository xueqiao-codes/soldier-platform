package org.soldier.platform.svr_platform.thrift;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.TTransportException;

public class MapQueue<Key, Value> {
	private Map<Key, BlockingQueue<Value>> members;
	
	public MapQueue() {
		members = new ConcurrentHashMap<Key, BlockingQueue<Value>>(5);
	}
	
	public void registerKey(Key key, int number) {
		if (members.containsKey(key)) {
			return ;
		}
		
		synchronized(this) {
			if (members.containsKey(key)) {
				return ;
			}
			BlockingQueue<Value> queue = null;
			if (number > 0) {
				queue = new LinkedBlockingQueue<Value>(number); 
			} else {
				queue = new LinkedBlockingQueue<Value>();
			}
			members.put(key, queue);
		}
	}
	
	private BlockingQueue<Value> getQueue(Key key) throws TTransportException {
		BlockingQueue<Value> queue = members.get(key);
		if (queue == null) {
			throw new TTransportException("queue is not registered for key=" + key);
		}
		return queue;
	}
	
	public void add(Key key, Value value) throws TTransportException{
		BlockingQueue<Value> queue = getQueue(key);
		if (!queue.add(value)) {
			throw new TTransportException("add failed!");
		}
	}
	
	public Value poll(Key key) throws TTransportException{
		Value result = null;
		BlockingQueue<Value> queue = getQueue(key);
		while (true) {
			try {
				if (result != null) {
					break;
				}
				result = queue.poll(10000000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {}
		}
		return result;
	}
	
	public Value poll(Key key, long timeout, TimeUnit unit) 
			throws InterruptedException, TTransportException {
		return getQueue(key).poll(timeout, unit);
	}

}
