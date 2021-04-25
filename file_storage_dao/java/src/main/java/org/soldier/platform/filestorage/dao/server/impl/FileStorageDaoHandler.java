package org.soldier.platform.filestorage.dao.server.impl;

import java.nio.ByteBuffer;
import java.util.Properties;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.filestorage.dao.server.FileStorageDaoAdaptor;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

public class FileStorageDaoHandler extends FileStorageDaoAdaptor {
	private IFileStorageProvider fileStorageProvider;
	
	private void checkPath(String path) throws ErrorInfo {
		if (path == null || path.isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"path is null or empty");
		}
		if (path.startsWith("/")) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"path should not begin with /");
		}
	}
	
	@Override
	public int InitApp(Properties props) {
		try {
			fileStorageProvider = new AliyunOssStorageProvider();
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return -1;
		}
		return 0;
	}

	@Override
	protected ByteBuffer readFile(TServiceCntl oCntl, String storageKey,
			String path) throws ErrorInfo, TException {
		checkPath(path);
		
		byte[] content = fileStorageProvider.readFile(storageKey, path);
		if (content == null) {
			throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
					ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
		}
		
		return ByteBuffer.wrap(content);
	}

	@Override
	protected void writeFile(TServiceCntl oCntl, String storageKey,
			String path, ByteBuffer content, HttpOption option) throws ErrorInfo,TException {
		checkPath(path);
		fileStorageProvider.writeFile(storageKey, path, content.array(), option);
	}

	@Override
	protected void deleteFile(TServiceCntl oCntl, String storageKey, String path)
			throws ErrorInfo, TException {
		checkPath(path);
		fileStorageProvider.deleteFile(storageKey, path);
	}

	@Override
	public void destroy() {
	}
}
