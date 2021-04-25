package org.soldier.platform.machine;


import java.util.HashMap;
import java.util.Map; 

public class MachineManageBoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("queryMachineList",1);
    putEntry("addMachine",2);
    putEntry("updateMachine",3);
    putEntry("deleteMachine",4);
    putEntry("updateMachineRelatedMonitor",5);
    putEntry("deleteMachineRelatedMonitor",6);
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
