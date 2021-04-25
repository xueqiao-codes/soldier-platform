package org.soldier.platform.oa.user.dao;


import java.util.HashMap;
import java.util.Map; 

public class OaUserDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("queryUserByPage",1);
    putEntry("addUser",2);
    putEntry("updateUser",3);
    putEntry("deleteUser",4);
    putEntry("queryUser",5);
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
