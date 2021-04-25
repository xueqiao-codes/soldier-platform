package org.soldier.platform.fast_thrift_proxy;

import org.soldier.base.logger.AppLog;

public class Main {
	public static void main(String[] args) {
		AppLog.init("apps/fast_thrift_proxy");
		new HttpServer(8069).run();
	}
}
