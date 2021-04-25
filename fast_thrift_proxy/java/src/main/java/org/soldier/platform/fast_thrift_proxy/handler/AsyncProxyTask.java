package org.soldier.platform.fast_thrift_proxy.handler;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Request;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Response;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core.Dispatcher;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core.IRequestCallback;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import net.qihoo.qconf.Qconf;

public class AsyncProxyTask implements IRequestCallback {
	private static final int PROXY_REQUEST_KEY = AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.request.count", null);
	private static final int PROXY_SUCCESS_KEY = AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.success.count", null);
	private static final int PROXY_FORBIDDEN_KEY = AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.forbidden.count", null);
	private static final int PROXY_FALIED_INNER_KEY = AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.failed.inner.count", null);
	private static final int PROXY_FAILED_HAWK_KEY = AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.failed.hawk.count", null);
	private static final int PROXY_FAILED_OTHER_KEY = AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.failed.other.count", null);
	
	
	private FullHttpRequest mHttpReq;
	private Channel mChannel;
	private HttpVersion mHttpVersion;
	private boolean isKeepAlive;
	private String mContentType;
	
	private TProtocolFactory mFrontProtocolFactory;
	private TProtocolFactory mBackendProtocolFactory;
	
	private Dispatcher mDispatcher;
	
	public AsyncProxyTask(FullHttpRequest httpReq
			, Channel channel
			, Dispatcher dispatcher) {
		mHttpReq = httpReq;
		mChannel = channel;
		mDispatcher = dispatcher;
		
		isKeepAlive = HttpUtil.isKeepAlive(mHttpReq);
		mHttpVersion = mHttpReq.protocolVersion();
		
		mFrontProtocolFactory = new TCompactProtocol.Factory();
		mBackendProtocolFactory = new TCompactProtocol.Factory();
	}
	
	public void run() throws Exception {
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(mHttpReq.uri());
		Map<String, List<String>> parameters = queryStringDecoder.parameters();
		if (parameters == null || !parameters.containsKey("servant") ) {
			sendHttpResponse(HttpResponseStatus.NOT_FOUND, "servant is not set");
			return ;
		}
		
		if (mHttpReq.content() == null || mHttpReq.content().readableBytes() <= 0) {
			sendHttpResponse(HttpResponseStatus.NO_CONTENT, "please post the content");
			return ;
		}
		
		String servantName = parameters.get("servant").get(0);
	    	
		ArrayList<String> servantList = Qconf.getBatchKeys("thrift_proxy");
		if (!servantList.contains(servantName)) {
			sendHttpResponse(HttpResponseStatus.NOT_FOUND, "servant not existed");
			return ;
		}
			
		ArrayList<String> servantKeys = Qconf.getBatchKeys("thrift_proxy/" + servantName);
		if (!servantKeys.contains("service_key")) {
			sendHttpResponse(HttpResponseStatus.NOT_FOUND, "server config error, no service_key find");
			return ;
		}
			
		int serviceKey = Integer.valueOf(Qconf.getConf("thrift_proxy/" + servantName + "/service_key").trim());
		int defaultTimeoutMs = 3000;
		if (serviceKey <= 0) {
			throw new Exception("server config error, service_key <= 0");
		}
			
		if (servantKeys.contains("default_timeout_ms")) {
			defaultTimeoutMs = Integer.valueOf(
					Qconf.getConf("thrift_proxy/" + servantName + "/default_timeout_ms").trim());
			if (defaultTimeoutMs <= 0) {
				throw new Exception("server config error, default_timeout_ms <= 0");
			}
		}
		
		String[] forbiddenFunctionsRegList = null;
		if (servantKeys.contains("forbidden_functions")) {
			forbiddenFunctionsRegList = StringUtils.split(
					Qconf.getConf("thrift_proxy/" + servantName + "/forbidden_functions")
					, ";");
		}
		
		String functionConfPrefix = null;
		if (servantKeys.contains("functions")) {
			functionConfPrefix = "thrift_proxy/" + servantName + "/functions";
		}
		
		String oaAuth = null;
        if (servantKeys.contains("oa_auth")) {
            oaAuth = Qconf.getConf("thrift_proxy/" + servantName + "/oa_auth");
        }
        
        String authRequest = null;
        if (servantKeys.contains("auth_request")) {
            authRequest = Qconf.getConf("thrift_proxy/" + servantName + "/auth_request");
        }

		InetSocketAddress remoteAddress = (InetSocketAddress)mChannel.remoteAddress();
		String xForwardAddress = remoteAddress.getAddress().getHostAddress();
		String xRealIp = mHttpReq.headers().get("X-Real-IP");
		if (xRealIp != null && xRealIp.isEmpty()) {
			xForwardAddress = xRealIp;
		} else {
			List<String> xForwardForHeaders = mHttpReq.headers().getAll("X-Forwarded-For");
			if(xForwardForHeaders != null && !xForwardForHeaders.isEmpty()) {
				xForwardAddress = xForwardForHeaders.get(xForwardForHeaders.size() - 1);
			} 
		}
		
		mContentType = mHttpReq.headers().get(HttpHeaderNames.CONTENT_TYPE);
		if (StringUtils.isNotEmpty(mContentType) && mContentType.startsWith("application/json")) {
		    mFrontProtocolFactory = new TJSONProtocol.Factory();
		}
		
		if (AppLog.traceEnabled()) {
		    Iterator<Entry<String, String>> it = mHttpReq.headers().iteratorAsString();
		    while(it.hasNext()) {
		        Entry<String, String> headerPair = it.next();
		        AppLog.t("HEADER " + headerPair.getKey() + " : " + headerPair.getValue());
		    }
		}
		
		byte[] requestContents = new byte[mHttpReq.content().readableBytes()];
		mHttpReq.content().readBytes(requestContents);
		
		Request req = new Request(requestContents, serviceKey, mFrontProtocolFactory, mBackendProtocolFactory);
		req.setServantName(servantName);
		req.getRequestOption().setTimeoutMills(defaultTimeoutMs);
		req.setForbiddenMethodRegList(forbiddenFunctionsRegList);
		req.setFunctionConfPrefix(functionConfPrefix);
		req.setXForwaredAdddress(xForwardAddress);
		req.setHttpHeaders(mHttpReq.headers());
		req.setOaAuth(oaAuth);
		req.setAuthRequest(authRequest);
		
		mDispatcher.send(req, this);
		AttrReporterFactory.getDefault().inc(PROXY_REQUEST_KEY, 1);
	}
	
