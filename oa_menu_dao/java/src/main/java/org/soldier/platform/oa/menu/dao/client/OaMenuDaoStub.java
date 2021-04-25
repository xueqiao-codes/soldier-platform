package org.soldier.platform.oa.menu.dao.client;

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
import java.util.List;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;
import org.soldier.platform.oa.menu.dao.OaMenuDao;
import org.soldier.platform.oa.menu.dao.OaMenuDaoVariable;

public class OaMenuDaoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         OaMenuDaoVariable.serviceKey, methodName, routeKey); 
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

  public List<TSystemMenu>  getSystemMenus(int routeKey, int timeout,QuerySystemMenuOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("getSystemMenus", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    List<TSystemMenu> result = null;
    try {
      transport.open();
      result = client.getSystemMenus(platformArgs, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"getSystemMenus", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"getSystemMenus", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public List<TSubMenu>  getSubMenus(int routeKey, int timeout,QuerySubMenuOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("getSubMenus", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    List<TSubMenu> result = null;
    try {
      transport.open();
      result = client.getSubMenus(platformArgs, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"getSubMenus", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"getSubMenus", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public int  addSystemMenu(int routeKey, int timeout,TSystemMenu menu)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addSystemMenu", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    int result = 0;
    try {
      transport.open();
      result = client.addSystemMenu(platformArgs, menu);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"addSystemMenu", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"addSystemMenu", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  deleteSystemMenu(int routeKey, int timeout,int systemMenuId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteSystemMenu", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    try {
      transport.open();
      client.deleteSystemMenu(platformArgs, systemMenuId);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"deleteSystemMenu", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"deleteSystemMenu", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public int  addSubMenu(int routeKey, int timeout,TSubMenu menu)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addSubMenu", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    int result = 0;
    try {
      transport.open();
      result = client.addSubMenu(platformArgs, menu);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"addSubMenu", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"addSubMenu", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  deleteSubMenu(int routeKey, int timeout,int menuId)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteSubMenu", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    try {
      transport.open();
      client.deleteSubMenu(platformArgs, menuId);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"deleteSubMenu", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"deleteSubMenu", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateSubMenu(int routeKey, int timeout,TSubMenu menu)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateSubMenu", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    try {
      transport.open();
      client.updateSubMenu(platformArgs, menu);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"updateSubMenu", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"updateSubMenu", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateSystemMenu(int routeKey, int timeout,TSystemMenu menu)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(OaMenuDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateSystemMenu", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(OaMenuDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    OaMenuDao.Client client = new  OaMenuDao.Client(protocol);
    try {
      transport.open();
      client.updateSystemMenu(platformArgs, menu);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"updateSystemMenu", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        OaMenuDaoVariable.serviceKey,"updateSystemMenu", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

}
