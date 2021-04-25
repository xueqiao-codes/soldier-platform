package org.soldier.platform.proxy.test.ao.client;

import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_aoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class proxy_test_aoAsyncStub { 
  private String peerAddr;

  public void setPeerAddr(final String ipAddr) { 
    if (ipAddr == null) { 
      peerAddr = null; 
    }
    if (-1l != NetHelper.AddrNet(ipAddr)) {
      peerAddr = ipAddr; 
    }
  }

  public String getPeerAddr() { 
    return peerAddr;
  }

  public void send_testEcho(int routeKey, int timeout, String content) throws TException {
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
    SvrContainer.getInstance().sendRequest(
        create_testEchoServiceCall(routeKey, timeout, platformArgs, content), new TRequestOption());
  }

  public void send_testEcho(int routeKey, int timeout, String content,TRequestOption requestOption) throws TException { 
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
    SvrContainer.getInstance().sendRequest(
        create_testEchoServiceCall(routeKey, timeout, platformArgs, content), requestOption);
  }

  public long testEcho(int routeKey, int timeout, String content, IMethodCallback<proxy_test_ao.testEcho_args, proxy_test_ao.testEcho_result> callback) throws TException{
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
    return SvrContainer.getInstance().sendRequest(
            create_testEchoServiceCall(routeKey, timeout, platformArgs, content), callback);
  }

  public long add_testEchoCall(AsyncCallRunner runner, int routeKey, int timeout, String content, IMethodCallback<proxy_test_ao.testEcho_args, proxy_test_ao.testEcho_result> callback) throws TException{
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
    return runner.addAsyncCall(
            create_testEchoServiceCall(routeKey, timeout, platformArgs, content), callback);
  }

  protected TServiceCall create_testEchoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String content){
    TServiceCall serviceCall = new TServiceCall();
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

}
