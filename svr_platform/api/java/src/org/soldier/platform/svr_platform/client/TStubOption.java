package org.soldier.platform.svr_platform.client;

import org.apache.commons.lang.math.RandomUtils;

public class TStubOption {
	private long routeKey = 0;
	private int timeoutMs = 2000;
	
	/**
	 *  Used by SvrPlatform
	 */
	private StackTraceElement callTrace;
	
	public TStubOption() {
		routeKey = RandomUtils.nextLong();
	}
	
	public TStubOption(long routeKey) {
		this.routeKey = routeKey;
	}
	
	public long getRouteKey() {
		return routeKey;
	}
	
	public TStubOption setRouteKey(long routeKey) {
		this.routeKey = routeKey;
		return this;
	}

	public int getTimeoutMs() {
		return timeoutMs;
	}

	public TStubOption setTimeoutMs(int timeoutMs) {
		this.timeoutMs = timeoutMs;
		return this;
	}

	public StackTraceElement getCallTrace() {
		return callTrace;
	}

	public TStubOption setCallTrace(StackTraceElement callTrace) {
		this.callTrace = callTrace;
		return this;
	}
	
}
