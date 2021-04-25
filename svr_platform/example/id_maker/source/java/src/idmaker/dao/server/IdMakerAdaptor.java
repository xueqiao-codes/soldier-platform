package idmaker.dao.server;

import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import java.util.Map;
import java.util.HashMap;
import idmaker.dao.IdMaker;
import idmaker.dao.IdMakerVariable;


public abstract class IdMakerAdaptor implements IdMaker.Iface{
  // unmodified map, so we do not need lock for this 
  private final Map<String, String[]> methodParameterNameMap = new HashMap<String, String[]>(); 

  public String[] getMethodParameterName(String methodName) {
    return methodParameterNameMap.get(methodName);
  }

  @Override
  public long CreateId(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    TServiceCntl oCntl = new TServiceCntl(IdMakerVariable.serviceKey,"CreateId",platformArgs);
    return CreateId(oCntl, type);
  }

  protected abstract long CreateId(TServiceCntl oCntl,int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  protected IdMakerAdaptor(){
    methodParameterNameMap.put("CreateId",new String[]{"platformArgs", "type"});
  }
  protected abstract int InitApp(final Properties props);

}
