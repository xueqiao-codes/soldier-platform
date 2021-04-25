package org.soldier.platform.proxy.test.ao;


import java.util.HashMap;
import java.util.Map; 

public class proxy_test_aoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("testEcho",1);
    putEntry("testEchoList",2);
    putEntry("testEchoListStruct",3);
    putEntry("echoTypes",4);
    putEntry("testEchoSet",5);
    putEntry("testEchoMap",6);
    putEntry("testEchoTypesList",7);
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
