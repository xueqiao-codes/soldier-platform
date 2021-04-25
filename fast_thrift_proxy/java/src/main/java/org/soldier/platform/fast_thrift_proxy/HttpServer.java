package org.soldier.platform.fast_thrift_proxy;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpServer  {
	private int mPort;
	
	public HttpServer(int port) {
		this.mPort = port;
	}
	
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .handler(new LoggingHandler(LogLevel.INFO))
             .channel(NioServerSocketChannel.class) 
             .childHandler(new HttpInitializer())  
             .option(ChannelOption.SO_BACKLOG, 128)   
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childOption(ChannelOption.TCP_NODELAY, true);
    		AppLog.i("server started on port " + mPort);
    		
            ChannelFuture f = b.bind(mPort).sync(); // (7)
            
            AttrReporterFactory.thirtySecs().keep(
            		AttrReporterFactory.thirtySecs().requireKey("fast_thrift_proxy.keepalive", null), 1);
            f.channel().closeFuture().sync();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            AppLog.i("server stoped..");
        }
	}
	
}
