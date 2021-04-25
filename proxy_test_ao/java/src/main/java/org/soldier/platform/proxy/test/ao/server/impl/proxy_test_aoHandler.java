package org.soldier.platform.proxy.test.ao.server.impl;


import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import org.apache.thrift.TException;
import org.soldier.platform.proxy.test.ao.EchoListResult;
import org.soldier.platform.proxy.test.ao.EchoTypes;
import org.soldier.platform.proxy.test.ao.server.proxy_test_aoAdaptor;

public class proxy_test_aoHandler extends proxy_test_aoAdaptor{
  @Override
  public int InitApp(Properties props){
    return 0;
  }

  @Override
  protected String testEcho(TServiceCntl oCntl, String content) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException{
    if ("errorplatform".equalsIgnoreCase(content)) {
    	throw new ErrorInfo(30000, "error");
    }
    if ("timeout".equalsIgnoreCase(content)) {
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    if ("uncatch".equalsIgnoreCase(content)) {
    	throw new IllegalStateException("uncatch state");
    }
    
    return content;
  }

  @Override
  public void destroy() {
  }

  @Override
  protected List<String> testEchoList(TServiceCntl oCntl, List<String> contentList) throws ErrorInfo, TException {
	return contentList;
  }

  @Override
  protected EchoListResult testEchoListStruct(TServiceCntl oCntl, List<String> contentList) throws ErrorInfo, TException {
	EchoListResult result =  new EchoListResult();
	result.setContentList(contentList);
	return result;
  }

  @Override
  protected EchoTypes echoTypes(TServiceCntl oCntl, EchoTypes types, boolean throw_action) throws ErrorInfo, TException {
	if (throw_action) {
		throw new ErrorInfo(1, "throw action test");
	}
	
	return types;
  }

  @Override
  protected Set<Long> testEchoSet(TServiceCntl oCntl, Set<Long> valueList) throws ErrorInfo, TException {
    return valueList;
  }

  @Override
  protected Map<Long, String> testEchoMap(TServiceCntl oCntl, Map<Long, String> mapValue) throws ErrorInfo, TException {
    return mapValue;
  }

  @Override
  protected List<EchoTypes> testEchoTypesList(TServiceCntl oCntl, List<EchoTypes> types) throws ErrorInfo, TException {
    return types;
  }
}
