/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.soldier.framework.message_bus.swig;

public class MessageAgentSwig {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected MessageAgentSwig(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MessageAgentSwig obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MessageAgentBridgeJNI.delete_MessageAgentSwig(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public static void initNativeLogName(String name) {
    MessageAgentBridgeJNI.MessageAgentSwig_initNativeLogName(name);
  }

  public static boolean initMessageAgent(String agent_name, String message_graph_path, IMessageConsumerSwig consumer) {
    return MessageAgentBridgeJNI.MessageAgentSwig_initMessageAgent(agent_name, message_graph_path, IMessageConsumerSwig.getCPtr(consumer), consumer);
  }

  public static void destroyMessageAgent(String agent_name) {
    MessageAgentBridgeJNI.MessageAgentSwig_destroyMessageAgent(agent_name);
  }

  public static String prepareGuardMessage(String agent_name, String topic, byte[] data, GuardPolicySwig policy) {
    return MessageAgentBridgeJNI.MessageAgentSwig_prepareGuardMessage(agent_name, topic, data, GuardPolicySwig.getCPtr(policy), policy);
  }

  public static boolean sendMessageDirectly(String agent_name, String topic, byte[] data) {
    return MessageAgentBridgeJNI.MessageAgentSwig_sendMessageDirectly(agent_name, topic, data);
  }

  public static boolean sendMessageAndRmGuard(String agent_name, String topic, byte[] data, String guard_id) {
    return MessageAgentBridgeJNI.MessageAgentSwig_sendMessageAndRmGuard(agent_name, topic, data, guard_id);
  }

  public static boolean rmGuardMessage(String agent_name, String guard_id) {
    return MessageAgentBridgeJNI.MessageAgentSwig_rmGuardMessage(agent_name, guard_id);
  }

  public MessageAgentSwig() {
    this(MessageAgentBridgeJNI.new_MessageAgentSwig(), true);
  }

}
