package org.soldier.platform.oa.user.dao.client;

import org.soldier.platform.oa.user.dao.OaUserDao;
import org.soldier.platform.oa.user.dao.OaUserDaoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;

public class OaUserDaoAsyncStub { 
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

  public void send_queryUserByPage(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_queryUserByPage(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long queryUserByPage(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize, IMethodCallback<OaUserDao.queryUserByPage_args, OaUserDao.queryUserByPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_queryUserByPageCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize, IMethodCallback<OaUserDao.queryUserByPage_args, OaUserDao.queryUserByPage_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryUserByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_queryUserByPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaUserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserDao.queryUserByPage_args request = new OaUserDao.queryUserByPage_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUserByPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserDao.queryUserByPage_result.class);
    return serviceCall;
  }

  public void send_addUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addUserServiceCall(routeKey, timeout, platformArgs, user), new TRequestOption());
  }

  public void send_addUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addUserServiceCall(routeKey, timeout, platformArgs, user), requestOption);
  }

  public long addUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, IMethodCallback<OaUserDao.addUser_args, OaUserDao.addUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  public long add_addUserCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, IMethodCallback<OaUserDao.addUser_args, OaUserDao.addUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  protected TServiceCall create_addUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.OaUser user){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaUserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserDao.addUser_args request = new OaUserDao.addUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserDao.addUser_result.class);
    return serviceCall;
  }

  public void send_updateUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateUserServiceCall(routeKey, timeout, platformArgs, user), new TRequestOption());
  }

  public void send_updateUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateUserServiceCall(routeKey, timeout, platformArgs, user), requestOption);
  }

  public long updateUser(int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, IMethodCallback<OaUserDao.updateUser_args, OaUserDao.updateUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  public long add_updateUserCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.OaUser user, IMethodCallback<OaUserDao.updateUser_args, OaUserDao.updateUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  protected TServiceCall create_updateUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.OaUser user){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaUserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserDao.updateUser_args request = new OaUserDao.updateUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserDao.updateUser_result.class);
    return serviceCall;
  }

  public void send_deleteUser(int routeKey, int timeout, int userId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId), new TRequestOption());
  }

  public void send_deleteUser(int routeKey, int timeout, int userId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId), requestOption);
  }

  public long deleteUser(int routeKey, int timeout, int userId, IMethodCallback<OaUserDao.deleteUser_args, OaUserDao.deleteUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId), callback);
  }

  public long add_deleteUserCall(AsyncCallRunner runner, int routeKey, int timeout, int userId, IMethodCallback<OaUserDao.deleteUser_args, OaUserDao.deleteUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteUserServiceCall(routeKey, timeout, platformArgs, userId), callback);
  }

  protected TServiceCall create_deleteUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaUserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserDao.deleteUser_args request = new OaUserDao.deleteUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUserId(userId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserDao.deleteUser_result.class);
    return serviceCall;
  }

  public void send_queryUser(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryUserServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_queryUser(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryUserServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long queryUser(int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, IMethodCallback<OaUserDao.queryUser_args, OaUserDao.queryUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryUserServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_queryUserCall(AsyncCallRunner runner, int routeKey, int timeout, org.soldier.platform.oa.user.QueryUserOption option, IMethodCallback<OaUserDao.queryUser_args, OaUserDao.queryUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryUserServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_queryUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.QueryUserOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaUserDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaUserDao.queryUser_args request = new OaUserDao.queryUser_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaUserDao.queryUser_result.class);
    return serviceCall;
  }

}
