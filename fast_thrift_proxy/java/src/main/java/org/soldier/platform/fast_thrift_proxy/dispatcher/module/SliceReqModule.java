package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.ThriftReqParts;

public class SliceReqModule implements IReqModule {

	@Override
	public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
		ThriftReqParts parts = new ThriftReqParts();
		
		TProtocol inProtocol = param.getRequest().getFrontProtocolFactory().getProtocol(
				new TMemoryInputTransport(param.getRequest().getReqBytes()));
		
		try {
			parts.headerMsg = inProtocol.readMessageBegin();
			param.getRequest().setMethodName(parts.headerMsg.name);
			
			parts.argsStruct =  inProtocol.readStructBegin();
			while(true) {
				TField argField = inProtocol.readFieldBegin();
				if (argField.type == TType.STOP) {
					break;
				}
				
				FieldPart fieldPart = FieldPart.construct(argField, inProtocol,  param.getRequest().getFrontProtocolFactory());
				parts.fields.add(fieldPart);
				
				inProtocol.readFieldEnd();
				
				if (AppLog.debugEnabled()) {
				    AppLog.d("read field=" + fieldPart);
				}
			}
			
			inProtocol.readStructEnd();
			
			if (AppLog.debugEnabled()) {
			    AppLog.d("read sturct=" + parts.argsStruct);
			}
			
			inProtocol.readMessageEnd();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			return new Result(ResultType.E_CANCEL
					, ErrorCode.REQ_CONTENT_ERROR
					, e.getMessage());
		}
		
		param.getRequest().setThriftReqParts(parts);
		return IReqModule.RESULT_OK;
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return SliceReqModule.class.getSimpleName();
	}

}
