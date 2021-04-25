package org.soldier.platform.machine.client;

import org.soldier.platform.machine.MachineManageBo;
import org.soldier.platform.machine.MachineManageBoVariable;
import org.apache.thrift.TException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.QueryMachineOption;

public class MachineManageBoAsyncStub extends BaseStub { 
  public MachineManageBoAsyncStub() {
    super(MachineManageBoVariable.serviceKey);
  }
  public void send_queryMachineList(int routeKey, int timeout, QueryMachineOption option, int pageIndex, int pageSize) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_queryMachineListServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_queryMachineList(int routeKey, int timeout, QueryMachineOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_queryMachineListServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long queryMachineList(int routeKey, int timeout, QueryMachineOption option, int pageIndex, int pageSize, IMethodCallback<MachineManageBo.queryMachineList_args, MachineManageBo.queryMachineList_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_queryMachineListServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_queryMachineListCall(AsyncCallRunner runner, int routeKey, int timeout, QueryMachineOption option, int pageIndex, int pageSize, IMethodCallback<MachineManageBo.queryMachineList_args, MachineManageBo.queryMachineList_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_queryMachineListServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_queryMachineListServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryMachineOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(MachineManageBoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MachineManageBo.queryMachineList_args request = new MachineManageBo.queryMachineList_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryMachineList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MachineManageBo.queryMachineList_result.class);
    return serviceCall;
  }

  public void send_addMachine(int routeKey, int timeout, Machine newMachine) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_addMachineServiceCall(routeKey, timeout, platformArgs, newMachine), new TRequestOption());
  }

  public void send_addMachine(int routeKey, int timeout, Machine newMachine,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_addMachineServiceCall(routeKey, timeout, platformArgs, newMachine), requestOption);
  }

  public long addMachine(int routeKey, int timeout, Machine newMachine, IMethodCallback<MachineManageBo.addMachine_args, MachineManageBo.addMachine_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_addMachineServiceCall(routeKey, timeout, platformArgs, newMachine), callback);
  }

  public long add_addMachineCall(AsyncCallRunner runner, int routeKey, int timeout, Machine newMachine, IMethodCallback<MachineManageBo.addMachine_args, MachineManageBo.addMachine_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_addMachineServiceCall(routeKey, timeout, platformArgs, newMachine), callback);
  }

  protected TServiceCall create_addMachineServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Machine newMachine){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(MachineManageBoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MachineManageBo.addMachine_args request = new MachineManageBo.addMachine_args();
    request.setPlatformArgs(platformArgs);
    request.setNewMachine(newMachine);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addMachine");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MachineManageBo.addMachine_result.class);
    return serviceCall;
  }

  public void send_updateMachine(int routeKey, int timeout, Machine updateMachine) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_updateMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), new TRequestOption());
  }

  public void send_updateMachine(int routeKey, int timeout, Machine updateMachine,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_updateMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), requestOption);
  }

  public long updateMachine(int routeKey, int timeout, Machine updateMachine, IMethodCallback<MachineManageBo.updateMachine_args, MachineManageBo.updateMachine_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_updateMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), callback);
  }

  public long add_updateMachineCall(AsyncCallRunner runner, int routeKey, int timeout, Machine updateMachine, IMethodCallback<MachineManageBo.updateMachine_args, MachineManageBo.updateMachine_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_updateMachineServiceCall(routeKey, timeout, platformArgs, updateMachine), callback);
  }

  protected TServiceCall create_updateMachineServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Machine updateMachine){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(MachineManageBoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MachineManageBo.updateMachine_args request = new MachineManageBo.updateMachine_args();
    request.setPlatformArgs(platformArgs);
    request.setUpdateMachine(updateMachine);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateMachine");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MachineManageBo.updateMachine_result.class);
    return serviceCall;
  }

  public void send_deleteMachine(int routeKey, int timeout, String hostName) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_deleteMachineServiceCall(routeKey, timeout, platformArgs, hostName), new TRequestOption());
  }

  public void send_deleteMachine(int routeKey, int timeout, String hostName,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_deleteMachineServiceCall(routeKey, timeout, platformArgs, hostName), requestOption);
  }

  public long deleteMachine(int routeKey, int timeout, String hostName, IMethodCallback<MachineManageBo.deleteMachine_args, MachineManageBo.deleteMachine_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_deleteMachineServiceCall(routeKey, timeout, platformArgs, hostName), callback);
  }

  public long add_deleteMachineCall(AsyncCallRunner runner, int routeKey, int timeout, String hostName, IMethodCallback<MachineManageBo.deleteMachine_args, MachineManageBo.deleteMachine_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_deleteMachineServiceCall(routeKey, timeout, platformArgs, hostName), callback);
  }

  protected TServiceCall create_deleteMachineServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(MachineManageBoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MachineManageBo.deleteMachine_args request = new MachineManageBo.deleteMachine_args();
    request.setPlatformArgs(platformArgs);
    request.setHostName(hostName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteMachine");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MachineManageBo.deleteMachine_result.class);
    return serviceCall;
  }

  public void send_updateMachineRelatedMonitor(int routeKey, int timeout, String hostName) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_updateMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), new TRequestOption());
  }

  public void send_updateMachineRelatedMonitor(int routeKey, int timeout, String hostName,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_updateMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), requestOption);
  }

  public long updateMachineRelatedMonitor(int routeKey, int timeout, String hostName, IMethodCallback<MachineManageBo.updateMachineRelatedMonitor_args, MachineManageBo.updateMachineRelatedMonitor_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_updateMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), callback);
  }

  public long add_updateMachineRelatedMonitorCall(AsyncCallRunner runner, int routeKey, int timeout, String hostName, IMethodCallback<MachineManageBo.updateMachineRelatedMonitor_args, MachineManageBo.updateMachineRelatedMonitor_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_updateMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), callback);
  }

  protected TServiceCall create_updateMachineRelatedMonitorServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(MachineManageBoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MachineManageBo.updateMachineRelatedMonitor_args request = new MachineManageBo.updateMachineRelatedMonitor_args();
    request.setPlatformArgs(platformArgs);
    request.setHostName(hostName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateMachineRelatedMonitor");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MachineManageBo.updateMachineRelatedMonitor_result.class);
    return serviceCall;
  }

  public void send_deleteMachineRelatedMonitor(int routeKey, int timeout, String hostName) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_deleteMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), new TRequestOption());
  }

  public void send_deleteMachineRelatedMonitor(int routeKey, int timeout, String hostName,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_deleteMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), requestOption);
  }

  public long deleteMachineRelatedMonitor(int routeKey, int timeout, String hostName, IMethodCallback<MachineManageBo.deleteMachineRelatedMonitor_args, MachineManageBo.deleteMachineRelatedMonitor_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_deleteMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), callback);
  }

  public long add_deleteMachineRelatedMonitorCall(AsyncCallRunner runner, int routeKey, int timeout, String hostName, IMethodCallback<MachineManageBo.deleteMachineRelatedMonitor_args, MachineManageBo.deleteMachineRelatedMonitor_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_deleteMachineRelatedMonitorServiceCall(routeKey, timeout, platformArgs, hostName), callback);
  }

  protected TServiceCall create_deleteMachineRelatedMonitorServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(MachineManageBoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MachineManageBo.deleteMachineRelatedMonitor_args request = new MachineManageBo.deleteMachineRelatedMonitor_args();
    request.setPlatformArgs(platformArgs);
    request.setHostName(hostName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteMachineRelatedMonitor");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MachineManageBo.deleteMachineRelatedMonitor_result.class);
    return serviceCall;
  }

}
