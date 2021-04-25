package org.soldier.platform.svr_platform.client;

import org.apache.log4j.Logger;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.soldier.base.StringFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;

class UnNotifyAsyncMethodCallback extends SvrAsyncMethodCallback{
	private static Logger logger = Logger.getLogger(UnNotifyAsyncMethodCallback.class);
	private TRequestOption requestOption;
	
	public UnNotifyAsyncMethodCallback(final TServiceCall serviceCall,
								  final TRequestOption requestOption){
		super(serviceCall);
		if(requestOption == null){
			this.requestOption = new TRequestOption();
		} else {
			this.requestOption = requestOption;
		}
	}
	
	@Override
	public void onComplete(SvrAsyncMethod response) {
		try {
			if(SvrContainer.isLogOn()){
				TBase<?,?> result = response.getResult();
				AutoExpandingBufferWriteTransport transport = new AutoExpandingBufferWriteTransport(128, 2.0);
				TProtocol protol = new TSimpleJSONProtocol(transport);
				result.write(protol);
				StringBuffer logBuffer = new StringBuffer(256);
				logBuffer.append("SUCCESS:");
				logBuffer.append("{serviceCall = " + serviceCall );
				logBuffer.append(",requestOption=" + requestOption );
				logBuffer.append("}->{response = ");
				logBuffer.append(StringFactory.netUtf8String(transport.getBuffer(), transport.getPos()));
				logBuffer.append(" }");
				logger.info(logBuffer.toString());
				logBuffer = null;
			}
//			if (!SvrConfiguration.getIsUsingInpService()) {
				ServiceFinderFactory.getServiceFinder().updateCallInfo(
						serviceCall.getServiceKey(), serviceCall.getMethodName(), 
						serviceCall.getChooseServiceIp(), null);
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			if (!SvrConfiguration.getIsUsingInpService()) {
				ServiceFinderFactory.getServiceFinder().updateCallInfo(
						serviceCall.getServiceKey(), serviceCall.getMethodName(), 
						serviceCall.getChooseServiceIp(), e);
//			}
		}
	}

	@Override
	public void onError(Exception exception) {
		if(requestOption == null){
			return ;
		}
		if(requestOption.getRetryTimes() > 0){
			try {
				TRequestOption newRequestOption = (TRequestOption)requestOption.clone();
				newRequestOption.setRetryTimes(requestOption.getRetryTimes() - 1);
				SvrContainer.getInstance().sendRequest(serviceCall, newRequestOption);
			} catch (TException e) {
				e.printStackTrace();
			}
		}else { 
			if(requestOption.getErrorAgentTimes() > 0){
			//TODO 异步交给RetryAgent去做一些事情
			}
			
			StringBuffer logBuffer = new StringBuffer(256);
			logBuffer.append("FAILED:");
			logBuffer.append("{serviceCall = " + serviceCall );
			logBuffer.append(",requestOption=" + requestOption );
			logBuffer.append("} throws "); 
			logBuffer.append(exception.getClass().getName());
			logBuffer.append("(");
			logBuffer.append(exception.getMessage());
			logBuffer.append(")");
			logger.error(logBuffer.toString());
		}
	}
}
