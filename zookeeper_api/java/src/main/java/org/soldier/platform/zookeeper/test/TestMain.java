package org.soldier.platform.zookeeper.test;

import org.soldier.base.StringFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.zookeeper.ZooKeeperManager;
import org.soldier.platform.zookeeper.ZooKeeperManagerFactory;

public class TestMain {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("please input zookeeper key");
			return ;
		}
		
		AppLog.init("test/zookeeper_api_test");
		
		String key = args[0];
		
		ZooKeeperManager manager = ZooKeeperManagerFactory.Global().get(key);
		
		while(true) {
			try {
				AppLog.i("qconfName=" + ZooKeeperManagerFactory.Global().getQconfIDCName(key));
				AppLog.i("get /zookeeper=" + 
					StringFactory.newUtf8String(manager.getZooKeeper().getData("/zookeeper", false, null)));
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
			Thread.sleep(1000);
		}
	}
}
