package org.soldier.platform.dal_set.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryDalSetRoleOption;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.QueryRoleServiceRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleSetRelationOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleServiceRelationList;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelationList;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.DalSetDao;
import org.soldier.platform.dal_set.dao.DalSetDaoVariable;


public abstract class DalSetDaoAdaptor implements DalSetDao.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public DalSetHostList queryDalSetHosts(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetHostOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryDalSetHosts",platformArgs);
    return queryDalSetHosts(oCntl, pageIndex, pageSize, option);
  }

  protected abstract DalSetHostList queryDalSetHosts(TServiceCntl oCntl, int pageIndex, int pageSize, QueryDalSetHostOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addDalSetHost(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetHost host) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addDalSetHost",platformArgs);
addDalSetHost(oCntl, host);
  }

  protected abstract void addDalSetHost(TServiceCntl oCntl, DalSetHost host) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDalSetHost(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetHost host) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateDalSetHost",platformArgs);
updateDalSetHost(oCntl, host);
  }

  protected abstract void updateDalSetHost(TServiceCntl oCntl, DalSetHost host) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteDalSetHost(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String hostKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteDalSetHost",platformArgs);
deleteDalSetHost(oCntl, hostKey);
  }

  protected abstract void deleteDalSetHost(TServiceCntl oCntl, String hostKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public DalSetUserList queryDalSetUsers(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetUserOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryDalSetUsers",platformArgs);
    return queryDalSetUsers(oCntl, pageIndex, pageSize, option);
  }

  protected abstract DalSetUserList queryDalSetUsers(TServiceCntl oCntl, int pageIndex, int pageSize, QueryDalSetUserOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addDalSetUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addDalSetUser",platformArgs);
addDalSetUser(oCntl, user);
  }

  protected abstract void addDalSetUser(TServiceCntl oCntl, DalSetUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDalSetUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateDalSetUser",platformArgs);
updateDalSetUser(oCntl, user);
  }

  protected abstract void updateDalSetUser(TServiceCntl oCntl, DalSetUser user) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteDalSetUser(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String userKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteDalSetUser",platformArgs);
deleteDalSetUser(oCntl, userKey);
  }

  protected abstract void deleteDalSetUser(TServiceCntl oCntl, String userKey) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public DalSetTableList queryDalSetTables(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetTableOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryDalSetTables",platformArgs);
    return queryDalSetTables(oCntl, pageIndex, pageSize, option);
  }

  protected abstract DalSetTableList queryDalSetTables(TServiceCntl oCntl, int pageIndex, int pageSize, QueryDalSetTableOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addDalSetTable(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetTable table) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addDalSetTable",platformArgs);
addDalSetTable(oCntl, table);
  }

  protected abstract void addDalSetTable(TServiceCntl oCntl, DalSetTable table) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDalSetTable(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetTable table) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateDalSetTable",platformArgs);
updateDalSetTable(oCntl, table);
  }

  protected abstract void updateDalSetTable(TServiceCntl oCntl, DalSetTable table) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteDalSetTable(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String tablePrefixName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteDalSetTable",platformArgs);
deleteDalSetTable(oCntl, tablePrefixName);
  }

  protected abstract void deleteDalSetTable(TServiceCntl oCntl, String tablePrefixName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public DalSetRoleList queryDalSetRoles(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryDalSetRoleOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryDalSetRoles",platformArgs);
    return queryDalSetRoles(oCntl, pageIndex, pageSize, option);
  }

  protected abstract DalSetRoleList queryDalSetRoles(TServiceCntl oCntl, int pageIndex, int pageSize, QueryDalSetRoleOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addDalSetRole(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetRole role) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addDalSetRole",platformArgs);
addDalSetRole(oCntl, role);
  }

  protected abstract void addDalSetRole(TServiceCntl oCntl, DalSetRole role) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDalSetRole(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, DalSetRole role) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateDalSetRole",platformArgs);
updateDalSetRole(oCntl, role);
  }

  protected abstract void updateDalSetRole(TServiceCntl oCntl, DalSetRole role) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteDalSetRole(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String roleName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteDalSetRole",platformArgs);
deleteDalSetRole(oCntl, roleName);
  }

  protected abstract void deleteDalSetRole(TServiceCntl oCntl, String roleName) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addTableRoleRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleTableRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addTableRoleRelation",platformArgs);
