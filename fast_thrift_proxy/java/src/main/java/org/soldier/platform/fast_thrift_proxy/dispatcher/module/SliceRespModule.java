package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IRespModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.ThriftRespParts;

public class SliceRespModule implements IRespModule {

    @Override
    public IModule.Result onProcess(long reqId, Param param, IModule.IModuleProcessor<Param> processor) {
        if (param.getResponse().getResponseContent() == null 
                || param.getResponse().getResponseContent().length == 0) {
            return IRespModule.RESULT_OK;
        }
        
        ThriftRespParts parts = new ThriftRespParts();
        
        TProtocol inProtocol = param.getRequest().getBackendProtocolFactory().getProtocol(
                new TMemoryInputTransport(param.getResponse().getResponseContent()));
        
        try {
            parts.headerMsg = inProtocol.readMessageBegin();
            if (parts.headerMsg.type == TMessageType.EXCEPTION) {
                parts.ex = TApplicationException.read(inProtocol);
                inProtocol.readMessageEnd();
                if (AppLog.debugEnabled()) {
                    AppLog.d("read TApplicationException=" + parts.ex);
                }
                param.getResponse().setThriftRespParts(parts);
                return IRespModule.RESULT_OK;
            }
            
            parts.respStruct =  inProtocol.readStructBegin();
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
                AppLog.d("read respStruct=" + parts.respStruct + " finished!");
            }
            inProtocol.readMessageEnd();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            return new Result(ResultType.E_CANCEL
                    , ErrorCode.REQ_CONTENT_ERROR
                    , e.getMessage());
        }
        
        param.getResponse().setThriftRespParts(parts);
        return IRespModule.RESULT_OK;
    }

    @Override
    public void cancel(long reqId) {
    }

    @Override
    public String getModuleName() {
        return SliceRespModule.class.getSimpleName();
    }
    
}
