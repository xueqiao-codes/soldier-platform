package org.soldier.platform.errorcode.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.soldier.platform.svr_platform.client.BaseStub;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import java.util.List;
import org.soldier.platform.errorcode.ErrorCodeAgent;
import org.soldier.platform.errorcode.ErrorCodeAgentVariable;

public class ErrorCodeAgentStub extends BaseStub {

  public ErrorCodeAgentStub() {
    super(ErrorCodeAgentVariable.serviceKey);
  }

  public List<org.soldier.platform.errorcode.manager.thriftapi.ErrorCodeItem>  getErrorCodeItem(String domain, int errorCode) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getErrorCodeItem(domain, errorCode, new TStubOption().setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

  public List<org.soldier.platform.errorcode.manager.thriftapi.ErrorCodeItem>  getErrorCodeItem(String domain, int errorCode,TStubOption platformStubOption) throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException {
    InvokeMethodInfo invokeInfo = new InvokeMethodInfo("getErrorCodeItem").setRouteKey(platformStubOption.getRouteKey()).setTimeoutMs(platformStubOption.getTimeoutMs());
    if (platformStubOption.getCallTrace() != null) { 
      invokeInfo.setSourceCallTrace(platformStubOption.getCallTrace());
    } else {
      invokeInfo.setSourceCallTrace(Thread.currentThread().getStackTrace()[2]);
    }
    return super.runSync(new ThriftCallable<List<org.soldier.platform.errorcode.manager.thriftapi.ErrorCodeItem>>(){
    @Override
    public List<org.soldier.platform.errorcode.manager.thriftapi.ErrorCodeItem> call(TProtocol protocol, PlatformArgs platformArgs) throws ErrorInfo, TException {
      return new ErrorCodeAgent.Client(protocol).getErrorCodeItem(platformArgs, domain, errorCode);
      }
    }, invokeInfo);
  }

  public List<org.soldier.platform.errorcode.manager.thriftapi.ErrorCodeItem>  getErrorCodeItem(int routeKey, int timeout,String domain, int errorCode)throws org.soldier.platform.svr_platform.comm.ErrorInfo, TException{
    return getErrorCodeItem(domain, errorCode, new TStubOption().setRouteKey(routeKey).setTimeoutMs(timeout).setCallTrace(Thread.currentThread().getStackTrace()[2]));
  }

}
