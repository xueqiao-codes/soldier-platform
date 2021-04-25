package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.util.HashMap;
import java.util.Map;

import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IRespModule;

public class AttrModule {
	
	private IRespModule mRespModule = new IRespModule() {
		@Override
		public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
			Map<String, String> requestTags = new HashMap<String, String>();
			requestTags.put("servicekey", String.valueOf(param.getRequest().getServiceKey()));
			requestTags.put("method", param.getRequest().getMethodName());
			
			AttrReporterFactory.getDefault().inc(
					AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.service.request.count"
					, requestTags)
					, 1);
			
			if (param.getResponse().getErrorCode() == 0) {
				AttrReporterFactory.getDefault().inc(
					AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.service.success.count"
							, requestTags)
					, 1);
				AttrReporterFactory.getDefault().average(
						AttrReporterFactory.getDefault().requireKey(
								"fast_thrift_proxy.service.response.timems"
								, requestTags)
						, param.getRequest().getTransportFinishedTimestampMs() - param.getRequest().getTransportStartTimestampMs());
				
			} else  {
				AttrReporterFactory.getDefault().inc(
						AttrReporterFactory.getDefault().requireKey("fast_thrift_proxy.service.failed.count"
								, requestTags)
						, 1);
			}
			
//			if (AppLog.debugEnabled()) {
//				AppLog.d("transportStartTimestampMs=" + param.getRequest().getTransportStartTimestampMs()
//						+", transportFinishedTimestampMs=" + param.getRequest().getTransportFinishedTimestampMs());
//			}
			
			return IRespModule.RESULT_OK;
		}

		@Override
		public void cancel(long reqId) {
		}

		@Override
		public String getModuleName() {
			return AttrModule.class.getSimpleName();
		}
	};
	
	public IRespModule getRespModule() {
		return mRespModule;
	}
}
