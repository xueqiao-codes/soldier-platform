package soldier.inp.test;

import java.util.ArrayList;
import java.util.List;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.container.InpService;
import org.soldier.platform.svr_platform.container.MultiInpServiceContainer;
import org.soldier.platform.svr_platform.container.MultiInpServiceContainerInMultiThread;
import org.soldier.platform.svr_platform.container.MultiInpServiceContainerInSingleThread;

public class Common {
	private static MultiInpServiceContainer container;
	public static void init() {
		AppLog.init("inp_service_test");
		
//		MultiInpServiceContainer container = new MultiInpServiceContainerInMultiThread();
		container = new MultiInpServiceContainerInSingleThread();
		
		List<InpService> servicesList = new ArrayList<InpService>();
		
		InpService inpService = new InpService();
		inpService.getServiceProperties().setProperty("workerNum", "5");
		inpService.setServiceName("id_maker");
		inpService.setServiceKey(200);
		inpService.setServiceClass("idmaker.dao.server.IdMakerAdaptor");
		servicesList.add(inpService);
		
		inpService = new InpService();
		inpService.setServiceName("user_dao");
		inpService.setServiceKey(300);
		inpService.setServiceClass("com.car.user.dao.server.UserDaoAdaptor");
		inpService.getServiceProperties().setProperty("workerNum", "5");
		servicesList.add(inpService);
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				InpServiceContainer container = new InpServiceContainer();
//				Properties properties = new Properties();
//				properties.setProperty("workerNum", "5");
//				properties.setProperty("Adaptor-Class", "idmaker.dao.server.IdMakerAdaptor");
//				try {
//					container.start(properties);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}).start();
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				InpServiceContainer container = new InpServiceContainer();
//				Properties properties = new Properties();
//				properties.setProperty("workerNum", "5");
//				properties.setProperty("Adaptor-Class", "com.car.user.dao.server.UserDaoAdaptor");
//				try {
//					container.start(properties);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}).start();
		container.loadServices(servicesList);
	}
	
	public static void destroy() {
		container.destory();
	}
}
