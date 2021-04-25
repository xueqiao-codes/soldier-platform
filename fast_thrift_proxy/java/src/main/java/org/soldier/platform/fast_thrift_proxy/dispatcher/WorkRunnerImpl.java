package org.soldier.platform.fast_thrift_proxy.dispatcher;

import java.util.concurrent.LinkedBlockingQueue;

import org.soldier.base.logger.AppLog;

public class WorkRunnerImpl extends Thread implements IWorkRunner {
	
	private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<Runnable>();
	private volatile boolean mRunning;
	
	public WorkRunnerImpl() {
		this.setDaemon(true);
	}
	
	public WorkRunnerImpl startWork() {
		mRunning = true;
		this.start();
		return this;
	}
	
	public void endWork() {
		mRunning = false;
		this.interrupt();
	}
	
	@Override
	public void run() {
		setName("WorkRunner-" + getId());
		while(mRunning) {
			try {
				Runnable task = mQueue.take();
				if (task != null) {
					task.run();
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage());
			}
		} 
	}
	
	@Override
	public Thread getThread() {
		return this;
	}

	@Override
	public void postWork(Runnable runnable) {
		try {
			mQueue.put(runnable);
		} catch (Throwable e) {
			AppLog.e(e.getMessage());
		}
	}

	@Override
	public void postWorkDelayed(Runnable runnable, long timeDelayed) {
		throw new UnsupportedOperationException("postWorkDelayed is not supported");
	}

}
