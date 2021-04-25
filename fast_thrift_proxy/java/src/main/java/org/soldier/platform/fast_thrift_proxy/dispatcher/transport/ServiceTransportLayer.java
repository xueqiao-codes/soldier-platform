package org.soldier.platform.fast_thrift_proxy.dispatcher.transport;

import java.io.IOException;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.ITransportLayer;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Request;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Response;

public class ServiceTransportLayer  implements ITransportLayer {

	private TAsyncClientManager mAsyncClientManager;
	
	public ServiceTransportLayer() throws IOException {
		mAsyncClientManager = new TAsyncClientManager();
	}
	
	private class AsyncCall implements AsyncMethodCallback<ServiceAsyncCall> {
		private long mRequestId;
		private ITransportCallback mTransportCallback;
		private Request mRequest;
		
		public AsyncCall(long requestId
				, ITransportCallback transportCallback
				, Request request) {
			this.mRequestId = requestId;
			this.mTransportCallback = transportCallback;
			this.mRequest = request;
		}
		
		@Override
		public void onComplete(ServiceAsyncCall response) {
			Response transportResponse = new Response();
			try {
				transportResponse.setResponseContent(response.getResult());
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
				transportResponse.setErrorCode(ErrorCode.INNER_ERROR);
				transportResponse.setErrorMsg(e.getMessage());
			} 
			
			mRequest.setTransportFinishedTimestampMs(System.currentTimeMillis());
			mTransportCallback.onRequestFinished(mRequestId, transportResponse);
		}

		@Override
		public void onError(Exception e) {
			AppLog.e(mRequestId + " onError, exception=" + e.getMessage(), e);
			Response transportResponse = new Response();
			transportResponse.setErrorCode(ErrorCode.NETWORK_EXCEPTION);
			transportResponse.setErrorMsg(e.getMessage());
			if (e != null) {
				if (e instanceof java.util.concurrent.TimeoutException) {
					transportResponse.setErrorCode(ErrorCode.CONNECTION_TIMEOUT);
				} else if (e instanceof java.net.ConnectException) {
					transportResponse.setErrorCode(ErrorCode.CONNECTED_FAILED);
				}
			}
			
			mRequest.setTransportFinishedTimestampMs(System.currentTimeMillis());
			mTransportCallback.onRequestFinished(mRequestId, transportResponse);
		}
	}
	
	@Override
	public void onTransport(ITransportCallback callback
			, long requestId
			, Request request) {
		try {
			TNonblockingTransport  transport = new TNonblockingSocket(
					request.getRemoteAddress() 
					, request.getRemotePort()
					, request.getRequestOption().getTimeoutMills());
			
			ServiceAsyncClient asyncClient = new ServiceAsyncClient(
					request.getBackendProtocolFactory()
					, mAsyncClientManager
					, transport
					, request.getRequestOption().getTimeoutMills());
			
			ServiceAsyncCall asyncCall = new ServiceAsyncCall(asyncClient
					, request.getBackendProtocolFactory()
					, transport
					, new AsyncCall(requestId, callback, request)
					, request);
			
			request.setTransportStartTimestampMs(System.currentTimeMillis());
			mAsyncClientManager.call(asyncCall);
			
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			callback.onRequestFinished(requestId, new Response(ErrorCode.INNER_ERROR, e.getMessage()));
		}
	}

	@Override
	public void cancel(long requestId) {
		
	}


}
