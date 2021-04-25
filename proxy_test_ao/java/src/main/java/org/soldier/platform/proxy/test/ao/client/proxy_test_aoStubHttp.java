package org.soldier.platform.proxy.test.ao.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.EClientLang;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.thrift.InpSocket;
import org.soldier.platform.proxy.test.ao.EchoListResult;
import org.soldier.platform.proxy.test.ao.EchoTypes;
import org.soldier.platform.proxy.test.ao.proxy_test_ao;
import org.soldier.platform.proxy.test.ao.proxy_test_aoVariable;

public class proxy_test_aoStubHttp{

  private String peerAddr;
  private static final String HTTP_ADDR = "http://devproxy.xueqiao.cn?servant=proxy_test_ao";

//  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
//    String ip = peerAddr; 
//    if(ip == null){ 
//      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
//         proxy_test_aoVariable.serviceKey, methodName, routeKey); 
//    }
//    return ip;
//  }

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
    /*TTransport socketTransport = null;
      String ip = null ;
    if (SvrConfiguration.getIsUsingInpService()) { 
      socketTransport = new InpSocket(proxy_test_aoVariable.serviceKey, timeout);
    } else {
      try{
        ip = GetServiceIp("testEcho", routeKey);
      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(proxy_test_aoVariable.serviceKey);
      socketTransport = new TSocket(ip, port, timeout);
    }*/
//    TTransport transport = new TFramedTransport(socketTransport);
	CloseableHttpClient httpclient = HttpClients.createDefault();  
    THttpClient transport = new THttpClient(HTTP_ADDR,
    		httpclient);
    
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    platformArgs.setClientLang(EClientLang.CN);
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    proxy_test_ao.Client client = new  proxy_test_ao.Client(protocol);
    String result = "";
    long timeStartTimestamp = System.currentTimeMillis();
    try {
      
      transport.open();
      result = client.testEcho(platformArgs, content);
      if (!SvrConfiguration.getIsUsingInpService()) { 
//        ServiceFinderFactory.getServiceFinder().updateCallInfo(
//        proxy_test_aoVariable.serviceKey,"testEcho", ip, null);
      }
    } catch (TException e) {
      if (!SvrConfiguration.getIsUsingInpService()) {
//      ServiceFinderFactory.getServiceFinder().updateCallInfo(
//        proxy_test_aoVariable.serviceKey,"testEcho", ip, e);
      }
      throw e;
    }finally{
    	try {
    	httpclient.close();
    	} catch (Throwable e) {
    		
    	}
      transport.close();
      System.out.println("timeEsceped=" + (System.currentTimeMillis() -timeStartTimestamp) + "ms");
    }
    return result;
  }
  
  public List<String>  testEchoList(int routeKey, int timeout,List<String> contentList)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
	    /*TTransport socketTransport = null;
	      String ip = null ;
	    if (SvrConfiguration.getIsUsingInpService()) { 
	      socketTransport = new InpSocket(proxy_test_aoVariable.serviceKey, timeout);
	    } else {
	      try{
	        ip = GetServiceIp("testEcho", routeKey);
	      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
	      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(proxy_test_aoVariable.serviceKey);
	      socketTransport = new TSocket(ip, port, timeout);
	    }*/
//	    TTransport transport = new TFramedTransport(socketTransport);
		CloseableHttpClient httpclient = HttpClients.createDefault();  
	    THttpClient transport = new THttpClient(HTTP_ADDR,
	    		httpclient);
	    
	    TProtocol protocol = new TCompactProtocol(transport);
	    PlatformArgs platformArgs = new PlatformArgs();
	    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
	    platformArgs.setSourceDesc(
	         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
	         + callStackElement.getLineNumber() + "]");
	    proxy_test_ao.Client client = new  proxy_test_ao.Client(protocol);
	    List<String> result = null;
	    long timeStartTimestamp = System.currentTimeMillis();
	    try {
	      
	      transport.open();
	      result = client.testEchoList(platformArgs, contentList);
	      if (!SvrConfiguration.getIsUsingInpService()) { 
//	        ServiceFinderFactory.getServiceFinder().updateCallInfo(
//	        proxy_test_aoVariable.serviceKey,"testEcho", ip, null);
	      }
	    } catch (TException e) {
	      if (!SvrConfiguration.getIsUsingInpService()) {
//	      ServiceFinderFactory.getServiceFinder().updateCallInfo(
//	        proxy_test_aoVariable.serviceKey,"testEcho", ip, e);
	      }
	      throw e;
	    }finally{
	      transport.close();
	      System.out.println("timeEsceped=" + (System.currentTimeMillis() -timeStartTimestamp) + "ms");
	    }
	    return result;
	  }
  
  public EchoListResult  testEchoListStruct(int routeKey, int timeout,List<String> contentList)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
	    /*TTransport socketTransport = null;
	      String ip = null ;
	    if (SvrConfiguration.getIsUsingInpService()) { 
	      socketTransport = new InpSocket(proxy_test_aoVariable.serviceKey, timeout);
	    } else {
	      try{
	        ip = GetServiceIp("testEcho", routeKey);
	      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
	      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(proxy_test_aoVariable.serviceKey);
	      socketTransport = new TSocket(ip, port, timeout);
	    }*/
