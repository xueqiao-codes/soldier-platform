package org.soldier.platform.file_storage.impl;

import org.soldier.platform.oss.api.AliyunOssStorage;
import org.soldier.platform.oss.api.OssFailedException;

class AliyunOssStorageHolder {
	private static AliyunOssStorage sInstance;
	public static AliyunOssStorage get() throws OssFailedException  {
		if (sInstance == null) {
			synchronized(AliyunOssStorageHolder.class) {
				if (sInstance == null) {
					sInstance = new AliyunOssStorage("oss/config");
				}
			}
		}
		return sInstance;
	}
}
