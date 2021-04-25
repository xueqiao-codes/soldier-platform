package org.soldier.platform.svr_platform.container;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;

public class IfaceProxy implements InvocationHandler{
	private Object m_impl;
	private Method m_getParameterNameMethod;
	
	private int m_servickey;
	
	public IfaceProxy(final Object impl){
		this.m_impl = impl;
		try {
			Method maybeGetParameterNameMethod = m_impl.getClass().getMethod(
					"getMethodParameterName", String.class);
			if(maybeGetParameterNameMethod.getReturnType().equals(String[].class) && 
					Modifier.isPublic(maybeGetParameterNameMethod.getModifiers())) {
				m_getParameterNameMethod = maybeGetParameterNameMethod;
			}
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
	}
	
	public void setServiceKey(int serviceKey) {
		this.m_servickey = serviceKey;
	}

	private static enum CallResult {
		SUCCESS,
		BUSINESS_ERROR,
		PLATFORM_ERROR
	}
	
	@Override
	public Object invoke(Object thisObject, Method method, Object[] args)
			throws Throwable {
		Map<String, String> requestTags = new HashMap<String, String>();
		requestTags.put("method", method.getName());
		requestTags.put("servicekey", String.valueOf(this.m_servickey));
		
		CallResult callResult = CallResult.SUCCESS;
		
		Object invokeResult = null;
		long currentTimeMillis = System.currentTimeMillis();
		StringBuffer methodCallBuffer = null;
		try {
			if (AppLog.infoEnabled()) {
				methodCallBuffer = new StringBuffer(128);
				formatMethodCallLog(methodCallBuffer, method, args);
			}
			invokeResult = method.invoke(m_impl, args);
			
			if(methodCallBuffer != null){
				StringBuffer buffer = new StringBuffer(methodCallBuffer.length() + 32);
				formatMethodDescLog(buffer, methodCallBuffer, System.currentTimeMillis() - currentTimeMillis);
				buffer.append(" return ");
				if(method.getReturnType() != void.class ) {
					if(invokeResult == null) {
						buffer.append(" null ");
					} else {
						String resultString = invokeResult.toString();
						if (resultString.length() > 4096) {
							buffer.append(resultString, 0, 4096);
							buffer.append("...");
						} else {
							buffer.append(resultString);
						}
					}
				}
				AppLog.i(buffer.toString());
			}
			
			AttrReporterFactory.getDefault().average(
					AttrReporterFactory.getDefault().requireKey("service.server.response.timems", requestTags)
					, System.currentTimeMillis() - currentTimeMillis);
			return invokeResult;
		} catch(InvocationTargetException e){
			if(methodCallBuffer != null){
				StringBuffer buffer = new StringBuffer(methodCallBuffer.length() + 32);
				formatMethodDescLog(buffer, methodCallBuffer, System.currentTimeMillis() - currentTimeMillis);
				buffer.append(" throws ");
				buffer.append(e.getTargetException().toString());
				AppLog.e(buffer.toString());
			}
			if (e.getTargetException().getClass().getName().equals(ErrorInfo.class.getName())) {
				ErrorInfo err = (ErrorInfo)e.getTargetException();
				if (err.getErrorCode() >= 10000 && err.getErrorCode() < 50000) {
					callResult = CallResult.PLATFORM_ERROR;
				} else {
					callResult = CallResult.BUSINESS_ERROR;
				}
			} else {
				callResult = CallResult.PLATFORM_ERROR;
			}
			
            throw e.getTargetException();
        } finally {
        	Map<String, String> totalTags = new HashMap<String, String>();
        	totalTags.put("servicekey", String.valueOf(this.m_servickey));
        	
        	AttrReporterFactory.getDefault().inc(AttrReporterFactory.getDefault().requireKey("service.server.request.count", requestTags), 1);
        	AttrReporterFactory.getDefault().inc(AttrReporterFactory.getDefault().requireKey("service.server.request.total", totalTags), 1);
        	if (callResult == CallResult.SUCCESS) {
        		AttrReporterFactory.getDefault().inc(
    					AttrReporterFactory.getDefault().requireKey("service.server.success.count", requestTags), 1);
        		AttrReporterFactory.getDefault().inc(
    					AttrReporterFactory.getDefault().requireKey("service.server.success.total", totalTags), 1);
        	} else if(callResult == CallResult.BUSINESS_ERROR) {
        		AttrReporterFactory.getDefault().inc(
						AttrReporterFactory.getDefault().requireKey("service.server.failed.business.count", requestTags), 1);
        		AttrReporterFactory.getDefault().inc(AttrReporterFactory.getDefault().requireKey("service.server.failed.business.total", totalTags), 1);
        	} else if(callResult == CallResult.PLATFORM_ERROR) {
        		AttrReporterFactory.getDefault().inc(
						AttrReporterFactory.getDefault().requireKey("service.server.failed.platform.count", requestTags), 1);
        		AttrReporterFactory.getDefault().inc(
						AttrReporterFactory.getDefault().requireKey("service.server.failed.platform.total", totalTags), 1);
        	}
        }
	}
	
	private void formatMethodDescLog(StringBuffer buffer, StringBuffer methodCallBuffer, long timeEscape) {
		buffer.append("[perform=");
		buffer.append(timeEscape);
		buffer.append("ms] ");
		buffer.append(methodCallBuffer);
	}
	
	
	private void formatMethodCallLog(
				StringBuffer buffer,
				final Method method, 
				final Object[] args) {
		buffer.append(method.getName());
		buffer.append("(");
		if(args == null) {
			buffer.append(")");
			return ;
		}
		
		// 探测是否能够得到调用方法中参数的名字，如果能得到，就打印到日志中
		String[] parameterNames = null;
		if(m_getParameterNameMethod != null) {
			try {
				parameterNames = (String[]) m_getParameterNameMethod.invoke(m_impl, method.getName());
			} catch (Exception e) {
				AppLog.e(e.getMessage(), e);
			}
			if(parameterNames != null && parameterNames.length != args.length){
				parameterNames = null;
			}
		}
		
		for(int index = 0; index < args.length; ++index) {
			if(index > 0) {
				buffer.append(", ");
			}
			// 记录调用参数的名称，方便阅读
			if(parameterNames != null 
				&& parameterNames[index] != null 
				&& !parameterNames[index].isEmpty()) {
				buffer.append(parameterNames[index]);
				buffer.append('=');
			}
			// 记录具体的参数
			Object arg = args[index];
			if(arg == null) {
				buffer.append("null");
			} else {
				if(arg instanceof String){
					buffer.append('"');
					String argStr = (String)arg;
					if(argStr.length() > 64){
						buffer.append("[");
						buffer.append(argStr.length());
						buffer.append("]");
						buffer.append(argStr.substring(0, 64));
						buffer.append("...");
					} else {
						buffer.append(argStr);
					}
					buffer.append('"');
				} else {
					String logInfoStr = arg.toString();
					if ( SvrConfiguration.getLogItemMaxLength() > 0
						 && logInfoStr.length() >  SvrConfiguration.getLogItemMaxLength()) {
						buffer.append(logInfoStr.substring(0, 
								(int)SvrConfiguration.getLogItemMaxLength()));
					} else {
						buffer.append(logInfoStr);
					}
				}
			}
		}
		buffer.append(")");
	}


}
