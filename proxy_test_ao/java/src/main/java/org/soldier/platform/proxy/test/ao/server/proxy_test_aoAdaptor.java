package org.soldier.platform.proxy.test.ao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.proxy.test.ao.EchoListResult;
import org.soldier.platform.proxy.test.ao.EchoTypes;
import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_aoVariable;


public abstract class proxy_test_aoAdaptor implements proxy_test_ao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public String testEcho(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String content) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"testEcho",platformArgs);
    return testEcho(oCntl, content);
  }

  protected abstract String testEcho(TServiceCntl oCntl, String content) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<String> testEchoList(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<String> contentList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"testEchoList",platformArgs);
    return testEchoList(oCntl, contentList);
  }

  protected abstract List<String> testEchoList(TServiceCntl oCntl, List<String> contentList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public EchoListResult testEchoListStruct(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<String> contentList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"testEchoListStruct",platformArgs);
    return testEchoListStruct(oCntl, contentList);
  }

  protected abstract EchoListResult testEchoListStruct(TServiceCntl oCntl, List<String> contentList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public EchoTypes echoTypes(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, EchoTypes types, boolean throw_action) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"echoTypes",platformArgs);
    return echoTypes(oCntl, types, throw_action);
  }

  protected abstract EchoTypes echoTypes(TServiceCntl oCntl, EchoTypes types, boolean throw_action) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Set<Long> testEchoSet(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<Long> valueList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"testEchoSet",platformArgs);
    return testEchoSet(oCntl, valueList);
  }

  protected abstract Set<Long> testEchoSet(TServiceCntl oCntl, Set<Long> valueList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public Map<Long,String> testEchoMap(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Map<Long,String> mapValue) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"testEchoMap",platformArgs);
    return testEchoMap(oCntl, mapValue);
  }

  protected abstract Map<Long,String> testEchoMap(TServiceCntl oCntl, Map<Long,String> mapValue) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<EchoTypes> testEchoTypesList(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<EchoTypes> types) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(proxy_test_aoVariable.serviceKey,"testEchoTypesList",platformArgs);
    return testEchoTypesList(oCntl, types);
  }

  protected abstract List<EchoTypes> testEchoTypesList(TServiceCntl oCntl, List<EchoTypes> types) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected proxy_test_aoAdaptor(){
    methodParameterNameMap.put("testEcho",new String[]{"platformArgs", "content"});
    methodParameterNameMap.put("testEchoList",new String[]{"platformArgs", "contentList"});
    methodParameterNameMap.put("testEchoListStruct",new String[]{"platformArgs", "contentList"});
    methodParameterNameMap.put("echoTypes",new String[]{"platformArgs", "types", "throw_action"});
    methodParameterNameMap.put("testEchoSet",new String[]{"platformArgs", "valueList"});
    methodParameterNameMap.put("testEchoMap",new String[]{"platformArgs", "mapValue"});
    methodParameterNameMap.put("testEchoTypesList",new String[]{"platformArgs", "types"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
