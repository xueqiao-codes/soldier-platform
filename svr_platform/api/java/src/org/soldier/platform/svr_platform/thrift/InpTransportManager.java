package org.soldier.platform.svr_platform.thrift;

public class InpTransportManager {
	private static InpTransportManager sInstance;
	private static ConnectQueueMode sMode;
	
	private IConnectQueue connectQueue;
	
	public static InpTransportManager getInstance() {
		if (sInstance == null) {
			synchronized(InpTransportManager.class) {
				if (sInstance == null) {
					sInstance = new InpTransportManager();
				}
			}
		}
		return sInstance;
	}
	
	public enum ConnectQueueMode {
		MultiThreadInpServerMode,
		SingleThreadInpServerMode
	}
	
	public static void setConnectQueueMode(ConnectQueueMode mode) {
		sMode = mode;
	}
	
	private InpTransportManager() {
		if (sMode == null) {
			throw new Error("ConnectQueueMode is not set");
		}
		if (sMode == ConnectQueueMode.MultiThreadInpServerMode) {
			connectQueue = new MultiThreadInpServerConnectQueue();
		} else {
			connectQueue = new SingleThreadInpServerConnectionQueue();
		}
	}
	
	public IConnectQueue getConnectQueue() {
		return connectQueue;
	}
}
