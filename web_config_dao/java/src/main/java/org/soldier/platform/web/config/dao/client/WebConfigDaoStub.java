package org.soldier.platform.web.config.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import org.soldier.platform.web.config.dao.ConfigType;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.WebConfigList;
import org.soldier.platform.web.config.dao.WebConfigDao;
import org.soldier.platform.web.config.dao.WebConfigDaoVariable;

public class WebConfigDaoStub extends BaseStub {

  public WebConfigDaoStub() {
    super(WebConfigDaoVariable.serviceKey);
  }

  public WebConfigList  queryConfigByPage(QueryWebConfigOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryConfigByPage(option, pageIndex, pageSize, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public WebConfigList  queryConfigByPage(QueryWebConfigOption option, int pageIndex, int pageSize,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryConfigByPage").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<WebConfigList>(){
    @Override
    public WebConfigList call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new WebConfigDao.Client(protocol).queryConfigByPage(platformArgs, option, pageIndex, pageSize);
      }
    }, invokeInfo);
  }

  public WebConfigList  queryConfigByPage(int routeKey, int timeout,QueryWebConfigOption option, int pageIndex, int pageSize)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryConfigByPage(option, pageIndex, pageSize, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<WebConfig>  queryConfig(QueryWebConfigOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryConfig(option, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<WebConfig>  queryConfig(QueryWebConfigOption option,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("queryConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<WebConfig>>(){
    @Override
    public List<WebConfig> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new WebConfigDao.Client(protocol).queryConfig(platformArgs, option);
      }
    }, invokeInfo);
  }

  public List<WebConfig>  queryConfig(int routeKey, int timeout,QueryWebConfigOption option)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return queryConfig(option, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addWebConfig(WebConfig config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addWebConfig(config, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  addWebConfig(WebConfig config,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("addWebConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new WebConfigDao.Client(protocol).addWebConfig(platformArgs, config);
      return null;
      }
    }, invokeInfo);
  }

  public void  addWebConfig(int routeKey, int timeout,WebConfig config)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    addWebConfig(config, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteWebConfig(String webProjectName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteWebConfig(webProjectName, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  deleteWebConfig(String webProjectName,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("deleteWebConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new WebConfigDao.Client(protocol).deleteWebConfig(platformArgs, webProjectName);
      return null;
      }
    }, invokeInfo);
  }

  public void  deleteWebConfig(int routeKey, int timeout,String webProjectName)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    deleteWebConfig(webProjectName, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateWebConfig(WebConfig config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateWebConfig(config, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateWebConfig(WebConfig config,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateWebConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new WebConfigDao.Client(protocol).updateWebConfig(platformArgs, config);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateWebConfig(int routeKey, int timeout,WebConfig config)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateWebConfig(config, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateNginxConfig(String config, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateNginxConfig(config, type, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public void  updateNginxConfig(String config, ConfigType type,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("updateNginxConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    super.runSync(new ThriftCallable<Void>(){
    @Override
    public Void call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      new WebConfigDao.Client(protocol).updateNginxConfig(platformArgs, config, type);
      return null;
      }
    }, invokeInfo);
  }

  public void  updateNginxConfig(int routeKey, int timeout,String config, ConfigType type)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    updateNginxConfig(config, type, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getLastVersion(ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastVersion(type, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public int  getLastVersion(ConfigType type,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getLastVersion").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<Integer>(){
    @Override
    public Integer call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new WebConfigDao.Client(protocol).getLastVersion(platformArgs, type);
      }
    }, invokeInfo);
  }

  public int  getLastVersion(int routeKey, int timeout,ConfigType type)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastVersion(type, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public String  getLastestNginxConfig(ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastestNginxConfig(type, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public String  getLastestNginxConfig(ConfigType type,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getLastestNginxConfig").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<String>(){
    @Override
    public String call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new WebConfigDao.Client(protocol).getLastestNginxConfig(platformArgs, type);
      }
    }, invokeInfo);
  }

  public String  getLastestNginxConfig(int routeKey, int timeout,ConfigType type)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getLastestNginxConfig(type, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
