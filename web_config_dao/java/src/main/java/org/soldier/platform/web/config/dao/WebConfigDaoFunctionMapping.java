package org.soldier.platform.web.config.dao;


import java.util.HashMap;
import java.util.Map; 

public class WebConfigDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("queryConfigByPage",1);
    putEntry("queryConfig",2);
    putEntry("addWebConfig",3);
    putEntry("deleteWebConfig",4);
    putEntry("updateWebConfig",5);
    putEntry("updateNginxConfig",6);
    putEntry("getLastVersion",7);
    putEntry("getLastestNginxConfig",8);
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
