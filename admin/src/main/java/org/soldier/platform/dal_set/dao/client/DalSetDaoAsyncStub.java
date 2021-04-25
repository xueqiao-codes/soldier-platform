package org.soldier.platform.dal_set.dao.client;

import org.soldier.platform.dal_set.dao.DalSetDao;
import org.soldier.platform.dal_set.dao.DalSetDaoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;

public class DalSetDaoAsyncStub { 
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

  public void send_queryDalSetHosts(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetHostOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetHostsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryDalSetHosts(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetHostOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetHostsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryDalSetHosts(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetHostOption option, IMethodCallback<DalSetDao.queryDalSetHosts_args, DalSetDao.queryDalSetHosts_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryDalSetHostsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryDalSetHostsCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetHostOption option, IMethodCallback<DalSetDao.queryDalSetHosts_args, DalSetDao.queryDalSetHosts_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryDalSetHostsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryDalSetHostsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetHostOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryDalSetHosts_args request = new DalSetDao.queryDalSetHosts_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryDalSetHosts");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryDalSetHosts_result.class);
    return serviceCall;
  }

  public void send_addDalSetHost(int routeKey, int timeout, DalSetHost host) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetHostServiceCall(routeKey, timeout, platformArgs, host), new TRequestOption());
  }

