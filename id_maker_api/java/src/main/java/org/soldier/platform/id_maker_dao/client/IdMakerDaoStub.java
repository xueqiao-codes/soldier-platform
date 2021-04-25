package org.soldier.platform.id_maker_dao.client;

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
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerInfo;
import org.soldier.platform.id_maker_dao.IdMakerInfoList;
import org.soldier.platform.id_maker_dao.IdMakerQueryOption;
import org.soldier.platform.id_maker_dao.IdMakerDao;
import org.soldier.platform.id_maker_dao.IdMakerDaoVariable;

public class IdMakerDaoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         IdMakerDaoVariable.serviceKey, methodName, routeKey); 
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

  public IdAllocResult  allocIds(int routeKey, int timeout,int type)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(IdMakerDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("allocIds", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(IdMakerDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    IdMakerDao.Client client = new  IdMakerDao.Client(protocol);
    IdAllocResult result = null;
    try {
      transport.open();
      result = client.allocIds(platformArgs, type);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"allocIds", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"allocIds", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  registerType(int routeKey, int timeout,IdMakerInfo info)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(IdMakerDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("registerType", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(IdMakerDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    IdMakerDao.Client client = new  IdMakerDao.Client(protocol);
    try {
      transport.open();
      client.registerType(platformArgs, info);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"registerType", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"registerType", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateType(int routeKey, int timeout,IdMakerInfo info)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(IdMakerDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateType", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(IdMakerDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    IdMakerDao.Client client = new  IdMakerDao.Client(protocol);
    try {
      transport.open();
      client.updateType(platformArgs, info);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"updateType", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"updateType", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public IdMakerInfo  getIdMakerInfoByType(int routeKey, int timeout,int type)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(IdMakerDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("getIdMakerInfoByType", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(IdMakerDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    IdMakerDao.Client client = new  IdMakerDao.Client(protocol);
    IdMakerInfo result = null;
    try {
      transport.open();
      result = client.getIdMakerInfoByType(platformArgs, type);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"getIdMakerInfoByType", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"getIdMakerInfoByType", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public IdMakerInfoList  queryIdMakerTypeInfoList(int routeKey, int timeout,int pageIndex, int pageSize, IdMakerQueryOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(IdMakerDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryIdMakerTypeInfoList", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(IdMakerDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    IdMakerDao.Client client = new  IdMakerDao.Client(protocol);
    IdMakerInfoList result = null;
    try {
      transport.open();
      result = client.queryIdMakerTypeInfoList(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"queryIdMakerTypeInfoList", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        IdMakerDaoVariable.serviceKey,"queryIdMakerTypeInfoList", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

}
