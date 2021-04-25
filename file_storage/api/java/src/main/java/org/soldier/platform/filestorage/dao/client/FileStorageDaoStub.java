package org.soldier.platform.filestorage.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.nio.ByteBuffer;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.filestorage.dao.FileStorageDao;
import org.soldier.platform.filestorage.dao.FileStorageDaoVariable;

public class FileStorageDaoStub extends BaseStub {

  public FileStorageDaoStub() {
    super(FileStorageDaoVariable.serviceKey);
  }

  public ByteBuffer  readFile(String storageKey, String path) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return readFile(storageKey, path, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ByteBuffer  readFile(String storageKey, String path,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("readFile").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<ByteBuffer>(){
    @Override
    public ByteBuffer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new FileStorageDao.Client(protocol).readFile(platformArgs, storageKey, path);
      }
    }, invokeInfo);
  }

  public ByteBuffer  readFile(int routeKey, int timeout,String storageKey, String path)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return readFile(storageKey, path, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  writeFile(String storageKey, String path, ByteBuffer content, HttpOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    writeFile(storageKey, path, content, option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  writeFile(String storageKey, String path, ByteBuffer content, HttpOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("writeFile").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new FileStorageDao.Client(protocol).writeFile(platformArgs, storageKey, path, content, option);
      return null;
      }
    }, invokeInfo);
  }

  public void  writeFile(int routeKey, int timeout,String storageKey, String path, ByteBuffer content, HttpOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    writeFile(storageKey, path, content, option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteFile(String storageKey, String path) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteFile(storageKey, path, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteFile(String storageKey, String path,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteFile").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new FileStorageDao.Client(protocol).deleteFile(platformArgs, storageKey, path);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteFile(int routeKey, int timeout,String storageKey, String path)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteFile(storageKey, path, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
