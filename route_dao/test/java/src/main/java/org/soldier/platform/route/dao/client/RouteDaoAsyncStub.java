package org.soldier.platform.route.dao.client;

import org.soldier.platform.route.dao.RouteDao;
import org.soldier.platform.route.dao.RouteDaoVariable;
import org.apache.thrift.TException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.client.BaseStub;
import java.util.List;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.SimpleRouteInfo;

public class RouteDaoAsyncStub extends BaseStub { 
  public RouteDaoAsyncStub() {
    super(RouteDaoVariable.serviceKey);
  }
  public void send_addRoute(int routeKey, int timeout, RouteInfo route) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addRouteServiceCall(routeKey, timeout, platformArgs, route), new TRequestOption());
  }

  public void send_addRoute(int routeKey, int timeout, RouteInfo route,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addRouteServiceCall(routeKey, timeout, platformArgs, route), requestOption);
  }

  public long addRoute(int routeKey, int timeout, RouteInfo route, IMethodCallback<RouteDao.addRoute_args, RouteDao.addRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_addRouteServiceCall(routeKey, timeout, platformArgs, route), callback);
  }

  public long add_addRouteCall(AsyncCallRunner runner, int routeKey, int timeout, RouteInfo route, IMethodCallback<RouteDao.addRoute_args, RouteDao.addRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_addRouteServiceCall(routeKey, timeout, platformArgs, route), callback);
  }

  protected TServiceCall create_addRouteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RouteInfo route){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.addRoute_args request = new RouteDao.addRoute_args();
    request.setPlatformArgs(platformArgs);
    request.setRoute(route);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addRoute");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.addRoute_result.class);
    return serviceCall;
  }

  public void send_updateRoute(int routeKey, int timeout, RouteInfo route) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateRouteServiceCall(routeKey, timeout, platformArgs, route), new TRequestOption());
  }

  public void send_updateRoute(int routeKey, int timeout, RouteInfo route,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateRouteServiceCall(routeKey, timeout, platformArgs, route), requestOption);
  }

  public long updateRoute(int routeKey, int timeout, RouteInfo route, IMethodCallback<RouteDao.updateRoute_args, RouteDao.updateRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_updateRouteServiceCall(routeKey, timeout, platformArgs, route), callback);
  }

  public long add_updateRouteCall(AsyncCallRunner runner, int routeKey, int timeout, RouteInfo route, IMethodCallback<RouteDao.updateRoute_args, RouteDao.updateRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_updateRouteServiceCall(routeKey, timeout, platformArgs, route), callback);
  }

  protected TServiceCall create_updateRouteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RouteInfo route){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.updateRoute_args request = new RouteDao.updateRoute_args();
    request.setPlatformArgs(platformArgs);
    request.setRoute(route);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateRoute");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.updateRoute_result.class);
    return serviceCall;
  }

  public void send_deleteRoute(int routeKey, int timeout, int serviceKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteRouteServiceCall(routeKey, timeout, platformArgs, serviceKey), new TRequestOption());
  }

  public void send_deleteRoute(int routeKey, int timeout, int serviceKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteRouteServiceCall(routeKey, timeout, platformArgs, serviceKey), requestOption);
  }

  public long deleteRoute(int routeKey, int timeout, int serviceKey, IMethodCallback<RouteDao.deleteRoute_args, RouteDao.deleteRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_deleteRouteServiceCall(routeKey, timeout, platformArgs, serviceKey), callback);
  }

  public long add_deleteRouteCall(AsyncCallRunner runner, int routeKey, int timeout, int serviceKey, IMethodCallback<RouteDao.deleteRoute_args, RouteDao.deleteRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_deleteRouteServiceCall(routeKey, timeout, platformArgs, serviceKey), callback);
  }

  protected TServiceCall create_deleteRouteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int serviceKey){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.deleteRoute_args request = new RouteDao.deleteRoute_args();
    request.setPlatformArgs(platformArgs);
    request.setServiceKey(serviceKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteRoute");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.deleteRoute_result.class);
    return serviceCall;
  }

  public void send_queryRouteInfoList(int routeKey, int timeout, int pageIndex, int pageSize, QueryRouteOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryRouteInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryRouteInfoList(int routeKey, int timeout, int pageIndex, int pageSize, QueryRouteOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryRouteInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryRouteInfoList(int routeKey, int timeout, int pageIndex, int pageSize, QueryRouteOption option, IMethodCallback<RouteDao.queryRouteInfoList_args, RouteDao.queryRouteInfoList_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_queryRouteInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryRouteInfoListCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryRouteOption option, IMethodCallback<RouteDao.queryRouteInfoList_args, RouteDao.queryRouteInfoList_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_queryRouteInfoListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryRouteInfoListServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRouteOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.queryRouteInfoList_args request = new RouteDao.queryRouteInfoList_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryRouteInfoList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.queryRouteInfoList_result.class);
    return serviceCall;
  }

  public void send_syncRoute(int routeKey, int timeout, String config) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncRouteServiceCall(routeKey, timeout, platformArgs, config), new TRequestOption());
  }

  public void send_syncRoute(int routeKey, int timeout, String config,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_syncRouteServiceCall(routeKey, timeout, platformArgs, config), requestOption);
  }

  public long syncRoute(int routeKey, int timeout, String config, IMethodCallback<RouteDao.syncRoute_args, RouteDao.syncRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_syncRouteServiceCall(routeKey, timeout, platformArgs, config), callback);
  }

  public long add_syncRouteCall(AsyncCallRunner runner, int routeKey, int timeout, String config, IMethodCallback<RouteDao.syncRoute_args, RouteDao.syncRoute_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_syncRouteServiceCall(routeKey, timeout, platformArgs, config), callback);
  }

  protected TServiceCall create_syncRouteServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String config){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.syncRoute_args request = new RouteDao.syncRoute_args();
    request.setPlatformArgs(platformArgs);
    request.setConfig(config);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("syncRoute");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.syncRoute_result.class);
    return serviceCall;
  }

  public void send_getLastRouteVersion(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getLastRouteVersionServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getLastRouteVersion(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getLastRouteVersionServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getLastRouteVersion(int routeKey, int timeout, IMethodCallback<RouteDao.getLastRouteVersion_args, RouteDao.getLastRouteVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getLastRouteVersionServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getLastRouteVersionCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<RouteDao.getLastRouteVersion_args, RouteDao.getLastRouteVersion_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getLastRouteVersionServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getLastRouteVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.getLastRouteVersion_args request = new RouteDao.getLastRouteVersion_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getLastRouteVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.getLastRouteVersion_result.class);
    return serviceCall;
  }

  public void send_getAllSimpleRouteInfo(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getAllSimpleRouteInfoServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getAllSimpleRouteInfo(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getAllSimpleRouteInfoServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getAllSimpleRouteInfo(int routeKey, int timeout, IMethodCallback<RouteDao.getAllSimpleRouteInfo_args, RouteDao.getAllSimpleRouteInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getAllSimpleRouteInfoServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getAllSimpleRouteInfoCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<RouteDao.getAllSimpleRouteInfo_args, RouteDao.getAllSimpleRouteInfo_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getAllSimpleRouteInfoServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getAllSimpleRouteInfoServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.getAllSimpleRouteInfo_args request = new RouteDao.getAllSimpleRouteInfo_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getAllSimpleRouteInfo");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.getAllSimpleRouteInfo_result.class);
    return serviceCall;
  }

  public void send_getLastestRouteConfig(int routeKey, int timeout) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getLastestRouteConfigServiceCall(routeKey, timeout, platformArgs), new TRequestOption());
  }

  public void send_getLastestRouteConfig(int routeKey, int timeout,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_getLastestRouteConfigServiceCall(routeKey, timeout, platformArgs), requestOption);
  }

  public long getLastestRouteConfig(int routeKey, int timeout, IMethodCallback<RouteDao.getLastestRouteConfig_args, RouteDao.getLastestRouteConfig_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_getLastestRouteConfigServiceCall(routeKey, timeout, platformArgs), callback);
  }

  public long add_getLastestRouteConfigCall(AsyncCallRunner runner, int routeKey, int timeout, IMethodCallback<RouteDao.getLastestRouteConfig_args, RouteDao.getLastestRouteConfig_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setTimeoutMs(timeout);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_getLastestRouteConfigServiceCall(routeKey, timeout, platformArgs), callback);
  }

  protected TServiceCall create_getLastestRouteConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(RouteDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    RouteDao.getLastestRouteConfig_args request = new RouteDao.getLastestRouteConfig_args();
    request.setPlatformArgs(platformArgs);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getLastestRouteConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(RouteDao.getLastestRouteConfig_result.class);
    return serviceCall;
  }

}
