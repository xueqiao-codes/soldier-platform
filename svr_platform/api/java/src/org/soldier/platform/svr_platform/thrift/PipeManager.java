package org.soldier.platform.svr_platform.thrift;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

public class PipeManager {
	private static PipeManager sInstance = new PipeManager();
	public static PipeManager getInstance() {
		return sInstance;
	}
	
	private PipePool inClientBlockingPipePool;
	private PipePool inClientUnBlockingPipePool;
	private PipePool outClientBlockingPipePool;
	private PipePool outClientUnBlockingPipePool;
	
	public PipeManager() {
		inClientBlockingPipePool = new PipePool(50, true, true, 50);
		inClientUnBlockingPipePool = new PipePool(25, false, true, 25);
		outClientBlockingPipePool = new PipePool(50, true, true, 50);
		outClientUnBlockingPipePool = new PipePool(25, true, false, 25);
		System.out.println("PipeManager Created!");
	}
	
	private PipePool getClientPipePool(boolean isIn, boolean isBlocking) {
		if (isIn) {
			if (isBlocking) {
				return inClientBlockingPipePool;
			} else {
				return inClientUnBlockingPipePool;
			}
		} else {
			if (isBlocking) {
				return outClientBlockingPipePool;
			} else {
				return outClientUnBlockingPipePool;
			}
		}
	}
	
	public Pipe openClientIn(boolean isBlocking) throws IOException {
		return getClientPipePool(true, isBlocking).openPipe();
	}
	
	public Pipe openClientOut(boolean isBlocking) throws IOException {
		return getClientPipePool(false, isBlocking).openPipe();
	}
	
	public void closeIn(boolean isClient, boolean isClientBlocking,
			SourceChannel channel, boolean addCache) {
		PipePool pool = null;
		if (isClient) {
			pool = getClientPipePool(true, isClientBlocking);
		} else {
			pool = getClientPipePool(false, isClientBlocking);
		}
		pool.close(channel, addCache);
	}
	
	public void closeOut(boolean isClient, boolean isClientBlocking,
			SinkChannel channel, boolean addCache) {
		PipePool pool = null;
		if (isClient) {
			pool = getClientPipePool(false, isClientBlocking);
		} else {
			pool = getClientPipePool(true, isClientBlocking);
		}
		pool.close(channel, addCache);
	}
	
	public void destory() {
		inClientBlockingPipePool.destroy();
		inClientUnBlockingPipePool.destroy();
		outClientBlockingPipePool.destroy();
		outClientUnBlockingPipePool.destroy();
	}
}
