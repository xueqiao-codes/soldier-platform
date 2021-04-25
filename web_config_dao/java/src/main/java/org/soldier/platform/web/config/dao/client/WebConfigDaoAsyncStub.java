package org.soldier.platform.web.config.dao.client;

import org.soldier.platform.web.config.dao.WebConfigDao;
import org.soldier.platform.web.config.dao.WebConfigDaoVariable;
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
import org.soldier.platform.web.config.dao.ConfigType;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.WebConfigList;

public class WebConfigDaoAsyncStub extends BaseStub { 
  public WebConfigDaoAsyncStub() {
    super(WebConfigDaoVariable.serviceKey);
  }
  public void send_queryConfigByPage(int routeKey, int timeout, QueryWebConfigOption option, int pageIndex, int pageSize) throws TException {
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
        create_queryConfigByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), new TRequestOption());
  }

  public void send_queryConfigByPage(int routeKey, int timeout, QueryWebConfigOption option, int pageIndex, int pageSize,TRequestOption requestOption) throws TException { 
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
        create_queryConfigByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), requestOption);
  }

  public long queryConfigByPage(int routeKey, int timeout, QueryWebConfigOption option, int pageIndex, int pageSize, IMethodCallback<WebConfigDao.queryConfigByPage_args, WebConfigDao.queryConfigByPage_result> callback) throws TException{
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
            create_queryConfigByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  public long add_queryConfigByPageCall(AsyncCallRunner runner, int routeKey, int timeout, QueryWebConfigOption option, int pageIndex, int pageSize, IMethodCallback<WebConfigDao.queryConfigByPage_args, WebConfigDao.queryConfigByPage_result> callback) throws TException{
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
            create_queryConfigByPageServiceCall(routeKey, timeout, platformArgs, option, pageIndex, pageSize), callback);
  }

  protected TServiceCall create_queryConfigByPageServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryWebConfigOption option, int pageIndex, int pageSize){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.queryConfigByPage_args request = new WebConfigDao.queryConfigByPage_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryConfigByPage");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.queryConfigByPage_result.class);
    return serviceCall;
  }

  public void send_queryConfig(int routeKey, int timeout, QueryWebConfigOption option) throws TException {
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
        create_queryConfigServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_queryConfig(int routeKey, int timeout, QueryWebConfigOption option,TRequestOption requestOption) throws TException { 
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
        create_queryConfigServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long queryConfig(int routeKey, int timeout, QueryWebConfigOption option, IMethodCallback<WebConfigDao.queryConfig_args, WebConfigDao.queryConfig_result> callback) throws TException{
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
            create_queryConfigServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_queryConfigCall(AsyncCallRunner runner, int routeKey, int timeout, QueryWebConfigOption option, IMethodCallback<WebConfigDao.queryConfig_args, WebConfigDao.queryConfig_result> callback) throws TException{
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
            create_queryConfigServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_queryConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryWebConfigOption option){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.queryConfig_args request = new WebConfigDao.queryConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.queryConfig_result.class);
    return serviceCall;
  }

  public void send_addWebConfig(int routeKey, int timeout, WebConfig config) throws TException {
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
        create_addWebConfigServiceCall(routeKey, timeout, platformArgs, config), new TRequestOption());
  }

  public void send_addWebConfig(int routeKey, int timeout, WebConfig config,TRequestOption requestOption) throws TException { 
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
        create_addWebConfigServiceCall(routeKey, timeout, platformArgs, config), requestOption);
  }

  public long addWebConfig(int routeKey, int timeout, WebConfig config, IMethodCallback<WebConfigDao.addWebConfig_args, WebConfigDao.addWebConfig_result> callback) throws TException{
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
            create_addWebConfigServiceCall(routeKey, timeout, platformArgs, config), callback);
  }

  public long add_addWebConfigCall(AsyncCallRunner runner, int routeKey, int timeout, WebConfig config, IMethodCallback<WebConfigDao.addWebConfig_args, WebConfigDao.addWebConfig_result> callback) throws TException{
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
            create_addWebConfigServiceCall(routeKey, timeout, platformArgs, config), callback);
  }

  protected TServiceCall create_addWebConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, WebConfig config){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.addWebConfig_args request = new WebConfigDao.addWebConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setConfig(config);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addWebConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.addWebConfig_result.class);
    return serviceCall;
  }

  public void send_deleteWebConfig(int routeKey, int timeout, String webProjectName) throws TException {
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
        create_deleteWebConfigServiceCall(routeKey, timeout, platformArgs, webProjectName), new TRequestOption());
  }

  public void send_deleteWebConfig(int routeKey, int timeout, String webProjectName,TRequestOption requestOption) throws TException { 
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
        create_deleteWebConfigServiceCall(routeKey, timeout, platformArgs, webProjectName), requestOption);
  }

  public long deleteWebConfig(int routeKey, int timeout, String webProjectName, IMethodCallback<WebConfigDao.deleteWebConfig_args, WebConfigDao.deleteWebConfig_result> callback) throws TException{
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
            create_deleteWebConfigServiceCall(routeKey, timeout, platformArgs, webProjectName), callback);
  }

  public long add_deleteWebConfigCall(AsyncCallRunner runner, int routeKey, int timeout, String webProjectName, IMethodCallback<WebConfigDao.deleteWebConfig_args, WebConfigDao.deleteWebConfig_result> callback) throws TException{
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
            create_deleteWebConfigServiceCall(routeKey, timeout, platformArgs, webProjectName), callback);
  }

  protected TServiceCall create_deleteWebConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String webProjectName){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.deleteWebConfig_args request = new WebConfigDao.deleteWebConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setWebProjectName(webProjectName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteWebConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.deleteWebConfig_result.class);
    return serviceCall;
  }

  public void send_updateWebConfig(int routeKey, int timeout, WebConfig config) throws TException {
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
        create_updateWebConfigServiceCall(routeKey, timeout, platformArgs, config), new TRequestOption());
  }

  public void send_updateWebConfig(int routeKey, int timeout, WebConfig config,TRequestOption requestOption) throws TException { 
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
        create_updateWebConfigServiceCall(routeKey, timeout, platformArgs, config), requestOption);
  }

  public long updateWebConfig(int routeKey, int timeout, WebConfig config, IMethodCallback<WebConfigDao.updateWebConfig_args, WebConfigDao.updateWebConfig_result> callback) throws TException{
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
            create_updateWebConfigServiceCall(routeKey, timeout, platformArgs, config), callback);
  }

  public long add_updateWebConfigCall(AsyncCallRunner runner, int routeKey, int timeout, WebConfig config, IMethodCallback<WebConfigDao.updateWebConfig_args, WebConfigDao.updateWebConfig_result> callback) throws TException{
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
            create_updateWebConfigServiceCall(routeKey, timeout, platformArgs, config), callback);
  }

  protected TServiceCall create_updateWebConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, WebConfig config){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.updateWebConfig_args request = new WebConfigDao.updateWebConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setConfig(config);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateWebConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.updateWebConfig_result.class);
    return serviceCall;
  }

  public void send_updateNginxConfig(int routeKey, int timeout, String config, ConfigType type) throws TException {
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
        create_updateNginxConfigServiceCall(routeKey, timeout, platformArgs, config, type), new TRequestOption());
  }

  public void send_updateNginxConfig(int routeKey, int timeout, String config, ConfigType type,TRequestOption requestOption) throws TException { 
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
        create_updateNginxConfigServiceCall(routeKey, timeout, platformArgs, config, type), requestOption);
  }

  public long updateNginxConfig(int routeKey, int timeout, String config, ConfigType type, IMethodCallback<WebConfigDao.updateNginxConfig_args, WebConfigDao.updateNginxConfig_result> callback) throws TException{
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
            create_updateNginxConfigServiceCall(routeKey, timeout, platformArgs, config, type), callback);
  }

  public long add_updateNginxConfigCall(AsyncCallRunner runner, int routeKey, int timeout, String config, ConfigType type, IMethodCallback<WebConfigDao.updateNginxConfig_args, WebConfigDao.updateNginxConfig_result> callback) throws TException{
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
            create_updateNginxConfigServiceCall(routeKey, timeout, platformArgs, config, type), callback);
  }

  protected TServiceCall create_updateNginxConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String config, ConfigType type){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.updateNginxConfig_args request = new WebConfigDao.updateNginxConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setConfig(config);
    request.setType(type);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateNginxConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.updateNginxConfig_result.class);
    return serviceCall;
  }

  public void send_getLastVersion(int routeKey, int timeout, ConfigType type) throws TException {
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
        create_getLastVersionServiceCall(routeKey, timeout, platformArgs, type), new TRequestOption());
  }

  public void send_getLastVersion(int routeKey, int timeout, ConfigType type,TRequestOption requestOption) throws TException { 
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
        create_getLastVersionServiceCall(routeKey, timeout, platformArgs, type), requestOption);
  }

  public long getLastVersion(int routeKey, int timeout, ConfigType type, IMethodCallback<WebConfigDao.getLastVersion_args, WebConfigDao.getLastVersion_result> callback) throws TException{
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
            create_getLastVersionServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  public long add_getLastVersionCall(AsyncCallRunner runner, int routeKey, int timeout, ConfigType type, IMethodCallback<WebConfigDao.getLastVersion_args, WebConfigDao.getLastVersion_result> callback) throws TException{
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
            create_getLastVersionServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  protected TServiceCall create_getLastVersionServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ConfigType type){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.getLastVersion_args request = new WebConfigDao.getLastVersion_args();
    request.setPlatformArgs(platformArgs);
    request.setType(type);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getLastVersion");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.getLastVersion_result.class);
    return serviceCall;
  }

  public void send_getLastestNginxConfig(int routeKey, int timeout, ConfigType type) throws TException {
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
        create_getLastestNginxConfigServiceCall(routeKey, timeout, platformArgs, type), new TRequestOption());
  }

  public void send_getLastestNginxConfig(int routeKey, int timeout, ConfigType type,TRequestOption requestOption) throws TException { 
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
        create_getLastestNginxConfigServiceCall(routeKey, timeout, platformArgs, type), requestOption);
  }

  public long getLastestNginxConfig(int routeKey, int timeout, ConfigType type, IMethodCallback<WebConfigDao.getLastestNginxConfig_args, WebConfigDao.getLastestNginxConfig_result> callback) throws TException{
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
            create_getLastestNginxConfigServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  public long add_getLastestNginxConfigCall(AsyncCallRunner runner, int routeKey, int timeout, ConfigType type, IMethodCallback<WebConfigDao.getLastestNginxConfig_args, WebConfigDao.getLastestNginxConfig_result> callback) throws TException{
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
            create_getLastestNginxConfigServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  protected TServiceCall create_getLastestNginxConfigServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ConfigType type){
    TServiceCall serviceCall = new TServiceCall();
    if (!getPeerAddr().isEmpty()) {
      serviceCall.setChooseServiceIp(getPeerAddr());
    }
    if (getPeerPort() != 0) {
      serviceCall.setChooseServicePort(getPeerPort());
    }
    serviceCall.setServiceKey(WebConfigDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    WebConfigDao.getLastestNginxConfig_args request = new WebConfigDao.getLastestNginxConfig_args();
    request.setPlatformArgs(platformArgs);
    request.setType(type);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getLastestNginxConfig");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(WebConfigDao.getLastestNginxConfig_result.class);
    return serviceCall;
  }

}
