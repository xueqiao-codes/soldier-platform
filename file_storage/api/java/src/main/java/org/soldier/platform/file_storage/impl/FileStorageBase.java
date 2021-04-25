package org.soldier.platform.file_storage.impl;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.file_storage.FileStorage;
import org.soldier.platform.file_storage.FileStorageException;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.StorageInfoList;
import org.soldier.platform.file_storage_info.dao.client.FileStorageInfoDaoStub;

abstract class FileStorageBase extends FileStorage implements Runnable {
    private String urlPrefix;
    private ScheduledExecutorService executorService;
    
    protected FileStorageBase(String storageKey, ScheduledExecutorService executorService) {
        super(storageKey);
        this.executorService = executorService;
        updateConfiguration();
    }

    protected void checkState() throws FileStorageException {
        if (urlPrefix == null) {
            throw new FileStorageException("Storage state is invalid!");
        }
        if (urlPrefix.isEmpty()) {
            throw new FileStorageException("Storage is not exist, please register");
        }
    }
    
    private void updateConfiguration() {
        QueryStorageInfoListOption option = new QueryStorageInfoListOption();
        option.setStorageKey(storageKey);
        try {
            StorageInfoList resultList = new FileStorageInfoDaoStub().queryStorageList(
                    RandomUtils.nextInt(), 3000, 0, 10, option);
            if (resultList.getTotalNum() <= 0) {
                urlPrefix = "";
                if (executorService != null) {
                    executorService.schedule(this, 300, TimeUnit.SECONDS);
                }
                return ;
            }
            urlPrefix = resultList.getResultList().get(0).getDomain();
        } catch (Exception e) {
            e.printStackTrace();
            AppLog.e(e.getMessage(), e);
            if (executorService != null) {
                executorService.schedule(this, 30, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public String getUrl(String path) throws FileStorageException {
        return getUrl(path, URLMode.HTTP);
    }
    
    @Override
    public String getUrl(String path, URLMode mode) throws FileStorageException {
        checkState();
        
        StringBuffer urlBuffer = new StringBuffer(path.length() + urlPrefix.length() + 10);
        if (mode == URLMode.HTTPS) {
            urlBuffer.append("https://");
        } else {
            urlBuffer.append("http://");
        }
        urlBuffer.append(urlPrefix);
        urlBuffer.append("/");
        urlBuffer.append(path);
        return urlBuffer.toString();
    }
    
    @Override
    public void run() {
        updateConfiguration();
    }


}
