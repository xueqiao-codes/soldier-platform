package org.soldier.platform.machine.client;

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
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.machine.MachineManageBo;
import org.soldier.platform.machine.MachineManageBoVariable;

public class MachineManageBoStub extends BaseStub {

  public MachineManageBoStub() {
    super(MachineManageBoVariable.serviceKey);
}

  public MachineList  queryMachineList(int routeKey, int timeout,QueryMachineOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MachineManageBoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MachineManageBoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("queryMachineList", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("queryMachineList");
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
    MachineManageBo.Client client = new  MachineManageBo.Client(protocol);
    MachineList result = null;
    try {
      transport.open();
      result = client.queryMachineList(platformArgs, option, pageIndex, pageSize);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"queryMachineList", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"queryMachineList", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

  public void  addMachine(int routeKey, int timeout,Machine newMachine)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MachineManageBoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MachineManageBoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("addMachine", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("addMachine");
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
    MachineManageBo.Client client = new  MachineManageBo.Client(protocol);
    try {
      transport.open();
      client.addMachine(platformArgs, newMachine);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"addMachine", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"addMachine", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateMachine(int routeKey, int timeout,Machine updateMachine)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MachineManageBoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MachineManageBoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("updateMachine", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("updateMachine");
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
    MachineManageBo.Client client = new  MachineManageBo.Client(protocol);
    try {
      transport.open();
      client.updateMachine(platformArgs, updateMachine);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"updateMachine", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"updateMachine", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteMachine(int routeKey, int timeout,String hostName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MachineManageBoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MachineManageBoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("deleteMachine", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("deleteMachine");
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
    MachineManageBo.Client client = new  MachineManageBo.Client(protocol);
    try {
      transport.open();
      client.deleteMachine(platformArgs, hostName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"deleteMachine", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"deleteMachine", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  updateMachineRelatedMonitor(int routeKey, int timeout,String hostName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MachineManageBoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MachineManageBoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("updateMachineRelatedMonitor", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("updateMachineRelatedMonitor");
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
    MachineManageBo.Client client = new  MachineManageBo.Client(protocol);
    try {
      transport.open();
      client.updateMachineRelatedMonitor(platformArgs, hostName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"updateMachineRelatedMonitor", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"updateMachineRelatedMonitor", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

  public void  deleteMachineRelatedMonitor(int routeKey, int timeout,String hostName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(MachineManageBoVariable.serviceKey)) { 
      socketTransport = new InpSocket(MachineManageBoVariable.serviceKey, timeout);
    } else {
      try{
        ip = chooseServiceAddr("deleteMachineRelatedMonitor", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
        port =  chooseServicePort("deleteMachineRelatedMonitor");
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
    MachineManageBo.Client client = new  MachineManageBo.Client(protocol);
    try {
      transport.open();
      client.deleteMachineRelatedMonitor(platformArgs, hostName);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"deleteMachineRelatedMonitor", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        MachineManageBoVariable.serviceKey,"deleteMachineRelatedMonitor", ip, e);
      throw e;
    }finally{
      transport.close();
    }
  }

}
