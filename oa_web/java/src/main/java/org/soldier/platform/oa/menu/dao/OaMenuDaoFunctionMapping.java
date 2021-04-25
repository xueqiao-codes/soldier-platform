package org.soldier.platform.oa.menu.dao;


import java.util.HashMap;
import java.util.Map; 

public class OaMenuDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("getSystemMenus",1);
    putEntry("getSubMenus",2);
    putEntry("addSystemMenu",3);
    putEntry("deleteSystemMenu",4);
    putEntry("addSubMenu",5);
    putEntry("deleteSubMenu",6);
    putEntry("updateSubMenu",7);
  }

  public static int getUniqueNumber(String functionName) {
    Integer number = sMapping.get(functionName);
    if (number == null) {
      return -1;    }
    return number.intValue();
  }

  private static void putEntry(String functionName, int uniqueNumber) {
    sMapping.put(functionName, uniqueNumber);
  }

}
