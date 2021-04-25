package org.soldier.platform.oa.menu.dao.client;

import org.soldier.platform.oa.menu.dao.OaMenuDao;
import org.soldier.platform.oa.menu.dao.OaMenuDaoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;

public class OaMenuDaoAsyncStub { 
  private String peerAddr;

  public void setPeerAddr(final String ipAddr) { 
    if (ipAddr == null) { 
      peerAddr = null; 
    }
    if (-1l != NetHelper.AddrNet(ipAddr)) {
      peerAddr = ipAddr; 
    }
  }

  public String getPeerAddr() { 
    return peerAddr;
  }

  public void send_getSystemMenus(int routeKey, int timeout, QuerySystemMenuOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getSystemMenusServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_getSystemMenus(int routeKey, int timeout, QuerySystemMenuOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getSystemMenusServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long getSystemMenus(int routeKey, int timeout, QuerySystemMenuOption option, IMethodCallback<OaMenuDao.getSystemMenus_args, OaMenuDao.getSystemMenus_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_getSystemMenusServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_getSystemMenusCall(AsyncCallRunner runner, int routeKey, int timeout, QuerySystemMenuOption option, IMethodCallback<OaMenuDao.getSystemMenus_args, OaMenuDao.getSystemMenus_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_getSystemMenusServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_getSystemMenusServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QuerySystemMenuOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.getSystemMenus_args request = new OaMenuDao.getSystemMenus_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSystemMenus");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.getSystemMenus_result.class);
    return serviceCall;
  }

  public void send_getSubMenus(int routeKey, int timeout, QuerySubMenuOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getSubMenusServiceCall(routeKey, timeout, platformArgs, option), new TRequestOption());
  }

  public void send_getSubMenus(int routeKey, int timeout, QuerySubMenuOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_getSubMenusServiceCall(routeKey, timeout, platformArgs, option), requestOption);
  }

  public long getSubMenus(int routeKey, int timeout, QuerySubMenuOption option, IMethodCallback<OaMenuDao.getSubMenus_args, OaMenuDao.getSubMenus_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_getSubMenusServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  public long add_getSubMenusCall(AsyncCallRunner runner, int routeKey, int timeout, QuerySubMenuOption option, IMethodCallback<OaMenuDao.getSubMenus_args, OaMenuDao.getSubMenus_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_getSubMenusServiceCall(routeKey, timeout, platformArgs, option), callback);
  }

  protected TServiceCall create_getSubMenusServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, QuerySubMenuOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.getSubMenus_args request = new OaMenuDao.getSubMenus_args();
    request.setPlatformArgs(platformArgs);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("getSubMenus");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.getSubMenus_result.class);
    return serviceCall;
  }

  public void send_addSystemMenu(int routeKey, int timeout, TSystemMenu menu) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), new TRequestOption());
  }

  public void send_addSystemMenu(int routeKey, int timeout, TSystemMenu menu,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), requestOption);
  }

  public long addSystemMenu(int routeKey, int timeout, TSystemMenu menu, IMethodCallback<OaMenuDao.addSystemMenu_args, OaMenuDao.addSystemMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  public long add_addSystemMenuCall(AsyncCallRunner runner, int routeKey, int timeout, TSystemMenu menu, IMethodCallback<OaMenuDao.addSystemMenu_args, OaMenuDao.addSystemMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  protected TServiceCall create_addSystemMenuServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSystemMenu menu){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.addSystemMenu_args request = new OaMenuDao.addSystemMenu_args();
    request.setPlatformArgs(platformArgs);
    request.setMenu(menu);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addSystemMenu");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.addSystemMenu_result.class);
    return serviceCall;
  }

  public void send_deleteSystemMenu(int routeKey, int timeout, int systemMenuId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteSystemMenuServiceCall(routeKey, timeout, platformArgs, systemMenuId), new TRequestOption());
  }

  public void send_deleteSystemMenu(int routeKey, int timeout, int systemMenuId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteSystemMenuServiceCall(routeKey, timeout, platformArgs, systemMenuId), requestOption);
  }

  public long deleteSystemMenu(int routeKey, int timeout, int systemMenuId, IMethodCallback<OaMenuDao.deleteSystemMenu_args, OaMenuDao.deleteSystemMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteSystemMenuServiceCall(routeKey, timeout, platformArgs, systemMenuId), callback);
  }

  public long add_deleteSystemMenuCall(AsyncCallRunner runner, int routeKey, int timeout, int systemMenuId, IMethodCallback<OaMenuDao.deleteSystemMenu_args, OaMenuDao.deleteSystemMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteSystemMenuServiceCall(routeKey, timeout, platformArgs, systemMenuId), callback);
  }

  protected TServiceCall create_deleteSystemMenuServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int systemMenuId){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.deleteSystemMenu_args request = new OaMenuDao.deleteSystemMenu_args();
    request.setPlatformArgs(platformArgs);
    request.setSystemMenuId(systemMenuId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSystemMenu");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.deleteSystemMenu_result.class);
    return serviceCall;
  }

  public void send_addSubMenu(int routeKey, int timeout, TSubMenu menu) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addSubMenuServiceCall(routeKey, timeout, platformArgs, menu), new TRequestOption());
  }

  public void send_addSubMenu(int routeKey, int timeout, TSubMenu menu,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_addSubMenuServiceCall(routeKey, timeout, platformArgs, menu), requestOption);
  }

  public long addSubMenu(int routeKey, int timeout, TSubMenu menu, IMethodCallback<OaMenuDao.addSubMenu_args, OaMenuDao.addSubMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_addSubMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  public long add_addSubMenuCall(AsyncCallRunner runner, int routeKey, int timeout, TSubMenu menu, IMethodCallback<OaMenuDao.addSubMenu_args, OaMenuDao.addSubMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_addSubMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  protected TServiceCall create_addSubMenuServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSubMenu menu){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.addSubMenu_args request = new OaMenuDao.addSubMenu_args();
    request.setPlatformArgs(platformArgs);
    request.setMenu(menu);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addSubMenu");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.addSubMenu_result.class);
    return serviceCall;
  }

  public void send_deleteSubMenu(int routeKey, int timeout, int menuId) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteSubMenuServiceCall(routeKey, timeout, platformArgs, menuId), new TRequestOption());
  }

  public void send_deleteSubMenu(int routeKey, int timeout, int menuId,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_deleteSubMenuServiceCall(routeKey, timeout, platformArgs, menuId), requestOption);
  }

  public long deleteSubMenu(int routeKey, int timeout, int menuId, IMethodCallback<OaMenuDao.deleteSubMenu_args, OaMenuDao.deleteSubMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_deleteSubMenuServiceCall(routeKey, timeout, platformArgs, menuId), callback);
  }

  public long add_deleteSubMenuCall(AsyncCallRunner runner, int routeKey, int timeout, int menuId, IMethodCallback<OaMenuDao.deleteSubMenu_args, OaMenuDao.deleteSubMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_deleteSubMenuServiceCall(routeKey, timeout, platformArgs, menuId), callback);
  }

  protected TServiceCall create_deleteSubMenuServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int menuId){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.deleteSubMenu_args request = new OaMenuDao.deleteSubMenu_args();
    request.setPlatformArgs(platformArgs);
    request.setMenuId(menuId);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteSubMenu");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.deleteSubMenu_result.class);
    return serviceCall;
  }

  public void send_updateSubMenu(int routeKey, int timeout, TSubMenu menu) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateSubMenuServiceCall(routeKey, timeout, platformArgs, menu), new TRequestOption());
  }

  public void send_updateSubMenu(int routeKey, int timeout, TSubMenu menu,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateSubMenuServiceCall(routeKey, timeout, platformArgs, menu), requestOption);
  }

  public long updateSubMenu(int routeKey, int timeout, TSubMenu menu, IMethodCallback<OaMenuDao.updateSubMenu_args, OaMenuDao.updateSubMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateSubMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  public long add_updateSubMenuCall(AsyncCallRunner runner, int routeKey, int timeout, TSubMenu menu, IMethodCallback<OaMenuDao.updateSubMenu_args, OaMenuDao.updateSubMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateSubMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  protected TServiceCall create_updateSubMenuServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSubMenu menu){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.updateSubMenu_args request = new OaMenuDao.updateSubMenu_args();
    request.setPlatformArgs(platformArgs);
    request.setMenu(menu);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSubMenu");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.updateSubMenu_result.class);
    return serviceCall;
  }

  public void send_updateSystemMenu(int routeKey, int timeout, TSystemMenu menu) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), new TRequestOption());
  }

  public void send_updateSystemMenu(int routeKey, int timeout, TSystemMenu menu,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        create_updateSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), requestOption);
  }

  public long updateSystemMenu(int routeKey, int timeout, TSystemMenu menu, IMethodCallback<OaMenuDao.updateSystemMenu_args, OaMenuDao.updateSystemMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            create_updateSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  public long add_updateSystemMenuCall(AsyncCallRunner runner, int routeKey, int timeout, TSystemMenu menu, IMethodCallback<OaMenuDao.updateSystemMenu_args, OaMenuDao.updateSystemMenu_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            create_updateSystemMenuServiceCall(routeKey, timeout, platformArgs, menu), callback);
  }

  protected TServiceCall create_updateSystemMenuServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, TSystemMenu menu){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(OaMenuDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    OaMenuDao.updateSystemMenu_args request = new OaMenuDao.updateSystemMenu_args();
    request.setPlatformArgs(platformArgs);
    request.setMenu(menu);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateSystemMenu");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(OaMenuDao.updateSystemMenu_result.class);
    return serviceCall;
  }

}