	public void sendHttpResponse(HttpResponseStatus status, String content) {
		try {
			sendHttpResponse(status, "text/plain;charset=utf-8", content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			AppLog.f("Unexepcted not support UTF-8");
		}
	}
	
	public void sendHttpResponse(HttpResponseStatus status, String contentType, byte[] body) {
		sendHttpResponse(status, contentType, Unpooled.wrappedBuffer(body));
	}
	
	public void sendHttpResponse(HttpResponseStatus status
			, String contentType
			, ByteBuf body) {
		FullHttpResponse response = null;
		if (body != null) {
			response = new DefaultFullHttpResponse(mHttpVersion, status, body);
		} else {
			response = new DefaultFullHttpResponse(mHttpVersion, status);
		}
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		if (contentType != null && !contentType.isEmpty()) {
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
		} else {
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream");
		}
		if (isKeepAlive) {
        	response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        	response.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
        }
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		
		ChannelFuture f = mChannel.writeAndFlush(response);
		if (!isKeepAlive || response.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
	}

	@Override
	public void onRequestFinished(long requestId, Request request, Response response) {
		if (AppLog.infoEnabled() || response.getErrorCode() != 0) {
			StringBuilder logInfoBuilder = new StringBuilder(128);
			logInfoBuilder.append("process serviceKey=");
			logInfoBuilder.append(request.getServiceKey());
			logInfoBuilder.append(", function=");
			logInfoBuilder.append(request.getMethodName());
			logInfoBuilder.append(", functionTimeoutMs=");
			logInfoBuilder.append(request.getRequestOption().getTimeoutMills());
			logInfoBuilder.append(", serviceIp=");
			logInfoBuilder.append(request.getRemoteAddress());
			logInfoBuilder.append(", xForwardAddress=");
			logInfoBuilder.append(request.getXForwardAddress());
			logInfoBuilder.append(", timeEscaped=");
			logInfoBuilder.append(request.getFinishedTimestampMs() - request.getCreateTimestampMs());
			logInfoBuilder.append("ms");
			if (request.getTransportStartTimestampMs() > 0 && request.getTransportFinishedTimestampMs() > 0) {
				logInfoBuilder.append(", transportTimeEscaped=");
				logInfoBuilder.append(request.getTransportFinishedTimestampMs() - request.getTransportStartTimestampMs());
				logInfoBuilder.append("ms");
			}
			if (response.getErrorCode() != 0) {
				logInfoBuilder.append(", failed errorCode=");
				logInfoBuilder.append(response.getErrorCode());
				logInfoBuilder.append(", errorMsg=");
				logInfoBuilder.append(response.getErrorMsg());
				AppLog.e(logInfoBuilder.toString());
			} else {
				logInfoBuilder.append(" success ");
				AppLog.i(logInfoBuilder.toString());
			}
		}
		
		if (response.getErrorCode() == ErrorCode.ERROR_FORBIDDEN) {
			AttrReporterFactory.getDefault().inc(PROXY_FORBIDDEN_KEY, 1);
			sendHttpResponse(HttpResponseStatus.FORBIDDEN, response.getErrorMsg());
			return ;
		}
		if (response.getErrorCode() == ErrorCode.ERROR_NOT_AUTH) {
			AttrReporterFactory.getDefault().inc(PROXY_SUCCESS_KEY, 1);
			sendHttpResponse(HttpResponseStatus.UNAUTHORIZED, response.getErrorMsg());
			return ;
		}
		if (response.getErrorCode() == ErrorCode.INNER_ERROR) {
			AttrReporterFactory.getDefault().inc(PROXY_FALIED_INNER_KEY, 1);
			sendHttpResponse(HttpResponseStatus.SERVICE_UNAVAILABLE, response.getErrorMsg());
			return ;
		}
		if (response.getErrorCode() == ErrorCode.HAWK_CALL_ERROR) {
			AttrReporterFactory.getDefault().inc(PROXY_FAILED_HAWK_KEY, 1);
			sendHttpResponse(HttpResponseStatus.SERVICE_UNAVAILABLE, response.getErrorMsg());
		}
		if (response.getErrorCode() != 0) {
			AttrReporterFactory.getDefault().inc(PROXY_FAILED_OTHER_KEY, 1);
			sendHttpResponse(HttpResponseStatus.SERVICE_UNAVAILABLE, response.getErrorMsg());
			return ;
		}
		
		AttrReporterFactory.getDefault().inc(PROXY_SUCCESS_KEY, 1);
		sendHttpResponse(HttpResponseStatus.OK, mContentType, Unpooled.wrappedBuffer(response.getOutputData()));
		
//		sendHttpResponse(HttpResponseStatus.OK, mContentType, Unpooled.wrappedBuffer(response.getResponseContent()));
	}
	
}
