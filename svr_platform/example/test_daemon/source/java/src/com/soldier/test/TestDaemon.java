package com.soldier.test;

import idmaker.dao.client.IdMakerStub;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestDaemon {
	public static void main(String[] args) {
		AppLog.d("TestDaemon Started!");
		IdMakerStub oStub = new IdMakerStub();
		while(true) {
//			try {
				AppLog.d("TestDaemon Run! CurrentTime=" + System.currentTimeMillis());
				
				try {
					long id = oStub.CreateId(1000, 500, 1);
					AppLog.d("CreateId id=" + id);
				} catch (ErrorInfo e) {
					e.printStackTrace();
				} catch (TException e) {
					e.printStackTrace();
				}
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}

}
