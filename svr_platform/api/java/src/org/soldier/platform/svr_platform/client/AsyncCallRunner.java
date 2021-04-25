package org.soldier.platform.svr_platform.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;

/**
 *  异步调用Runner<br/>
 *  
 *  异步调用过程中分为请求线程和IO线程（SvrContainer的SelectThread）
 *  ，请求线程将包批量发给IO线程后，IO线程负责回收包并将请求回复串行化丢到请求
 *  线程中（不保证回复的时序和请求的时序一直，不同的请求有快有慢）
 * @author Xairy
 *
 */
public class AsyncCallRunner implements IMethodCallback<TBase<?,?>, TBase<?,?>> {
	private static class TCallRecordReq<Req extends TBase<?,?>, Resp extends TBase<?,?>>{
		private long callId;
		private TServiceCall call;
		private IMethodCallback<Req, Resp> callback;
		
		public TCallRecordReq(final long callId,
						   final TServiceCall call,
						   final IMethodCallback<Req, Resp> callback){
			this.callId = callId;
			this.call = call;
			this.callback = callback;
		}

		public TServiceCall getCall() {
			return call;
		}

		public IMethodCallback<Req, Resp> getCallback() {
			return callback;
		}

		public long getCallId() {
			return callId;
		}
	}
	
	private static class TCallRecordResp{
		private long callId;
		private TBase<? ,?> resp;
		private Exception err;
		
		public TCallRecordResp(final long callId,
							   final TBase<?,?> resp){
			this.callId = callId;
			this.resp = resp;
		}
		
		public TCallRecordResp(final long callId,
							   final Exception err){
			this.callId = callId;
			this.err = err;
		}
		
		public long getCallId() {
			return callId;
		}
		
		public TBase<?,?> getResp() {
			return resp;
		}

		public Exception getErr() {
			return err;
		}
	}
	
	private enum EState{
		EInit,
		EStart,
		ECollectingResult,
	}
	
	// 这里采用不安全的HashMap是因为插入和查找是在同一个runner线程中，不能在IO线程访问
	// (就是不能在IMethodCallback的回调方法中访问）
	private Map<Long, TCallRecordReq<?, ?>> callRecordReqMap 
					= new HashMap<Long, TCallRecordReq<?, ?>>();
	
	// 将调用结果串行化到一个队列中进行处理
	private BlockingQueue<TCallRecordResp> callResultQueue = 
					new LinkedBlockingQueue<TCallRecordResp>();
	
	// 处理的状态
	private EState currentState = EState.EInit;
	
	public void start() throws TException{
		if(currentState != EState.EInit){
			throw new TException("state not in init");
		}
		callRecordReqMap.clear();
		callResultQueue.clear();
		currentState = EState.EStart;
		
	}
	
	public <Req extends TBase<?, ?>, Resp extends TBase<?, ?>>
				long addAsyncCall(
						final TServiceCall call,
						IMethodCallback<Req, Resp> callback) throws TException{
		if(currentState != EState.EStart){
			throw new TException("not in start state");
		}
		
		long callId =  SvrContainer.getInstance().sendRequest(call, this);
		TCallRecordReq<Req,Resp> callRecord
			= new TCallRecordReq<Req, Resp>(callId, call, callback);
		callRecordReqMap.put(callId, callRecord);
		return callId;
	}
	
	/**
	 * 整个等待过程启动， 启动前必须现调用start方法，以及添加相应的异步调用
	 * @param timeout 超时时间
	 * @throws InterruptedException 
	 * @careful 并非run之后才发出addAsyncCall的请求，而是addAsyncCall的时候就已经发出具体的网络请求
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run(final long timeout)throws TException{
		if(currentState != EState.EStart){
			throw new TException("not in start state");
		}
		currentState = EState.ECollectingResult;
		long timeleft = timeout;
		while(timeleft > 0){
			long timebegin = System.currentTimeMillis();
			TCallRecordResp resp = null;
			try {
				resp = callResultQueue.poll(timeleft, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(resp != null){
				TCallRecordReq req = callRecordReqMap.get(resp.getCallId());
				if(req != null){
					if(req.getCallback() != null){
						if(resp.getErr() != null) {
							req.getCallback().onError(resp.getCallId(),
									req.getCall().getRequest(), 
									resp.getErr());
						} else {
							req.getCallback().onComplete(resp.getCallId(),
								req.getCall().getRequest(), resp.getResp());
						}
					}
					callRecordReqMap.remove(resp.getCallId());
					if(callRecordReqMap.isEmpty()){
						break;
					}
				}
			}
			timeleft -= (System.currentTimeMillis() - timebegin);
		}
		currentState = EState.EInit; // 放在超时异常前，是为了让IO线程尽快的丢包
		
		if(!callRecordReqMap.isEmpty()){
			for(Entry<Long, TCallRecordReq<?, ?>> callEntry : callRecordReqMap.entrySet()){
				TCallRecordReq timeoutReq = callEntry.getValue();
				if(timeoutReq.getCallback() != null){
					timeoutReq.getCallback().onError(
							timeoutReq.getCallId(), 
							timeoutReq.getCall().getRequest(),
							new TimeoutException("runner has wait for " + timeout + "ms"));
				}
			}
			// 处理超时了， 清空所有的状态记录
			callRecordReqMap.clear();
		}
		
		callResultQueue.clear();
	}

	@Override
	public void onComplete(long callId, TBase<?,?> req, TBase<?,?> resp) {
		if(currentState == EState.EInit){
			// 丢弃遗留的回报
			return ;
		}
		
		callResultQueue.add(new TCallRecordResp(callId, resp));
	}

	@Override
	public void onError(long callId, TBase<?,?> req, Exception e) {
		if(currentState == EState.EInit){
			// 丢弃遗留的回报
			return ;
		}
		
		callResultQueue.add(new TCallRecordResp(callId, e));
	}
}
