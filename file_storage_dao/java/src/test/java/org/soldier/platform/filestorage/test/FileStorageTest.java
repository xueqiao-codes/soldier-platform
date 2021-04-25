package org.soldier.platform.filestorage.test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.StringFactory;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.filestorage.dao.client.FileStorageDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class FileStorageTest {
	private static void addFile() {
		FileStorageDaoStub stub = new FileStorageDaoStub();
		
		String content = "this is test string, 写点儿中文吧!123";
		
		HttpOption option = new HttpOption();
		option.setCatchControl("no-cache");
		option.setContentType("text/plain");
		Map<String, String> userMetaMap = new HashMap<String, String>();
		userMetaMap.put("User", "Value");
		option.setUserMetaData(userMetaMap);
		try {
			stub.writeFile(RandomUtils.nextInt(), 5000, "test", "haha2.txt", 
					ByteBuffer.wrap(content.getBytes("UTF-8")), option);
			System.out.println("success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void readFile() {
		FileStorageDaoStub stub = new FileStorageDaoStub();
		
		try {
			ByteBuffer bytes = stub.readFile(RandomUtils.nextInt(), 5000, "test", "haha.txt");
			String content = StringFactory.newUtf8String(bytes.array());
			System.out.println("get content =[" + content + "]");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void deleteFile() {
		FileStorageDaoStub stub = new FileStorageDaoStub();
		
		try {
			stub.deleteFile(RandomUtils.nextInt(), 5000, "test", "haha.txt");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		addFile();
//		readFile();
//		deleteFile();
	}
}
