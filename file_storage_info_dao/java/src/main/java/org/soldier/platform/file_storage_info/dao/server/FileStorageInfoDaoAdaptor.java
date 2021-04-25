package org.soldier.platform.file_storage_info.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.StorageInfo;
import org.soldier.platform.file_storage_info.dao.StorageInfoList;
import org.soldier.platform.file_storage_info.dao.FileStorageInfoDao;
import org.soldier.platform.file_storage_info.dao.FileStorageInfoDaoVariable;


public abstract class FileStorageInfoDaoAdaptor implements FileStorageInfoDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void addStorage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StorageInfo storageInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageInfoDaoVariable.serviceKey,"addStorage",platformArgs);
addStorage(oCntl, storageInfo);
  }

  protected abstract void addStorage(TServiceCntl oCntl, StorageInfo storageInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public StorageInfoList queryStorageList(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryStorageInfoListOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageInfoDaoVariable.serviceKey,"queryStorageList",platformArgs);
    return queryStorageList(oCntl, pageIndex, pageSize, option);
  }

  protected abstract StorageInfoList queryStorageList(TServiceCntl oCntl, int pageIndex, int pageSize, QueryStorageInfoListOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteStorage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageInfoDaoVariable.serviceKey,"deleteStorage",platformArgs);
deleteStorage(oCntl, storageKey);
  }

  protected abstract void deleteStorage(TServiceCntl oCntl, String storageKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateStorage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StorageInfo storageInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageInfoDaoVariable.serviceKey,"updateStorage",platformArgs);
updateStorage(oCntl, storageInfo);
  }

  protected abstract void updateStorage(TServiceCntl oCntl, StorageInfo storageInfo) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected FileStorageInfoDaoAdaptor(){
    methodParameterNameMap.put("addStorage",new String[]{"platformArgs", "storageInfo"});
    methodParameterNameMap.put("queryStorageList",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("deleteStorage",new String[]{"platformArgs", "storageKey"});
    methodParameterNameMap.put("updateStorage",new String[]{"platformArgs", "storageInfo"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
