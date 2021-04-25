package com.car.user.dao.client;

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
import com.car.user.dao.UserInfo;
import com.car.user.dao.UserDao;
import com.car.user.dao.UserDaoVariable;

public class UserDaoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         UserDaoVariable.serviceKey, methodName, routeKey); 
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

  public long  Register(int routeKey, int timeout,String userEmail, String userPasswd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService() 
    		&& SvrConfiguration.isServiceInProcess(UserDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("Register", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    long result = 0;
    try {
      transport.open();
      result = client.Register(platformArgs, userEmail, userPasswd);
//      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"Register", ip, null);
//      }
    } catch (TException e) {
//      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"Register", ip, e);
//      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public long  CheckLogIn(int routeKey, int timeout,String userEmail, String userPasswd)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("CheckLogIn", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    long result = 0;
    try {
      transport.open();
      result = client.CheckLogIn(platformArgs, userEmail, userPasswd);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"CheckLogIn", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"CheckLogIn", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public long  GetCollectUserUid(int routeKey, int timeout,int userSource, String userSourceId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("GetCollectUserUid", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    long result = 0;
    try {
      transport.open();
      result = client.GetCollectUserUid(platformArgs, userSource, userSourceId);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"GetCollectUserUid", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"GetCollectUserUid", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public UserInfo  getUserInfo(int routeKey, int timeout,long userId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(UserDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("getUserInfo", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    UserInfo result = null;
    try {
      transport.open();
      result = client.getUserInfo(platformArgs, userId);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"getUserInfo", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"getUserInfo", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  setUserInfo(int routeKey, int timeout,UserInfo user)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(UserDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("setUserInfo", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    try {
      transport.open();
      client.setUserInfo(platformArgs, user);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"setUserInfo", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"setUserInfo", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  AddUserRp(int routeKey, int timeout,int userId, int value)throws TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("AddUserRp", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    try {
      transport.open();
      client.AddUserRp(platformArgs, userId, value);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"AddUserRp", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"AddUserRp", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  AddUserRpAndScore(int routeKey, int timeout,int userId, int rpValue, int userScore)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(UserDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("AddUserRpAndScore", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(UserDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    UserDao.Client client = new  UserDao.Client(protocol);
    try {
      transport.open();
      client.AddUserRpAndScore(platformArgs, userId, rpValue, userScore);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"AddUserRpAndScore", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        UserDaoVariable.serviceKey,"AddUserRpAndScore", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

}
