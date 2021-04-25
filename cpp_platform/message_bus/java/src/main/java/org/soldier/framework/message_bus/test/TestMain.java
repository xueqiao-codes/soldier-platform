package org.soldier.framework.message_bus.test;

import java.io.File;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.EClientLang;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class TestMain {
	public static void main(String[] args) {
		AppLog.init("test/libmessage_bus_java_test");
		MessageAgent.initNativeLogName("libmessage_bus_java_test");
		
		MessageAgent agent = new MessageAgent(
				new TestConsumer()
				,  "message_bus_test"
				,  new File("/data/configs/qconf/xueqiao/trade/hosting/message_graph.json"));
		
		while(true) {
			PlatformArgs platformArgs = new PlatformArgs();
			platformArgs.setSourceDesc(TestMain.class.getName() + ", " + System.currentTimeMillis());
			platformArgs.setClientLang(EClientLang.EN);
			
			for (int index = 0; index < 10; ++index) {
				String guardId = agent.prepareGuardMessage(platformArgs
						, new TimeoutGuardPolicy().setTimeoutSeconds(10));
				System.out.println("do some thing..., guardId=" + guardId);
				agent.rmGuardMessage(guardId);
			}
//			agent.sendMessageAndRmGuard(platformArgs, guardId);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
