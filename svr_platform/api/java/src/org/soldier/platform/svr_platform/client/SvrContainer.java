package org.soldier.platform.svr_platform.client;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import org.soldier.base.Assert;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpNonblockingSocket;
import org.soldier.platform.svr_platform.thrift.TInpAsyncClientManager;

/**
 * 服务提供容器
 */
public class SvrContainer {
	private static Logger logger = Logger.getLogger(SvrContainer.class);
	private static boolean logOn = false;	
	private static SvrContainer instance = null;
	
	private IServiceFinder serviceFinder = null;
	private TAsyncClientManager remoteAsyncClientManager= null;
	private TAsyncClientManager inpAsyncClientManager = null;
	
	public static void setLogOn(final boolean isOn){
		logOn = isOn;
	}
	
	public static boolean isLogOn(){
		return logOn;
	}
	
	private static synchronized void init(){
		if(instance == null){
			try {
				instance = new SvrContainer();
			} catch (IOException e) {
				Assert.True(false, "AsyncMethodAgent Construct throws IOException, " +
						"not supported for select, " + e.getMessage());
			}
		}
	}
	
	public static SvrContainer getInstance(){
		if(instance == null){
			init();
		}
		return instance;
	}
	
	public static void destory(){
		if(instance != null){
			instance.stop();
			instance = null;
		}
	}
	
	private void stop(){
		if(remoteAsyncClientManager != null){
			remoteAsyncClientManager.stop();
			remoteAsyncClientManager = null;
		}
		if(inpAsyncClientManager != null) {
			inpAsyncClientManager.stop();
			inpAsyncClientManager = null;
		}
	}
	
	protected SvrContainer() throws IOException{
//		if (SvrConfiguration.getIsUsingInpService()) {
//			asyncClientManager = new TInpAsyncClientManager();
//		} else {
//			asyncClientManager = new TAsyncClientManager();
//		}
		remoteAsyncClientManager = new TAsyncClientManager();
		if (SvrConfiguration.getIsUsingInpService()) {
			inpAsyncClientManager = new TInpAsyncClientManager();
		}
		
		serviceFinder = ServiceFinderFactory.getServiceFinder();
	}
	
	/**
	 * 在容器中自动进行重试的异步请求发送
	 */
	public void sendRequest(TServiceCall serviceCall,
							TRequestOption option)throws TException{
		sendRequest(serviceCall, option, 
				new UnNotifyAsyncMethodCallback(serviceCall, option));		
	}
	
	public <Req extends TBase<?,?>, Resp extends TBase<?,?>>
			long sendRequest(TServiceCall serviceCall,
							 final IMethodCallback<Req, Resp> clientCallback) throws TException{
		return sendRequest(serviceCall, null, 
				new NotifyAsyncMethodCallback<Req, Resp>(serviceCall, clientCallback));
	}
	
	/**
	 *  自定义回调的发送请求
	 */
	private long sendRequest(TServiceCall serviceCall,
							 final TRequestOption requestOption,
							 SvrAsyncMethodCallback callback) throws TException{
		TNonblockingTransport  transport = null;
		boolean isServiceCallInpMode = false;
		if (SvrConfiguration.getIsUsingInpService() 
				&& SvrConfiguration.isServiceInProcess(serviceCall.getServiceKey())) {
			isServiceCallInpMode = true;
		}
		try {
			if (isServiceCallInpMode) {
				// Inp timeout is not good for system
				serviceCall.setTimeout(Integer.MAX_VALUE);
				transport = new InpNonblockingSocket(serviceCall.getServiceKey());
			} else {
				if (serviceCall.getChooseServiceIp() == null
						|| serviceCall.getChooseServiceIp().isEmpty()) {
					serviceCall.setChooseServiceIp(
							serviceFinder.getServiceIp(serviceCall.getServiceKey(), 
								serviceCall.getMethodName(),
								serviceCall.getRouteKey()));
				}
				if (serviceCall.getChooseServicePort() == 0) {
					serviceCall.setChooseServicePort(
							serviceFinder.getServicePort(serviceCall.getServiceKey()));
				}
				
				transport =
						new TNonblockingSocket(
								serviceCall.getChooseServiceIp(), 
								serviceCall.getChooseServicePort(), 
								serviceCall.getTimeout());
			}
		} catch (IOException e) {
			throw new TException(e.getMessage());
		} catch (ServiceException e) {
			throw new TException(e.getMessage());
		}
		TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
		SvrAsyncClient client = null;
		if (isServiceCallInpMode) {
			client = new InpSvrAsyncClient(protocolFactory,
						this.inpAsyncClientManager, transport, serviceCall.getTimeout());
		} else {
			client = new SvrAsyncClient(protocolFactory, 
						this.remoteAsyncClientManager, transport, serviceCall.getTimeout());
		}
		SvrAsyncMethod method = new SvrAsyncMethod(serviceCall, client, protocolFactory, 
				transport, callback, serviceCall.getOneWay());
		callback.setCallId(method.getCallId());
		client.setCurrentMethod(method);
		if (isServiceCallInpMode) {
			inpAsyncClientManager.call(method);
		} else {
			remoteAsyncClientManager.call(method);
		}
		
		long callId = method.getCallId();
		if(logOn){
			StringBuffer logBuffer = new StringBuffer(256);
			logBuffer.append("SEND REQUEST {");
			logBuffer.append("callId=" + callId);
			logBuffer.append(", serviceCall=" + serviceCall );
			logBuffer.append(", requestOption=" + requestOption );
			logBuffer.append(", callback=" + callback.getClass().getName() + "}");
			logger.info(logBuffer.toString());
		}
		return callId;
	}	
}
