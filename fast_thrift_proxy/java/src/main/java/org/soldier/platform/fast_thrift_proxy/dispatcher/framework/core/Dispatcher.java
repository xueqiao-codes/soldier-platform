package org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.IWorkRunner;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IRespModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.ITransportLayer;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Request;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Response;

/**
 * 核心调度器, 调度流程为
 *     请求模块处理 --> 进入传输层 --> 回复模块处理 --> 回调
 * 
 * 其中，请求模块和回复模块都可以取消，异步挂起处理流程, 每个模块不用关心整体逻辑，只用关心自己的处理流程，
 * 可以在Request中挂数据，让下游模块帮助处理，减少模块间的耦合度。
 * 
 * 该类负责了核心的流程模块的调度和执行
 * 
 * @author wileywang
 *
 */
public abstract class Dispatcher implements ITransportLayer.ITransportCallback {
	
	private static long sLastRequestId = 100;
	
	private static long createRequestId() {
		synchronized(Dispatcher.class) {
			return sLastRequestId++;
		}
	}
	
	private IWorkRunner mDispatcherRunner; 
	
	protected List<IModule<IReqModule.Param>> mReqModuleList;
	private Map<Long, ModuleTask<IReqModule.Param>> mPendingRequests;
	
	protected List<IModule<IRespModule.Param>> mRespModuleList;
	private Map<Long, ModuleTask<IRespModule.Param>> mPendingResponses;
	
	protected ITransportLayer mTransportLayer;
	private Map<Long, Request> mPendingTransports;
	
	private Map<Long, IRequestCallback> mCallbacks;
	
	private IModuleTaskExecutor<IReqModule.Param> mRequestTaskExecutor 
			= new IModuleTaskExecutor<IReqModule.Param>() {

		@Override
		public void onTaskFinished(long requestId, IReqModule.Param reqParam) {
			checkInDispatcherThread();
			if (null == mPendingRequests.remove(requestId)) {
				AppLog.i("request onTaskFinished, drop request(not found, maybe cancelled) for requestId=" + requestId);
				return ;
			}
			
			// 如果上层的模块已经进行了数据的处理，就使用对应的模块
			if (reqParam.getRequest().getTransportData() == null) {
				onResponseCallback(requestId, reqParam.getRequest(), 
						new Response(ErrorCode.INNER_ERROR, "Encode transport data failed!"));
				return ;
			}
			
			mPendingTransports.put(requestId, reqParam.getRequest());
			
			mTransportLayer.onTransport(Dispatcher.this, requestId, reqParam.getRequest());
		}

		@Override
		public void onTaskCancelled(long requestId, IReqModule.Param reqParam
				, int errorCode, String errorMsg, IModule<IReqModule.Param> module) {
			checkInDispatcherThread();
			if (null == mPendingRequests.remove(requestId)) {
				AppLog.i("request onTaskCancelled, drop request(not found, maybe cancelled) for requestId=" + requestId);
				return ;
			}
			
			onResponseCallback(requestId, reqParam.getRequest(), new Response(errorCode, errorMsg));
		}
		
	};
	
	private IModuleTaskExecutor<IRespModule.Param> mResponseTaskExecutor
			= new IModuleTaskExecutor<IRespModule.Param>() {

		@Override
		public void onTaskFinished(long requestId, IRespModule.Param respParam) {
			checkInDispatcherThread();
			if (null == mPendingResponses.remove(requestId)) {
				AppLog.i("response onTaskFinished, drop request(not found, maybe cancelled) for requestId=" + requestId);
				return ;
			}
			
			onResponseCallback(requestId, respParam.getRequest(), respParam.getResponse());
		}

		@Override
		public void onTaskCancelled(long requestId, IRespModule.Param respParam,
				int errorCode, String errorMsg, IModule<IRespModule.Param> module) {
			checkInDispatcherThread();
			if (null == mPendingResponses.remove(requestId)) {
				AppLog.i("response onTaskCancelled, drop request(not found) for requestId=" + requestId);
				return ;
			}
			
			onResponseCallback(requestId, respParam.getRequest(), new Response(errorCode, errorMsg));
		}
		
	};
	
	public Dispatcher(IWorkRunner dispatcherRunner) {
		this.mDispatcherRunner = dispatcherRunner;
		
		this.mReqModuleList = new ArrayList<IModule<IReqModule.Param>>();
		this.mPendingRequests = new HashMap<Long, ModuleTask<IReqModule.Param>>();
		
		this.mRespModuleList = new ArrayList<IModule<IRespModule.Param>>();
		this.mPendingResponses = new HashMap<Long, ModuleTask<IRespModule.Param>>();
		
		this.mPendingTransports = new HashMap<Long, Request>();
		this.mCallbacks = new HashMap<Long, IRequestCallback>();
	}
	
	protected IWorkRunner getDispatcherRunner() {
		return mDispatcherRunner;
	}
	