addTableRoleRelation(oCntl, relation);
  }

  protected abstract void addTableRoleRelation(TServiceCntl oCntl, RoleTableRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteTableRoleRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleTableRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteTableRoleRelation",platformArgs);
deleteTableRoleRelation(oCntl, relation);
  }

  protected abstract void deleteTableRoleRelation(TServiceCntl oCntl, RoleTableRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public RoleTableRelationList queryTableRoleRelations(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRoleTableRelationOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryTableRoleRelations",platformArgs);
    return queryTableRoleRelations(oCntl, pageIndex, pageSize, option);
  }

  protected abstract RoleTableRelationList queryTableRoleRelations(TServiceCntl oCntl, int pageIndex, int pageSize, QueryRoleTableRelationOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public RoleSetRelationList queryRoleSetRelations(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRoleSetRelationOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryRoleSetRelations",platformArgs);
    return queryRoleSetRelations(oCntl, pageIndex, pageSize, option);
  }

  protected abstract RoleSetRelationList queryRoleSetRelations(TServiceCntl oCntl, int pageIndex, int pageSize, QueryRoleSetRelationOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addRoleSetRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleSetRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addRoleSetRelation",platformArgs);
addRoleSetRelation(oCntl, relation);
  }

  protected abstract void addRoleSetRelation(TServiceCntl oCntl, RoleSetRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteRoleSetRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleSetRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteRoleSetRelation",platformArgs);
deleteRoleSetRelation(oCntl, relation);
  }

  protected abstract void deleteRoleSetRelation(TServiceCntl oCntl, RoleSetRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateRoleSetRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleSetRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateRoleSetRelation",platformArgs);
updateRoleSetRelation(oCntl, relation);
  }

  protected abstract void updateRoleSetRelation(TServiceCntl oCntl, RoleSetRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public RoleServiceRelationList queryRoleServiceRelations(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryRoleServiceRelationOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"queryRoleServiceRelations",platformArgs);
    return queryRoleServiceRelations(oCntl, pageIndex, pageSize, option);
  }

  protected abstract RoleServiceRelationList queryRoleServiceRelations(TServiceCntl oCntl, int pageIndex, int pageSize, QueryRoleServiceRelationOption option) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void addRoleServiceRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleServiceRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"addRoleServiceRelation",platformArgs);
addRoleServiceRelation(oCntl, relation);
  }

  protected abstract void addRoleServiceRelation(TServiceCntl oCntl, RoleServiceRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateRoleServiceRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleServiceRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateRoleServiceRelation",platformArgs);
updateRoleServiceRelation(oCntl, relation);
  }

  protected abstract void updateRoleServiceRelation(TServiceCntl oCntl, RoleServiceRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void deleteRoleServiceRelation(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, RoleServiceRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"deleteRoleServiceRelation",platformArgs);
deleteRoleServiceRelation(oCntl, relation);
  }

  protected abstract void deleteRoleServiceRelation(TServiceCntl oCntl, RoleServiceRelation relation) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public void updateDalSetXml(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String xml) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"updateDalSetXml",platformArgs);
updateDalSetXml(oCntl, xml);
  }

  protected abstract void updateDalSetXml(TServiceCntl oCntl, String xml) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public int getLastVersion(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"getLastVersion",platformArgs);
    return getLastVersion(oCntl);
  }

  protected abstract int getLastVersion(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  @Override
  public String getLastXml(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(DalSetDaoVariable.serviceKey,"getLastXml",platformArgs);
    return getLastXml(oCntl);
  }

  protected abstract String getLastXml(TServiceCntl oCntl) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected DalSetDaoAdaptor(){
    methodParameterNameMap.put("queryDalSetHosts",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("addDalSetHost",new String[]{"platformArgs", "host"});
    methodParameterNameMap.put("updateDalSetHost",new String[]{"platformArgs", "host"});
    methodParameterNameMap.put("deleteDalSetHost",new String[]{"platformArgs", "hostKey"});
    methodParameterNameMap.put("queryDalSetUsers",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("addDalSetUser",new String[]{"platformArgs", "user"});
    methodParameterNameMap.put("updateDalSetUser",new String[]{"platformArgs", "user"});
    methodParameterNameMap.put("deleteDalSetUser",new String[]{"platformArgs", "userKey"});
    methodParameterNameMap.put("queryDalSetTables",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("addDalSetTable",new String[]{"platformArgs", "table"});
    methodParameterNameMap.put("updateDalSetTable",new String[]{"platformArgs", "table"});
    methodParameterNameMap.put("deleteDalSetTable",new String[]{"platformArgs", "tablePrefixName"});
    methodParameterNameMap.put("queryDalSetRoles",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("addDalSetRole",new String[]{"platformArgs", "role"});
    methodParameterNameMap.put("updateDalSetRole",new String[]{"platformArgs", "role"});
    methodParameterNameMap.put("deleteDalSetRole",new String[]{"platformArgs", "roleName"});
    methodParameterNameMap.put("addTableRoleRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("deleteTableRoleRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("queryTableRoleRelations",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("queryRoleSetRelations",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("addRoleSetRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("deleteRoleSetRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("updateRoleSetRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("queryRoleServiceRelations",new String[]{"platformArgs", "pageIndex", "pageSize", "option"});
    methodParameterNameMap.put("addRoleServiceRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("updateRoleServiceRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("deleteRoleServiceRelation",new String[]{"platformArgs", "relation"});
    methodParameterNameMap.put("updateDalSetXml",new String[]{"platformArgs", "xml"});
    methodParameterNameMap.put("getLastVersion",new String[]{"platformArgs"});
    methodParameterNameMap.put("getLastXml",new String[]{"platformArgs"});
  }
  protected abstract int InitApp(final Properties props);

  protected abstract void destroy();

}
