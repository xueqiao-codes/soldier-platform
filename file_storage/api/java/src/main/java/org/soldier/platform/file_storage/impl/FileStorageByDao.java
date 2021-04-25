package org.soldier.platform.file_storage.impl;

import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.file_storage.FileStorageException;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.filestorage.dao.client.FileStorageDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

public class FileStorageByDao extends FileStorageBase {
	private FileStorageDaoStub storageDaoStub;
	
	public FileStorageByDao(String storageKey, ScheduledExecutorService executorService) {
		super(storageKey, executorService);
		storageDaoStub = new FileStorageDaoStub();
	}
	
	@Override
	public byte[] read(String path) throws FileStorageException {
		checkPath(path);
	
		checkState();
		try {
			return storageDaoStub.readFile(RandomUtils.nextInt(), timeout, storageKey, path).array();
		} catch (ErrorInfo e) {
			if (e.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				return null;
			}
			throw new FileStorageException(e);
		} catch (TException e) {
			throw new FileStorageException(e);
		}
	}

	@Override
	public void write(String path, byte[] content) throws FileStorageException {
		write(path, content, null);
	}


	@Override
	public void write(String path, byte[] content, HttpOption option)
			throws FileStorageException {
		checkPath(path);
		super.checkState();
		try {
			storageDaoStub.writeFile(RandomUtils.nextInt(),
					timeout, storageKey, path, ByteBuffer.wrap(content), option);
		} catch (Exception e) {
			throw new FileStorageException(e);
		}
	}

	@Override
	public void delete(String path) throws FileStorageException {
		checkPath(path);
		checkState();
		
		try {
			storageDaoStub.deleteFile(RandomUtils.nextInt(), timeout, storageKey, path);
		} catch (Exception e) {
			throw new FileStorageException(e);
		}
	}


}
