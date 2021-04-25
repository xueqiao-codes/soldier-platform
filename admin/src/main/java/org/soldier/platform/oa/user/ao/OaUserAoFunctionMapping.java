package org.soldier.platform.oa.user.ao;


import java.util.HashMap;
import java.util.Map; 

public class OaUserAoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("login",1);
    putEntry("registerUser",2);
    putEntry("checkSession",3);
    putEntry("updateUser",4);
    putEntry("deleteUser",5);
    putEntry("logout",6);
    putEntry("queryUser",7);
    putEntry("queryUserByPage",8);
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
