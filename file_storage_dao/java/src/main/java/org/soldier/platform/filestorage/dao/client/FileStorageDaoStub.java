package org.soldier.platform.filestorage.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpSocket;
import java.nio.ByteBuffer;
import org.soldier.platform.filestorage.dao.HttpOption;
import org.soldier.platform.filestorage.dao.FileStorageDao;
import org.soldier.platform.filestorage.dao.FileStorageDaoVariable;

public class FileStorageDaoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         FileStorageDaoVariable.serviceKey, methodName, routeKey); 
    }
    return ip;
  }

  public void setPeerAddr(final String ipAddr){
    if (ipAddr == null) { 
      peerAddr = null;
    }
    if (-1l != NetHelper.AddrNet(ipAddr)) { 
      peerAddr = ipAddr;
    }  }

  public String getPeerAddr() { 
    return peerAddr;
  }

  public ByteBuffer  readFile(int routeKey, int timeout,String storageKey, String path)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(FileStorageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("readFile", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(FileStorageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    FileStorageDao.Client client = new  FileStorageDao.Client(protocol);
    ByteBuffer result = null;
    try {
      transport.open();
      result = client.readFile(platformArgs, storageKey, path);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        FileStorageDaoVariable.serviceKey,"readFile", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        FileStorageDaoVariable.serviceKey,"readFile", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  writeFile(int routeKey, int timeout,String storageKey, String path, ByteBuffer content, HttpOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(FileStorageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("writeFile", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(FileStorageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    FileStorageDao.Client client = new  FileStorageDao.Client(protocol);
    try {
      transport.open();
      client.writeFile(platformArgs, storageKey, path, content, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        FileStorageDaoVariable.serviceKey,"writeFile", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        FileStorageDaoVariable.serviceKey,"writeFile", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteFile(int routeKey, int timeout,String storageKey, String path)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(FileStorageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteFile", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(FileStorageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    FileStorageDao.Client client = new  FileStorageDao.Client(protocol);
    try {
      transport.open();
      client.deleteFile(platformArgs, storageKey, path);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        FileStorageDaoVariable.serviceKey,"deleteFile", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        FileStorageDaoVariable.serviceKey,"deleteFile", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

}
