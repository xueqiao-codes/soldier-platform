package org.soldier.platform.svr_platform.container;

import java.net.InetSocketAddress;

import org.apache.thrift.server.SocketAddressThreadLocal;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class TServiceCntl {
	private int serviceKey;
	private String apiName;
	private PlatformArgs platformArgs;
	
	public TServiceCntl(
			final int serviceKey,
			final String apiName, 
			final PlatformArgs platformArgs) {
		this.serviceKey = serviceKey;
		this.apiName = apiName;
		this.platformArgs = platformArgs;
		InetSocketAddress remoteAddress = SocketAddressThreadLocal.getInstance().get();
		if (remoteAddress != null) {
			this.platformArgs.setRemoteAddress(remoteAddress.getAddress().getHostAddress());
			this.platformArgs.setRemotePort(remoteAddress.getPort());
		}
	}
	
	public int getServiceKey() {
		return serviceKey;
	}
	
	public String getApiName() {
		return apiName;
	}
	
	public PlatformArgs getPlatformArgs() {
		return platformArgs;
	}
	
	public String getDalSetServiceName() {
		return serviceKey + "_" + apiName;
	}
	
}
