package com.car.user.dao.server.impl;


import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import com.car.user.dao.server.UserDaoAdaptor;
import com.car.user.dao.UserInfo;

public class UserDaoHandler extends UserDaoAdaptor{
  @Override
  public int InitApp(Properties props){
    return 0;
  }

  @Override
  protected long Register(TServiceCntl oCntl,String userEmail, String userPasswd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    return 1;
  }

  @Override
  protected long CheckLogIn(TServiceCntl oCntl,String userEmail, String userPasswd) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    return 0;
  }

  @Override
  protected long GetCollectUserUid(TServiceCntl oCntl,int userSource, String userSourceId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    return 0;
  }

  @Override
  protected UserInfo getUserInfo(TServiceCntl oCntl,long userId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
	  throw new Error("hehe");
//	  return null;
  }

  @Override
  protected void setUserInfo(TServiceCntl oCntl,UserInfo user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
  }

  @Override
  protected void AddUserRp(TServiceCntl oCntl,int userId, int value) throws org.apache.thrift.TException{
	 
  }

  @Override
  protected void AddUserRpAndScore(TServiceCntl oCntl,int userId, int rpValue, int userScore) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
  }

}
