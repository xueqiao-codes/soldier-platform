package org.soldier.platform.oa.session.dao;


import java.util.HashMap;
import java.util.Map; 

public class OaSessionDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("updateSession",1);
    putEntry("getSession",2);
    putEntry("deleteSession",3);
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
