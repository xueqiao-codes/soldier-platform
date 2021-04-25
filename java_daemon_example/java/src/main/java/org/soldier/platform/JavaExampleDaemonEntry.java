package org.soldier.platform;

import org.soldier.base.logger.AppLog;

public class JavaExampleDaemonEntry {
	public static void main(String[] args) {
	    while(true) {
	        AppLog.d("example runonce again");
	        try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
	    }
	}
}
