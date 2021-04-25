package org.soldier.platform.proxy.test.ao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.NetHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_aoVariable;

public class proxy_test_aoStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         proxy_test_aoVariable.serviceKey, methodName, routeKey); 
    }
    return ip;
  }

  public void setPeerAddr(final String ipAddr){
    if (ipAddr == null) { 
      peerAddr = null;
    }
    if (-1l != NetHelper.AddrNet(ipAddr)) { 
      peerAddr = ipAddr;
    }  }

  public String getPeerAddr() { 
    return peerAddr;
  }

  public String  testEcho(int routeKey, int timeout,String content)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    TTransport socketTransport = null;
    String ip = null ;
    int port = 0;
    if (SvrConfiguration.getIsUsingInpService() && SvrConfiguration.isServiceInProcess(proxy_test_aoVariable.serviceKey)) { 
      socketTransport = new InpSocket(proxy_test_aoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("testEcho", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      port =  ServiceFinderFactory.getServiceFinder().getServicePort(proxy_test_aoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }
    TTransport transport = new TFramedTransport(socketTransport);
    TProtocol protocol = new TCompactProtocol(transport);
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
    if(ip != null) { platformArgs.setRemoteAddress(ip); }
    if(port != 0) { platformArgs.setRemotePort(port); }
    proxy_test_ao.Client client = new  proxy_test_ao.Client(protocol);
    String result = "";
    try {
      transport.open();
      result = client.testEcho(platformArgs, content);
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        proxy_test_aoVariable.serviceKey,"testEcho", ip, null);
    } catch (TException e) {
      ServiceFinderFactory.getServiceFinder().updateCallInfo(
        proxy_test_aoVariable.serviceKey,"testEcho", ip, e);
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

}
