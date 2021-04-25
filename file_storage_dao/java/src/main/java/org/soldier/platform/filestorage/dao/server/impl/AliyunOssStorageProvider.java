package org.soldier.platform.filestorage.dao.server.impl;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.oss.api.AliyunOssStorage;
import org.soldier.platform.oss.api.OssFailedException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

public class AliyunOssStorageProvider implements IFileStorageProvider {

	private AliyunOssStorage mStorage;
	
	public AliyunOssStorageProvider() throws OssFailedException {
		mStorage = new AliyunOssStorage("oss/config");
	}
	
	@Override
	public byte[] readFile(String storageKey, String path) throws ErrorInfo {
		try {
			return mStorage.readFile(storageKey, path);
		} catch (OssFailedException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
					, ErrorCodeInner.SERVER_INNER_ERROR.getErrorMsg());
		}
	}

	@Override
	public void writeFile(String storageKey, String path, byte[] content, HttpOption option) throws ErrorInfo {
		org.soldier.platform.oss.api.HttpOption ossStorageOption = new org.soldier.platform.oss.api.HttpOption();
		if (option != null) {
			if (option.isSetCatchControl()) {
				ossStorageOption.setCatchControl(option.getCatchControl());
			}
			if (option.isSetContentDisposition()) {
				ossStorageOption.setContentDisposition(option.getContentDisposition());
			}
			if (option.isSetContentEncoding()) {
				ossStorageOption.setContentEncoding(option.getContentEncoding());
			}
			if (option.isSetContentType()) {
				ossStorageOption.setContentType(option.getContentType());
			}
			if (option.isSetExpireTimestamp()) {
				ossStorageOption.setExpireTimestamp(option.getExpireTimestamp());
			}
			if (option.isSetUserMetaData()) {
				ossStorageOption.setUserMetaData(option.getUserMetaData());
			}
		}
		try {
			mStorage.writeFile(storageKey, path, content, ossStorageOption);
		} catch (OssFailedException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
					, ErrorCodeInner.SERVER_INNER_ERROR.getErrorMsg());
		}
	}

	@Override
	public void deleteFile(String storageKey, String path) throws ErrorInfo {
		try {
			mStorage.deleteFile(storageKey, path);
		} catch (OssFailedException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
					, ErrorCodeInner.SERVER_INNER_ERROR.getErrorMsg());
		}
	}

}
