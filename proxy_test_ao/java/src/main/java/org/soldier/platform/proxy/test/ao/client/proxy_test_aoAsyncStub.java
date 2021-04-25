package org.soldier.platform.proxy.test.ao.client;

import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_aoVariable;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.soldier.platform.proxy.test.ao.EchoListResult;
import org.soldier.platform.proxy.test.ao.EchoTypes;

public class proxy_test_aoAsyncStub extends BaseStub { 
  public proxy_test_aoAsyncStub() {
    super(proxy_test_aoVariable.serviceKey);
  }
  public void send_testEcho(int routeKey, int timeout, String content) throws TException {
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
        create_testEchoServiceCall(routeKey, timeout, platformArgs, content), new TRequestOption());
  }

  public void send_testEcho(int routeKey, int timeout, String content,TRequestOption requestOption) throws TException { 
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
        create_testEchoServiceCall(routeKey, timeout, platformArgs, content), requestOption);
  }

  public long testEcho(int routeKey, int timeout, String content, IMethodCallback<proxy_test_ao.testEcho_args, proxy_test_ao.testEcho_result> callback) throws TException{
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
            create_testEchoServiceCall(routeKey, timeout, platformArgs, content), callback);
  }

  public long add_testEchoCall(AsyncCallRunner runner, int routeKey, int timeout, String content, IMethodCallback<proxy_test_ao.testEcho_args, proxy_test_ao.testEcho_result> callback) throws TException{
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
            create_testEchoServiceCall(routeKey, timeout, platformArgs, content), callback);
  }

  protected TServiceCall create_testEchoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String content){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.testEcho_args request = new proxy_test_ao.testEcho_args();
    request.setPlatformArgs(platformArgs);
    request.setContent(content);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("testEcho");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.testEcho_result.class);
    return serviceCall;
  }

  public void send_testEchoList(int routeKey, int timeout, List<String> contentList) throws TException {
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
        create_testEchoListServiceCall(routeKey, timeout, platformArgs, contentList), new TRequestOption());
  }

  public void send_testEchoList(int routeKey, int timeout, List<String> contentList,TRequestOption requestOption) throws TException { 
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
        create_testEchoListServiceCall(routeKey, timeout, platformArgs, contentList), requestOption);
  }

  public long testEchoList(int routeKey, int timeout, List<String> contentList, IMethodCallback<proxy_test_ao.testEchoList_args, proxy_test_ao.testEchoList_result> callback) throws TException{
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
            create_testEchoListServiceCall(routeKey, timeout, platformArgs, contentList), callback);
  }

  public long add_testEchoListCall(AsyncCallRunner runner, int routeKey, int timeout, List<String> contentList, IMethodCallback<proxy_test_ao.testEchoList_args, proxy_test_ao.testEchoList_result> callback) throws TException{
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
            create_testEchoListServiceCall(routeKey, timeout, platformArgs, contentList), callback);
  }

  protected TServiceCall create_testEchoListServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<String> contentList){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.testEchoList_args request = new proxy_test_ao.testEchoList_args();
    request.setPlatformArgs(platformArgs);
    request.setContentList(contentList);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("testEchoList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.testEchoList_result.class);
    return serviceCall;
  }

  public void send_testEchoListStruct(int routeKey, int timeout, List<String> contentList) throws TException {
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
        create_testEchoListStructServiceCall(routeKey, timeout, platformArgs, contentList), new TRequestOption());
  }

  public void send_testEchoListStruct(int routeKey, int timeout, List<String> contentList,TRequestOption requestOption) throws TException { 
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
        create_testEchoListStructServiceCall(routeKey, timeout, platformArgs, contentList), requestOption);
  }

  public long testEchoListStruct(int routeKey, int timeout, List<String> contentList, IMethodCallback<proxy_test_ao.testEchoListStruct_args, proxy_test_ao.testEchoListStruct_result> callback) throws TException{
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
            create_testEchoListStructServiceCall(routeKey, timeout, platformArgs, contentList), callback);
  }

  public long add_testEchoListStructCall(AsyncCallRunner runner, int routeKey, int timeout, List<String> contentList, IMethodCallback<proxy_test_ao.testEchoListStruct_args, proxy_test_ao.testEchoListStruct_result> callback) throws TException{
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
            create_testEchoListStructServiceCall(routeKey, timeout, platformArgs, contentList), callback);
  }

  protected TServiceCall create_testEchoListStructServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<String> contentList){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.testEchoListStruct_args request = new proxy_test_ao.testEchoListStruct_args();
    request.setPlatformArgs(platformArgs);
    request.setContentList(contentList);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("testEchoListStruct");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.testEchoListStruct_result.class);
    return serviceCall;
  }

  public void send_echoTypes(int routeKey, int timeout, EchoTypes types, boolean throw_action) throws TException {
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
        create_echoTypesServiceCall(routeKey, timeout, platformArgs, types, throw_action), new TRequestOption());
  }

  public void send_echoTypes(int routeKey, int timeout, EchoTypes types, boolean throw_action,TRequestOption requestOption) throws TException { 
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
        create_echoTypesServiceCall(routeKey, timeout, platformArgs, types, throw_action), requestOption);
  }

  public long echoTypes(int routeKey, int timeout, EchoTypes types, boolean throw_action, IMethodCallback<proxy_test_ao.echoTypes_args, proxy_test_ao.echoTypes_result> callback) throws TException{
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
            create_echoTypesServiceCall(routeKey, timeout, platformArgs, types, throw_action), callback);
  }

  public long add_echoTypesCall(AsyncCallRunner runner, int routeKey, int timeout, EchoTypes types, boolean throw_action, IMethodCallback<proxy_test_ao.echoTypes_args, proxy_test_ao.echoTypes_result> callback) throws TException{
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
            create_echoTypesServiceCall(routeKey, timeout, platformArgs, types, throw_action), callback);
  }

  protected TServiceCall create_echoTypesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, EchoTypes types, boolean throw_action){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.echoTypes_args request = new proxy_test_ao.echoTypes_args();
    request.setPlatformArgs(platformArgs);
    request.setTypes(types);
    request.setThrow_action(throw_action);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("echoTypes");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.echoTypes_result.class);
    return serviceCall;
  }

  public void send_testEchoSet(int routeKey, int timeout, Set<Long> valueList) throws TException {
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
        create_testEchoSetServiceCall(routeKey, timeout, platformArgs, valueList), new TRequestOption());
  }

  public void send_testEchoSet(int routeKey, int timeout, Set<Long> valueList,TRequestOption requestOption) throws TException { 
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
        create_testEchoSetServiceCall(routeKey, timeout, platformArgs, valueList), requestOption);
  }

  public long testEchoSet(int routeKey, int timeout, Set<Long> valueList, IMethodCallback<proxy_test_ao.testEchoSet_args, proxy_test_ao.testEchoSet_result> callback) throws TException{
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
            create_testEchoSetServiceCall(routeKey, timeout, platformArgs, valueList), callback);
  }

  public long add_testEchoSetCall(AsyncCallRunner runner, int routeKey, int timeout, Set<Long> valueList, IMethodCallback<proxy_test_ao.testEchoSet_args, proxy_test_ao.testEchoSet_result> callback) throws TException{
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
            create_testEchoSetServiceCall(routeKey, timeout, platformArgs, valueList), callback);
  }

  protected TServiceCall create_testEchoSetServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Set<Long> valueList){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.testEchoSet_args request = new proxy_test_ao.testEchoSet_args();
    request.setPlatformArgs(platformArgs);
    request.setValueList(valueList);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("testEchoSet");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.testEchoSet_result.class);
    return serviceCall;
  }

  public void send_testEchoMap(int routeKey, int timeout, Map<Long,String> mapValue) throws TException {
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
        create_testEchoMapServiceCall(routeKey, timeout, platformArgs, mapValue), new TRequestOption());
  }

  public void send_testEchoMap(int routeKey, int timeout, Map<Long,String> mapValue,TRequestOption requestOption) throws TException { 
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
        create_testEchoMapServiceCall(routeKey, timeout, platformArgs, mapValue), requestOption);
  }

  public long testEchoMap(int routeKey, int timeout, Map<Long,String> mapValue, IMethodCallback<proxy_test_ao.testEchoMap_args, proxy_test_ao.testEchoMap_result> callback) throws TException{
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
            create_testEchoMapServiceCall(routeKey, timeout, platformArgs, mapValue), callback);
  }

  public long add_testEchoMapCall(AsyncCallRunner runner, int routeKey, int timeout, Map<Long,String> mapValue, IMethodCallback<proxy_test_ao.testEchoMap_args, proxy_test_ao.testEchoMap_result> callback) throws TException{
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
            create_testEchoMapServiceCall(routeKey, timeout, platformArgs, mapValue), callback);
  }

  protected TServiceCall create_testEchoMapServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, Map<Long,String> mapValue){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.testEchoMap_args request = new proxy_test_ao.testEchoMap_args();
    request.setPlatformArgs(platformArgs);
    request.setMapValue(mapValue);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("testEchoMap");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.testEchoMap_result.class);
    return serviceCall;
  }

  public void send_testEchoTypesList(int routeKey, int timeout, List<EchoTypes> types) throws TException {
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
        create_testEchoTypesListServiceCall(routeKey, timeout, platformArgs, types), new TRequestOption());
  }

  public void send_testEchoTypesList(int routeKey, int timeout, List<EchoTypes> types,TRequestOption requestOption) throws TException { 
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
        create_testEchoTypesListServiceCall(routeKey, timeout, platformArgs, types), requestOption);
  }

  public long testEchoTypesList(int routeKey, int timeout, List<EchoTypes> types, IMethodCallback<proxy_test_ao.testEchoTypesList_args, proxy_test_ao.testEchoTypesList_result> callback) throws TException{
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
            create_testEchoTypesListServiceCall(routeKey, timeout, platformArgs, types), callback);
  }

  public long add_testEchoTypesListCall(AsyncCallRunner runner, int routeKey, int timeout, List<EchoTypes> types, IMethodCallback<proxy_test_ao.testEchoTypesList_args, proxy_test_ao.testEchoTypesList_result> callback) throws TException{
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
            create_testEchoTypesListServiceCall(routeKey, timeout, platformArgs, types), callback);
  }

  protected TServiceCall create_testEchoTypesListServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, List<EchoTypes> types){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(proxy_test_aoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    proxy_test_ao.testEchoTypesList_args request = new proxy_test_ao.testEchoTypesList_args();
    request.setPlatformArgs(platformArgs);
    request.setTypes(types);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("testEchoTypesList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(proxy_test_ao.testEchoTypesList_result.class);
    return serviceCall;
  }

}
