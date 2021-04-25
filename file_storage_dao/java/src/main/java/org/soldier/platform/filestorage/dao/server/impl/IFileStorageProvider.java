package org.soldier.platform.filestorage.dao.server.impl;

import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public interface IFileStorageProvider {
	public byte[] readFile(String storageKey, String path) throws ErrorInfo;
	
	public void writeFile(String storageKey, String path, byte[] content, HttpOption option) throws ErrorInfo;
	
	public void deleteFile(String storageKey, String path) throws ErrorInfo;
}
