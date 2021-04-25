package org.soldier.platform.errorcode.client;

import org.soldier.platform.errorcode.ErrorCodeAgent;
import org.soldier.platform.errorcode.ErrorCodeAgentVariable;
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

public class ErrorCodeAgentAsyncStub extends BaseStub { 
  public ErrorCodeAgentAsyncStub() {
    super(ErrorCodeAgentVariable.serviceKey);
  }
  public void send_getErrorCodeItem(int routeKey, int timeout, String domain, int errorCode) throws TException {
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
        create_getErrorCodeItemServiceCall(routeKey, timeout, platformArgs, domain, errorCode), new TRequestOption());
  }

  public void send_getErrorCodeItem(int routeKey, int timeout, String domain, int errorCode,TRequestOption requestOption) throws TException { 
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
        create_getErrorCodeItemServiceCall(routeKey, timeout, platformArgs, domain, errorCode), requestOption);
  }

  public long getErrorCodeItem(int routeKey, int timeout, String domain, int errorCode, IMethodCallback<ErrorCodeAgent.getErrorCodeItem_args, ErrorCodeAgent.getErrorCodeItem_result> callback) throws TException{
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
            create_getErrorCodeItemServiceCall(routeKey, timeout, platformArgs, domain, errorCode), callback);
  }

  public long add_getErrorCodeItemCall(AsyncCallRunner runner, int routeKey, int timeout, String domain, int errorCode, IMethodCallback<ErrorCodeAgent.getErrorCodeItem_args, ErrorCodeAgent.getErrorCodeItem_result> callback) throws TException{
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
            create_getErrorCodeItemServiceCall(routeKey, timeout, platformArgs, domain, errorCode), callback);
  }

  protected TServiceCall create_getErrorCodeItemServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String domain, int errorCode){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(ErrorCodeAgentVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    ErrorCodeAgent.getErrorCodeItem_args request = new ErrorCodeAgent.getErrorCodeItem_args();
    request.setPlatformArgs(platformArgs);
    request.setDomain(domain);
    request.setErrorCode(errorCode);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getErrorCodeItem");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(ErrorCodeAgent.getErrorCodeItem_result.class);
    return serviceCall;
  }

}
