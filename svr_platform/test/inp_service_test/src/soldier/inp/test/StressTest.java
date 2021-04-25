package soldier.inp.test;

import org.apache.thrift.TException;
import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_ao.testEcho_args;
import org.soldier.platform.proxy.test.ao.proxy_test_ao.testEcho_result;
import org.soldier.platform.proxy.test.ao.client.proxy_test_aoAsyncStub;
import org.soldier.platform.proxy.test.ao.client.proxy_test_aoStub;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.car.user.dao.UserDao;
import com.car.user.dao.UserDao.Register_args;
import com.car.user.dao.UserDao.Register_result;
import com.car.user.dao.client.UserDaoAsyncStub;
import com.car.user.dao.client.UserDaoStub;


public class StressTest {
	private static int TOTAL_TEST_NUMBER = 100;
	
	private static class ProxyEchoTestMethodCallback
		implements IMethodCallback<testEcho_args, testEcho_result> {


		@Override
		public void onError(long callId, testEcho_args req,
				Exception e) {
			System.out.println("PrintCreateIdMethodCallback onError callId=" + callId);
			e.printStackTrace();
		}

		@Override
		public void onComplete(long callId, testEcho_args req, testEcho_result resp) {
			// TODO Auto-generated method stub
			System.out.println("ProxyEchoTestMethodCallback onComplete callId=" + callId 
					+ ",result=" + resp.success);
		}
		
	}
		
	private static class PrintUserRegisterMethodCallback
		implements IMethodCallback<UserDao.Register_args, UserDao.Register_result> {

		@Override
		public void onComplete(long callId, Register_args req,
				Register_result resp) {
			System.out.println("PrintUserRegisterMethodCallback onComplete callId=" + callId 
					+ ",result=" + resp.success);
		}

		@Override
		public void onError(long callId, Register_args req,
				Exception e) {
			System.out.println("PrintUserRegisterMethodCallback onError callId=" + callId);
			e.printStackTrace();
		}
		
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Common.init();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			Pipe p = Pipe.open();
//			p.source().close();
//			p.sink().close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		for (int count = 0; count < 1; count++) {
//			runTestCaseAsync();
			runTestCaseOnAThread();
		}
		
		try {
			Thread.sleep(150000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Common.destroy();
	}
	
	private static void runTestCaseAsync() {
		new Thread(new Runnable() {
			public void run() {
				int totalCount = 0;
				while(totalCount++ < TOTAL_TEST_NUMBER) {
					AsyncCallRunner runner = new AsyncCallRunner();
					proxy_test_aoAsyncStub proxyTestAsyncStub = new proxy_test_aoAsyncStub();
					UserDaoAsyncStub userDaoAsyncStub = new UserDaoAsyncStub();
					try {
						runner.start();

						for (int count = 0; count < 10; count++) {
							proxyTestAsyncStub.add_testEchoCall(runner, 0, 1000,  String.valueOf(count), new ProxyEchoTestMethodCallback());
							userDaoAsyncStub.addRegisterCall(runner,
									0, 1000, "haha", "123", 
									new PrintUserRegisterMethodCallback());
//							System.out.println("asyncStub.addCreateIdCall called");
						}

						runner.run(3000); 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
		
	private static void runTestCaseOnAThread() {
		new Thread(new Runnable() {
			public void run() {
		
			proxy_test_aoStub proxyStub = new proxy_test_aoStub();
			UserDaoStub userDaoStub = new UserDaoStub();
			int count = 0;
			long total = 0;
			while(count++ < TOTAL_TEST_NUMBER) {
			try {
				long currentTime = System.currentTimeMillis();
				String result = proxyStub.testEcho(1000, 1000, String.valueOf(count));
				long escape = System.currentTimeMillis() - currentTime;
				
				total += escape;
				System.out.println("result=" + result + ", escape=" + (System.currentTimeMillis() - currentTime));
			
				long id = userDaoStub.Register(1000, 1000, "123", "345");
				System.out.println("userDaoStub id= " + id );
				
				if (count % 500 == 0) {
					userDaoStub.getUserInfo(0, 1000, 123);
				}
			} catch (ErrorInfo e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			
			System.out.println("avg=" + (total / count));
		}
		}).start();
	}
}
