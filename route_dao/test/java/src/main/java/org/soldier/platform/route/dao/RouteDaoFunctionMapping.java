package org.soldier.platform.route.dao;


import java.util.HashMap;
import java.util.Map; 

public class RouteDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("addRoute",1);
    putEntry("updateRoute",2);
    putEntry("deleteRoute",3);
    putEntry("queryRouteInfoList",4);
    putEntry("syncRoute",5);
    putEntry("getLastRouteVersion",6);
    putEntry("getAllSimpleRouteInfo",7);
    putEntry("getLastestRouteConfig",8);
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
