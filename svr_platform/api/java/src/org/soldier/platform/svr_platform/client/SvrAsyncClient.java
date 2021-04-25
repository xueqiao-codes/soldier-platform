package org.soldier.platform.svr_platform.client;

import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.async.TAsyncMethodCall;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingTransport;

class SvrAsyncClient extends TAsyncClient {
	public SvrAsyncClient(TProtocolFactory protocolFactory,
			TAsyncClientManager manager, 
			TNonblockingTransport transport,
			long timeout) {
		super(protocolFactory, manager, transport, timeout);
	}
	
	public void setCurrentMethod(TAsyncMethodCall<?> method){
		this.___currentMethod = method;
	}
	
	@Override
	protected void onComplete() {
		___transport.close();
	}
	
}