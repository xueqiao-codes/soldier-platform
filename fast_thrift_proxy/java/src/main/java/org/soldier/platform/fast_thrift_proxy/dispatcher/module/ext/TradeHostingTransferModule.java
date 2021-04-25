package org.soldier.platform.fast_thrift_proxy.dispatcher.module.ext;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

/**
 *  交易机入口函数
 */
public class TradeHostingTransferModule implements IReqModule {
	private File mTradeHostingFlag;
	private boolean isTradeHosting;
	
	public TradeHostingTransferModule() {
		mTradeHostingFlag = new File("/usr/local/soldier/trade_hosting");
		if ("true".equalsIgnoreCase(System.getenv("TRADE_HOSTING"))
		        || (mTradeHostingFlag.exists() && mTradeHostingFlag.isFile())) {
			AppLog.i("inited as trade hosting machine");
			isTradeHosting = true;
		} else {
			AppLog.i("inited not as trade hosting machine");
			isTradeHosting = false;
		}
	}
	
	@Override
	public IModule.Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
		if (param.getRequest().getServiceKey() != 701 
		     && param.getRequest().getServiceKey() != 253) {
			return IReqModule.RESULT_OK;
		}
		
		if (!isTradeHosting) {
			return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_FORBIDDEN, "not a trading host");
		}
		
		if (param.getRequest().getServiceKey() == 253) {
		    // 合约查询全部转发到本地托管机
		    param.getRequest().setRemoteAddress("127.0.0.1");
		    return IReqModule.RESULT_OK;
		}
		
		LandingInfo landingInfo = null;
		for (FieldPart fieldPart : param.getRequest().getThriftReqParts().fields)  {
			if (fieldPart.argField.id == 2) {
				landingInfo = new LandingInfo();
				try {
					landingInfo.read(param.getRequest().getFrontProtocolFactory().getProtocol(
							new TMemoryInputTransport(fieldPart.argBytes, fieldPart.offset, fieldPart.len)));
				} catch (Throwable e) {
					AppLog.e(e.getMessage(), e);
					return new Result(ResultType.E_CANCEL, ErrorCode.INNER_ERROR, "read landingInfo failed!");
				}
				fieldPart.fieldBase = landingInfo;
				break;
			}
		}
		
		if (landingInfo == null 
			|| landingInfo.getMachineId() <= 0
			|| landingInfo.getSubUserId() <= 0
			|| StringUtils.isEmpty(landingInfo.getToken())) {
			return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_NOT_AUTH, "landingInfo is not filled!");
		}
		
		long startTimestamp = System.currentTimeMillis();
		try {
			List<HostingSession> hostingSessionList = new TradeHostingStorageApiStub().getHostingSession(
			        landingInfo.getSubUserId());
			if (hostingSessionList.isEmpty()) {
			    return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_NOT_AUTH, "not login");
			}
			
			HostingSession hostingSession = hostingSessionList.get(0);
			if (hostingSession.getMachineId() != landingInfo.getMachineId()) {
			    return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_NOT_AUTH, "machineId is not equals");
			}
			
			if (!landingInfo.getToken().equals(hostingSession.getToken())) {
			    return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_NOT_AUTH, "token error!");
			}
			
			return IReqModule.RESULT_OK;
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			return new Result(ResultType.E_CANCEL, ErrorCode.INNER_ERROR, "getHostingSession failed");
		} finally {
			if (AppLog.infoEnabled()) {
				AppLog.i("check session used time=" + (System.currentTimeMillis() - startTimestamp) + "ms");
			}
		}
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return TradeHostingTransferModule.class.getSimpleName();
	}

}
