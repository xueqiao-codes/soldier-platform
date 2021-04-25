package org.soldier.platform.oa.user.ao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.platform.svr_platform.client.BaseStub;
import java.util.List;
import org.soldier.platform.oa.user.ao.LoginResult;
import org.soldier.platform.oa.user.ao.OaUserAo;
import org.soldier.platform.oa.user.ao.OaUserAoVariable;

public class OaUserAoStub extends BaseStub {

  public OaUserAoStub() {
    super(OaUserAoVariable.serviceKey);
}

  public LoginResult  login(int routeKey, int timeout,String userName, String passwordMd5)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("login", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("login");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    LoginResult result = null;
    try {
      transport.open();
      result = client.login(platformArgs, userName, passwordMd5);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"login", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"login", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public int  registerUser(int routeKey, int timeout,org.soldier.platform.oa.user.OaUser user, String operationUserName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("registerUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("registerUser");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    int result = 0;
    try {
      transport.open();
      result = client.registerUser(platformArgs, user, operationUserName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"registerUser", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"registerUser", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public boolean  checkSession(int routeKey, int timeout,int userId, String userName, String secretKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("checkSession", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("checkSession");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    boolean result = false;
    try {
      transport.open();
      result = client.checkSession(platformArgs, userId, userName, secretKey);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"checkSession", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"checkSession", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  updateUser(int routeKey, int timeout,org.soldier.platform.oa.user.OaUser user)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("updateUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("updateUser");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    try {
      transport.open();
      client.updateUser(platformArgs, user);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"updateUser", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"updateUser", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteUser(int routeKey, int timeout,int userId, String operationUserName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("deleteUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("deleteUser");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    try {
      transport.open();
      client.deleteUser(platformArgs, userId, operationUserName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"deleteUser", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"deleteUser", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  logout(int routeKey, int timeout,int userId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("logout", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("logout");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    try {
      transport.open();
      client.logout(platformArgs, userId);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"logout", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"logout", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public List<org.soldier.platform.oa.user.OaUser>  queryUser(int routeKey, int timeout,org.soldier.platform.oa.user.QueryUserOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("queryUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("queryUser");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    List<org.soldier.platform.oa.user.OaUser> result = null;
    try {
      transport.open();
      result = client.queryUser(platformArgs, option);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"queryUser", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"queryUser", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public org.soldier.platform.oa.user.OaUserPage  queryUserByPage(int routeKey, int timeout,org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(OaUserAoVariable.serviceKey)) { 
      socketTransport = new InpSocket(OaUserAoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("queryUserByPage", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("queryUserByPage");
        socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    OaUserAo.Client client = new  OaUserAo.Client(protocol);
    org.soldier.platform.oa.user.OaUserPage result = null;
    try {
      transport.open();
      result = client.queryUserByPage(platformArgs, option, pageIndex, pageSize);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"queryUserByPage", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaUserAoVariable.serviceKey,"queryUserByPage", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

}
