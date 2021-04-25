package soldier.inp.test;

import org.apache.thrift.TException;

import com.car.user.dao.UserInfo;
import com.car.user.dao.client.UserDaoStub;

public class ExceptionTest {
	public static void  main(String[] args) {
		Common.init();
		
		UserDaoStub userDaoStub = new UserDaoStub();
		try {
			UserInfo info = userDaoStub.getUserInfo(0, 100, 1);
			System.out.println(info.toString());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		UserInfo info = new UserInfo();
//		info.setUserEmail("wangli@email.com");
//		info.setUserAlwayPlace("hehe");
//		
//		String json = Json.toJson(info);
//		System.out.println(json);
//		
//		try {
//			UserInfo resultInfo = Json.fromJson(json, UserInfo.class);
//			System.out.println("result=" + resultInfo.toString());
//		} catch (JsonException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Common.destroy();
	}
}