//	    TTransport transport = new TFramedTransport(socketTransport);
		CloseableHttpClient httpclient = HttpClients.createDefault();  
	    THttpClient transport = new THttpClient(HTTP_ADDR,
	    		httpclient);
	    
	    TProtocol protocol = new TCompactProtocol(transport);
	    PlatformArgs platformArgs = new PlatformArgs();
	    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
	    platformArgs.setSourceDesc(
	         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
	         + callStackElement.getLineNumber() + "]");
	    proxy_test_ao.Client client = new  proxy_test_ao.Client(protocol);
	    EchoListResult result = null;
	    long timeStartTimestamp = System.currentTimeMillis();
	    try {
	      
	      transport.open();
	      result = client.testEchoListStruct(platformArgs, contentList);
	      if (!SvrConfiguration.getIsUsingInpService()) { 
//	        ServiceFinderFactory.getServiceFinder().updateCallInfo(
//	        proxy_test_aoVariable.serviceKey,"testEcho", ip, null);
	      }
	    } catch (TException e) {
	      if (!SvrConfiguration.getIsUsingInpService()) {
//	      ServiceFinderFactory.getServiceFinder().updateCallInfo(
//	        proxy_test_aoVariable.serviceKey,"testEcho", ip, e);
	      }
	      throw e;
	    }finally{
	      transport.close();
	      System.out.println("timeEsceped=" + (System.currentTimeMillis() -timeStartTimestamp) + "ms");
	    }
	    return result;
	  }
  
  public EchoTypes  echoTypes(int routeKey, int timeout,EchoTypes types, boolean throw_action)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
	    /*TTransport socketTransport = null;
	      String ip = null ;
	    if (SvrConfiguration.getIsUsingInpService()) { 
	      socketTransport = new InpSocket(proxy_test_aoVariable.serviceKey, timeout);
	    } else {
	      try{
	        ip = GetServiceIp("testEcho", routeKey);
	      } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
	      int port =  ServiceFinderFactory.getServiceFinder().getServicePort(proxy_test_aoVariable.serviceKey);
	      socketTransport = new TSocket(ip, port, timeout);
	    }*/
//	    TTransport transport = new TFramedTransport(socketTransport);
		CloseableHttpClient httpclient = HttpClients.createDefault();  
	    THttpClient transport = new THttpClient(HTTP_ADDR,
	    		httpclient);
	    
	    TProtocol protocol = new TCompactProtocol(transport);
	    PlatformArgs platformArgs = new PlatformArgs();
	    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
	    platformArgs.setSourceDesc(
	         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
	         + callStackElement.getLineNumber() + "]");
	    proxy_test_ao.Client client = new  proxy_test_ao.Client(protocol);
	    EchoTypes result = null;
	    long timeStartTimestamp = System.currentTimeMillis();
	    try {
	      
	      transport.open();
	      result = client.echoTypes(platformArgs, types, throw_action);
	      if (!SvrConfiguration.getIsUsingInpService()) { 
//	        ServiceFinderFactory.getServiceFinder().updateCallInfo(
//	        proxy_test_aoVariable.serviceKey,"testEcho", ip, null);
	      }
	    } catch (TException e) {
	      if (!SvrConfiguration.getIsUsingInpService()) {
//	      ServiceFinderFactory.getServiceFinder().updateCallInfo(
//	        proxy_test_aoVariable.serviceKey,"testEcho", ip, e);
	      }
	      throw e;
	    }finally{
	      transport.close();
	      System.out.println("timeEsceped=" + (System.currentTimeMillis() -timeStartTimestamp) + "ms");
	    }
	    return result;
	  }
  
	public static void main(String[] args) {
		try {
//			new proxy_test_aoStubHttp().testEcho(0, 5000, "timeout");
			
			System.out.println(new proxy_test_aoStubHttp().testEcho(0, 3000, "123123123"));
			
			List<String> arrayList = new ArrayList<String>();
			arrayList.add("test1");
			arrayList.add("test2");
			System.out.println("arrayList.size=" + arrayList.size());
//			
//			System.out.println(new proxy_test_aoStubHttp().testEchoList(0, 3000, arrayList));
			System.out.println(new proxy_test_aoStubHttp().testEchoListStruct(0, 3000, arrayList));
			
//			EchoTypes types = new EchoTypes();
//			types.setBool_field(true);
//			types.setDouble_field(1.22);
//			types.setInt32_field(1234);
//			types.setInt64_field(11111111111111111L);
//			types.setString_field("this is a string");
//			
//			Map<String, String> testMap = new HashMap<String, String>();
//			testMap.put("key1", "value1");
//			types.setMap_field(testMap);
//			
//			Set<String> testSet = new HashSet<String>();
//			testSet.add("set1");
//			types.setSet_field(testSet);
			
//			types.setStruct_field(new EchoListResult().setContentList(arrayList));
			
//			System.out.println(new proxy_test_aoStubHttp().echoTypes(0, 3000, types, false));
////			
//			System.out.println(new proxy_test_aoStubHttp().echoTypes(0, 3000, types, true));
			
			
			System.out.println(new proxy_test_aoStubHttp().testEcho(0, 3000, "errorplatform"));
			
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

}
