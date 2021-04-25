package org.soldier.platform.msgq.dao.client;

import org.soldier.platform.msgq.dao.MsgQManageDao;
import org.soldier.platform.msgq.dao.MsgQManageDaoVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.msgq.dao.MsgQCluster;
import org.soldier.platform.msgq.dao.MsgQClusterList;
import org.soldier.platform.msgq.dao.MsgQComsumerList;
import org.soldier.platform.msgq.dao.MsgQConsumer;
import org.soldier.platform.msgq.dao.MsgQProducer;
import org.soldier.platform.msgq.dao.MsgQProducerList;
import org.soldier.platform.msgq.dao.MsgQTopic;
import org.soldier.platform.msgq.dao.MsgQTopicList;
import org.soldier.platform.msgq.dao.QueryMsgQClusterOption;
import org.soldier.platform.msgq.dao.QueryMsgQConsumerOption;
import org.soldier.platform.msgq.dao.QueryMsgQProducerOption;
import org.soldier.platform.msgq.dao.QueryMsgQTopicOption;

public class MsgQManageDaoAsyncStub { 
  private String peerAddr;

  public void setPeerAddr(final String ipAddr) { 
    if (ipAddr == null) { 
      peerAddr = null; 
    }
    if (-1l != NetHelper.AddrNet(ipAddr)) {
      peerAddr = ipAddr; 
    }
  }

  public String getPeerAddr() { 
    return peerAddr;
  }

