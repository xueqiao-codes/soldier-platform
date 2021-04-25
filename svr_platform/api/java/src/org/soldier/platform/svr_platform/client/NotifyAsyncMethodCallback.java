package org.soldier.platform.svr_platform.client;

import org.apache.thrift.TBase;

class NotifyAsyncMethodCallback<Req extends TBase<?,?>, Resp extends TBase<?,?>> 
			extends SvrAsyncMethodCallback {
	private IMethodCallback<Req, Resp> clientCallback;
	
	public NotifyAsyncMethodCallback(final TServiceCall serviceCall,
			IMethodCallback<Req, Resp> clientCallback){
		super(serviceCall);
		this.clientCallback = clientCallback;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(SvrAsyncMethod response) {
		if(clientCallback != null){
			try {
				Resp result = (Resp)response.getResult();
				
				clientCallback.onComplete(callId, (Req)serviceCall.getRequest(), result);
				
//				if (!SvrConfiguration.getIsUsingInpService()) {
					ServiceFinderFactory.getServiceFinder().updateCallInfo(
							serviceCall.getServiceKey(), serviceCall.getMethodName(), 
							serviceCall.getChooseServiceIp(), null);
//				}
			} catch (Exception e) {
				onError(e);
//				if (!SvrConfiguration.getIsUsingInpService()) {
					ServiceFinderFactory.getServiceFinder().updateCallInfo(
						serviceCall.getServiceKey(), serviceCall.getMethodName(), 
						serviceCall.getChooseServiceIp(), e);
//				}
			} 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onError(Exception exception) {
		if(clientCallback != null){
			clientCallback.onError(callId, (Req)serviceCall.getRequest(), exception);
		}
	}

}
