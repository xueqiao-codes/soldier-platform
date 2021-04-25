package org.soldier.platform.svr_platform.client;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.AFUNIXSocketTransport;
import org.soldier.platform.svr_platform.thrift.InpSocket;

public class BaseStub {
	private String peerAddr = "";
	private int peerPort;
	
	private int serviceKey;
	private String xForwardAddress;
	
	private File socketFile;
	
	protected BaseStub(int serviceKey) {
		this.serviceKey = serviceKey;
	}
	
	public void setPeerAddr(String peerAddr) {
		if (peerAddr == null) {
			this.peerAddr = "";
		} else {
			this.peerAddr = peerAddr.trim();
		}
	}
	
	public void setPeerPort(int peerPort) {
		this.peerPort = peerPort;
	}
	
	public String getPeerAddr() {
		return this.peerAddr;
	}
	
	public int getPeerPort() {
		return this.peerPort;
	}
	
	public File getSocketFile() {
		return socketFile;
	}

	public void setSocketFile(File socketFile) {
		this.socketFile = socketFile;
	}

	public String getxForwardAddress() {
		return xForwardAddress;
	}

	public void setxForwardAddress(String xForwardAddress) {
		this.xForwardAddress = xForwardAddress;
	}
	
	protected String chooseServiceAddr(final String methodName, long routeKey) throws ServiceException {
		if (!peerAddr.isEmpty()) {
			return peerAddr;
		}
		return ServiceFinderFactory.getServiceFinder().getServiceIp(serviceKey, methodName, routeKey);
	}
	
	protected int chooseServicePort(final String methodName) {
		if (peerPort != 0) {
			return peerPort;
		}
		
		return ServiceFinderFactory.getServiceFinder().getServicePort(serviceKey);
	}
	
	protected interface IFeedBack {
		public void onInvokeSuccess(InvokeMethodInfo methodInfo);
		public void onInvokeException(InvokeMethodInfo methodInfo, Exception e);
	}
	
	protected static abstract class TransportExt implements IFeedBack {
		private TTransport transport;
		private PlatformArgs platformArgs;
		
		protected TransportExt(TTransport transport, PlatformArgs platformArgs) {
			this.transport = transport;
			this.platformArgs = platformArgs;
		}
		
		public TTransport getTransport() {
			return transport;
		}
		
		public PlatformArgs getPlatformArgs() {
			return platformArgs;
		}
	}
	
	private class RemoteTransportExt extends TransportExt {
		private String remoteIP;
		
		public RemoteTransportExt(TTransport transport, PlatformArgs platformArgs) {
			super(transport, platformArgs);
		}
		
		public RemoteTransportExt setRemoteIP(String remoteIP) {
			this.remoteIP = remoteIP;
			return this;
		}
		
		@Override
		public void onInvokeSuccess(InvokeMethodInfo methodInfo) {
			ServiceFinderFactory.getServiceFinder().updateCallInfo(
			        serviceKey, methodInfo.getMethodName(), remoteIP, null);
		}

		@Override
		public void onInvokeException(InvokeMethodInfo methodInfo, Exception e) {
			ServiceFinderFactory.getServiceFinder().updateCallInfo(serviceKey
					, methodInfo.getMethodName(), remoteIP, e);
		}
		
	}
	
	private class InpTransportExt extends TransportExt {
		public InpTransportExt(TTransport transport, PlatformArgs platformArgs) {
			super(transport, platformArgs);
		}

		@Override
		public void onInvokeSuccess(InvokeMethodInfo methodInfo) {
		}

		@Override
		public void onInvokeException(InvokeMethodInfo methodInfo, Exception e) {
		}
	}
	
	private class UnixSocketTransportExt extends TransportExt {
		protected UnixSocketTransportExt(TTransport transport, PlatformArgs platformArgs) {
			super(transport, platformArgs);
		}

		@Override
		public void onInvokeSuccess(InvokeMethodInfo methodInfo) {
		}

		@Override
		public void onInvokeException(InvokeMethodInfo methodInfo, Exception e) {
		}
	}
	
