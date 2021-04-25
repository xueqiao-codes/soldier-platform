package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.util.ArrayList;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;

import net.qihoo.qconf.Qconf;

public class TimeoutConfigReqModule implements IReqModule {

	@Override
	public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
		try {
			String functionConfPrefix = param.getRequest().getFunctionConfPrefix();
			if (functionConfPrefix != null && !functionConfPrefix.isEmpty()) {
				ArrayList<String> functionsList = Qconf.getBatchKeys(functionConfPrefix);
				if (functionsList != null && functionsList.contains(param.getRequest().getMethodName())) {
					ArrayList<String> functionConfigKeys 
						= Qconf.getBatchKeys(functionConfPrefix + "/" + param.getRequest().getMethodName());
					if (functionConfigKeys.contains("timeout")) {
						param.getRequest().getRequestOption().setTimeoutMills(
								Integer.valueOf(Qconf.getConf(functionConfPrefix + "/"
											+ param.getRequest().getMethodName() + "/timeout")));
					}
				}
			}
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_CONFIG
					, "timeout config error for " + param.getRequest().getMethodName());
		}
		return IReqModule.RESULT_OK;
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return TimeoutConfigReqModule.class.getSimpleName();
	}

}
