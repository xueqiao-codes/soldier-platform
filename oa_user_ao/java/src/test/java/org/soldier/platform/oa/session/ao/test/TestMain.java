package org.soldier.platform.oa.session.ao.test;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.Md5;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestMain {
	public static void login() {
		OaUserAoStub stub = new OaUserAoStub();
		
		try {
			System.out.println(stub.login(RandomUtils.nextInt(), 3000, "pengguangbo", Md5.toMD5("hehe")));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkSession() {
		OaUserAoStub stub = new OaUserAoStub();
		
		try {
			System.out.println(stub.checkSession(RandomUtils.nextInt(),
					1500, 1042, "pengguangbo", "noasfpkabkwwlzvclyypokrdshrtrtqv"));
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logout() {
//		OaUserAoStub stub = new OaUserAoStub();
//		try {
//			stub.logout(RandomUtils.nextInt(), 1000, 1042);
//		} catch (ErrorInfo e) {
//			e.printStackTrace();
//		} catch (TException e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static void main(String[] args) {
//		login();
		checkSession();
//		logout();
	}
}
