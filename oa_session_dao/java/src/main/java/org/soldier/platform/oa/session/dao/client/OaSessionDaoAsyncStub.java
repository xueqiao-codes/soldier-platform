package org.soldier.platform.oa.session.dao.client;

import org.soldier.platform.oa.session.dao.OaSessionDao;
import org.soldier.platform.oa.session.dao.OaSessionDaoVariable;
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
import org.soldier.platform.oa.session.dao.TSession;

public class OaSessionDaoAsyncStub extends BaseStub { 
  public OaSessionDaoAsyncStub() {
    super(OaSessionDaoVariable.serviceKey);
  }
  public void send_updateSession(int routeKey, int timeout, TSession session) throws TException {
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
        create_updateSessionServiceCall(routeKey, timeout, platformArgs, session), new TRequestOption());
  }

  public void send_updateSession(int routeKey, int timeout, TSession session,TRequestOption requestOption) throws TException { 
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
        create_updateSessionServiceCall(routeKey, timeout, platformArgs, session), requestOption);
  }

  public long updateSession(int routeKey, int timeout, TSession session, IMethodCallback<OaSessionDao.updateSession_args, OaSessionDao.updateSession_result> callback) throws TException{
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
            create_updateSessionServiceCall(routeKey, timeout, platformArgs, session), callback);
  }

  public long add_updateSessionCall(AsyncCallRunner runner, int routeKey, int timeout, TSession session, IMethodCallback<OaSessionDao.updateSession_args, OaSessionDao.updateSession_result> callback) throws TException{
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
            create_updateSessionServiceCall(routeKey, timeout, platformArgs, session), callback);
  }

  protected TServiceCall create_updateSessionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSession session){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaSessionDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaSessionDao.updateSession_args request = new OaSessionDao.updateSession_args();
    request.setPlatformArgs(platformArgs);
    request.setSession(session);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSession");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaSessionDao.updateSession_result.class);
    return serviceCall;
  }

  public void send_getSession(int routeKey, int timeout, int userId, String userName) throws TException {
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
        create_getSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), new TRequestOption());
  }

  public void send_getSession(int routeKey, int timeout, int userId, String userName,TRequestOption requestOption) throws TException { 
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
        create_getSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), requestOption);
  }

  public long getSession(int routeKey, int timeout, int userId, String userName, IMethodCallback<OaSessionDao.getSession_args, OaSessionDao.getSession_result> callback) throws TException{
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
            create_getSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), callback);
  }

  public long add_getSessionCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, String userName, IMethodCallback<OaSessionDao.getSession_args, OaSessionDao.getSession_result> callback) throws TException{
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
            create_getSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), callback);
  }

  protected TServiceCall create_getSessionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaSessionDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaSessionDao.getSession_args request = new OaSessionDao.getSession_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setUserName(userName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSession");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaSessionDao.getSession_result.class);
    return serviceCall;
  }

  public void send_deleteSession(int routeKey, int timeout, int userId, String userName) throws TException {
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
        create_deleteSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), new TRequestOption());
  }

  public void send_deleteSession(int routeKey, int timeout, int userId, String userName,TRequestOption requestOption) throws TException { 
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
        create_deleteSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), requestOption);
  }

  public long deleteSession(int routeKey, int timeout, int userId, String userName, IMethodCallback<OaSessionDao.deleteSession_args, OaSessionDao.deleteSession_result> callback) throws TException{
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
            create_deleteSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), callback);
  }

  public long add_deleteSessionCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, String userName, IMethodCallback<OaSessionDao.deleteSession_args, OaSessionDao.deleteSession_result> callback) throws TException{
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
            create_deleteSessionServiceCall(routeKey, timeout, platformArgs, userId, userName), callback);
  }

  protected TServiceCall create_deleteSessionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaSessionDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaSessionDao.deleteSession_args request = new OaSessionDao.deleteSession_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setUserName(userName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSession");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaSessionDao.deleteSession_result.class);
    return serviceCall;
  }

}
