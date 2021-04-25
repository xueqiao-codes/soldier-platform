package org.soldier.platform.proxy.test.ao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.soldier.platform.proxy.test.ao.EchoListResult;
import org.soldier.platform.proxy.test.ao.EchoTypes;
import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_aoVariable;

public class proxy_test_aoStub extends BaseStub {

  public proxy_test_aoStub() {
    super(proxy_test_aoVariable.serviceKey);
  }

  public String  testEcho(String content) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEcho(content, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public String  testEcho(String content,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("testEcho").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<String>(){
    @Override
    public String call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).testEcho(platformArgs, content);
      }
    }, invokeInfo);
  }

  public String  testEcho(int routeKey, int timeout,String content)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEcho(content, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<String>  testEchoList(List<String> contentList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoList(contentList, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<String>  testEchoList(List<String> contentList,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("testEchoList").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<String>>(){
    @Override
    public List<String> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).testEchoList(platformArgs, contentList);
      }
    }, invokeInfo);
  }

  public List<String>  testEchoList(int routeKey, int timeout,List<String> contentList)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoList(contentList, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public EchoListResult  testEchoListStruct(List<String> contentList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoListStruct(contentList, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public EchoListResult  testEchoListStruct(List<String> contentList,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("testEchoListStruct").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<EchoListResult>(){
    @Override
    public EchoListResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).testEchoListStruct(platformArgs, contentList);
      }
    }, invokeInfo);
  }

  public EchoListResult  testEchoListStruct(int routeKey, int timeout,List<String> contentList)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoListStruct(contentList, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public EchoTypes  echoTypes(EchoTypes types, boolean throw_action) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return echoTypes(types, throw_action, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public EchoTypes  echoTypes(EchoTypes types, boolean throw_action,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("echoTypes").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<EchoTypes>(){
    @Override
    public EchoTypes call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).echoTypes(platformArgs, types, throw_action);
      }
    }, invokeInfo);
  }

  public EchoTypes  echoTypes(int routeKey, int timeout,EchoTypes types, boolean throw_action)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return echoTypes(types, throw_action, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Set<Long>  testEchoSet(Set<Long> valueList) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoSet(valueList, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Set<Long>  testEchoSet(Set<Long> valueList,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("testEchoSet").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Set<Long>>(){
    @Override
    public Set<Long> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).testEchoSet(platformArgs, valueList);
      }
    }, invokeInfo);
  }

  public Set<Long>  testEchoSet(int routeKey, int timeout,Set<Long> valueList)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoSet(valueList, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,String>  testEchoMap(Map<Long,String> mapValue) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoMap(mapValue, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public Map<Long,String>  testEchoMap(Map<Long,String> mapValue,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("testEchoMap").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Map<Long,String>>(){
    @Override
    public Map<Long,String> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).testEchoMap(platformArgs, mapValue);
      }
    }, invokeInfo);
  }

  public Map<Long,String>  testEchoMap(int routeKey, int timeout,Map<Long,String> mapValue)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoMap(mapValue, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<EchoTypes>  testEchoTypesList(List<EchoTypes> types) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoTypesList(types, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<EchoTypes>  testEchoTypesList(List<EchoTypes> types,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("testEchoTypesList").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<EchoTypes>>(){
    @Override
    public List<EchoTypes> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new proxy_test_ao.Client(protocol).testEchoTypesList(platformArgs, types);
      }
    }, invokeInfo);
  }

  public List<EchoTypes>  testEchoTypesList(int routeKey, int timeout,List<EchoTypes> types)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return testEchoTypesList(types, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
