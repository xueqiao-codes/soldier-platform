package org.soldier.platform.svr_platform.client;

import org.apache.thrift.async.AsyncMethodCallback;

abstract class SvrAsyncMethodCallback implements AsyncMethodCallback<SvrAsyncMethod> {
	protected TServiceCall serviceCall;
	protected long callId;
	
	public SvrAsyncMethodCallback(final TServiceCall serviceCall){
		this.serviceCall = serviceCall;
	}
	
	public void setCallId(final long callId){
		this.callId = callId;
	}

}