	protected static class InvokeMethodInfo {
		private String methodName;
		private long routeKey;
		private int timeoutMs;
		private StackTraceElement sourceCallTrace;
		
		public InvokeMethodInfo(String methodName) {
			this.methodName = methodName;
		}
		
		public String getMethodName() {
			return this.methodName;
		}
		public InvokeMethodInfo setMethodName(String methodName) {
			this.methodName = methodName;
			return this;
		}
		
		public long getRouteKey() {
			return routeKey;
		}
		public InvokeMethodInfo setRouteKey(long routeKey) {
			this.routeKey = routeKey;
			return this;
		}
		
		public int getTimeoutMs() {
			return timeoutMs;
		}
		public InvokeMethodInfo setTimeoutMs(int timeoutMs) {
			this.timeoutMs = timeoutMs;
			return this;
		}

		public StackTraceElement getSourceCallTrace() {
			return sourceCallTrace;
		}

		public InvokeMethodInfo setSourceCallTrace(StackTraceElement sourceCallTrace) {
			this.sourceCallTrace = sourceCallTrace;
			return this;
		}

	}
	
	protected TransportExt prepareTransportExt(InvokeMethodInfo methodInfo) throws TException {
		TTransport transport = null;
		PlatformArgs platformArgs = new PlatformArgs();
		platformArgs.setTimeoutMs(methodInfo.getTimeoutMs());
		if (xForwardAddress != null) {
			platformArgs.setXForwardAddress(xForwardAddress);
		}
		
		if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(serviceKey)) {
			transport = new InpSocket(serviceKey, methodInfo.getTimeoutMs());
			return new InpTransportExt(transport, platformArgs);
		} else if (socketFile != null) {
			transport = new AFUNIXSocketTransport(socketFile);
			return new UnixSocketTransportExt(transport, platformArgs);
		} else {
			try {
				String choosedRemoteIP = chooseServiceAddr(methodInfo.getMethodName(), methodInfo.getRouteKey());
				int choosedRemotePort = chooseServicePort(methodInfo.getMethodName());
				
				transport = new TSocket(choosedRemoteIP, choosedRemotePort, methodInfo.getTimeoutMs());
				platformArgs.setRemoteAddress(choosedRemoteIP);
				platformArgs.setRemotePort(choosedRemotePort);
				try {
					platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					AppLog.w(e.getMessage(), e);
				}
				
				return new RemoteTransportExt(transport, platformArgs).setRemoteIP(choosedRemoteIP);
			} catch (ServiceException e1) {
				throw new TException(e1.getMessage());
			}
		}
	}
	
	protected static interface ThriftCallable<R> {
		public R call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException;
	}
	
	protected <R> R runSync(
			ThriftCallable<R> methodCall
			, InvokeMethodInfo methodInfo) throws ErrorInfo, TException {
		TransportExt transportExt = prepareTransportExt(methodInfo);
		TTransport framedTransport = new TFramedTransport(transportExt.getTransport());
		TProtocol protocol = new TCompactProtocol(framedTransport);
		
		StringBuilder sourceDescBuilder = new StringBuilder(64);
		sourceDescBuilder.append(methodInfo.getSourceCallTrace().getClassName())
				.append("[")
				.append(methodInfo.getSourceCallTrace().getMethodName())
				.append(":")
				.append(methodInfo.getSourceCallTrace().getLineNumber())
				.append("]");
		transportExt.getPlatformArgs().setSourceDesc(sourceDescBuilder.toString());
		
		R result = null;
		try {
			framedTransport.open();
			result = methodCall.call(protocol, transportExt.getPlatformArgs());
			transportExt.onInvokeSuccess(methodInfo);
		} catch (ErrorInfo ei) {
			transportExt.onInvokeException(methodInfo, ei);
			throw ei;
		} catch (TTransportException et) {
			transportExt.onInvokeException(methodInfo, et);
			throw et;
		} catch (Exception e) {
			transportExt.onInvokeException(methodInfo, e);
			throw new TException(e);
		} finally {
			framedTransport.close();
		}
		return result;
	}
}