	/**
	 *  发送成功，返回大于0
	 */
	public long send(final Request request, final IRequestCallback callback) {
		if (request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		if (callback == null) {
			throw new IllegalArgumentException("response should not be null");
		}
		
		final long requestId = createRequestId();
		mDispatcherRunner.postWork(new Runnable() {
			@Override
			public void run() {
				onHandleRequest(requestId, request, callback);
			}
		});

		return requestId;
	}
	
	public void cancel(final long requestId) {
		if (requestId <= 0) {
			return ;
		}
		
		if (isInDispatcherThread()) {
			onCancel(requestId);
		} else {
			mDispatcherRunner.postWork(new Runnable() {

				@Override
				public void run() {
					onCancel(requestId);
				}
				
			});
		}
		
	}
	
	private void onCancel(long requestId) {
		if (null == mCallbacks.remove(requestId)) {
			return ;
		}
		
		ModuleTask<IReqModule.Param> reqTask = mPendingRequests.remove(requestId);
		if (reqTask != null) {
			reqTask.cancel();
			return ;
		}
		
		Request transportReq = mPendingTransports.remove(requestId);
		if (transportReq != null) {
			mTransportLayer.cancel(requestId);
			return ;
		}
		
		ModuleTask<IRespModule.Param> respTask = mPendingResponses.remove(requestId);
		if (respTask != null) {
			respTask.cancel();
			return ;
		}
	}
	
	
	private void onHandleRequest(long requestId, Request request, IRequestCallback callback) {
		mCallbacks.put(requestId, callback);
		
		if (AppLog.debugEnabled()) {
			StringBuilder logBuilder = new StringBuilder(128);
			logBuilder.append("handle request, requestId=");
			logBuilder.append(requestId);
			logBuilder.append(", serviceKey=");
			logBuilder.append(request.getServiceKey());
			logBuilder.append(", callback=");
			logBuilder.append(callback);

			AppLog.d(logBuilder.toString());
		}
		
		ModuleTask<IReqModule.Param> reqTask 
			= new ModuleTask<IReqModule.Param>(
					mReqModuleList, requestId, new IReqModule.Param(request), mRequestTaskExecutor);
		mPendingRequests.put(requestId, reqTask);
		reqTask.start();
	}
	
	private void onResponseCallback(final long requestId, final Request request, final Response response) {
		request.setFinishedTimestampMs(System.currentTimeMillis());
		try {
			if (AppLog.debugEnabled()) {
				StringBuilder logBuilder = new StringBuilder(128);
				logBuilder.append("Response returns, requestId=");
				logBuilder.append(requestId);
				logBuilder.append(", serviceKey=");
				logBuilder.append(request.getServiceKey());
				logBuilder.append(", errorCode=");
				logBuilder.append(response.getErrorCode());
				if (response.getErrorCode() == 0) {
					logBuilder.append(", responseContentLen=");
					logBuilder.append(response.getResponseContent().length);
				} else {
					logBuilder.append(", subErrorCode=");
					logBuilder.append(response.getSubErrorCode());
					logBuilder.append(", errorMsg=");
					logBuilder.append(response.getErrorMsg());
				}

				AppLog.d(logBuilder.toString());
			}
			final IRequestCallback callback = mCallbacks.remove(requestId);
			if (callback != null) {
				if (request.getRequestOption() != null && request.getRequestOption().getCallbackRunner() != null) {
					request.getRequestOption().getCallbackRunner().postWork(new Runnable() {

						@Override
						public void run() {
							// 按照对应的线程进行回调
							try {
								callback.onRequestFinished(requestId, request, response);
							} catch (Throwable e) {
								AppLog.e(e.getMessage(), e);
							}
						}
						
					});
				} else {
					callback.onRequestFinished(requestId, request, response);
				}
			} else {
				AppLog.w("onResponseCallback callback is not found for requestId=" + requestId);
			}
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
	private void onHandleResponse(long requestId, Response response) {
		Request transportReq = mPendingTransports.remove(requestId);
		if (transportReq == null) {
			AppLog.i("onHandleResponse drop request(not found, maybe cancelled) for requestId="  + requestId);
			return ;
		}
		
		ModuleTask<IRespModule.Param> respTask
			= new ModuleTask<IRespModule.Param>(mRespModuleList, requestId
					, new IRespModule.Param(transportReq, response), mResponseTaskExecutor);
		mPendingResponses.put(requestId, respTask);
		respTask.start();
	}
	
	public boolean isInDispatcherThread() {
		return Thread.currentThread() == mDispatcherRunner.getThread();
	}
	
	public void checkInDispatcherThread() {
		if (!isInDispatcherThread()) {
			throw new IllegalStateException("calling this should be in wup dispatcher thread");
		}
	}
	
	@Override
	public void onRequestFinished(final long requestId, final Response response) {
		if (isInDispatcherThread()) {
			try {
				onHandleResponse(requestId, response);
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		} else {
			mDispatcherRunner.postWork(new Runnable() {
				@Override
				public void run() {
					try {
						onHandleResponse(requestId, response);
					} catch (Throwable e) {
						AppLog.e(e.getMessage(), e);
					}
				}
			});
		}
	}
}
