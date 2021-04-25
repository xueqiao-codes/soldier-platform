package org.soldier.platform.msgq.dao;


import java.util.HashMap;
import java.util.Map; 

public class MsgQManageDaoFunctionMapping {

  private static Map<String, Integer> sMapping = new HashMap<String, Integer>();

  static {
    putEntry("queryMsgQClusters",1);
    putEntry("addMsgQCluster",2);
    putEntry("updateMsgQCluster",3);
    putEntry("deleteMsgQCluster",4);
    putEntry("queryMsgQTopics",5);
    putEntry("addMsgQTopic",6);
    putEntry("updateMsgQTopic",7);
    putEntry("deleteMsgQTopic",8);
    putEntry("queryMsgQConsumers",9);
    putEntry("addMsgQConsumer",10);
    putEntry("updateMsgQConsumer",11);
    putEntry("deleteMsgQConsumer",12);
    putEntry("queryMsgQProducerList",13);
    putEntry("addMsgQProducer",14);
    putEntry("updateMsgQProducer",15);
    putEntry("deleteMsgQProducer",16);
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
