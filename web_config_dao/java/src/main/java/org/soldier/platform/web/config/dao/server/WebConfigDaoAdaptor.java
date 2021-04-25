package org.soldier.platform.web.config.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.web.config.dao.ConfigType;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.WebConfigList;
import org.soldier.platform.web.config.dao.WebConfigDao;
import org.soldier.platform.web.config.dao.WebConfigDaoVariable;


public abstract class WebConfigDaoAdaptor implements WebConfigDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public WebConfigList queryConfigByPage(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryWebConfigOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"queryConfigByPage",platformArgs);
    return queryConfigByPage(oCntl, option, pageIndex, pageSize);
  }

  protected abstract WebConfigList queryConfigByPage(TServiceCntl oCntl, QueryWebConfigOption option, int pageIndex, int pageSize) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<WebConfig> queryConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QueryWebConfigOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"queryConfig",platformArgs);
    return queryConfig(oCntl, option);
  }

  protected abstract List<WebConfig> queryConfig(TServiceCntl oCntl, QueryWebConfigOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addWebConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, WebConfig config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"addWebConfig",platformArgs);
addWebConfig(oCntl, config);
  }

  protected abstract void addWebConfig(TServiceCntl oCntl, WebConfig config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteWebConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String webProjectName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"deleteWebConfig",platformArgs);
deleteWebConfig(oCntl, webProjectName);
  }

  protected abstract void deleteWebConfig(TServiceCntl oCntl, String webProjectName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateWebConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, WebConfig config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"updateWebConfig",platformArgs);
updateWebConfig(oCntl, config);
  }

  protected abstract void updateWebConfig(TServiceCntl oCntl, WebConfig config) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateNginxConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String config, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"updateNginxConfig",platformArgs);
updateNginxConfig(oCntl, config, type);
  }

  protected abstract void updateNginxConfig(TServiceCntl oCntl, String config, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getLastVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"getLastVersion",platformArgs);
    return getLastVersion(oCntl, type);
  }

  protected abstract int getLastVersion(TServiceCntl oCntl, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public String getLastestNginxConfig(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(WebConfigDaoVariable.serviceKey,"getLastestNginxConfig",platformArgs);
    return getLastestNginxConfig(oCntl, type);
  }

  protected abstract String getLastestNginxConfig(TServiceCntl oCntl, ConfigType type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected WebConfigDaoAdaptor(){
    methodParameterNameMap.put("queryConfigByPage",new String[]{"platformArgs", "option", "pageIndex", "pageSize"});
    methodParameterNameMap.put("queryConfig",new String[]{"platformArgs", "option"});
    methodParameterNameMap.put("addWebConfig",new String[]{"platformArgs", "config"});
    methodParameterNameMap.put("deleteWebConfig",new String[]{"platformArgs", "webProjectName"});
    methodParameterNameMap.put("updateWebConfig",new String[]{"platformArgs", "config"});
    methodParameterNameMap.put("updateNginxConfig",new String[]{"platformArgs", "config", "type"});
    methodParameterNameMap.put("getLastVersion",new String[]{"platformArgs", "type"});
    methodParameterNameMap.put("getLastestNginxConfig",new String[]{"platformArgs", "type"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
