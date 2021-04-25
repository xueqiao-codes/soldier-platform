package org.soldier.platform.file_storage_info.dao.test;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.file_storage_info.dao.AccessAttribute;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.StorageInfo;
import org.soldier.platform.file_storage_info.dao.client.FileStorageInfoDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class FileStorageInfoTest {
	private static void addStorage() {
		FileStorageInfoDaoStub stub = new FileStorageInfoDaoStub();
		
		StorageInfo info = new StorageInfo();
		info.setStorageKey("test1");
		info.setAccessAttribute(AccessAttribute.PrivateRead);
		info.setDesc("这是一个测试的存储Key");
		
		try {
			stub.addStorage(RandomUtils.nextInt(), 2000, info);
			System.out.println("add success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteStorage() {
		try {
			new FileStorageInfoDaoStub().deleteStorage(RandomUtils.nextInt(), 1500, "test");
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void queryStorage() {
		QueryStorageInfoListOption option = new QueryStorageInfoListOption();
		
		try {
			System.out.println(
					new FileStorageInfoDaoStub().queryStorageList(RandomUtils.nextInt(), 3000, 0, 10, option));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void updateStorage() {
		StorageInfo info = new StorageInfo();
		info.setStorageKey("test1");
		info.setAccessAttribute(AccessAttribute.PublicRead);
		info.setDesc("这是一个测试的存储Key, 并修改");
		info.setDomain("hehe");
		
		try {
			new FileStorageInfoDaoStub().updateStorage(RandomUtils.nextInt(),
					1500, info);
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		addStorage();
//		deleteStorage();
//		queryStorage();
		updateStorage();
	}
}
