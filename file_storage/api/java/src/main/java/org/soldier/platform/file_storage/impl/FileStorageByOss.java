package org.soldier.platform.file_storage.impl;

import java.util.concurrent.ScheduledExecutorService;

import org.soldier.platform.file_storage.FileStorageException;
import org.soldier.platform.filestorage.dao.EACLControlList;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.oss.api.OssFailedException;

import com.aliyun.oss.model.CannedAccessControlList;

public class FileStorageByOss extends FileStorageBase {
    
    public FileStorageByOss(String storageKey, ScheduledExecutorService executorService) {
        super(storageKey, executorService);
    }

    @Override
    public byte[] read(String path) throws FileStorageException {
        try {
            return AliyunOssStorageHolder.get().readFile(getStorageKey(), path);
        } catch (Throwable e) {
            throw new FileStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void write(String path, byte[] content) throws FileStorageException {
        write(path, content, null);
    }

    @Override
    public void write(String path, byte[] content, HttpOption option) throws FileStorageException {
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
            if (option.isSetAclControl()) {
                if (option.getAclControl() == EACLControlList.Default) {
                    ossStorageOption.setAcl(CannedAccessControlList.Default);
                } else if (option.getAclControl() == EACLControlList.Private) {
                    ossStorageOption.setAcl(CannedAccessControlList.Private);
                } else if (option.getAclControl() == EACLControlList.PublicRead) {
                    ossStorageOption.setAcl(CannedAccessControlList.PublicRead);
                } else if (option.getAclControl() == EACLControlList.PublicReadWrite) {
                    ossStorageOption.setAcl(CannedAccessControlList.PublicReadWrite);
                }
            }
        }
        try {
            AliyunOssStorageHolder.get().writeFile(storageKey, path, content, ossStorageOption);
        } catch (OssFailedException e) {
            throw new FileStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String path) throws FileStorageException {
        try {
            AliyunOssStorageHolder.get().deleteFile(storageKey, path);
        } catch (OssFailedException e) {
            throw new FileStorageException(e.getMessage(), e);
        }
    }

}
