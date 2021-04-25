package org.soldier.platform.oa.user.ao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import java.util.Set;
import org.soldier.platform.oa.user.ao.ECheckResult;
import org.soldier.platform.oa.user.ao.LoginResult;
import org.soldier.platform.oa.user.ao.OaUserAo;
import org.soldier.platform.oa.user.ao.OaUserAoVariable;

public class OaUserAoStub extends BaseStub {

  public OaUserAoStub() {
    super(OaUserAoVariable.serviceKey);
  }

  public LoginResult  login(String userName, String passwordMd5) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return login(userName, passwordMd5, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public LoginResult  login(String userName, String passwordMd5,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("login").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<LoginResult>(){
    @Override
    public LoginResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaUserAo.Client(protocol).login(platformArgs, userName, passwordMd5);
      }
    }, invokeInfo);
  }

  public LoginResult  login(int routeKey, int timeout,String userName, String passwordMd5)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return login(userName, passwordMd5, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  registerUser(org.soldier.platform.oa.user.OaUser user, String operationUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return registerUser(user, operationUserName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  registerUser(org.soldier.platform.oa.user.OaUser user, String operationUserName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("registerUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaUserAo.Client(protocol).registerUser(platformArgs, user, operationUserName);
      }
    }, invokeInfo);
  }

  public int  registerUser(int routeKey, int timeout,org.soldier.platform.oa.user.OaUser user, String operationUserName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return registerUser(user, operationUserName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  checkSession(int userId, String userName, String secretKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSession(userId, userName, secretKey, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public boolean  checkSession(int userId, String userName, String secretKey,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("checkSession").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Boolean>(){
    @Override
    public Boolean call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaUserAo.Client(protocol).checkSession(platformArgs, userId, userName, secretKey);
      }
    }, invokeInfo);
  }

  public boolean  checkSession(int routeKey, int timeout,int userId, String userName, String secretKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSession(userId, userName, secretKey, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateUser(org.soldier.platform.oa.user.OaUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateUser(user, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateUser(org.soldier.platform.oa.user.OaUser user,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new OaUserAo.Client(protocol).updateUser(platformArgs, user);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateUser(int routeKey, int timeout,org.soldier.platform.oa.user.OaUser user)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateUser(user, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteUser(int userId, String operationUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteUser(userId, operationUserName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteUser(int userId, String operationUserName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new OaUserAo.Client(protocol).deleteUser(platformArgs, userId, operationUserName);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteUser(int routeKey, int timeout,int userId, String operationUserName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteUser(userId, operationUserName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  logout(int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    logout(userId, userName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  logout(int userId, String userName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("logout").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new OaUserAo.Client(protocol).logout(platformArgs, userId, userName);
      return null;
      }
    }, invokeInfo);
  }

  public void  logout(int routeKey, int timeout,int userId, String userName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    logout(userId, userName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.oa.user.OaUser>  queryUser(org.soldier.platform.oa.user.QueryUserOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUser(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.oa.user.OaUser>  queryUser(org.soldier.platform.oa.user.QueryUserOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUser").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<org.soldier.platform.oa.user.OaUser>>(){
    @Override
    public List<org.soldier.platform.oa.user.OaUser> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaUserAo.Client(protocol).queryUser(platformArgs, option);
      }
    }, invokeInfo);
  }

  public List<org.soldier.platform.oa.user.OaUser>  queryUser(int routeKey, int timeout,org.soldier.platform.oa.user.QueryUserOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUser(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public org.soldier.platform.oa.user.OaUserPage  queryUserByPage(org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUserByPage(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public org.soldier.platform.oa.user.OaUserPage  queryUserByPage(org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryUserByPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<org.soldier.platform.oa.user.OaUserPage>(){
    @Override
    public org.soldier.platform.oa.user.OaUserPage call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaUserAo.Client(protocol).queryUserByPage(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public org.soldier.platform.oa.user.OaUserPage  queryUserByPage(int routeKey, int timeout,org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryUserByPage(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ECheckResult  checkSessionAndGroups(int userId, String userName, String secretKey, Set<String> groups) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSessionAndGroups(userId, userName, secretKey, groups, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public ECheckResult  checkSessionAndGroups(int userId, String userName, String secretKey, Set<String> groups,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("checkSessionAndGroups").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<ECheckResult>(){
    @Override
    public ECheckResult call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaUserAo.Client(protocol).checkSessionAndGroups(platformArgs, userId, userName, secretKey, groups);
      }
    }, invokeInfo);
  }

  public ECheckResult  checkSessionAndGroups(int routeKey, int timeout,int userId, String userName, String secretKey, Set<String> groups)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return checkSessionAndGroups(userId, userName, secretKey, groups, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
