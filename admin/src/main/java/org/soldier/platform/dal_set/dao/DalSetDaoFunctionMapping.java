package org.soldier.platform.dal_set.dao;


import java.util.HashMap;
import java.util.Map; 

public class DalSetDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("queryDalSetHosts",1);
    putEntry("addDalSetHost",2);
    putEntry("updateDalSetHost",3);
    putEntry("deleteDalSetHost",4);
    putEntry("queryDalSetUsers",5);
    putEntry("addDalSetUser",6);
    putEntry("updateDalSetUser",7);
    putEntry("deleteDalSetUser",8);
    putEntry("queryDalSetTables",9);
    putEntry("addDalSetTable",10);
    putEntry("updateDalSetTable",11);
    putEntry("deleteDalSetTable",12);
    putEntry("queryDalSetRoles",13);
    putEntry("addDalSetRole",14);
    putEntry("updateDalSetRole",15);
    putEntry("deleteDalSetRole",16);
    putEntry("addTableRoleRelation",17);
    putEntry("deleteTableRoleRelation",18);
    putEntry("queryTableRoleRelations",19);
    putEntry("queryRoleSetRelations",20);
    putEntry("addRoleSetRelation",21);
    putEntry("deleteRoleSetRelation",22);
    putEntry("updateRoleSetRelation",23);
    putEntry("queryRoleServiceRelations",24);
    putEntry("addRoleServiceRelation",25);
    putEntry("updateRoleServiceRelation",26);
    putEntry("deleteRoleServiceRelation",27);
    putEntry("updateDalSetXml",28);
    putEntry("getLastVersion",29);
    putEntry("getLastXml",30);
  }

  public int getUniqueNumber(String functionName) {
    Integer number = sMapping.get(functionName);
    if (number == null) {
      return -1;    }
    return number.intValue();
  }

  private static void putEntry(String functionName, int uniqueNumber) {
    sMapping.put(functionName, uniqueNumber);
  }

}
