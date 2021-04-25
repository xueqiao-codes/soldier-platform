package id_maker_dao;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestIdMakerDao {
	public static void main(String[] args) {
		IdMakerDaoStub stub = new IdMakerDaoStub();
		
		IdMakerInfo info = new IdMakerInfo();
		info.setType(3);
		info.setId(2000);
		info.setAllocSize(1000);
		info.setDesc("测试");
		
		try {
//			stub.registerType(RandomUtils.nextInt(), 1000, info);
//			System.out.println("register success!");
			
//			long time = System.currentTimeMillis();
//			IdAllocResult result = stub.allocIds(RandomUtils.nextInt(), 500, 10);
//			System.out.println("[" + (System.currentTimeMillis() - time) + "]CreateId " + result.toString());
//			
//			System.out.println(stub.getIdMakerInfoByType(RandomUtils.nextInt(), 500, 10));
			
			IdMakerQueryOption option = new IdMakerQueryOption();
			option.setDesc("测");
			System.out.println(stub.queryIdMakerTypeInfoList(RandomUtils.nextInt(), 3000, 0, 10, option));
			
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
