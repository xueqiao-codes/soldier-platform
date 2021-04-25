package org.soldier.platform.oa.session.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.oa.session.dao.TSession;
import org.soldier.platform.oa.session.dao.OaSessionDao;
import org.soldier.platform.oa.session.dao.OaSessionDaoVariable;


public abstract class OaSessionDaoAdaptor implements OaSessionDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void updateSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaSessionDaoVariable.serviceKey,"updateSession",platformArgs);
updateSession(oCntl, session);
  }

  protected abstract void updateSession(TServiceCntl oCntl, TSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TSession> getSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaSessionDaoVariable.serviceKey,"getSession",platformArgs);
    return getSession(oCntl, userId, userName);
  }

  protected abstract List<TSession> getSession(TServiceCntl oCntl, int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSession(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaSessionDaoVariable.serviceKey,"deleteSession",platformArgs);
deleteSession(oCntl, userId, userName);
  }

  protected abstract void deleteSession(TServiceCntl oCntl, int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected OaSessionDaoAdaptor(){
    methodParameterNameMap.put("updateSession",new String[]{"platformArgs", "session"});
    methodParameterNameMap.put("getSession",new String[]{"platformArgs", "userId", "userName"});
    methodParameterNameMap.put("deleteSession",new String[]{"platformArgs", "userId", "userName"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
