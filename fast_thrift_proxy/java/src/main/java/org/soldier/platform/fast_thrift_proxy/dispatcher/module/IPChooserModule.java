package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;

public class IPChooserModule implements IReqModule {

	@Override
	public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
		try {
			param.getRequest().setRemoteAddress(ServiceFinderFactory.getServiceFinder().getServiceIp(
					param.getRequest().getServiceKey()
					, param.getRequest().getMethodName()
					, RandomUtils.nextInt()));
			param.getRequest().setRemotePort(ServiceFinderFactory.getServiceFinder().getServicePort(
					param.getRequest().getServiceKey()));
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			return new Result(ResultType.E_CANCEL, ErrorCode.INNER_ERROR, "choose service ip failed!");
		}
		return IReqModule.RESULT_OK;
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return IPChooserModule.class.getSimpleName();
	}

}
