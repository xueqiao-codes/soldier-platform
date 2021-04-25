package org.soldier.platform.proxy.test.ao.test;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_ao.testEcho_args;
import org.soldier.platform.proxy.test.ao.proxy_test_ao.testEcho_result;
import org.soldier.platform.proxy.test.ao.client.proxy_test_aoAsyncStub;
import org.soldier.platform.proxy.test.ao.client.proxy_test_aoStub;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TStubOption;

public class TestMain {
	
	public static void main(String[] args) {
		AppLog.init("test/proxy_test_ao_test");
		
		SvrContainer.setLogOn(true);
		
		proxy_test_aoStub stub = new proxy_test_aoStub();
		stub.setxForwardAddress("127.0.0.1");
		try {
			System.out.println(stub.testEcho("default"));
			System.out.println(stub.testEcho("stubOption", new TStubOption().setTimeoutMs(5000)));
			System.out.println(stub.testEcho(RandomUtils.nextInt(), 3000, "123"));
			System.out.println(stub.testEcho("timeout", new TStubOption().setTimeoutMs(8000)));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		stub.setPeerAddr("172.16.131.115");
		stub.setPeerPort(1234);
		try {
			System.out.println(stub.testEcho(RandomUtils.nextInt(), 3000, "port error"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		proxy_test_aoAsyncStub asyncStub = new proxy_test_aoAsyncStub();
		try {
			asyncStub.testEcho(RandomUtils.nextInt(), 3000, "async right"
					, new IMethodCallback<proxy_test_ao.testEcho_args, proxy_test_ao.testEcho_result>() {

						@Override
						public void onComplete(long callId, testEcho_args req, testEcho_result resp) {
							System.out.println(resp.success);
						}

						@Override
						public void onError(long callId, testEcho_args req, Exception e) {
							e.printStackTrace();
						}
						
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		asyncStub.setPeerAddr("172.17.225.25");
		asyncStub.setPeerPort(1245);
		
		TRequestOption option = new TRequestOption();
		option.setRetryTimes(3);
		try {
			asyncStub.send_testEcho(RandomUtils.nextInt(), 3000, "async send", option);
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			asyncStub.testEcho(RandomUtils.nextInt(), 3000, "async wrong"
					, new IMethodCallback<proxy_test_ao.testEcho_args, proxy_test_ao.testEcho_result>() {

						@Override
						public void onComplete(long callId, testEcho_args req, testEcho_result resp) {
							System.out.println(resp.success);
						}

						@Override
						public void onError(long callId, testEcho_args req, Exception e) {
							e.printStackTrace();
						}
						
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
