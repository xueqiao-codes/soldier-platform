package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;

public class ForbiddenReqModule implements IReqModule {

	@Override
	public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
		if (param.getRequest().getForbiddenMethodRegList() != null) {
			for (String regex : param.getRequest().getForbiddenMethodRegList()) {
				if (regex != null && !regex.isEmpty()) {
					if (param.getRequest().getMethodName().matches(regex)) {
						return new Result(ResultType.E_CANCEL, ErrorCode.ERROR_FORBIDDEN, "method forbidden");
					}
				}
			}
		}
		return IReqModule.RESULT_OK;
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return ForbiddenReqModule.class.getSimpleName();
	}

}
