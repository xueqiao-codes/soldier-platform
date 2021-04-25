package idmaker.dao.server.impl;


import java.util.Properties;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import idmaker.dao.server.IdMakerAdaptor;

public class IdMakerHandler extends IdMakerAdaptor{
  @Override
  public int InitApp(Properties props){
    return 0;
  }

  @Override
  protected long CreateId(TServiceCntl oCntl,int type) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    return 0;
  }

}