  public void send_queryMsgQClusters(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQClusterOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQClustersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryMsgQClusters(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQClusterOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQClustersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryMsgQClusters(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQClusterOption option, IMethodCallback<MsgQManageDao.queryMsgQClusters_args, MsgQManageDao.queryMsgQClusters_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_queryMsgQClustersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryMsgQClustersCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQClusterOption option, IMethodCallback<MsgQManageDao.queryMsgQClusters_args, MsgQManageDao.queryMsgQClusters_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_queryMsgQClustersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryMsgQClustersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryMsgQClusterOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.queryMsgQClusters_args request = new MsgQManageDao.queryMsgQClusters_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryMsgQClusters");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.queryMsgQClusters_result.class);
    return serviceCall;
  }

  public void send_addMsgQCluster(int routeKey, int timeout, MsgQCluster cluster) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), new TRequestOption());
  }

  public void send_addMsgQCluster(int routeKey, int timeout, MsgQCluster cluster,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), requestOption);
  }

  public long addMsgQCluster(int routeKey, int timeout, MsgQCluster cluster, IMethodCallback<MsgQManageDao.addMsgQCluster_args, MsgQManageDao.addMsgQCluster_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_addMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), callback);
  }

  public long add_addMsgQClusterCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQCluster cluster, IMethodCallback<MsgQManageDao.addMsgQCluster_args, MsgQManageDao.addMsgQCluster_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_addMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), callback);
  }

  protected TServiceCall create_addMsgQClusterServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQCluster cluster){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.addMsgQCluster_args request = new MsgQManageDao.addMsgQCluster_args();
    request.setPlatformArgs(platformArgs);
    request.setCluster(cluster);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addMsgQCluster");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.addMsgQCluster_result.class);
    return serviceCall;
  }

  public void send_updateMsgQCluster(int routeKey, int timeout, MsgQCluster cluster) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), new TRequestOption());
  }

  public void send_updateMsgQCluster(int routeKey, int timeout, MsgQCluster cluster,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), requestOption);
  }

  public long updateMsgQCluster(int routeKey, int timeout, MsgQCluster cluster, IMethodCallback<MsgQManageDao.updateMsgQCluster_args, MsgQManageDao.updateMsgQCluster_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_updateMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), callback);
  }

  public long add_updateMsgQClusterCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQCluster cluster, IMethodCallback<MsgQManageDao.updateMsgQCluster_args, MsgQManageDao.updateMsgQCluster_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_updateMsgQClusterServiceCall(routeKey, timeout, platformArgs, cluster), callback);
  }

  protected TServiceCall create_updateMsgQClusterServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQCluster cluster){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.updateMsgQCluster_args request = new MsgQManageDao.updateMsgQCluster_args();
    request.setPlatformArgs(platformArgs);
    request.setCluster(cluster);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateMsgQCluster");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.updateMsgQCluster_result.class);
    return serviceCall;
  }

  public void send_deleteMsgQCluster(int routeKey, int timeout, String clusterName) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQClusterServiceCall(routeKey, timeout, platformArgs, clusterName), new TRequestOption());
  }

  public void send_deleteMsgQCluster(int routeKey, int timeout, String clusterName,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQClusterServiceCall(routeKey, timeout, platformArgs, clusterName), requestOption);
  }

  public long deleteMsgQCluster(int routeKey, int timeout, String clusterName, IMethodCallback<MsgQManageDao.deleteMsgQCluster_args, MsgQManageDao.deleteMsgQCluster_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_deleteMsgQClusterServiceCall(routeKey, timeout, platformArgs, clusterName), callback);
  }

  public long add_deleteMsgQClusterCall(AsyncCallRunner runner, int routeKey, int timeout, String clusterName, IMethodCallback<MsgQManageDao.deleteMsgQCluster_args, MsgQManageDao.deleteMsgQCluster_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_deleteMsgQClusterServiceCall(routeKey, timeout, platformArgs, clusterName), callback);
  }

  protected TServiceCall create_deleteMsgQClusterServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String clusterName){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.deleteMsgQCluster_args request = new MsgQManageDao.deleteMsgQCluster_args();
    request.setPlatformArgs(platformArgs);
    request.setClusterName(clusterName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteMsgQCluster");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.deleteMsgQCluster_result.class);
    return serviceCall;
  }

  public void send_queryMsgQTopics(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQTopicOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQTopicsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryMsgQTopics(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQTopicOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQTopicsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryMsgQTopics(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQTopicOption option, IMethodCallback<MsgQManageDao.queryMsgQTopics_args, MsgQManageDao.queryMsgQTopics_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_queryMsgQTopicsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryMsgQTopicsCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQTopicOption option, IMethodCallback<MsgQManageDao.queryMsgQTopics_args, MsgQManageDao.queryMsgQTopics_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_queryMsgQTopicsServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryMsgQTopicsServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryMsgQTopicOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.queryMsgQTopics_args request = new MsgQManageDao.queryMsgQTopics_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryMsgQTopics");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.queryMsgQTopics_result.class);
    return serviceCall;
  }

  public void send_addMsgQTopic(int routeKey, int timeout, MsgQTopic topic) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), new TRequestOption());
  }

  public void send_addMsgQTopic(int routeKey, int timeout, MsgQTopic topic,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), requestOption);
  }

  public long addMsgQTopic(int routeKey, int timeout, MsgQTopic topic, IMethodCallback<MsgQManageDao.addMsgQTopic_args, MsgQManageDao.addMsgQTopic_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_addMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), callback);
  }

  public long add_addMsgQTopicCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQTopic topic, IMethodCallback<MsgQManageDao.addMsgQTopic_args, MsgQManageDao.addMsgQTopic_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_addMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), callback);
  }

  protected TServiceCall create_addMsgQTopicServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQTopic topic){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.addMsgQTopic_args request = new MsgQManageDao.addMsgQTopic_args();
    request.setPlatformArgs(platformArgs);
    request.setTopic(topic);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addMsgQTopic");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.addMsgQTopic_result.class);
    return serviceCall;
  }

  public void send_updateMsgQTopic(int routeKey, int timeout, MsgQTopic topic) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), new TRequestOption());
  }

  public void send_updateMsgQTopic(int routeKey, int timeout, MsgQTopic topic,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), requestOption);
  }

  public long updateMsgQTopic(int routeKey, int timeout, MsgQTopic topic, IMethodCallback<MsgQManageDao.updateMsgQTopic_args, MsgQManageDao.updateMsgQTopic_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_updateMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), callback);
  }

  public long add_updateMsgQTopicCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQTopic topic, IMethodCallback<MsgQManageDao.updateMsgQTopic_args, MsgQManageDao.updateMsgQTopic_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_updateMsgQTopicServiceCall(routeKey, timeout, platformArgs, topic), callback);
  }

  protected TServiceCall create_updateMsgQTopicServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQTopic topic){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.updateMsgQTopic_args request = new MsgQManageDao.updateMsgQTopic_args();
    request.setPlatformArgs(platformArgs);
    request.setTopic(topic);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateMsgQTopic");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.updateMsgQTopic_result.class);
    return serviceCall;
  }

  public void send_deleteMsgQTopic(int routeKey, int timeout, String topicName) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQTopicServiceCall(routeKey, timeout, platformArgs, topicName), new TRequestOption());
  }

  public void send_deleteMsgQTopic(int routeKey, int timeout, String topicName,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQTopicServiceCall(routeKey, timeout, platformArgs, topicName), requestOption);
  }

  public long deleteMsgQTopic(int routeKey, int timeout, String topicName, IMethodCallback<MsgQManageDao.deleteMsgQTopic_args, MsgQManageDao.deleteMsgQTopic_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_deleteMsgQTopicServiceCall(routeKey, timeout, platformArgs, topicName), callback);
  }

  public long add_deleteMsgQTopicCall(AsyncCallRunner runner, int routeKey, int timeout, String topicName, IMethodCallback<MsgQManageDao.deleteMsgQTopic_args, MsgQManageDao.deleteMsgQTopic_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_deleteMsgQTopicServiceCall(routeKey, timeout, platformArgs, topicName), callback);
  }

  protected TServiceCall create_deleteMsgQTopicServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String topicName){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.deleteMsgQTopic_args request = new MsgQManageDao.deleteMsgQTopic_args();
    request.setPlatformArgs(platformArgs);
    request.setTopicName(topicName);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteMsgQTopic");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.deleteMsgQTopic_result.class);
    return serviceCall;
  }

  public void send_queryMsgQConsumers(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQConsumerOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQConsumersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryMsgQConsumers(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQConsumerOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQConsumersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryMsgQConsumers(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQConsumerOption option, IMethodCallback<MsgQManageDao.queryMsgQConsumers_args, MsgQManageDao.queryMsgQConsumers_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_queryMsgQConsumersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryMsgQConsumersCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQConsumerOption option, IMethodCallback<MsgQManageDao.queryMsgQConsumers_args, MsgQManageDao.queryMsgQConsumers_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_queryMsgQConsumersServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryMsgQConsumersServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryMsgQConsumerOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.queryMsgQConsumers_args request = new MsgQManageDao.queryMsgQConsumers_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryMsgQConsumers");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.queryMsgQConsumers_result.class);
    return serviceCall;
  }

  public void send_addMsgQConsumer(int routeKey, int timeout, MsgQConsumer consumer) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), new TRequestOption());
  }

  public void send_addMsgQConsumer(int routeKey, int timeout, MsgQConsumer consumer,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), requestOption);
  }

  public long addMsgQConsumer(int routeKey, int timeout, MsgQConsumer consumer, IMethodCallback<MsgQManageDao.addMsgQConsumer_args, MsgQManageDao.addMsgQConsumer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_addMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), callback);
  }

  public long add_addMsgQConsumerCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQConsumer consumer, IMethodCallback<MsgQManageDao.addMsgQConsumer_args, MsgQManageDao.addMsgQConsumer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_addMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), callback);
  }

  protected TServiceCall create_addMsgQConsumerServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQConsumer consumer){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.addMsgQConsumer_args request = new MsgQManageDao.addMsgQConsumer_args();
    request.setPlatformArgs(platformArgs);
    request.setConsumer(consumer);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addMsgQConsumer");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.addMsgQConsumer_result.class);
    return serviceCall;
  }

  public void send_updateMsgQConsumer(int routeKey, int timeout, MsgQConsumer consumer) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), new TRequestOption());
  }

  public void send_updateMsgQConsumer(int routeKey, int timeout, MsgQConsumer consumer,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), requestOption);
  }

  public long updateMsgQConsumer(int routeKey, int timeout, MsgQConsumer consumer, IMethodCallback<MsgQManageDao.updateMsgQConsumer_args, MsgQManageDao.updateMsgQConsumer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_updateMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), callback);
  }

  public long add_updateMsgQConsumerCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQConsumer consumer, IMethodCallback<MsgQManageDao.updateMsgQConsumer_args, MsgQManageDao.updateMsgQConsumer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_updateMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumer), callback);
  }

  protected TServiceCall create_updateMsgQConsumerServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQConsumer consumer){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.updateMsgQConsumer_args request = new MsgQManageDao.updateMsgQConsumer_args();
    request.setPlatformArgs(platformArgs);
    request.setConsumer(consumer);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateMsgQConsumer");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.updateMsgQConsumer_result.class);
    return serviceCall;
  }

  public void send_deleteMsgQConsumer(int routeKey, int timeout, String consumerKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumerKey), new TRequestOption());
  }

  public void send_deleteMsgQConsumer(int routeKey, int timeout, String consumerKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumerKey), requestOption);
  }

  public long deleteMsgQConsumer(int routeKey, int timeout, String consumerKey, IMethodCallback<MsgQManageDao.deleteMsgQConsumer_args, MsgQManageDao.deleteMsgQConsumer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_deleteMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumerKey), callback);
  }

  public long add_deleteMsgQConsumerCall(AsyncCallRunner runner, int routeKey, int timeout, String consumerKey, IMethodCallback<MsgQManageDao.deleteMsgQConsumer_args, MsgQManageDao.deleteMsgQConsumer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_deleteMsgQConsumerServiceCall(routeKey, timeout, platformArgs, consumerKey), callback);
  }

  protected TServiceCall create_deleteMsgQConsumerServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String consumerKey){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.deleteMsgQConsumer_args request = new MsgQManageDao.deleteMsgQConsumer_args();
    request.setPlatformArgs(platformArgs);
    request.setConsumerKey(consumerKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteMsgQConsumer");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.deleteMsgQConsumer_result.class);
    return serviceCall;
  }

  public void send_queryMsgQProducerList(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQProducerOption option) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQProducerListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), new TRequestOption());
  }

  public void send_queryMsgQProducerList(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQProducerOption option,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_queryMsgQProducerListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), requestOption);
  }

  public long queryMsgQProducerList(int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQProducerOption option, IMethodCallback<MsgQManageDao.queryMsgQProducerList_args, MsgQManageDao.queryMsgQProducerList_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_queryMsgQProducerListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  public long add_queryMsgQProducerListCall(AsyncCallRunner runner, int routeKey, int timeout, int pageIndex, int pageSize, QueryMsgQProducerOption option, IMethodCallback<MsgQManageDao.queryMsgQProducerList_args, MsgQManageDao.queryMsgQProducerList_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_queryMsgQProducerListServiceCall(routeKey, timeout, platformArgs, pageIndex, pageSize, option), callback);
  }

  protected TServiceCall create_queryMsgQProducerListServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int pageIndex, int pageSize, QueryMsgQProducerOption option){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.queryMsgQProducerList_args request = new MsgQManageDao.queryMsgQProducerList_args();
    request.setPlatformArgs(platformArgs);
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    request.setOption(option);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("queryMsgQProducerList");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.queryMsgQProducerList_result.class);
    return serviceCall;
  }

  public void send_addMsgQProducer(int routeKey, int timeout, MsgQProducer producer) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), new TRequestOption());
  }

  public void send_addMsgQProducer(int routeKey, int timeout, MsgQProducer producer,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_addMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), requestOption);
  }

  public long addMsgQProducer(int routeKey, int timeout, MsgQProducer producer, IMethodCallback<MsgQManageDao.addMsgQProducer_args, MsgQManageDao.addMsgQProducer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_addMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), callback);
  }

  public long add_addMsgQProducerCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQProducer producer, IMethodCallback<MsgQManageDao.addMsgQProducer_args, MsgQManageDao.addMsgQProducer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_addMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), callback);
  }

  protected TServiceCall create_addMsgQProducerServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQProducer producer){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.addMsgQProducer_args request = new MsgQManageDao.addMsgQProducer_args();
    request.setPlatformArgs(platformArgs);
    request.setProducer(producer);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("addMsgQProducer");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.addMsgQProducer_result.class);
    return serviceCall;
  }

  public void send_updateMsgQProducer(int routeKey, int timeout, MsgQProducer producer) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), new TRequestOption());
  }

  public void send_updateMsgQProducer(int routeKey, int timeout, MsgQProducer producer,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_updateMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), requestOption);
  }

  public long updateMsgQProducer(int routeKey, int timeout, MsgQProducer producer, IMethodCallback<MsgQManageDao.updateMsgQProducer_args, MsgQManageDao.updateMsgQProducer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_updateMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), callback);
  }

  public long add_updateMsgQProducerCall(AsyncCallRunner runner, int routeKey, int timeout, MsgQProducer producer, IMethodCallback<MsgQManageDao.updateMsgQProducer_args, MsgQManageDao.updateMsgQProducer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_updateMsgQProducerServiceCall(routeKey, timeout, platformArgs, producer), callback);
  }

  protected TServiceCall create_updateMsgQProducerServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, MsgQProducer producer){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.updateMsgQProducer_args request = new MsgQManageDao.updateMsgQProducer_args();
    request.setPlatformArgs(platformArgs);
    request.setProducer(producer);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("updateMsgQProducer");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.updateMsgQProducer_result.class);
    return serviceCall;
  }

  public void send_deleteMsgQProducer(int routeKey, int timeout, String producerKey) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQProducerServiceCall(routeKey, timeout, platformArgs, producerKey), new TRequestOption());
  }

  public void send_deleteMsgQProducer(int routeKey, int timeout, String producerKey,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    SvrContainer.getInstance().sendRequest(
        create_deleteMsgQProducerServiceCall(routeKey, timeout, platformArgs, producerKey), requestOption);
  }

  public long deleteMsgQProducer(int routeKey, int timeout, String producerKey, IMethodCallback<MsgQManageDao.deleteMsgQProducer_args, MsgQManageDao.deleteMsgQProducer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return SvrContainer.getInstance().sendRequest(
            create_deleteMsgQProducerServiceCall(routeKey, timeout, platformArgs, producerKey), callback);
  }

  public long add_deleteMsgQProducerCall(AsyncCallRunner runner, int routeKey, int timeout, String producerKey, IMethodCallback<MsgQManageDao.deleteMsgQProducer_args, MsgQManageDao.deleteMsgQProducer_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    try {
      platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      AppLog.w(e.getMessage(), e);
    }
    return runner.addAsyncCall(
            create_deleteMsgQProducerServiceCall(routeKey, timeout, platformArgs, producerKey), callback);
  }

  protected TServiceCall create_deleteMsgQProducerServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, String producerKey){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(MsgQManageDaoVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    MsgQManageDao.deleteMsgQProducer_args request = new MsgQManageDao.deleteMsgQProducer_args();
    request.setPlatformArgs(platformArgs);
    request.setProducerKey(producerKey);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("deleteMsgQProducer");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(MsgQManageDao.deleteMsgQProducer_result.class);
    return serviceCall;
  }

}
