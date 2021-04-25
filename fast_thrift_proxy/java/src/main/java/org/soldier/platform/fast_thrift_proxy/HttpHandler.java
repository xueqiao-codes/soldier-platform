package org.soldier.platform.fast_thrift_proxy;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core.Dispatcher;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core.DispatcherImpl;
import org.soldier.platform.fast_thrift_proxy.handler.AsyncProxyTask;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private static AtomicLong CONNECT_COUNT = new AtomicLong(0);
	private static int ATTR_KEY_CONNECT_COUNT = AttrReporterFactory.getDefault().requireKey(
			"fast_thrift_proxy.connection.count", null);
	
	private static ThreadLocal<Dispatcher> IO_DISPATCHER = new ThreadLocal<Dispatcher>();
	
    public HttpHandler() {
    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		try {
			handleHttpRequest(ctx, req);
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			sendHttpError(ctx, req, HttpResponseStatus.SERVICE_UNAVAILABLE, e.getMessage());
		}
	}
	
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (!req.decoderResult().isSuccess()) {
        	sendHttpError(ctx, req, HttpResponseStatus.BAD_REQUEST, null);
            return;
        }
        
        if (IO_DISPATCHER.get() == null) {
        	IO_DISPATCHER.set(new DispatcherImpl());
        }
        
        new AsyncProxyTask(req, ctx.channel(), IO_DISPATCHER.get()).run();
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		try {
			if (AppLog.warnEnabled()) {
				AppLog.w("exceptionCaught connection=" + ctx.channel().remoteAddress()
						+ ", cause=" + cause);
			}
			ctx.close();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	if (AppLog.infoEnabled()) {
    		AppLog.i("Connection added " + ctx.channel().remoteAddress());
    	}
    	AttrReporterFactory.getDefault().keep(ATTR_KEY_CONNECT_COUNT, CONNECT_COUNT.incrementAndGet());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    	if (AppLog.infoEnabled()) {
    		AppLog.i("Connection removed " + ctx.channel().remoteAddress());
    	}
    	AttrReporterFactory.getDefault().keep(ATTR_KEY_CONNECT_COUNT, CONNECT_COUNT.decrementAndGet());
    }
    
    private void sendHttpError(ChannelHandlerContext ctx
    		, FullHttpRequest req
    		, HttpResponseStatus errStatus
    		, String errContent) {
    	ByteBuf errContentBuf = null;
    	if (errContent != null) {
    		byte[] errContentBytes = errContent.getBytes(Charset.forName("UTF-8"));
    		errContentBuf = Unpooled.buffer(errContentBytes.length);
    		errContentBuf.writeBytes(errContentBytes);
    	} else {
    		errContentBuf = Unpooled.buffer(0);
    	}
    	ctx.writeAndFlush(new DefaultFullHttpResponse(req.protocolVersion(), errStatus, errContentBuf))
			.addListener(ChannelFutureListener.CLOSE);
    }
	
}
