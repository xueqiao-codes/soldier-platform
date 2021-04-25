package org.soldier.platform.svr_platform.thrift;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.soldier.base.logger.AppLog;

public class PipePool {
	private BlockingQueue<Pipe> idlePipes;
	private Map<SourceChannel, Pipe> sourcePipesMap;
	private Map<SinkChannel, Pipe> sinkPipesMap;
	private boolean isSourceChannelBlocking;
	private boolean isSinkChannelBlocking;
	private ReentrantLock lock;
	private int maxPipeNumber;
	private int allocPipeNumber;
	
	public PipePool(int maxIdleNum, 
			boolean isSourceChannelBlocking,
			boolean isSinkChannelBlocking,
			int maxPipeNumber) {
		idlePipes = new LinkedBlockingQueue<Pipe>(maxIdleNum);
		sourcePipesMap = new HashMap<SourceChannel, Pipe>(5);
		sinkPipesMap = new HashMap<SinkChannel, Pipe>(5);
		this.isSourceChannelBlocking = isSourceChannelBlocking;
		this.isSinkChannelBlocking = isSinkChannelBlocking;
		lock = new ReentrantLock();
		this.maxPipeNumber = maxPipeNumber;
	}
	
	private synchronized void opAllocPipeNumber(int count) {
		allocPipeNumber += count;
		if (allocPipeNumber < 0) {
			allocPipeNumber = 0;
		}
	}
	
	private void addToIdlePipe(Pipe pipe) {
		if (!pipe.source().isOpen() || !pipe.sink().isOpen()) {
			closePipe(pipe);
			return ;
		}
		boolean success = true; 
		try {
			success = idlePipes.add(pipe);
		} catch(Exception e) {
			success = false;
		}
		if (!success) {
			closePipe(pipe);
		}
	}
	
	private void closePipe(Pipe pipe) {
		opAllocPipeNumber(-1);
		try {
			if (pipe.source().isOpen()) {
				pipe.source().close();
			}
			if (pipe.sink().isOpen()) {
				pipe.sink().close();
			}
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
	public Pipe openPipe() throws IOException {
		Pipe pipe = null;
		if (allocPipeNumber >= maxPipeNumber) {
			pipe = waitPoll();
		} else {
			pipe = idlePipes.poll();
		}
		if (pipe == null) {
			pipe = Pipe.open();
			if (!isSourceChannelBlocking) {
				pipe.source().configureBlocking(isSourceChannelBlocking);
			}
			if (!isSinkChannelBlocking) {
				pipe.sink().configureBlocking(isSinkChannelBlocking);
			}
			opAllocPipeNumber(1);
		} 
		
		lock.lock();
		sourcePipesMap.put(pipe.source(), pipe);
		sinkPipesMap.put(pipe.sink(), pipe);
		lock.unlock();
		
		return pipe;
	}
	
	public void close(SourceChannel source, boolean addCache) {
		lock.lock();
		Pipe pipe = sourcePipesMap.remove(source);
		if (pipe != null && !sinkPipesMap.containsKey(pipe.sink())) {
			lock.unlock();
			if (addCache) {
				addToIdlePipe(pipe);
			}
			return ;
		}
		lock.unlock();
	}
	
	public void close(SinkChannel sink, boolean addCache) {
		lock.lock();
		Pipe pipe = sinkPipesMap.remove(sink);
		if (pipe != null && !sourcePipesMap.containsKey(pipe.source())) {
			lock.unlock();
			if (addCache) {
				addToIdlePipe(pipe);
			}
			return ;
		}
		lock.unlock();
	}
	
	public Pipe waitPoll() {
		Pipe result = null;
		while (true) {
			try {
				if (result != null) {
					break;
				}
				result = idlePipes.poll(10000000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {}
		}
		return result;
	}
	
	public void destroy() {
		lock.lock();
		for (Entry<SinkChannel, Pipe> entry : sinkPipesMap.entrySet()) {
			closePipe(entry.getValue());
		}
		sinkPipesMap.clear();
		for (Entry<SourceChannel, Pipe> entry : sourcePipesMap.entrySet()) {
			closePipe(entry.getValue());
		}
		sourcePipesMap.clear();
		lock.unlock();
		
		Pipe pipe = null;
		while ((pipe = idlePipes.poll()) != null) {
			closePipe(pipe);
		}
		
		idlePipes = null;
		sourcePipesMap = null;
		sinkPipesMap = null;
		lock = null;
	}
}
