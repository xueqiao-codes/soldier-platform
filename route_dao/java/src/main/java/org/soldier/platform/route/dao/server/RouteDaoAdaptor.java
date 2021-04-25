package org.soldier.platform.route.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.SimpleRouteInfo;
import org.soldier.platform.route.dao.RouteDao;
import org.soldier.platform.route.dao.RouteDaoVariable;


public abstract class RouteDaoAdaptor implements RouteDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public void addRoute(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RouteInfo route) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"addRoute",platformArgs);
addRoute(oCntl, route);
  }

  protected abstract void addRoute(TServiceCntl oCntl, RouteInfo route) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateRoute(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RouteInfo route) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"updateRoute",platformArgs);
updateRoute(oCntl, route);
  }

  protected abstract void updateRoute(TServiceCntl oCntl, RouteInfo route) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteRoute(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int serviceKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"deleteRoute",platformArgs);
deleteRoute(oCntl, serviceKey);
  }

  protected abstract void deleteRoute(TServiceCntl oCntl, int serviceKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public RouteInfoList queryRouteInfoList(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRouteOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"queryRouteInfoList",platformArgs);
    return queryRouteInfoList(oCntl, pageIndex, pageSize, option);
  }

  protected abstract RouteInfoList queryRouteInfoList(TServiceCntl oCntl, int pageIndex, int pageSize, QueryRouteOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void syncRoute(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"syncRoute",platformArgs);
syncRoute(oCntl, config);
  }

  protected abstract void syncRoute(TServiceCntl oCntl, String config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getLastRouteVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"getLastRouteVersion",platformArgs);
    return getLastRouteVersion(oCntl);
  }

  protected abstract int getLastRouteVersion(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<SimpleRouteInfo> getAllSimpleRouteInfo(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"getAllSimpleRouteInfo",platformArgs);
    return getAllSimpleRouteInfo(oCntl);
  }

  protected abstract List<SimpleRouteInfo> getAllSimpleRouteInfo(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public String getLastestRouteConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(RouteDaoVariable.serviceKey,"getLastestRouteConfig",platformArgs);
    return getLastestRouteConfig(oCntl);
  }

  protected abstract String getLastestRouteConfig(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected RouteDaoAdaptor(){
    methodParameterNameMap.put("addRoute",new String[]{"platformArgs", "route"});
    methodParameterNameMap.put("updateRoute",new String[]{"platformArgs", "route"});
    methodParameterNameMap.put("deleteRoute",new String[]{"platformArgs", "serviceKey"});
    methodParameterNameMap.put("queryRouteInfoList",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("syncRoute",new String[]{"platformArgs", "config"});
    methodParameterNameMap.put("getLastRouteVersion",new String[]{"platformArgs"});
    methodParameterNameMap.put("getAllSimpleRouteInfo",new String[]{"platformArgs"});
    methodParameterNameMap.put("getLastestRouteConfig",new String[]{"platformArgs"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
