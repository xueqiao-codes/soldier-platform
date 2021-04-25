package idmaker.dao.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.ServiceException;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import idmaker.dao.IdMaker;
import idmaker.dao.IdMakerVariable;

public class IdMakerStub{

  private String peerAddr;;

  private String GetServiceIp(final String methodName, long routeKey) throws ServiceException{
    String ip = peerAddr; 
    if(ip == null){ 
      ip = ServiceFinderFactory.getServiceFinder().getServiceIp(
         IdMakerVariable.serviceKey, methodName, routeKey); 
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

  public long  CreateId(int routeKey, int timeout,int type)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    String ip = null ;
    try{
      ip = GetServiceIp("CreateId", routeKey);
    } catch (ServiceException e1) { throw new TException(e1.getMessage());} 
    int port =  ServiceFinderFactory.getServiceFinder().getServicePort(IdMakerVariable.serviceKey);
    TTransport transport = new TFramedTransport(new TSocket(ip, port, timeout));
    TProtocol protocol = new TCompactProtocol(transport);
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
         callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
         + callStackElement.getLineNumber() + "]");
    IdMaker.Client client = new  IdMaker.Client(protocol);
    long result = 0;
    try {
      transport.open();
      result = client.CreateId(platformArgs, type);
    } catch (TException e) {
      e.printStackTrace();
      throw e;
    }finally{
      transport.close();
    }
    return result;
  }

}
