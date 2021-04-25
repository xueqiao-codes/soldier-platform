package org.soldier.platform.svr_platform.client;

/**
 *  异步请求的配置参数
 */
public class TRequestOption implements Cloneable {
	private int retryTimes = 0;       
	private int errorAgentTimes = 0;  
	
	public TRequestOption() {
	}
	
	public TRequestOption(int retryTimes) {
		setRetryTimes(retryTimes);
	}
	
	public int getRetryTimes() {
		return retryTimes;
	}
	/**
	 *  设置在SvrContainer中的重试次数
	 */
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
	
	public int getErrorAgentTimes() {
		return errorAgentTimes;
	}
	/**
	 *  设置在ErrorAgent中的重试次数
	 */
	public void setErrorAgentTimes(int errorAgentTimes) {
		this.errorAgentTimes = errorAgentTimes;
	}
	
	@Override
	public Object clone(){
		TRequestOption requestOption = null;
		try {
			requestOption = (TRequestOption) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return requestOption;
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer(32);
		buffer.append("[retryTimes=");
		buffer.append(retryTimes);
		buffer.append("][errorAgentTimes=");
		buffer.append(errorAgentTimes);
		buffer.append("]");
		return buffer.toString();
	}
}
