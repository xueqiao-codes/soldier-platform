package org.apache.thrift.server;

import java.net.InetSocketAddress;


public class SocketAddressThreadLocal {
	private static ThreadLocal<InetSocketAddress> socketAddressThreadLocal;
	
	public static ThreadLocal<InetSocketAddress> getInstance() {
		if (socketAddressThreadLocal == null) {
			synchronized(SocketAddressThreadLocal.class) {
				if (socketAddressThreadLocal == null) {
					socketAddressThreadLocal = new ThreadLocal<InetSocketAddress>();
				}
			}
		}
		return socketAddressThreadLocal;
	}
}
