package org.soldier.framework.message_bus.test;

import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class TestConsumer implements IMessageConsumer {

	@Override
	public void onInit() {
		System.out.println("onInit...");
	}
	
	@consume(PlatformArgs.class)
	ConsumeResult onTest(PlatformArgs args) {
		System.out.println("receiving " + args + ", currentTimestamp=" + System.currentTimeMillis());
		return ConsumeResult.CONSUME_OK;
	}

	@Override
	public StartUpMode onStartUp() {
		return StartUpMode.CLEAR_QUEUE_INIT;
	}
	
}
