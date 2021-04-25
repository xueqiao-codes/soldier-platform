package org.soldier.platform.oa.menu.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;
import org.soldier.platform.oa.menu.dao.OaMenuDao;
import org.soldier.platform.oa.menu.dao.OaMenuDaoVariable;


public abstract class OaMenuDaoAdaptor implements OaMenuDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public List<TSystemMenu> getSystemMenus(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QuerySystemMenuOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"getSystemMenus",platformArgs);
    return getSystemMenus(oCntl, option);
  }

  protected abstract List<TSystemMenu> getSystemMenus(TServiceCntl oCntl, QuerySystemMenuOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public List<TSubMenu> getSubMenus(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QuerySubMenuOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"getSubMenus",platformArgs);
    return getSubMenus(oCntl, option);
  }

  protected abstract List<TSubMenu> getSubMenus(TServiceCntl oCntl, QuerySubMenuOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int addSystemMenu(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSystemMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"addSystemMenu",platformArgs);
    return addSystemMenu(oCntl, menu);
  }

  protected abstract int addSystemMenu(TServiceCntl oCntl, TSystemMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSystemMenu(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int systemMenuId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"deleteSystemMenu",platformArgs);
deleteSystemMenu(oCntl, systemMenuId);
  }

  protected abstract void deleteSystemMenu(TServiceCntl oCntl, int systemMenuId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int addSubMenu(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSubMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"addSubMenu",platformArgs);
    return addSubMenu(oCntl, menu);
  }

  protected abstract int addSubMenu(TServiceCntl oCntl, TSubMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteSubMenu(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int menuId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"deleteSubMenu",platformArgs);
deleteSubMenu(oCntl, menuId);
  }

  protected abstract void deleteSubMenu(TServiceCntl oCntl, int menuId) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateSubMenu(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSubMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"updateSubMenu",platformArgs);
updateSubMenu(oCntl, menu);
  }

  protected abstract void updateSubMenu(TServiceCntl oCntl, TSubMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateSystemMenu(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSystemMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(OaMenuDaoVariable.serviceKey,"updateSystemMenu",platformArgs);
updateSystemMenu(oCntl, menu);
  }

  protected abstract void updateSystemMenu(TServiceCntl oCntl, TSystemMenu menu) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected OaMenuDaoAdaptor(){
    methodParameterNameMap.put("getSystemMenus",new String[]{"platformArgs", "option"});
    methodParameterNameMap.put("getSubMenus",new String[]{"platformArgs", "option"});
    methodParameterNameMap.put("addSystemMenu",new String[]{"platformArgs", "menu"});
    methodParameterNameMap.put("deleteSystemMenu",new String[]{"platformArgs", "systemMenuId"});
    methodParameterNameMap.put("addSubMenu",new String[]{"platformArgs", "menu"});
    methodParameterNameMap.put("deleteSubMenu",new String[]{"platformArgs", "menuId"});
    methodParameterNameMap.put("updateSubMenu",new String[]{"platformArgs", "menu"});
    methodParameterNameMap.put("updateSystemMenu",new String[]{"platformArgs", "menu"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
