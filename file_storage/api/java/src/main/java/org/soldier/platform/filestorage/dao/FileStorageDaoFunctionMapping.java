package org.soldier.platform.filestorage.dao;


import java.util.HashMap;
import java.util.Map; 

public class FileStorageDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("readFile",1);
    putEntry("writeFile",2);
    putEntry("deleteFile",3);
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
