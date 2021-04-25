package org.soldier.platform.svr_platform.client;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;

class SvrServiceClient extends TServiceClient{
	public SvrServiceClient(TProtocol prot) {
		super(prot);
	}
	
	public void receiveResult(TBase<?,?> result, 
			final String methodName) throws TException{
		super.receiveBase(result, methodName);
	}
}
