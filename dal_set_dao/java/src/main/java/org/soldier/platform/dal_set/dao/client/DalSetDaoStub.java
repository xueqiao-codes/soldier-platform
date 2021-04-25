package org.soldier.platform.dal_set.dao.client;

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
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.DalSetDao;
import org.soldier.platform.dal_set.dao.DalSetDaoVariable;

public class DalSetDaoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         DalSetDaoVariable.serviceKey, methodName, routeKey); 
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

  public DalSetHostList  queryDalSetHosts(int routeKey, int timeout,int pageIndex, int pageSize, QueryDalSetHostOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryDalSetHosts", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    DalSetHostList result = null;
    try {
      transport.open();
      result = client.queryDalSetHosts(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetHosts", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetHosts", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addDalSetHost(int routeKey, int timeout,DalSetHost host)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addDalSetHost", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addDalSetHost(platformArgs, host);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetHost", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetHost", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateDalSetHost(int routeKey, int timeout,DalSetHost host)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateDalSetHost", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateDalSetHost(platformArgs, host);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetHost", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetHost", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteDalSetHost(int routeKey, int timeout,String hostKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteDalSetHost", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteDalSetHost(platformArgs, hostKey);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetHost", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetHost", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public DalSetUserList  queryDalSetUsers(int routeKey, int timeout,int pageIndex, int pageSize, QueryDalSetUserOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryDalSetUsers", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    DalSetUserList result = null;
    try {
      transport.open();
      result = client.queryDalSetUsers(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetUsers", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetUsers", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addDalSetUser(int routeKey, int timeout,DalSetUser user)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addDalSetUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addDalSetUser(platformArgs, user);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetUser", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetUser", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateDalSetUser(int routeKey, int timeout,DalSetUser user)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateDalSetUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateDalSetUser(platformArgs, user);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetUser", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetUser", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteDalSetUser(int routeKey, int timeout,String userKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteDalSetUser", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteDalSetUser(platformArgs, userKey);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetUser", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetUser", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public DalSetTableList  queryDalSetTables(int routeKey, int timeout,int pageIndex, int pageSize, QueryDalSetTableOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryDalSetTables", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    DalSetTableList result = null;
    try {
      transport.open();
      result = client.queryDalSetTables(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetTables", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetTables", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addDalSetTable(int routeKey, int timeout,DalSetTable table)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addDalSetTable", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addDalSetTable(platformArgs, table);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetTable", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetTable", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateDalSetTable(int routeKey, int timeout,DalSetTable table)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateDalSetTable", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateDalSetTable(platformArgs, table);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetTable", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetTable", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteDalSetTable(int routeKey, int timeout,String tablePrefixName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteDalSetTable", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteDalSetTable(platformArgs, tablePrefixName);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetTable", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetTable", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public DalSetRoleList  queryDalSetRoles(int routeKey, int timeout,int pageIndex, int pageSize, QueryDalSetRoleOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryDalSetRoles", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    DalSetRoleList result = null;
    try {
      transport.open();
      result = client.queryDalSetRoles(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetRoles", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryDalSetRoles", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addDalSetRole(int routeKey, int timeout,DalSetRole role)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addDalSetRole", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addDalSetRole(platformArgs, role);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetRole", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addDalSetRole", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateDalSetRole(int routeKey, int timeout,DalSetRole role)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateDalSetRole", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateDalSetRole(platformArgs, role);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetRole", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetRole", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteDalSetRole(int routeKey, int timeout,String roleName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteDalSetRole", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteDalSetRole(platformArgs, roleName);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetRole", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteDalSetRole", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  addTableRoleRelation(int routeKey, int timeout,RoleTableRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addTableRoleRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addTableRoleRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addTableRoleRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addTableRoleRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteTableRoleRelation(int routeKey, int timeout,RoleTableRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteTableRoleRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteTableRoleRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteTableRoleRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteTableRoleRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public RoleTableRelationList  queryTableRoleRelations(int routeKey, int timeout,int pageIndex, int pageSize, QueryRoleTableRelationOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryTableRoleRelations", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    RoleTableRelationList result = null;
    try {
      transport.open();
      result = client.queryTableRoleRelations(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryTableRoleRelations", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryTableRoleRelations", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public RoleSetRelationList  queryRoleSetRelations(int routeKey, int timeout,int pageIndex, int pageSize, QueryRoleSetRelationOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryRoleSetRelations", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    RoleSetRelationList result = null;
    try {
      transport.open();
      result = client.queryRoleSetRelations(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryRoleSetRelations", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryRoleSetRelations", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addRoleSetRelation(int routeKey, int timeout,RoleSetRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addRoleSetRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addRoleSetRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addRoleSetRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addRoleSetRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteRoleSetRelation(int routeKey, int timeout,RoleSetRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteRoleSetRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteRoleSetRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteRoleSetRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteRoleSetRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateRoleSetRelation(int routeKey, int timeout,RoleSetRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateRoleSetRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateRoleSetRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateRoleSetRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateRoleSetRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public RoleServiceRelationList  queryRoleServiceRelations(int routeKey, int timeout,int pageIndex, int pageSize, QueryRoleServiceRelationOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryRoleServiceRelations", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    RoleServiceRelationList result = null;
    try {
      transport.open();
      result = client.queryRoleServiceRelations(platformArgs, pageIndex, pageSize, option);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryRoleServiceRelations", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"queryRoleServiceRelations", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addRoleServiceRelation(int routeKey, int timeout,RoleServiceRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addRoleServiceRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.addRoleServiceRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addRoleServiceRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"addRoleServiceRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateRoleServiceRelation(int routeKey, int timeout,RoleServiceRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateRoleServiceRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateRoleServiceRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateRoleServiceRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateRoleServiceRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteRoleServiceRelation(int routeKey, int timeout,RoleServiceRelation relation)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteRoleServiceRelation", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.deleteRoleServiceRelation(platformArgs, relation);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteRoleServiceRelation", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"deleteRoleServiceRelation", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateDalSetXml(int routeKey, int timeout,String xml)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateDalSetXml", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    try {
      transport.open();
      client.updateDalSetXml(platformArgs, xml);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetXml", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"updateDalSetXml", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
  }

  public int  getLastVersion(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("getLastVersion", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    int result = 0;
    try {
      transport.open();
      result = client.getLastVersion(platformArgs);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"getLastVersion", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"getLastVersion", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public String  getLastXml(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(DalSetDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("getLastXml", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(DalSetDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    DalSetDao.Client client = new  DalSetDao.Client(protocol);
    String result = "";
    try {
      transport.open();
      result = client.getLastXml(platformArgs);
      if (!SvrConfiguration.getIsUsingInpService()) { 
        ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"getLastXml", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        DalSetDaoVariable.serviceKey,"getLastXml", ip, e);
      }
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

}