  public void send_addDalSetHost(int routeKey, int timeout, DalSetHost host,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetHostServiceCall(routeKey, timeout, platformArgs, host), requestOption);
  }

  public long addDalSetHost(int routeKey, int timeout, DalSetHost host, IMethodCallback<DalSetDao.addDalSetHost_args, DalSetDao.addDalSetHost_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addDalSetHostServiceCall(routeKey, timeout, platformArgs, host), callback);
  }

  public long add_addDalSetHostCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetHost host, IMethodCallback<DalSetDao.addDalSetHost_args, DalSetDao.addDalSetHost_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addDalSetHostServiceCall(routeKey, timeout, platformArgs, host), callback);
  }

  protected TServiceCall create_addDalSetHostServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetHost host){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addDalSetHost_args request = new DalSetDao.addDalSetHost_args();
    request.setPlatformArgs(platformArgs);
    request.setHost(host);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addDalSetHost");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addDalSetHost_result.class);
    return serviceCall;
  }

  public void send_updateDalSetHost(int routeKey, int timeout, DalSetHost host) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetHostServiceCall(routeKey, timeout, platformArgs, host), new TRequestOption());
  }

  public void send_updateDalSetHost(int routeKey, int timeout, DalSetHost host,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetHostServiceCall(routeKey, timeout, platformArgs, host), requestOption);
  }

  public long updateDalSetHost(int routeKey, int timeout, DalSetHost host, IMethodCallback<DalSetDao.updateDalSetHost_args, DalSetDao.updateDalSetHost_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateDalSetHostServiceCall(routeKey, timeout, platformArgs, host), callback);
  }

  public long add_updateDalSetHostCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetHost host, IMethodCallback<DalSetDao.updateDalSetHost_args, DalSetDao.updateDalSetHost_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateDalSetHostServiceCall(routeKey, timeout, platformArgs, host), callback);
  }

  protected TServiceCall create_updateDalSetHostServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetHost host){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateDalSetHost_args request = new DalSetDao.updateDalSetHost_args();
    request.setPlatformArgs(platformArgs);
    request.setHost(host);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDalSetHost");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateDalSetHost_result.class);
    return serviceCall;
  }

  public void send_deleteDalSetHost(int routeKey, int timeout, String hostKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetHostServiceCall(routeKey, timeout, platformArgs, hostKey), new TRequestOption());
  }

  public void send_deleteDalSetHost(int routeKey, int timeout, String hostKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetHostServiceCall(routeKey, timeout, platformArgs, hostKey), requestOption);
  }

  public long deleteDalSetHost(int routeKey, int timeout, String hostKey, IMethodCallback<DalSetDao.deleteDalSetHost_args, DalSetDao.deleteDalSetHost_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteDalSetHostServiceCall(routeKey, timeout, platformArgs, hostKey), callback);
  }

  public long add_deleteDalSetHostCall(AsyncCallRunner runner, int routeKey, int timeout, String hostKey, IMethodCallback<DalSetDao.deleteDalSetHost_args, DalSetDao.deleteDalSetHost_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteDalSetHostServiceCall(routeKey, timeout, platformArgs, hostKey), callback);
  }

  protected TServiceCall create_deleteDalSetHostServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostKey){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteDalSetHost_args request = new DalSetDao.deleteDalSetHost_args();
    request.setPlatformArgs(platformArgs);
    request.setHostKey(hostKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteDalSetHost");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteDalSetHost_result.class);
    return serviceCall;
  }

  public void send_queryDalSetUsers(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetUserOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetUsersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryDalSetUsers(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetUserOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetUsersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryDalSetUsers(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetUserOption option, IMethodCallback<DalSetDao.queryDalSetUsers_args, DalSetDao.queryDalSetUsers_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryDalSetUsersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryDalSetUsersCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetUserOption option, IMethodCallback<DalSetDao.queryDalSetUsers_args, DalSetDao.queryDalSetUsers_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryDalSetUsersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryDalSetUsersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetUserOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryDalSetUsers_args request = new DalSetDao.queryDalSetUsers_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryDalSetUsers");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryDalSetUsers_result.class);
    return serviceCall;
  }

  public void send_addDalSetUser(int routeKey, int timeout, DalSetUser user) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetUserServiceCall(routeKey, timeout, platformArgs, user), new TRequestOption());
  }

  public void send_addDalSetUser(int routeKey, int timeout, DalSetUser user,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetUserServiceCall(routeKey, timeout, platformArgs, user), requestOption);
  }

  public long addDalSetUser(int routeKey, int timeout, DalSetUser user, IMethodCallback<DalSetDao.addDalSetUser_args, DalSetDao.addDalSetUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addDalSetUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  public long add_addDalSetUserCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetUser user, IMethodCallback<DalSetDao.addDalSetUser_args, DalSetDao.addDalSetUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addDalSetUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  protected TServiceCall create_addDalSetUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetUser user){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addDalSetUser_args request = new DalSetDao.addDalSetUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addDalSetUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addDalSetUser_result.class);
    return serviceCall;
  }

  public void send_updateDalSetUser(int routeKey, int timeout, DalSetUser user) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetUserServiceCall(routeKey, timeout, platformArgs, user), new TRequestOption());
  }

  public void send_updateDalSetUser(int routeKey, int timeout, DalSetUser user,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetUserServiceCall(routeKey, timeout, platformArgs, user), requestOption);
  }

  public long updateDalSetUser(int routeKey, int timeout, DalSetUser user, IMethodCallback<DalSetDao.updateDalSetUser_args, DalSetDao.updateDalSetUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateDalSetUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  public long add_updateDalSetUserCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetUser user, IMethodCallback<DalSetDao.updateDalSetUser_args, DalSetDao.updateDalSetUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateDalSetUserServiceCall(routeKey, timeout, platformArgs, user), callback);
  }

  protected TServiceCall create_updateDalSetUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetUser user){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateDalSetUser_args request = new DalSetDao.updateDalSetUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUser(user);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDalSetUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateDalSetUser_result.class);
    return serviceCall;
  }

  public void send_deleteDalSetUser(int routeKey, int timeout, String userKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetUserServiceCall(routeKey, timeout, platformArgs, userKey), new TRequestOption());
  }

  public void send_deleteDalSetUser(int routeKey, int timeout, String userKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetUserServiceCall(routeKey, timeout, platformArgs, userKey), requestOption);
  }

  public long deleteDalSetUser(int routeKey, int timeout, String userKey, IMethodCallback<DalSetDao.deleteDalSetUser_args, DalSetDao.deleteDalSetUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteDalSetUserServiceCall(routeKey, timeout, platformArgs, userKey), callback);
  }

  public long add_deleteDalSetUserCall(AsyncCallRunner runner, int routeKey, int timeout, String userKey, IMethodCallback<DalSetDao.deleteDalSetUser_args, DalSetDao.deleteDalSetUser_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteDalSetUserServiceCall(routeKey, timeout, platformArgs, userKey), callback);
  }

  protected TServiceCall create_deleteDalSetUserServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userKey){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteDalSetUser_args request = new DalSetDao.deleteDalSetUser_args();
    request.setPlatformArgs(platformArgs);
    request.setUserKey(userKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteDalSetUser");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteDalSetUser_result.class);
    return serviceCall;
  }

  public void send_queryDalSetTables(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetTableOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetTablesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryDalSetTables(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetTableOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetTablesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryDalSetTables(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetTableOption option, IMethodCallback<DalSetDao.queryDalSetTables_args, DalSetDao.queryDalSetTables_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryDalSetTablesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryDalSetTablesCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetTableOption option, IMethodCallback<DalSetDao.queryDalSetTables_args, DalSetDao.queryDalSetTables_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryDalSetTablesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryDalSetTablesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetTableOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryDalSetTables_args request = new DalSetDao.queryDalSetTables_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryDalSetTables");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryDalSetTables_result.class);
    return serviceCall;
  }

  public void send_addDalSetTable(int routeKey, int timeout, DalSetTable table) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetTableServiceCall(routeKey, timeout, platformArgs, table), new TRequestOption());
  }

  public void send_addDalSetTable(int routeKey, int timeout, DalSetTable table,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetTableServiceCall(routeKey, timeout, platformArgs, table), requestOption);
  }

  public long addDalSetTable(int routeKey, int timeout, DalSetTable table, IMethodCallback<DalSetDao.addDalSetTable_args, DalSetDao.addDalSetTable_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addDalSetTableServiceCall(routeKey, timeout, platformArgs, table), callback);
  }

  public long add_addDalSetTableCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetTable table, IMethodCallback<DalSetDao.addDalSetTable_args, DalSetDao.addDalSetTable_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addDalSetTableServiceCall(routeKey, timeout, platformArgs, table), callback);
  }

  protected TServiceCall create_addDalSetTableServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetTable table){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addDalSetTable_args request = new DalSetDao.addDalSetTable_args();
    request.setPlatformArgs(platformArgs);
    request.setTable(table);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addDalSetTable");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addDalSetTable_result.class);
    return serviceCall;
  }

  public void send_updateDalSetTable(int routeKey, int timeout, DalSetTable table) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetTableServiceCall(routeKey, timeout, platformArgs, table), new TRequestOption());
  }

  public void send_updateDalSetTable(int routeKey, int timeout, DalSetTable table,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetTableServiceCall(routeKey, timeout, platformArgs, table), requestOption);
  }

  public long updateDalSetTable(int routeKey, int timeout, DalSetTable table, IMethodCallback<DalSetDao.updateDalSetTable_args, DalSetDao.updateDalSetTable_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateDalSetTableServiceCall(routeKey, timeout, platformArgs, table), callback);
  }

  public long add_updateDalSetTableCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetTable table, IMethodCallback<DalSetDao.updateDalSetTable_args, DalSetDao.updateDalSetTable_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateDalSetTableServiceCall(routeKey, timeout, platformArgs, table), callback);
  }

  protected TServiceCall create_updateDalSetTableServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetTable table){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateDalSetTable_args request = new DalSetDao.updateDalSetTable_args();
    request.setPlatformArgs(platformArgs);
    request.setTable(table);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDalSetTable");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateDalSetTable_result.class);
    return serviceCall;
  }

  public void send_deleteDalSetTable(int routeKey, int timeout, String tablePrefixName) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetTableServiceCall(routeKey, timeout, platformArgs, tablePrefixName), new TRequestOption());
  }

  public void send_deleteDalSetTable(int routeKey, int timeout, String tablePrefixName,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetTableServiceCall(routeKey, timeout, platformArgs, tablePrefixName), requestOption);
  }

  public long deleteDalSetTable(int routeKey, int timeout, String tablePrefixName, IMethodCallback<DalSetDao.deleteDalSetTable_args, DalSetDao.deleteDalSetTable_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteDalSetTableServiceCall(routeKey, timeout, platformArgs, tablePrefixName), callback);
  }

  public long add_deleteDalSetTableCall(AsyncCallRunner runner, int routeKey, int timeout, String tablePrefixName, IMethodCallback<DalSetDao.deleteDalSetTable_args, DalSetDao.deleteDalSetTable_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteDalSetTableServiceCall(routeKey, timeout, platformArgs, tablePrefixName), callback);
  }

  protected TServiceCall create_deleteDalSetTableServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String tablePrefixName){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteDalSetTable_args request = new DalSetDao.deleteDalSetTable_args();
    request.setPlatformArgs(platformArgs);
    request.setTablePrefixName(tablePrefixName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteDalSetTable");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteDalSetTable_result.class);
    return serviceCall;
  }

  public void send_queryDalSetRoles(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetRoleOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetRolesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryDalSetRoles(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetRoleOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryDalSetRolesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryDalSetRoles(int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetRoleOption option, IMethodCallback<DalSetDao.queryDalSetRoles_args, DalSetDao.queryDalSetRoles_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryDalSetRolesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryDalSetRolesCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryDalSetRoleOption option, IMethodCallback<DalSetDao.queryDalSetRoles_args, DalSetDao.queryDalSetRoles_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryDalSetRolesServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryDalSetRolesServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetRoleOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryDalSetRoles_args request = new DalSetDao.queryDalSetRoles_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryDalSetRoles");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryDalSetRoles_result.class);
    return serviceCall;
  }

  public void send_addDalSetRole(int routeKey, int timeout, DalSetRole role) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), new TRequestOption());
  }

  public void send_addDalSetRole(int routeKey, int timeout, DalSetRole role,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), requestOption);
  }

  public long addDalSetRole(int routeKey, int timeout, DalSetRole role, IMethodCallback<DalSetDao.addDalSetRole_args, DalSetDao.addDalSetRole_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), callback);
  }

  public long add_addDalSetRoleCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetRole role, IMethodCallback<DalSetDao.addDalSetRole_args, DalSetDao.addDalSetRole_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), callback);
  }

  protected TServiceCall create_addDalSetRoleServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetRole role){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addDalSetRole_args request = new DalSetDao.addDalSetRole_args();
    request.setPlatformArgs(platformArgs);
    request.setRole(role);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addDalSetRole");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addDalSetRole_result.class);
    return serviceCall;
  }

  public void send_updateDalSetRole(int routeKey, int timeout, DalSetRole role) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), new TRequestOption());
  }

  public void send_updateDalSetRole(int routeKey, int timeout, DalSetRole role,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), requestOption);
  }

  public long updateDalSetRole(int routeKey, int timeout, DalSetRole role, IMethodCallback<DalSetDao.updateDalSetRole_args, DalSetDao.updateDalSetRole_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), callback);
  }

  public long add_updateDalSetRoleCall(AsyncCallRunner runner, int routeKey, int timeout, DalSetRole role, IMethodCallback<DalSetDao.updateDalSetRole_args, DalSetDao.updateDalSetRole_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateDalSetRoleServiceCall(routeKey, timeout, platformArgs, role), callback);
  }

  protected TServiceCall create_updateDalSetRoleServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetRole role){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateDalSetRole_args request = new DalSetDao.updateDalSetRole_args();
    request.setPlatformArgs(platformArgs);
    request.setRole(role);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDalSetRole");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateDalSetRole_result.class);
    return serviceCall;
  }

  public void send_deleteDalSetRole(int routeKey, int timeout, String roleName) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetRoleServiceCall(routeKey, timeout, platformArgs, roleName), new TRequestOption());
  }

  public void send_deleteDalSetRole(int routeKey, int timeout, String roleName,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteDalSetRoleServiceCall(routeKey, timeout, platformArgs, roleName), requestOption);
  }

  public long deleteDalSetRole(int routeKey, int timeout, String roleName, IMethodCallback<DalSetDao.deleteDalSetRole_args, DalSetDao.deleteDalSetRole_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteDalSetRoleServiceCall(routeKey, timeout, platformArgs, roleName), callback);
  }

  public long add_deleteDalSetRoleCall(AsyncCallRunner runner, int routeKey, int timeout, String roleName, IMethodCallback<DalSetDao.deleteDalSetRole_args, DalSetDao.deleteDalSetRole_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteDalSetRoleServiceCall(routeKey, timeout, platformArgs, roleName), callback);
  }

  protected TServiceCall create_deleteDalSetRoleServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String roleName){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteDalSetRole_args request = new DalSetDao.deleteDalSetRole_args();
    request.setPlatformArgs(platformArgs);
    request.setRoleName(roleName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteDalSetRole");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteDalSetRole_result.class);
    return serviceCall;
  }

  public void send_addTableRoleRelation(int routeKey, int timeout, RoleTableRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_addTableRoleRelation(int routeKey, int timeout, RoleTableRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long addTableRoleRelation(int routeKey, int timeout, RoleTableRelation relation, IMethodCallback<DalSetDao.addTableRoleRelation_args, DalSetDao.addTableRoleRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_addTableRoleRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleTableRelation relation, IMethodCallback<DalSetDao.addTableRoleRelation_args, DalSetDao.addTableRoleRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_addTableRoleRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleTableRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addTableRoleRelation_args request = new DalSetDao.addTableRoleRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addTableRoleRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addTableRoleRelation_result.class);
    return serviceCall;
  }

  public void send_deleteTableRoleRelation(int routeKey, int timeout, RoleTableRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_deleteTableRoleRelation(int routeKey, int timeout, RoleTableRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long deleteTableRoleRelation(int routeKey, int timeout, RoleTableRelation relation, IMethodCallback<DalSetDao.deleteTableRoleRelation_args, DalSetDao.deleteTableRoleRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_deleteTableRoleRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleTableRelation relation, IMethodCallback<DalSetDao.deleteTableRoleRelation_args, DalSetDao.deleteTableRoleRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteTableRoleRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_deleteTableRoleRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleTableRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteTableRoleRelation_args request = new DalSetDao.deleteTableRoleRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteTableRoleRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteTableRoleRelation_result.class);
    return serviceCall;
  }

  public void send_queryTableRoleRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleTableRelationOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryTableRoleRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryTableRoleRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleTableRelationOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryTableRoleRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryTableRoleRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleTableRelationOption option, IMethodCallback<DalSetDao.queryTableRoleRelations_args, DalSetDao.queryTableRoleRelations_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryTableRoleRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryTableRoleRelationsCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleTableRelationOption option, IMethodCallback<DalSetDao.queryTableRoleRelations_args, DalSetDao.queryTableRoleRelations_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryTableRoleRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryTableRoleRelationsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRoleTableRelationOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryTableRoleRelations_args request = new DalSetDao.queryTableRoleRelations_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryTableRoleRelations");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryTableRoleRelations_result.class);
    return serviceCall;
  }

  public void send_queryRoleSetRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleSetRelationOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryRoleSetRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryRoleSetRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleSetRelationOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryRoleSetRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryRoleSetRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleSetRelationOption option, IMethodCallback<DalSetDao.queryRoleSetRelations_args, DalSetDao.queryRoleSetRelations_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryRoleSetRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryRoleSetRelationsCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleSetRelationOption option, IMethodCallback<DalSetDao.queryRoleSetRelations_args, DalSetDao.queryRoleSetRelations_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryRoleSetRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryRoleSetRelationsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRoleSetRelationOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryRoleSetRelations_args request = new DalSetDao.queryRoleSetRelations_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryRoleSetRelations");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryRoleSetRelations_result.class);
    return serviceCall;
  }

  public void send_addRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_addRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long addRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation, IMethodCallback<DalSetDao.addRoleSetRelation_args, DalSetDao.addRoleSetRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_addRoleSetRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleSetRelation relation, IMethodCallback<DalSetDao.addRoleSetRelation_args, DalSetDao.addRoleSetRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_addRoleSetRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleSetRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addRoleSetRelation_args request = new DalSetDao.addRoleSetRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addRoleSetRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addRoleSetRelation_result.class);
    return serviceCall;
  }

  public void send_deleteRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_deleteRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long deleteRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation, IMethodCallback<DalSetDao.deleteRoleSetRelation_args, DalSetDao.deleteRoleSetRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_deleteRoleSetRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleSetRelation relation, IMethodCallback<DalSetDao.deleteRoleSetRelation_args, DalSetDao.deleteRoleSetRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_deleteRoleSetRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleSetRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteRoleSetRelation_args request = new DalSetDao.deleteRoleSetRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteRoleSetRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteRoleSetRelation_result.class);
    return serviceCall;
  }

  public void send_updateRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_updateRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long updateRoleSetRelation(int routeKey, int timeout, RoleSetRelation relation, IMethodCallback<DalSetDao.updateRoleSetRelation_args, DalSetDao.updateRoleSetRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_updateRoleSetRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleSetRelation relation, IMethodCallback<DalSetDao.updateRoleSetRelation_args, DalSetDao.updateRoleSetRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateRoleSetRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_updateRoleSetRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleSetRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateRoleSetRelation_args request = new DalSetDao.updateRoleSetRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateRoleSetRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateRoleSetRelation_result.class);
    return serviceCall;
  }

  public void send_queryRoleServiceRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleServiceRelationOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryRoleServiceRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryRoleServiceRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleServiceRelationOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_queryRoleServiceRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryRoleServiceRelations(int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleServiceRelationOption option, IMethodCallback<DalSetDao.queryRoleServiceRelations_args, DalSetDao.queryRoleServiceRelations_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_queryRoleServiceRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryRoleServiceRelationsCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryRoleServiceRelationOption option, IMethodCallback<DalSetDao.queryRoleServiceRelations_args, DalSetDao.queryRoleServiceRelations_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_queryRoleServiceRelationsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryRoleServiceRelationsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRoleServiceRelationOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.queryRoleServiceRelations_args request = new DalSetDao.queryRoleServiceRelations_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryRoleServiceRelations");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.queryRoleServiceRelations_result.class);
    return serviceCall;
  }

  public void send_addRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_addRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long addRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation, IMethodCallback<DalSetDao.addRoleServiceRelation_args, DalSetDao.addRoleServiceRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_addRoleServiceRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleServiceRelation relation, IMethodCallback<DalSetDao.addRoleServiceRelation_args, DalSetDao.addRoleServiceRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_addRoleServiceRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleServiceRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.addRoleServiceRelation_args request = new DalSetDao.addRoleServiceRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addRoleServiceRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.addRoleServiceRelation_result.class);
    return serviceCall;
  }

  public void send_updateRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_updateRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long updateRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation, IMethodCallback<DalSetDao.updateRoleServiceRelation_args, DalSetDao.updateRoleServiceRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_updateRoleServiceRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleServiceRelation relation, IMethodCallback<DalSetDao.updateRoleServiceRelation_args, DalSetDao.updateRoleServiceRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_updateRoleServiceRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleServiceRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateRoleServiceRelation_args request = new DalSetDao.updateRoleServiceRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateRoleServiceRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateRoleServiceRelation_result.class);
    return serviceCall;
  }

  public void send_deleteRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), new TRequestOption());
  }

  public void send_deleteRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), requestOption);
  }

  public long deleteRoleServiceRelation(int routeKey, int timeout, RoleServiceRelation relation, IMethodCallback<DalSetDao.deleteRoleServiceRelation_args, DalSetDao.deleteRoleServiceRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  public long add_deleteRoleServiceRelationCall(AsyncCallRunner runner, int routeKey, int timeout, RoleServiceRelation relation, IMethodCallback<DalSetDao.deleteRoleServiceRelation_args, DalSetDao.deleteRoleServiceRelation_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteRoleServiceRelationServiceCall(routeKey, timeout, platformArgs, relation), callback);
  }

  protected TServiceCall create_deleteRoleServiceRelationServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleServiceRelation relation){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.deleteRoleServiceRelation_args request = new DalSetDao.deleteRoleServiceRelation_args();
    request.setPlatformArgs(platformArgs);
    request.setRelation(relation);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteRoleServiceRelation");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.deleteRoleServiceRelation_result.class);
    return serviceCall;
  }

  public void send_updateDalSetXml(int routeKey, int timeout, String xml) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetXmlServiceCall(routeKey, timeout, platformArgs, xml), new TRequestOption());
  }

  public void send_updateDalSetXml(int routeKey, int timeout, String xml,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateDalSetXmlServiceCall(routeKey, timeout, platformArgs, xml), requestOption);
  }

  public long updateDalSetXml(int routeKey, int timeout, String xml, IMethodCallback<DalSetDao.updateDalSetXml_args, DalSetDao.updateDalSetXml_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateDalSetXmlServiceCall(routeKey, timeout, platformArgs, xml), callback);
  }

  public long add_updateDalSetXmlCall(AsyncCallRunner runner, int routeKey, int timeout, String xml, IMethodCallback<DalSetDao.updateDalSetXml_args, DalSetDao.updateDalSetXml_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateDalSetXmlServiceCall(routeKey, timeout, platformArgs, xml), callback);
  }

  protected TServiceCall create_updateDalSetXmlServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String xml){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.updateDalSetXml_args request = new DalSetDao.updateDalSetXml_args();
    request.setPlatformArgs(platformArgs);
    request.setXml(xml);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateDalSetXml");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.updateDalSetXml_result.class);
    return serviceCall;
  }

  public void send_getLastVersion(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getLastVersionServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getLastVersion(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getLastVersionServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getLastVersion(int routeKey, int timeout, IMethodCallback<DalSetDao.getLastVersion_args, DalSetDao.getLastVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_getLastVersionServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getLastVersionCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<DalSetDao.getLastVersion_args, DalSetDao.getLastVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_getLastVersionServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getLastVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.getLastVersion_args request = new DalSetDao.getLastVersion_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getLastVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.getLastVersion_result.class);
    return serviceCall;
  }

  public void send_getLastXml(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getLastXmlServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getLastXml(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getLastXmlServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getLastXml(int routeKey, int timeout, IMethodCallback<DalSetDao.getLastXml_args, DalSetDao.getLastXml_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_getLastXmlServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getLastXmlCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<DalSetDao.getLastXml_args, DalSetDao.getLastXml_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_getLastXmlServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getLastXmlServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(DalSetDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    DalSetDao.getLastXml_args request = new DalSetDao.getLastXml_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getLastXml");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(DalSetDao.getLastXml_result.class);
    return serviceCall;
  }

}
