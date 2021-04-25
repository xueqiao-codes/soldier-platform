package org.soldier.platform.oa.user.ao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.oa.user.ao.ECheckResult;
import org.soldier.platform.oa.user.ao.LoginResult;
import org.soldier.platform.oa.user.ao.OaUserAo;
import org.soldier.platform.oa.user.ao.OaUserAoVariable;


public abstract class OaUserAoAdaptor implements OaUserAo.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public LoginResult login(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userName, String passwordMd5) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"login",platformArgs);
    return login(oCntl, userName, passwordMd5);
  }

  protected abstract LoginResult login(TServiceCntl oCntl, String userName, String passwordMd5) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int registerUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.OaUser user, String operationUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"registerUser",platformArgs);
    return registerUser(oCntl, user, operationUserName);
  }

  protected abstract int registerUser(TServiceCntl oCntl, org.soldier.platform.oa.user.OaUser user, String operationUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public boolean checkSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName, String secretKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"checkSession",platformArgs);
    return checkSession(oCntl, userId, userName, secretKey);
  }

  protected abstract boolean checkSession(TServiceCntl oCntl, int userId, String userName, String secretKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.OaUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"updateUser",platformArgs);
updateUser(oCntl, user);
  }

  protected abstract void updateUser(TServiceCntl oCntl, org.soldier.platform.oa.user.OaUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String operationUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"deleteUser",platformArgs);
deleteUser(oCntl, userId, operationUserName);
  }

  protected abstract void deleteUser(TServiceCntl oCntl, int userId, String operationUserName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void logout(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"logout",platformArgs);
logout(oCntl, userId, userName);
  }

  protected abstract void logout(TServiceCntl oCntl, int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<org.soldier.platform.oa.user.OaUser> queryUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.QueryUserOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"queryUser",platformArgs);
    return queryUser(oCntl, option);
  }

  protected abstract List<org.soldier.platform.oa.user.OaUser> queryUser(TServiceCntl oCntl, org.soldier.platform.oa.user.QueryUserOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public org.soldier.platform.oa.user.OaUserPage queryUserByPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"queryUserByPage",platformArgs);
    return queryUserByPage(oCntl, option, pageIndex, pageSize);
  }

  protected abstract org.soldier.platform.oa.user.OaUserPage queryUserByPage(TServiceCntl oCntl, org.soldier.platform.oa.user.QueryUserOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public ECheckResult checkSessionAndGroups(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName, String secretKey, Set<String> groups) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaUserAoVariable.serviceKey,"checkSessionAndGroups",platformArgs);
    return checkSessionAndGroups(oCntl, userId, userName, secretKey, groups);
  }

  protected abstract ECheckResult checkSessionAndGroups(TServiceCntl oCntl, int userId, String userName, String secretKey, Set<String> groups) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected OaUserAoAdaptor(){
    methodParameterNameMap.put("login",new String[]{"platformArgs", "userName", "passwordMd5"});
    methodParameterNameMap.put("registerUser",new String[]{"platformArgs", "user", "operationUserName"});
    methodParameterNameMap.put("checkSession",new String[]{"platformArgs", "userId", "userName", "secretKey"});
    methodParameterNameMap.put("updateUser",new String[]{"platformArgs", "user"});
    methodParameterNameMap.put("deleteUser",new String[]{"platformArgs", "userId", "operationUserName"});
    methodParameterNameMap.put("logout",new String[]{"platformArgs", "userId", "userName"});
    methodParameterNameMap.put("queryUser",new String[]{"platformArgs", "option"});
    methodParameterNameMap.put("queryUserByPage",new String[]{"platformArgs", "option", "pageIndex", "pageSize"});
    methodParameterNameMap.put("checkSessionAndGroups",new String[]{"platformArgs", "userId", "userName", "secretKey", "groups"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
