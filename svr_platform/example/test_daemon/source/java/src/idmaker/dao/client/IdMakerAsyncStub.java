package idmaker.dao.client;

import idmaker.dao.IdMaker;
import idmaker.dao.IdMakerVariable;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.client.SvrContainer;
import org.soldier.platform.svr_platform.client.TRequestOption;
import org.soldier.platform.svr_platform.client.TServiceCall;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class IdMakerAsyncStub { 
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

  public void sendCreateId(int routeKey, int timeout, int type) throws TException {
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createCreateIdServiceCall(routeKey, timeout, platformArgs, type), new TRequestOption());
  }

  public void sendCreateId(int routeKey, int timeout, int type,TRequestOption requestOption) throws TException { 
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    SvrContainer.getInstance().sendRequest(
        createCreateIdServiceCall(routeKey, timeout, platformArgs, type), requestOption);
  }

  public long CreateId(int routeKey, int timeout, int type, IMethodCallback<IdMaker.CreateId_args, IdMaker.CreateId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":"
        + callStackElement.getLineNumber() + "]");
    return SvrContainer.getInstance().sendRequest(
            createCreateIdServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  public long addCreateIdCall(AsyncCallRunner runner, int routeKey, int timeout, int type, IMethodCallback<IdMaker.CreateId_args, IdMaker.CreateId_result> callback) throws TException{
    PlatformArgs platformArgs = new PlatformArgs();
    StackTraceElement callStackElement = Thread.currentThread().getStackTrace()[2];
    platformArgs.setSourceDesc(
        callStackElement.getClassName() + "[" + callStackElement.getMethodName() + ":" 
        + callStackElement.getLineNumber() + "]");
    return runner.addAsyncCall(
            createCreateIdServiceCall(routeKey, timeout, platformArgs, type), callback);
  }

  protected TServiceCall createCreateIdServiceCall(int routeKey, int timeout, org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int type){
    TServiceCall serviceCall = new TServiceCall();
    serviceCall.setServiceKey(IdMakerVariable.serviceKey);
    serviceCall.setRouteKey(routeKey);
    serviceCall.setOneWay(false);
    IdMaker.CreateId_args request = new IdMaker.CreateId_args();
    request.setPlatformArgs(platformArgs);
    request.setType(type);
    serviceCall.setRequest(request);
    serviceCall.setMethodName("CreateId");
    serviceCall.setTimeout(timeout);
    serviceCall.setResponse(IdMaker.CreateId_result.class);
    return serviceCall;
  }

}
