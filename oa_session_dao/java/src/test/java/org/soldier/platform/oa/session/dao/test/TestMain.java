package org.soldier.platform.oa.session.dao.test;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.oa.session.dao.TSession;
import org.soldier.platform.oa.session.dao.client.OaSessionDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestMain {
	public static void updateSession() {
		OaSessionDaoStub stub = new OaSessionDaoStub();
		
		TSession session = new TSession();
		session.setUserId(3);
		session.setSessionKey("hehe223");
		
		try {
			stub.updateSession(RandomUtils.nextInt(), 1000, session);
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void getSession() {
		OaSessionDaoStub stub = new OaSessionDaoStub();
		
//		try {
//			System.out.println(stub.getSession(RandomUtils.nextInt(), 1000, 1));
//		} catch (ErrorInfo e) {
//			e.printStackTrace();
//		} catch (TException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void deleteSession() {
		OaSessionDaoStub stub = new OaSessionDaoStub();
		
//		try {
//			stub.deleteSession(RandomUtils.nextInt(), 2000, 2);
//		} catch (ErrorInfo e) {
//			e.printStackTrace();
//		} catch (TException e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static void main(String[] args) {
//		updateSession();
//		getSession();
		deleteSession();
	}

}
