package org.soldier.platform.file_storage_info.dao.server.impl;

import org.soldier.platform.oss.api.AliyunOssStorage;
import org.soldier.platform.oss.api.OssFailedException;

public class AliyunOssDomainProvider implements IDomainProvider {
	private AliyunOssStorage mStorage;
	
	public AliyunOssDomainProvider() throws OssFailedException {
		mStorage = new AliyunOssStorage("oss/config");
	}
	
	@Override
	public String getDefaultDomain(String storageKey) {
		return mStorage.getOuterDomain() + "/" + storageKey;
	}

}
