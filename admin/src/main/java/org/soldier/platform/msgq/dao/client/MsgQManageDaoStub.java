package org.soldier.platform.msgq.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.NetHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.platform.msgq.dao.MsgQCluster;
import org.soldier.platform.msgq.dao.MsgQClusterList;
import org.soldier.platform.msgq.dao.MsgQComsumerList;
import org.soldier.platform.msgq.dao.MsgQConsumer;
import org.soldier.platform.msgq.dao.MsgQProducer;
import org.soldier.platform.msgq.dao.MsgQProducerList;
import org.soldier.platform.msgq.dao.MsgQTopic;
import org.soldier.platform.msgq.dao.MsgQTopicList;
import org.soldier.platform.msgq.dao.QueryMsgQClusterOption;
import org.soldier.platform.msgq.dao.QueryMsgQConsumerOption;
import org.soldier.platform.msgq.dao.QueryMsgQProducerOption;
import org.soldier.platform.msgq.dao.QueryMsgQTopicOption;
import org.soldier.platform.msgq.dao.MsgQManageDao;
import org.soldier.platform.msgq.dao.MsgQManageDaoVariable;

public class MsgQManageDaoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         MsgQManageDaoVariable.serviceKey, methodName, routeKey); 
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

  public MsgQClusterList  queryMsgQClusters(int routeKey, int timeout,int pageIndex, int pageSize, QueryMsgQClusterOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryMsgQClusters", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    MsgQClusterList result = null;
    try {
      transport.open();
      result = client.queryMsgQClusters(platformArgs, pageIndex, pageSize, option);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQClusters", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQClusters", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addMsgQCluster(int routeKey, int timeout,MsgQCluster cluster)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addMsgQCluster", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.addMsgQCluster(platformArgs, cluster);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQCluster", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQCluster", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateMsgQCluster(int routeKey, int timeout,MsgQCluster cluster)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateMsgQCluster", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.updateMsgQCluster(platformArgs, cluster);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQCluster", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQCluster", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteMsgQCluster(int routeKey, int timeout,String clusterName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteMsgQCluster", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.deleteMsgQCluster(platformArgs, clusterName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQCluster", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQCluster", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public MsgQTopicList  queryMsgQTopics(int routeKey, int timeout,int pageIndex, int pageSize, QueryMsgQTopicOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryMsgQTopics", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    MsgQTopicList result = null;
    try {
      transport.open();
      result = client.queryMsgQTopics(platformArgs, pageIndex, pageSize, option);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQTopics", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQTopics", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addMsgQTopic(int routeKey, int timeout,MsgQTopic topic)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addMsgQTopic", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.addMsgQTopic(platformArgs, topic);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQTopic", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQTopic", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateMsgQTopic(int routeKey, int timeout,MsgQTopic topic)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateMsgQTopic", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.updateMsgQTopic(platformArgs, topic);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQTopic", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQTopic", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteMsgQTopic(int routeKey, int timeout,String topicName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteMsgQTopic", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.deleteMsgQTopic(platformArgs, topicName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQTopic", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQTopic", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public MsgQComsumerList  queryMsgQConsumers(int routeKey, int timeout,int pageIndex, int pageSize, QueryMsgQConsumerOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryMsgQConsumers", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    MsgQComsumerList result = null;
    try {
      transport.open();
      result = client.queryMsgQConsumers(platformArgs, pageIndex, pageSize, option);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQConsumers", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQConsumers", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addMsgQConsumer(int routeKey, int timeout,MsgQConsumer consumer)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addMsgQConsumer", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.addMsgQConsumer(platformArgs, consumer);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQConsumer", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQConsumer", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateMsgQConsumer(int routeKey, int timeout,MsgQConsumer consumer)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateMsgQConsumer", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.updateMsgQConsumer(platformArgs, consumer);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQConsumer", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQConsumer", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteMsgQConsumer(int routeKey, int timeout,String consumerKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteMsgQConsumer", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.deleteMsgQConsumer(platformArgs, consumerKey);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQConsumer", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQConsumer", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public MsgQProducerList  queryMsgQProducerList(int routeKey, int timeout,int pageIndex, int pageSize, QueryMsgQProducerOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("queryMsgQProducerList", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    MsgQProducerList result = null;
    try {
      transport.open();
      result = client.queryMsgQProducerList(platformArgs, pageIndex, pageSize, option);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQProducerList", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"queryMsgQProducerList", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addMsgQProducer(int routeKey, int timeout,MsgQProducer producer)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("addMsgQProducer", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.addMsgQProducer(platformArgs, producer);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQProducer", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"addMsgQProducer", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateMsgQProducer(int routeKey, int timeout,MsgQProducer producer)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("updateMsgQProducer", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.updateMsgQProducer(platformArgs, producer);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQProducer", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"updateMsgQProducer", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteMsgQProducer(int routeKey, int timeout,String producerKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MsgQManageDaoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MsgQManageDaoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("deleteMsgQProducer", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(MsgQManageDaoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
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
    MsgQManageDao.Client client = new  MsgQManageDao.Client(protocol);
    try {
      transport.open();
      client.deleteMsgQProducer(platformArgs, producerKey);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQProducer", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MsgQManageDaoVariable.serviceKey,"deleteMsgQProducer", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

}
