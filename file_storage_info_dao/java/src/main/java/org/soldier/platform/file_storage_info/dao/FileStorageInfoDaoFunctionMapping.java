package org.soldier.platform.file_storage_info.dao;


import java.util.HashMap;
import java.util.Map; 

public class FileStorageInfoDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("addStorage",1);
    putEntry("queryStorageList",2);
    putEntry("deleteStorage",3);
    putEntry("updateStorage",4);
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
