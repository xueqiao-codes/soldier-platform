package org.soldier.platform.file_storage_info.dao.client;

import org.soldier.platform.file_storage_info.dao.FileStorageInfoDao;
import org.soldier.platform.file_storage_info.dao.FileStorageInfoDaoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.StorageInfo;
import org.soldier.platform.file_storage_info.dao.StorageInfoList;

public class FileStorageInfoDaoAsyncStub { 
  private String peerAddr;

  public void setPeerAddr(final String ipAddr) { 
    if (ipAddr == null) { 
      peerAddr = null; 
    }
    if (-1l != NetHelper.AddrNet(ipAddr)) {
      peerAddr = ipAddr; 
    }
  }

  public String getPeerAddr() { 
    return peerAddr;
  }

  public void send_addStorage(int routeKey, int timeout, StorageInfo storageInfo) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addStorageServiceCall(routeKey, timeout, platformArgs, storageInfo), new TRequestOption());
  }

  public void send_addStorage(int routeKey, int timeout, StorageInfo storageInfo,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addStorageServiceCall(routeKey, timeout, platformArgs, storageInfo), requestOption);
  }

  public long addStorage(int routeKey, int timeout, StorageInfo storageInfo, IMethodCallback<FileStorageInfoDao.addStorage_args, FileStorageInfoDao.addStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addStorageServiceCall(routeKey, timeout, platformArgs, storageInfo), callback);
  }

  public long add_addStorageCall(AsyncCallRunner runner, int routeKey, int timeout, StorageInfo storageInfo, IMethodCallback<FileStorageInfoDao.addStorage_args, FileStorageInfoDao.addStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addStorageServiceCall(routeKey, timeout, platformArgs, storageInfo), callback);
  }

  protected TServiceCall create_addStorageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, StorageInfo storageInfo){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(FileStorageInfoDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    FileStorageInfoDao.addStorage_args request = new FileStorageInfoDao.addStorage_args();
    request.setPlatformArgs(platformArgs);
    request.setStorageInfo(storageInfo);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addStorage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(FileStorageInfoDao.addStorage_result.class);
    return serviceCall;
  }

  public void send_queryStorageList(int routeKey, int timeout, int pageIndex, int pageSize, QueryStorageInfoListOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryStorageListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryStorageList(int routeKey, int timeout, int pageIndex, int pageSize, QueryStorageInfoListOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryStorageListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryStorageList(int routeKey, int timeout, int pageIndex, int pageSize, QueryStorageInfoListOption option, IMethodCallback<FileStorageInfoDao.queryStorageList_args, FileStorageInfoDao.queryStorageList_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryStorageListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryStorageListCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryStorageInfoListOption option, IMethodCallback<FileStorageInfoDao.queryStorageList_args, FileStorageInfoDao.queryStorageList_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryStorageListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryStorageListServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryStorageInfoListOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(FileStorageInfoDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    FileStorageInfoDao.queryStorageList_args request = new FileStorageInfoDao.queryStorageList_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryStorageList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(FileStorageInfoDao.queryStorageList_result.class);
    return serviceCall;
  }

  public void send_deleteStorage(int routeKey, int timeout, String storageKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteStorageServiceCall(routeKey, timeout, platformArgs, storageKey), new TRequestOption());
  }

  public void send_deleteStorage(int routeKey, int timeout, String storageKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteStorageServiceCall(routeKey, timeout, platformArgs, storageKey), requestOption);
  }

  public long deleteStorage(int routeKey, int timeout, String storageKey, IMethodCallback<FileStorageInfoDao.deleteStorage_args, FileStorageInfoDao.deleteStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteStorageServiceCall(routeKey, timeout, platformArgs, storageKey), callback);
  }

  public long add_deleteStorageCall(AsyncCallRunner runner, int routeKey, int timeout, String storageKey, IMethodCallback<FileStorageInfoDao.deleteStorage_args, FileStorageInfoDao.deleteStorage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteStorageServiceCall(routeKey, timeout, platformArgs, storageKey), callback);
  }

  protected TServiceCall create_deleteStorageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(FileStorageInfoDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    FileStorageInfoDao.deleteStorage_args request = new FileStorageInfoDao.deleteStorage_args();
    request.setPlatformArgs(platformArgs);
    request.setStorageKey(storageKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteStorage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(FileStorageInfoDao.deleteStorage_result.class);
    return serviceCall;
  }

}
