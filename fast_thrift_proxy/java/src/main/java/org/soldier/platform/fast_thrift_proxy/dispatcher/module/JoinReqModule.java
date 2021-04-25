package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.nio.ByteBuffer;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.ThriftReqParts;

public class JoinReqModule implements IReqModule {

	@Override
	public IModule.Result onProcess(
			long reqId, Param param, IModuleProcessor<Param> processor) {
		AutoExpandingBufferWriteTransport outputTransport 
				= new AutoExpandingBufferWriteTransport(param.getRequest().getReqBytes().length + 128, 2.0);
		TProtocol outputProtocol = param.getRequest().getBackendProtocolFactory().getProtocol(outputTransport);
		
		try {
			ThriftReqParts parts = param.getRequest().getThriftReqParts();
			outputProtocol.writeMessageBegin(parts.headerMsg);
			outputProtocol.writeStructBegin(parts.argsStruct);
			
//			AppLog.d("after message and struct, pos=" + outputTransport.getPos());
			
			for (FieldPart fieldPart : parts.fields) {
				fieldPart.write(outputProtocol);
//				AppLog.d("after field s=" + fieldPart.argField.id + ", pos=" + outputTransport.getPos());
			}
			outputProtocol.writeFieldStop();
//			AppLog.d("after field stop, pos=" + outputTransport.getPos());
			
			outputProtocol.writeStructEnd();
			outputProtocol.writeMessageEnd();
//			AppLog.d("after write end, pos=" + outputTransport.getPos());
			
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			return new Result(ResultType.E_CANCEL
					, ErrorCode.INNER_ERROR
					, e.getMessage());
		}
		
		param.getRequest().setTransportData(ByteBuffer.wrap(outputTransport.getBuf().array(), 0, outputTransport.getPos()));
		return IReqModule.RESULT_OK;
	}

	@Override
	public void cancel(long reqId) {
	}

	@Override
	public String getModuleName() {
		return null;
	}

}
