package com.car.user.dao.client;

import com.car.user.dao.UserDao;
import com.car.user.dao.UserDaoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import com.car.user.dao.UserInfo;

public class UserDaoAsyncStub { 
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

  public void sendRegister(int routeKey, int timeout, String userEmail, String userPasswd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createRegisterServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), new TRequestOption());
  }

  public void sendRegister(int routeKey, int timeout, String userEmail, String userPasswd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createRegisterServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), requestOption);
  }

  public long Register(int routeKey, int timeout, String userEmail, String userPasswd, IMethodCallback<UserDao.Register_args, UserDao.Register_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            createRegisterServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), callback);
  }

  public long addRegisterCall(AsyncCallRunner runner, int routeKey, int timeout, String userEmail, String userPasswd, IMethodCallback<UserDao.Register_args, UserDao.Register_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            createRegisterServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), callback);
  }

  protected TServiceCall createRegisterServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userEmail, String userPasswd){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(UserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserDao.Register_args request = new UserDao.Register_args();
    request.setPlatformArgs(platformArgs);
    request.setUserEmail(userEmail);
    request.setUserPasswd(userPasswd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("Register");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserDao.Register_result.class);
    return serviceCall;
  }

  public void sendCheckLogIn(int routeKey, int timeout, String userEmail, String userPasswd) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createCheckLogInServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), new TRequestOption());
  }

  public void sendCheckLogIn(int routeKey, int timeout, String userEmail, String userPasswd,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createCheckLogInServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), requestOption);
  }

  public long CheckLogIn(int routeKey, int timeout, String userEmail, String userPasswd, IMethodCallback<UserDao.CheckLogIn_args, UserDao.CheckLogIn_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            createCheckLogInServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), callback);
  }

  public long addCheckLogInCall(AsyncCallRunner runner, int routeKey, int timeout, String userEmail, String userPasswd, IMethodCallback<UserDao.CheckLogIn_args, UserDao.CheckLogIn_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            createCheckLogInServiceCall(routeKey, timeout, platformArgs, userEmail, userPasswd), callback);
  }

  protected TServiceCall createCheckLogInServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userEmail, String userPasswd){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(UserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserDao.CheckLogIn_args request = new UserDao.CheckLogIn_args();
    request.setPlatformArgs(platformArgs);
    request.setUserEmail(userEmail);
    request.setUserPasswd(userPasswd);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("CheckLogIn");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserDao.CheckLogIn_result.class);
    return serviceCall;
  }

  public void sendGetCollectUserUid(int routeKey, int timeout, int userSource, String userSourceId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createGetCollectUserUidServiceCall(routeKey, timeout, platformArgs, userSource, userSourceId), new TRequestOption());
  }

  public void sendGetCollectUserUid(int routeKey, int timeout, int userSource, String userSourceId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createGetCollectUserUidServiceCall(routeKey, timeout, platformArgs, userSource, userSourceId), requestOption);
  }

  public long GetCollectUserUid(int routeKey, int timeout, int userSource, String userSourceId, IMethodCallback<UserDao.GetCollectUserUid_args, UserDao.GetCollectUserUid_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            createGetCollectUserUidServiceCall(routeKey, timeout, platformArgs, userSource, userSourceId), callback);
  }

  public long addGetCollectUserUidCall(AsyncCallRunner runner, int routeKey, int timeout, int userSource, String userSourceId, IMethodCallback<UserDao.GetCollectUserUid_args, UserDao.GetCollectUserUid_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            createGetCollectUserUidServiceCall(routeKey, timeout, platformArgs, userSource, userSourceId), callback);
  }

  protected TServiceCall createGetCollectUserUidServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userSource, String userSourceId){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(UserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserDao.GetCollectUserUid_args request = new UserDao.GetCollectUserUid_args();
    request.setPlatformArgs(platformArgs);
    request.setUserSource(userSource);
    request.setUserSourceId(userSourceId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("GetCollectUserUid");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserDao.GetCollectUserUid_result.class);
    return serviceCall;
  }

  public void sendgetUserInfo(int routeKey, int timeout, long userId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        creategetUserInfoServiceCall(routeKey, timeout, platformArgs, userId), new TRequestOption());
  }

  public void sendgetUserInfo(int routeKey, int timeout, long userId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        creategetUserInfoServiceCall(routeKey, timeout, platformArgs, userId), requestOption);
  }

  public long getUserInfo(int routeKey, int timeout, long userId, IMethodCallback<UserDao.getUserInfo_args, UserDao.getUserInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            creategetUserInfoServiceCall(routeKey, timeout, platformArgs, userId), callback);
  }

  public long addgetUserInfoCall(AsyncCallRunner runner, int routeKey, int timeout, long userId, IMethodCallback<UserDao.getUserInfo_args, UserDao.getUserInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            creategetUserInfoServiceCall(routeKey, timeout, platformArgs, userId), callback);
  }

  protected TServiceCall creategetUserInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long userId){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(UserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserDao.getUserInfo_args request = new UserDao.getUserInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getUserInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserDao.getUserInfo_result.class);
    return serviceCall;
  }

  public void sendsetUserInfo(int routeKey, int timeout, UserInfo user) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createsetUserInfoServiceCall(routeKey, timeout, platformArgs, user), new TRequestOption());
  }

  public void sendsetUserInfo(int routeKey, int timeout, UserInfo user,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createsetUserInfoServiceCall(routeKey, timeout, platformArgs, user), requestOption);
  }

  public long setUserInfo(int routeKey, int timeout, UserInfo user, IMethodCallback<UserDao.setUserInfo_args, UserDao.setUserInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            createsetUserInfoServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  public long addsetUserInfoCall(AsyncCallRunner runner, int routeKey, int timeout, UserInfo user, IMethodCallback<UserDao.setUserInfo_args, UserDao.setUserInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            createsetUserInfoServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  protected TServiceCall createsetUserInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, UserInfo user){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(UserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserDao.setUserInfo_args request = new UserDao.setUserInfo_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("setUserInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserDao.setUserInfo_result.class);
    return serviceCall;
  }

  public void sendAddUserRpAndScore(int routeKey, int timeout, int userId, int rpValue, int userScore) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createAddUserRpAndScoreServiceCall(routeKey, timeout, platformArgs, userId, rpValue, userScore), new TRequestOption());
  }

  public void sendAddUserRpAndScore(int routeKey, int timeout, int userId, int rpValue, int userScore,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createAddUserRpAndScoreServiceCall(routeKey, timeout, platformArgs, userId, rpValue, userScore), requestOption);
  }

  public long AddUserRpAndScore(int routeKey, int timeout, int userId, int rpValue, int userScore, IMethodCallback<UserDao.AddUserRpAndScore_args, UserDao.AddUserRpAndScore_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            createAddUserRpAndScoreServiceCall(routeKey, timeout, platformArgs, userId, rpValue, userScore), callback);
  }

  public long addAddUserRpAndScoreCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, int rpValue, int userScore, IMethodCallback<UserDao.AddUserRpAndScore_args, UserDao.AddUserRpAndScore_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            createAddUserRpAndScoreServiceCall(routeKey, timeout, platformArgs, userId, rpValue, userScore), callback);
  }

  protected TServiceCall createAddUserRpAndScoreServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, int rpValue, int userScore){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(UserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    UserDao.AddUserRpAndScore_args request = new UserDao.AddUserRpAndScore_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    request.setRpValue(rpValue);
    request.setUserScore(userScore);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("AddUserRpAndScore");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(UserDao.AddUserRpAndScore_result.class);
    return serviceCall;
  }

}
