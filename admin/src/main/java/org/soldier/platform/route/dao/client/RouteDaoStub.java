package org.soldier.platform.route.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.SimpleRouteInfo;
import org.soldier.platform.route.dao.RouteDao;
import org.soldier.platform.route.dao.RouteDaoVariable;

public class RouteDaoStub extends BaseStub {

  public RouteDaoStub() {
    super(RouteDaoVariable.serviceKey);
  }

  public void  addRoute(RouteInfo route) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addRoute(route, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addRoute(RouteInfo route,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addRoute").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new RouteDao.Client(protocol).addRoute(platformArgs, route);
      return null;
      }
    }, invokeInfo);
  }

  public void  addRoute(int routeKey, int timeout,RouteInfo route)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addRoute(route, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateRoute(RouteInfo route) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateRoute(route, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateRoute(RouteInfo route,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateRoute").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new RouteDao.Client(protocol).updateRoute(platformArgs, route);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateRoute(int routeKey, int timeout,RouteInfo route)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateRoute(route, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteRoute(int serviceKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteRoute(serviceKey, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteRoute(int serviceKey,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteRoute").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new RouteDao.Client(protocol).deleteRoute(platformArgs, serviceKey);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteRoute(int routeKey, int timeout,int serviceKey)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteRoute(serviceKey, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public RouteInfoList  queryRouteInfoList(int pageIndex, int pageSize, QueryRouteOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryRouteInfoList(pageIndex, pageSize, option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public RouteInfoList  queryRouteInfoList(int pageIndex, int pageSize, QueryRouteOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryRouteInfoList").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<RouteInfoList>(){
    @Override
    public RouteInfoList call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new RouteDao.Client(protocol).queryRouteInfoList(platformArgs, pageIndex, pageSize, option);
      }
    }, invokeInfo);
  }

  public RouteInfoList  queryRouteInfoList(int routeKey, int timeout,int pageIndex, int pageSize, QueryRouteOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryRouteInfoList(pageIndex, pageSize, option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncRoute(String config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncRoute(config, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  syncRoute(String config,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("syncRoute").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new RouteDao.Client(protocol).syncRoute(platformArgs, config);
      return null;
      }
    }, invokeInfo);
  }

  public void  syncRoute(int routeKey, int timeout,String config)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    syncRoute(config, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getLastRouteVersion() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastRouteVersion(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getLastRouteVersion(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getLastRouteVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new RouteDao.Client(protocol).getLastRouteVersion(platformArgs);
      }
    }, invokeInfo);
  }

  public int  getLastRouteVersion(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastRouteVersion(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<SimpleRouteInfo>  getAllSimpleRouteInfo() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllSimpleRouteInfo(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<SimpleRouteInfo>  getAllSimpleRouteInfo(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getAllSimpleRouteInfo").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<SimpleRouteInfo>>(){
    @Override
    public List<SimpleRouteInfo> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new RouteDao.Client(protocol).getAllSimpleRouteInfo(platformArgs);
      }
    }, invokeInfo);
  }

  public List<SimpleRouteInfo>  getAllSimpleRouteInfo(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getAllSimpleRouteInfo(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public String  getLastestRouteConfig() throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastestRouteConfig(new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public String  getLastestRouteConfig(TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getLastestRouteConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<String>(){
    @Override
    public String call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new RouteDao.Client(protocol).getLastestRouteConfig(platformArgs);
      }
    }, invokeInfo);
  }

  public String  getLastestRouteConfig(int routeKey, int timeout)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastestRouteConfig(new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
