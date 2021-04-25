package org.soldier.platform.zookeeper;

public interface IConfProvider {
	public static class ZooConf {
		private int mSessionTimeout;
		private String mAddrs;
		
		public void setAddrs(String addrs) {
			this.mAddrs = addrs;
		}
		
		public String getAddrs() {
			return mAddrs;
		}
		
		public void setSessionTimeout(int sessionTimeout) {
			this.mSessionTimeout = sessionTimeout;
		}
		
		public int getSessionTimeout() {
			return mSessionTimeout;
		}
	}
	
	public static class ConfException extends Exception {
		private static final long serialVersionUID = 1L;

		public ConfException(String msg) {
			super(msg);
		}
		
		public ConfException(Throwable e) {
			super(e);
		}
		
		public ConfException(String msg, Throwable e) {
			super(msg, e);
		}
	}
	
	public ZooConf getActiveConf() throws ConfException;
}
