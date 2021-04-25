package org.soldier.platform.svr_platform.client;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.soldier.base.StringFactory;

/**
 *  方法调用的描述
 * @author Xairy
 */
public class TServiceCall {
	private int serviceKey = 0;    // 服务的命令号
	private String methodName = ""; // 调用的方法名
	private TBase<?,?> request;     // 请求参数
	private Class<? extends TBase<?,?>> responseClass;    // 回复参数
	private int routeKey = 0;      // 请求的routeKey
	private int timeout = 0;       // 请求的超时
	private boolean oneWay = false ;  // 是否是oneWay的请求方法
	private String chooseServiceIp = null;
	private int chooseServicePort = 0;
	
	public int getServiceKey() {
		return serviceKey;
	}
	public void setServiceKey(int serviceKey) {
		this.serviceKey = serviceKey;
	}
		
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public TBase<?, ?> getRequest() {
		return request;
	}
	public void setRequest(TBase<?, ?> request) {
		this.request = request;
	}
	
	public Class<? extends TBase<?,?>> getResponseClass() {
		return responseClass;
	}
	public void setResponse(Class<? extends TBase<?,?>> responseClass) {
		this.responseClass = responseClass;
	}
	
	public int getRouteKey() {
		return routeKey;
	}
	public void setRouteKey(int routeKey) {
		this.routeKey = routeKey;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public boolean getOneWay() {
		return oneWay;
	}
	public void setOneWay(boolean isOneWay) {
		this.oneWay = isOneWay;
	}
	
	public void setChooseServiceIp(final String ip) {
		this.chooseServiceIp = ip;
	}
	
	public String getChooseServiceIp() {
		return this.chooseServiceIp;
	}
	
	public int getChooseServicePort() {
		return chooseServicePort;
	}
	
	public void setChooseServicePort(int chooseServicePort) {
		this.chooseServicePort = chooseServicePort;
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer(256);
		buffer.append("{serviceKey=" + serviceKey);
		buffer.append(", methodName=" + methodName);
		buffer.append(", routeKey=" + routeKey);
		buffer.append(", timeout=" + timeout );
		buffer.append(", oneWay=" + oneWay);
		buffer.append(", chooseServiceIp=" + chooseServiceIp);
		buffer.append(", chooseServicePort=" + chooseServicePort);
		buffer.append(", request=");
		try {
			AutoExpandingBufferWriteTransport transport = new AutoExpandingBufferWriteTransport(128, 2.0);
			TProtocol protol = new TSimpleJSONProtocol(transport);
			request.write(protol);
			buffer.append(StringFactory.netUtf8String(transport.getBuf().array(), transport.getPos()));
		} catch (Exception e) {} 
		buffer.append(", responseClass=" + responseClass.getName());
		buffer.append("}");
		return buffer.toString();
	}
	
	
	
}
