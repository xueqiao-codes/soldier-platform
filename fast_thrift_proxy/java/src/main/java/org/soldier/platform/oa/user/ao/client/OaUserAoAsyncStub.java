package org.soldier.platform.oa.user.ao.client;

import org.soldier.platform.oa.user.ao.OaUserAo;
import org.soldier.platform.oa.user.ao.OaUserAoVariable;
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
import java.util.Set;
import org.soldier.platform.oa.user.ao.ECheckResult;
import org.soldier.platform.oa.user.ao.LoginResult;

public class OaUserAoAsyncStub extends BaseStub { 
  public OaUserAoAsyncStub() {
    super(OaUserAoVariable.serviceKey);
  }
  public void send_login(int routeKey, int timeout, String userName, String passwordMd5) throws TException {
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
        create_loginServiceCall(routeKey, timeout, platformArgs, userName, passwordMd5), new TRequestOption());
  }

  public void send_login(int routeKey, int timeout, String userName, String passwordMd5,TRequestOption requestOption) throws TException { 
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
        create_loginServiceCall(routeKey, timeout, platformArgs, userName, passwordMd5), requestOption);
  }

  public long login(int routeKey, int timeout, String userName, String passwordMd5, IMethodCallback<OaUserAo.login_args, OaUserAo.login_result> callback) throws TException{
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
            create_loginServiceCall(routeKey, timeout, platformArgs, userName, passwordMd5), callback);
  }

  public long add_loginCall(AsyncCallRunner runner, int routeKey, int timeout, String userName, String passwordMd5, IMethodCallback<OaUserAo.login_args, OaUserAo.login_result> callback) throws TException{
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
            create_loginServiceCall(routeKey, timeout, platformArgs, userName, passwordMd5), callback);
  }

  protected TServiceCall create_loginServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userName, String passwordMd5){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.login_args request = new OaUserAo.login_args();
    request.setPlatformArgs(platformArgs);
    request.setUserName(userName);
    request.setPasswordMd5(passwordMd5);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("login");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.login_result.class);
    return serviceCall;
  }

  public void send_registerUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, String operationUserName) throws TException {
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
        create_registerUserServiceCall(routeKey, timeout, platformArgs, user, operationUserName), new TRequestOption());
  }

  public void send_registerUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, String operationUserName,TRequestOption requestOption) throws TException { 
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
        create_registerUserServiceCall(routeKey, timeout, platformArgs, user, operationUserName), requestOption);
  }

  public long registerUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, String operationUserName, IMethodCallback<OaUserAo.registerUser_args, OaUserAo.registerUser_result> callback) throws TException{
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
            create_registerUserServiceCall(routeKey, timeout, platformArgs, user, operationUserName), callback);
  }

  public long add_registerUserCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, String operationUserName, IMethodCallback<OaUserAo.registerUser_args, OaUserAo.registerUser_result> callback) throws TException{
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
            create_registerUserServiceCall(routeKey, timeout, platformArgs, user, operationUserName), callback);
  }

  protected TServiceCall create_registerUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.OaUser user, String operationUserName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.registerUser_args request = new OaUserAo.registerUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    request.setOperationUserName(operationUserName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("registerUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.registerUser_result.class);
    return serviceCall;
  }

  public void send_checkSession(int routeKey, int timeout, int userId, String userName, String secretKey) throws TException {
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
        create_checkSessionServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey), new TRequestOption());
  }

  public void send_checkSession(int routeKey, int timeout, int userId, String userName, String secretKey,TRequestOption requestOption) throws TException { 
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
        create_checkSessionServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey), requestOption);
  }

  public long checkSession(int routeKey, int timeout, int userId, String userName, String secretKey, IMethodCallback<OaUserAo.checkSession_args, OaUserAo.checkSession_result> callback) throws TException{
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
            create_checkSessionServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey), callback);
  }

  public long add_checkSessionCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, String userName, String secretKey, IMethodCallback<OaUserAo.checkSession_args, OaUserAo.checkSession_result> callback) throws TException{
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
            create_checkSessionServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey), callback);
  }

  protected TServiceCall create_checkSessionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName, String secretKey){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.checkSession_args request = new OaUserAo.checkSession_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setUserName(userName);
    request.setSecretKey(secretKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("checkSession");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.checkSession_result.class);
    return serviceCall;
  }

  public void send_updateUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user) throws TException {
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
        create_updateUserServiceCall(routeKey, timeout, platformArgs, user), new TRequestOption());
  }

  public void send_updateUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user,TRequestOption requestOption) throws TException { 
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
        create_updateUserServiceCall(routeKey, timeout, platformArgs, user), requestOption);
  }

  public long updateUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, IMethodCallback<OaUserAo.updateUser_args, OaUserAo.updateUser_result> callback) throws TException{
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
            create_updateUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  public long add_updateUserCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, IMethodCallback<OaUserAo.updateUser_args, OaUserAo.updateUser_result> callback) throws TException{
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
            create_updateUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  protected TServiceCall create_updateUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.OaUser user){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.updateUser_args request = new OaUserAo.updateUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.updateUser_result.class);
    return serviceCall;
  }

  public void send_deleteUser(int routeKey, int timeout, int userId, String operationUserName) throws TException {
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
        create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId, operationUserName), new TRequestOption());
  }

  public void send_deleteUser(int routeKey, int timeout, int userId, String operationUserName,TRequestOption requestOption) throws TException { 
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
        create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId, operationUserName), requestOption);
  }

  public long deleteUser(int routeKey, int timeout, int userId, String operationUserName, IMethodCallback<OaUserAo.deleteUser_args, OaUserAo.deleteUser_result> callback) throws TException{
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
            create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId, operationUserName), callback);
  }

  public long add_deleteUserCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, String operationUserName, IMethodCallback<OaUserAo.deleteUser_args, OaUserAo.deleteUser_result> callback) throws TException{
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
            create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId, operationUserName), callback);
  }

  protected TServiceCall create_deleteUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String operationUserName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.deleteUser_args request = new OaUserAo.deleteUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setOperationUserName(operationUserName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.deleteUser_result.class);
    return serviceCall;
  }

  public void send_logout(int routeKey, int timeout, int userId, String userName) throws TException {
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
        create_logoutServiceCall(routeKey, timeout, platformArgs, userId, userName), new TRequestOption());
  }

  public void send_logout(int routeKey, int timeout, int userId, String userName,TRequestOption requestOption) throws TException { 
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
        create_logoutServiceCall(routeKey, timeout, platformArgs, userId, userName), requestOption);
  }

  public long logout(int routeKey, int timeout, int userId, String userName, IMethodCallback<OaUserAo.logout_args, OaUserAo.logout_result> callback) throws TException{
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
            create_logoutServiceCall(routeKey, timeout, platformArgs, userId, userName), callback);
  }

  public long add_logoutCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, String userName, IMethodCallback<OaUserAo.logout_args, OaUserAo.logout_result> callback) throws TException{
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
            create_logoutServiceCall(routeKey, timeout, platformArgs, userId, userName), callback);
  }

  protected TServiceCall create_logoutServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.logout_args request = new OaUserAo.logout_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setUserName(userName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("logout");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.logout_result.class);
    return serviceCall;
  }

  public void send_queryUser(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option) throws TException {
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
        create_queryUserServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_queryUser(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option,TRequestOption requestOption) throws TException { 
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
        create_queryUserServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long queryUser(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, IMethodCallback<OaUserAo.queryUser_args, OaUserAo.queryUser_result> callback) throws TException{
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
            create_queryUserServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_queryUserCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, IMethodCallback<OaUserAo.queryUser_args, OaUserAo.queryUser_result> callback) throws TException{
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
            create_queryUserServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_queryUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.QueryUserOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.queryUser_args request = new OaUserAo.queryUser_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.queryUser_result.class);
    return serviceCall;
  }

  public void send_queryUserByPage(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize) throws TException {
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
        create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_queryUserByPage(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long queryUserByPage(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize, IMethodCallback<OaUserAo.queryUserByPage_args, OaUserAo.queryUserByPage_result> callback) throws TException{
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
            create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_queryUserByPageCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize, IMethodCallback<OaUserAo.queryUserByPage_args, OaUserAo.queryUserByPage_result> callback) throws TException{
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
            create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_queryUserByPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.queryUserByPage_args request = new OaUserAo.queryUserByPage_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUserByPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.queryUserByPage_result.class);
    return serviceCall;
  }

  public void send_checkSessionAndGroups(int routeKey, int timeout, int userId, String userName, String secretKey, Set<String> groups) throws TException {
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
        create_checkSessionAndGroupsServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey, groups), new TRequestOption());
  }

  public void send_checkSessionAndGroups(int routeKey, int timeout, int userId, String userName, String secretKey, Set<String> groups,TRequestOption requestOption) throws TException { 
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
        create_checkSessionAndGroupsServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey, groups), requestOption);
  }

  public long checkSessionAndGroups(int routeKey, int timeout, int userId, String userName, String secretKey, Set<String> groups, IMethodCallback<OaUserAo.checkSessionAndGroups_args, OaUserAo.checkSessionAndGroups_result> callback) throws TException{
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
            create_checkSessionAndGroupsServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey, groups), callback);
  }

  public long add_checkSessionAndGroupsCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, String userName, String secretKey, Set<String> groups, IMethodCallback<OaUserAo.checkSessionAndGroups_args, OaUserAo.checkSessionAndGroups_result> callback) throws TException{
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
            create_checkSessionAndGroupsServiceCall(routeKey, timeout, platformArgs, userId, userName, secretKey, groups), callback);
  }

  protected TServiceCall create_checkSessionAndGroupsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName, String secretKey, Set<String> groups){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(OaUserAoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserAo.checkSessionAndGroups_args request = new OaUserAo.checkSessionAndGroups_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setUserName(userName);
    request.setSecretKey(secretKey);
    request.setGroups(groups);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("checkSessionAndGroups");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserAo.checkSessionAndGroups_result.class);
    return serviceCall;
  }

}
