package org.soldier.platform.file_storage.test;

import org.soldier.base.StringFactory;
import org.soldier.platform.file_storage.FileStorage;
import org.soldier.platform.file_storage.FileStorageException;
import org.soldier.platform.file_storage.FileStorageFactory;
import org.soldier.platform.filestorage.dao.HttpOption;

public class FileStorageApiTest {
	public static void main(String[] args) {
		
		FileStorage storage = FileStorageFactory.getInstance().getFileStorage("toy_user");
		while(true) {
			try {
				System.out.println(StringFactory.newUtf8String(storage.read("123/haha.txt")));
//				
				HttpOption option = new HttpOption();
				option.setCatchControl("no-cache");
				storage.write("123/haha.txt", "hehe_123".getBytes(), option);
				
				System.out.println(storage.getUrl("123/haha.txt"));
			} catch (FileStorageException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
