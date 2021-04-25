package org.soldier.platform.svr_platform.thrift;

import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

public class InpConnectInfo {
	private long requestKey;
	private SourceChannel inChannel;
	private SinkChannel outChannel;
	private boolean isClientBlocking;
	
	public InpConnectInfo(final long requestKey, SourceChannel inChannel,
			SinkChannel outChannel, boolean isClientBlocking) {
		this.requestKey = requestKey;
		this.inChannel = inChannel;
		this.outChannel = outChannel;
		this.isClientBlocking = isClientBlocking;
	}
	
	public long getRequestKey() {
		return requestKey;
	}
	
	public SourceChannel getInChannel() {
		return inChannel;
	}
	
	public SinkChannel getOutChannel() {
		return outChannel;
	}

	public boolean isClientBlocking() {
		return isClientBlocking;
	}

}
