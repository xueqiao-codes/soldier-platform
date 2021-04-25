package org.soldier.platform.fast_thrift_proxy.dispatcher.transport;

import java.nio.ByteBuffer;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncMethodCall;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingTransport;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Request;

public class ServiceAsyncCall extends TAsyncMethodCall <ServiceAsyncCall> {
	private Request mRequest; 
	
	public ServiceAsyncCall(TAsyncClient client
			, TProtocolFactory protocolFactory
			, TNonblockingTransport transport
			, AsyncMethodCallback<ServiceAsyncCall> callback
			, Request request) {
		super(client, request.getBackendProtocolFactory()
				, transport
				, callback
				, false);
		this.mRequest = request;
	}

	@Override
	protected void write_args(TProtocol protocol) throws TException {
		ByteBuffer transportData = mRequest.getTransportData();
		protocol.getTransport().write(transportData.array()
				, 0
				, transportData.remaining());
	}
	
	public byte[] getResult() throws TException, InstantiationException, IllegalAccessException {
		if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
			throw new IllegalStateException("Method call not finished!");
		}
		
		return getFrameBuffer().array();
	}
	
	public Request getRequest() {
		return mRequest;
	}
}
