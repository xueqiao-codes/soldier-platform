package org.soldier.platform.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class HeartBeatWorker implements IMethodCallback<PlatformArgs, PlatformArgs> {
	
	private AsyncCallRunner mCallRunner = new AsyncCallRunner();
	
	private long startTimestamp;
	private Map<Integer, Long> callResults = new HashMap<Integer, Long>();
	
	public HeartBeatWorker() {
	}
	
	public void run(Set<Integer> serviceKeys, int timeout) throws TException {
		callResults.clear();
		mCallRunner.start();
		
		startTimestamp = System.currentTimeMillis();
		for (Integer serviceKey : serviceKeys) {
			TServiceCall serviceCall = new TServiceCall();
			serviceCall.setMethodName("#heartBeat");
			serviceCall.setChooseServiceIp("127.0.0.1");
			
			PlatformArgs req = new PlatformArgs();
			req.setSourceDesc(String.valueOf(serviceKey));
			serviceCall.setRequest(req);
			serviceCall.setResponse(PlatformArgs.class);
			serviceCall.setServiceKey(serviceKey);
			serviceCall.setTimeout(timeout);
			
			mCallRunner.addAsyncCall(serviceCall, this);
		}
		
		mCallRunner.run(timeout);
	}
	
	public Map<Integer, Long> getResults() {
		return callResults;
	}
	
	@Override
	public void onComplete(long callId, PlatformArgs req, PlatformArgs resp) {
	}

	@Override
	public void onError(long callId, PlatformArgs req, Exception e) {
		int serviceKey = Integer.valueOf(req.getSourceDesc());
		
//		AppLog.e("serviceKey" + serviceKey +" " +  e.getMessage() + ", e.class=" + e.getClass().getName());
		if (e != null && 
			(e instanceof TApplicationException) || (e instanceof TimeoutException))  {
			callResults.put(serviceKey, System.currentTimeMillis() - startTimestamp);
			return ;
		} 
		callResults.put(serviceKey, -1L);
	}
	
	
}
