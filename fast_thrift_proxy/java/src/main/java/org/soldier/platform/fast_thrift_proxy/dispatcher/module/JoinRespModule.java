package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.nio.ByteBuffer;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.AutoExpandingBufferWriteTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IRespModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.ThriftRespParts;

public class JoinRespModule implements IRespModule {

    @Override
    public IModule.Result onProcess(long reqId, Param param, IModule.IModuleProcessor<Param> processor) {
        if (param.getResponse().getThriftRespParts() == null) {
            return IRespModule.RESULT_OK;
        }
        
        AutoExpandingBufferWriteTransport outputTransport 
            = new AutoExpandingBufferWriteTransport(param.getResponse().getResponseContent().length + 128, 2.0);
        TProtocol outputProtocol = param.getRequest().getFrontProtocolFactory().getProtocol(outputTransport);
        
        try {
            ThriftRespParts parts = param.getResponse().getThriftRespParts();
            outputProtocol.writeMessageBegin(parts.headerMsg);
            if (parts.ex != null) {
                parts.ex.write(outputProtocol);
            } else {
                outputProtocol.writeStructBegin(parts.respStruct);
                for (FieldPart fieldPart : parts.fields) {
                    fieldPart.write(outputProtocol);
                }
                outputProtocol.writeFieldStop();
                outputProtocol.writeStructEnd();
            }
            
            outputProtocol.writeMessageEnd();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            return new Result(ResultType.E_CANCEL
                    , ErrorCode.INNER_ERROR
                    , e.getMessage());
        }
            
        param.getResponse().setOutputData(ByteBuffer.wrap(outputTransport.getBuf().array(), 0, outputTransport.getPos()));
        return IReqModule.RESULT_OK;
    }

    @Override
    public void cancel(long reqId) {
    }

    @Override
    public String getModuleName() {
        return JoinRespModule.class.getSimpleName();
    }
    
}
