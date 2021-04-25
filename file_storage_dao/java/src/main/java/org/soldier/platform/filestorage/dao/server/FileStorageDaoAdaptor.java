package org.soldier.platform.filestorage.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.filestorage.dao.FileStorageDao;
import org.soldier.platform.filestorage.dao.FileStorageDaoVariable;


public abstract class FileStorageDaoAdaptor implements FileStorageDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public ByteBuffer readFile(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey, String path) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageDaoVariable.serviceKey,"readFile",platformArgs);
    return readFile(oCntl, storageKey, path);
  }

  protected abstract ByteBuffer readFile(TServiceCntl oCntl, String storageKey, String path) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void writeFile(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey, String path, ByteBuffer content, HttpOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageDaoVariable.serviceKey,"writeFile",platformArgs);
writeFile(oCntl, storageKey, path, content, option);
  }

  protected abstract void writeFile(TServiceCntl oCntl, String storageKey, String path, ByteBuffer content, HttpOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteFile(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String storageKey, String path) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(FileStorageDaoVariable.serviceKey,"deleteFile",platformArgs);
deleteFile(oCntl, storageKey, path);
  }

  protected abstract void deleteFile(TServiceCntl oCntl, String storageKey, String path) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected FileStorageDaoAdaptor(){
    methodParameterNameMap.put("readFile",new String[]{"platformArgs", "storageKey", "path"});
    methodParameterNameMap.put("writeFile",new String[]{"platformArgs", "storageKey", "path", "content", "option"});
    methodParameterNameMap.put("deleteFile",new String[]{"platformArgs", "storageKey", "path"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
