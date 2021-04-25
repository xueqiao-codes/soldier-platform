package org.soldier.platform.fast_thrift_proxy.dispatcher;

public class ErrorCode {
	public static final int REQ_CONTENT_ERROR = 1007;
	
	public static final int INNER_ERROR = 1008;

	public static final int CONNECTION_TIMEOUT = 1009; // 连接超时
	public static final int READ_TIMEOUT = 1010; // 读超时
	public static final int CONNECTED_FAILED = 1011; // 连接失败
	public static final int NETWORK_EXCEPTION = 1012;
	
	public static final int ERROR_FORBIDDEN = 1020;  //访问方法被禁止
	public static final int ERROR_NOT_AUTH = 1021;   //校验失败
	public static final int ERROR_CONFIG = 1022;     //内部的配置错误
	
	
	public static final int HAWK_CALL_ERROR = 1023; // 从hawk获取验证失败
	
}
