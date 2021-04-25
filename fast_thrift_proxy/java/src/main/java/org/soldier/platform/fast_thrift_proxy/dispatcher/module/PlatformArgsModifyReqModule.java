package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.svr_platform.comm.PlatformArgs;

public class PlatformArgsModifyReqModule implements IReqModule {

	@Override
	public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
		for (FieldPart fieldPart : param.getRequest().getThriftReqParts().fields) {
			if (fieldPart.argField.id == 1) {
				PlatformArgs platformArgs = new PlatformArgs();
				try {
				    platformArgs.read(param.getRequest().getFrontProtocolFactory().getProtocol(
                            new TMemoryInputTransport(fieldPart.argBytes, fieldPart.offset, fieldPart.len)));
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                    return new Result(ResultType.E_CANCEL, ErrorCode.INNER_ERROR, "read PlatformArgs failed!");
                }
				
				// 同时填充ClientLang
				param.getRequest().setClientLang(platformArgs.getClientLang());
				
				platformArgs.setSourceDesc("fast_thrift_proxy");
				platformArgs.setRemoteAddress(param.getRequest().getRemoteAddress());
				platformArgs.setRemotePort(param.getRequest().getRemotePort());
				platformArgs.setXForwardAddress(param.getRequest().getXForwardAddress());
				try {
					platformArgs.setSourceIp(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					AppLog.w(e.getMessage(), e);
				}
				if (platformArgs.getExtraParams() == null) {
				    platformArgs.setExtraParams(param.getRequest().getExtraParams());
				} else {
				    platformArgs.getExtraParams().putAll(param.getRequest().getExtraParams());
				}
				
				fieldPart.fieldBase = platformArgs;
				break;
			}
		}
		
		return IReqModule.RESULT_OK;
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return PlatformArgsModifyReqModule.class.getSimpleName();
	}

}
