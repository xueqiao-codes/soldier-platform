package org.soldier.framework.message_bus.api;

public interface IMessageConsumer {
	public static enum StartUpMode {
		CLEAR_QUEUE_INIT,
		RESERVE_QUEUE
	}
	
	public static enum ConsumeResult {
		CONSUME_OK,
		CONSUME_FAILED_DROP,
		CONSUME_RETRY
	}
	
	/**
	 *  启动代码
	 */
	public StartUpMode onStartUp();
	
	/**
	 *  重新初始化数据
	 */
	public void onInit() throws Exception ;
}
