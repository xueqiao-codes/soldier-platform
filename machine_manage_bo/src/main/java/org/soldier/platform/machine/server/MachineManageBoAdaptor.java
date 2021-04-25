package org.soldier.platform.machine.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.machine.MachineManageBo;
import org.soldier.platform.machine.MachineManageBoVariable;


public abstract class MachineManageBoAdaptor implements MachineManageBo.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public MachineList queryMachineList(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryMachineOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(MachineManageBoVariable.serviceKey,"queryMachineList",platformArgs);
    return queryMachineList(oCntl, option, pageIndex, pageSize);
  }

  protected abstract MachineList queryMachineList(TServiceCntl oCntl, QueryMachineOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addMachine(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Machine newMachine) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(MachineManageBoVariable.serviceKey,"addMachine",platformArgs);
addMachine(oCntl, newMachine);
  }

  protected abstract void addMachine(TServiceCntl oCntl, Machine newMachine) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateMachine(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Machine updateMachine) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(MachineManageBoVariable.serviceKey,"updateMachine",platformArgs);
updateMachine(oCntl, updateMachine);
  }

  protected abstract void updateMachine(TServiceCntl oCntl, Machine updateMachine) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteMachine(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(MachineManageBoVariable.serviceKey,"deleteMachine",platformArgs);
deleteMachine(oCntl, hostName);
  }

  protected abstract void deleteMachine(TServiceCntl oCntl, String hostName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateMachineRelatedMonitor(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(MachineManageBoVariable.serviceKey,"updateMachineRelatedMonitor",platformArgs);
updateMachineRelatedMonitor(oCntl, hostName);
  }

  protected abstract void updateMachineRelatedMonitor(TServiceCntl oCntl, String hostName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteMachineRelatedMonitor(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(MachineManageBoVariable.serviceKey,"deleteMachineRelatedMonitor",platformArgs);
deleteMachineRelatedMonitor(oCntl, hostName);
  }

  protected abstract void deleteMachineRelatedMonitor(TServiceCntl oCntl, String hostName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected MachineManageBoAdaptor(){
    methodParameterNameMap.put("queryMachineList",new String[]{"platformArgs", "option", "pageIndex", "pageSize"});
    methodParameterNameMap.put("addMachine",new String[]{"platformArgs", "newMachine"});
    methodParameterNameMap.put("updateMachine",new String[]{"platformArgs", "updateMachine"});
    methodParameterNameMap.put("deleteMachine",new String[]{"platformArgs", "hostName"});
    methodParameterNameMap.put("updateMachineRelatedMonitor",new String[]{"platformArgs", "hostName"});
    methodParameterNameMap.put("deleteMachineRelatedMonitor",new String[]{"platformArgs", "hostName"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
