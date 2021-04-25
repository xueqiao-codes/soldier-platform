package org.soldier.platform.svr_platform.client;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncMethodCall;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TNonblockingTransport;

class SvrAsyncMethod extends TAsyncMethodCall<SvrAsyncMethod>{
	private TServiceCall serviceCall;
	protected SvrAsyncMethod(
			TServiceCall serviceCall,
			TAsyncClient client,
			TProtocolFactory protocolFactory,
			TNonblockingTransport transport, AsyncMethodCallback<SvrAsyncMethod> callback,
			boolean isOneway) {
		super(client, protocolFactory, transport, callback, isOneway);
		this.serviceCall = serviceCall;
	}

	@Override
	protected void write_args(TProtocol protocol) throws TException {
		protocol.writeMessageBegin(
				new TMessage(serviceCall.getMethodName(), TMessageType.CALL, 0));
		serviceCall.getRequest().write(protocol);
		protocol.writeMessageEnd();
	}
	
	public TBase<?,?> getResult() throws TException, InstantiationException, IllegalAccessException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        TMemoryInputTransport memoryTransport = new TMemoryInputTransport(getFrameBuffer().array());
        TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        TBase<?,?> response = serviceCall.getResponseClass().newInstance();
        new SvrServiceClient(prot).receiveResult(response, serviceCall.getMethodName());
        return response;
    }
	
	public long getCallId(){
		return super.getSequenceId();
	}
}
