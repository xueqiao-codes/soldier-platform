package org.soldier.platform.svr_platform.client;

import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingTransport;
import org.soldier.platform.svr_platform.thrift.InpNonblockingSocket;

class InpSvrAsyncClient extends SvrAsyncClient {

	public InpSvrAsyncClient(TProtocolFactory protocolFactory,
			TAsyncClientManager manager, TNonblockingTransport transport,
			long timeout) {
		super(protocolFactory, manager, transport, timeout);
	}
	
	@Override
	protected void onError(Exception e) {
		if (___transport instanceof InpNonblockingSocket) {
			((InpNonblockingSocket)___transport).getInpSocket().closeThorough();
		}
		super.onError(e);
	}

}
