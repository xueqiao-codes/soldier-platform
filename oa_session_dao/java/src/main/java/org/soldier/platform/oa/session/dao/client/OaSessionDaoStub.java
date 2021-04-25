package org.soldier.platform.oa.session.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import org.soldier.platform.oa.session.dao.TSession;
import org.soldier.platform.oa.session.dao.OaSessionDao;
import org.soldier.platform.oa.session.dao.OaSessionDaoVariable;

public class OaSessionDaoStub extends BaseStub {

  public OaSessionDaoStub() {
    super(OaSessionDaoVariable.serviceKey);
  }

  public void  updateSession(TSession session) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSession(session, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateSession(TSession session,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateSession").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new OaSessionDao.Client(protocol).updateSession(platformArgs, session);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateSession(int routeKey, int timeout,TSession session)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateSession(session, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TSession>  getSession(int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSession(userId, userName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<TSession>  getSession(int userId, String userName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getSession").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<TSession>>(){
    @Override
    public List<TSession> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new OaSessionDao.Client(protocol).getSession(platformArgs, userId, userName);
      }
    }, invokeInfo);
  }

  public List<TSession>  getSession(int routeKey, int timeout,int userId, String userName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getSession(userId, userName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSession(int userId, String userName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSession(userId, userName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteSession(int userId, String userName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteSession").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new OaSessionDao.Client(protocol).deleteSession(platformArgs, userId, userName);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteSession(int routeKey, int timeout,int userId, String userName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteSession(userId, userName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
