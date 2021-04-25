package com.car.user.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import com.car.user.dao.UserInfo;
import com.car.user.dao.UserDao;
import com.car.user.dao.UserDaoVariable;


public abstract class UserDaoAdaptor implements UserDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public long Register(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userEmail, String userPasswd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"Register",platformArgs);
    return Register(oCntl, userEmail, userPasswd);
  }

  protected abstract long Register(TServiceCntl oCntl,String userEmail, String userPasswd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long CheckLogIn(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userEmail, String userPasswd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"CheckLogIn",platformArgs);
    return CheckLogIn(oCntl, userEmail, userPasswd);
  }

  protected abstract long CheckLogIn(TServiceCntl oCntl,String userEmail, String userPasswd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public long GetCollectUserUid(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userSource, String userSourceId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"GetCollectUserUid",platformArgs);
    return GetCollectUserUid(oCntl, userSource, userSourceId);
  }

  protected abstract long GetCollectUserUid(TServiceCntl oCntl,int userSource, String userSourceId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public UserInfo getUserInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, long userId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"getUserInfo",platformArgs);
    return getUserInfo(oCntl, userId);
  }

  protected abstract UserInfo getUserInfo(TServiceCntl oCntl,long userId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void setUserInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, UserInfo user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"setUserInfo",platformArgs);
setUserInfo(oCntl, user);
  }

  protected abstract void setUserInfo(TServiceCntl oCntl,UserInfo user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void AddUserRp(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, int value) throws org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"AddUserRp",platformArgs);
AddUserRp(oCntl, userId, value);
  }

  protected abstract void AddUserRp(TServiceCntl oCntl,int userId, int value) throws org.apache.thrift.TException;

  @Override
  public void AddUserRpAndScore(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, int rpValue, int userScore) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(UserDaoVariable.serviceKey,"AddUserRpAndScore",platformArgs);
AddUserRpAndScore(oCntl, userId, rpValue, userScore);
  }

  protected abstract void AddUserRpAndScore(TServiceCntl oCntl,int userId, int rpValue, int userScore) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected UserDaoAdaptor(){
    methodParameterNameMap.put("Register",new String[]{"platformArgs", "userEmail", "userPasswd"});
    methodParameterNameMap.put("CheckLogIn",new String[]{"platformArgs", "userEmail", "userPasswd"});
    methodParameterNameMap.put("GetCollectUserUid",new String[]{"platformArgs", "userSource", "userSourceId"});
    methodParameterNameMap.put("getUserInfo",new String[]{"platformArgs", "userId"});
    methodParameterNameMap.put("setUserInfo",new String[]{"platformArgs", "user"});
    methodParameterNameMap.put("AddUserRp",new String[]{"platformArgs", "userId", "value"});
    methodParameterNameMap.put("AddUserRpAndScore",new String[]{"platformArgs", "userId", "rpValue", "userScore"});
  }
  protected abstract int InitApp(final Properties props);

}
