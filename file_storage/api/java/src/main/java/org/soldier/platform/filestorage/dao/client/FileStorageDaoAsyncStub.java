package org.soldier.platform.filestorage.dao.client;

import org.soldier.platform.filestorage.dao.FileStorageDao;
import org.soldier.platform.filestorage.dao.FileStorageDaoVariable;
import org.apache.thrift.TException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.client.BaseStub;
import java.nio.ByteBuffer;
import org.soldier.platform.filestorage.dao.HttpOption;

public class FileStorageDaoAsyncStub extends BaseStub { 
  public FileStorageDaoAsyncStub() {
    super(FileStorageDaoVariable.serviceKey);
  }
  public void send_readFile(int routeKey, int timeout, String storageKey, String path) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_readFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), new TRequestOption());
  }

  public void send_readFile(int routeKey, int timeout, String storageKey, String path,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_readFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), requestOption);
  }

  public long readFile(int routeKey, int timeout, String storageKey, String path, IMethodCallback<FileStorageDao.readFile_args, FileStorageDao.readFile_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_readFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), callback);
  }

  public long add_readFileCall(AsyncCallRunner runner, int routeKey, int timeout, String storageKey, String path, IMethodCallback<FileStorageDao.readFile_args, FileStorageDao.readFile_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_readFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), callback);
  }

  protected TServiceCall create_readFileServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey, String path){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(FileStorageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    FileStorageDao.readFile_args request = new FileStorageDao.readFile_args();
    request.setPlatformArgs(platformArgs);
    request.setStorageKey(storageKey);
    request.setPath(path);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("readFile");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(FileStorageDao.readFile_result.class);
    return serviceCall;
  }

  public void send_writeFile(int routeKey, int timeout, String storageKey, String path, ByteBuffer content, HttpOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_writeFileServiceCall(routeKey, timeout, platformArgs, storageKey, path, content, option), new TRequestOption());
  }

  public void send_writeFile(int routeKey, int timeout, String storageKey, String path, ByteBuffer content, HttpOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_writeFileServiceCall(routeKey, timeout, platformArgs, storageKey, path, content, option), requestOption);
  }

  public long writeFile(int routeKey, int timeout, String storageKey, String path, ByteBuffer content, HttpOption option, IMethodCallback<FileStorageDao.writeFile_args, FileStorageDao.writeFile_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_writeFileServiceCall(routeKey, timeout, platformArgs, storageKey, path, content, option), callback);
  }

  public long add_writeFileCall(AsyncCallRunner runner, int routeKey, int timeout, String storageKey, String path, ByteBuffer content, HttpOption option, IMethodCallback<FileStorageDao.writeFile_args, FileStorageDao.writeFile_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_writeFileServiceCall(routeKey, timeout, platformArgs, storageKey, path, content, option), callback);
  }

  protected TServiceCall create_writeFileServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey, String path, ByteBuffer content, HttpOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(FileStorageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    FileStorageDao.writeFile_args request = new FileStorageDao.writeFile_args();
    request.setPlatformArgs(platformArgs);
    request.setStorageKey(storageKey);
    request.setPath(path);
    request.setContent(content);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("writeFile");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(FileStorageDao.writeFile_result.class);
    return serviceCall;
  }

  public void send_deleteFile(int routeKey, int timeout, String storageKey, String path) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), new TRequestOption());
  }

  public void send_deleteFile(int routeKey, int timeout, String storageKey, String path,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), requestOption);
  }

  public long deleteFile(int routeKey, int timeout, String storageKey, String path, IMethodCallback<FileStorageDao.deleteFile_args, FileStorageDao.deleteFile_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_deleteFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), callback);
  }

  public long add_deleteFileCall(AsyncCallRunner runner, int routeKey, int timeout, String storageKey, String path, IMethodCallback<FileStorageDao.deleteFile_args, FileStorageDao.deleteFile_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_deleteFileServiceCall(routeKey, timeout, platformArgs, storageKey, path), callback);
  }

  protected TServiceCall create_deleteFileServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey, String path){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(FileStorageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    FileStorageDao.deleteFile_args request = new FileStorageDao.deleteFile_args();
    request.setPlatformArgs(platformArgs);
    request.setStorageKey(storageKey);
    request.setPath(path);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteFile");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(FileStorageDao.deleteFile_result.class);
    return serviceCall;
  }

}
