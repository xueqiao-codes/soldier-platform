package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.errorcode.ErrorCodeAgent;
import org.soldier.platform.errorcode.ErrorCodeAgent.getErrorCodeItem_args;
import org.soldier.platform.errorcode.ErrorCodeAgent.getErrorCodeItem_result;
import org.soldier.platform.errorcode.client.ErrorCodeAgentAsyncStub;
import org.soldier.platform.errorcode.manager.thriftapi.ErrorCodeItem;
import org.soldier.platform.errorcode.manager.thriftapi.Lang;
import org.soldier.platform.fast_thrift_proxy.dispatcher.IWorkRunner;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.FieldPart;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IRespModule;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.comm.EClientActionType;
import org.soldier.platform.svr_platform.comm.EClientLang;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class ErrorInfoModityModule implements IRespModule {
    
    private IWorkRunner mDispatcherWorkRunner;
    
    public ErrorInfoModityModule(IWorkRunner dispatcherWorkRunner) {
        this.mDispatcherWorkRunner = dispatcherWorkRunner;
    }
    
    private static String getServerExceptionMsg(EClientLang clientLang, int serviceKey, int errorCode) {
        StringBuilder msgBuilder = new StringBuilder();
        if (clientLang == EClientLang.CN) {
            msgBuilder.append("服务异常, 请稍后重试");
        } else {
            msgBuilder.append("Service unavailable, please retry");
        }
        msgBuilder.append("(").append(serviceKey)
                  .append(",").append(errorCode)
                  .append(")");
        return msgBuilder.toString();
    }
    
    private static String getClientMsg(EClientLang clientLang, ErrorCodeItem item
            , int serviceKey, int errorCode) {
        Lang itemLang = Lang.EN;
        if (clientLang == EClientLang.CN) {
            itemLang = Lang.ZH;
        }
        
        String clientMsg = null;
        if (item.isSetErrorMessage()) {
            clientMsg = item.getErrorMessage().get(itemLang);
        }
        if (clientMsg == null) {
            clientMsg = getServerExceptionMsg(clientLang, serviceKey, errorCode);
        }
        
        return clientMsg;
    }
    
    private class ErrorCodeCallback implements IMethodCallback<ErrorCodeAgent.getErrorCodeItem_args, ErrorCodeAgent.getErrorCodeItem_result>  {
        
        private IModuleProcessor<Param> mProcessor;
        private Param mParam;
        private long mReqId;
        
        private ErrorInfo mErrInfo;
        
        public ErrorCodeCallback(IModuleProcessor<Param> processor
                , Param param
                , long reqId
                , ErrorInfo errInfo) {
            this.mProcessor = processor;
            this.mParam = param;
            this.mReqId = reqId;
            this.mErrInfo = errInfo;
        }
        
        @Override
        public void onComplete(long callId, getErrorCodeItem_args req, getErrorCodeItem_result resp) {
            if (resp.isSetSuccess()) {
                onHandleErrorCodeItem(resp.getSuccess());
            } else {
                onHandleErrorCodeItem(null);
            }
        }

        @Override
        public void onError(long callId, getErrorCodeItem_args req, Exception e) {
            onHandleErrorCodeItem(null);
        }
         
        private void onHandleErrorCodeItem(List<ErrorCodeItem> itemList) {
            if (itemList != null) {
                if (itemList.isEmpty()) {
                    mErrInfo.setClientActionType(EClientActionType.NO_MAPPING);
                    mErrInfo.setClientMsg(getServerExceptionMsg(
                            mParam.getRequest().getClientLang()
                            , mParam.getRequest().getServiceKey()
                            , mErrInfo.getErrorCode()));
                } else {
                    mErrInfo.setClientActionType(EClientActionType.CLIENT_HANDLE);
                    mErrInfo.setClientMsg(getClientMsg(
                            mParam.getRequest().getClientLang()
                            , itemList.get(0)
                            , mParam.getRequest().getServiceKey()
                            , mErrInfo.getErrorCode()));
                }
                if (AppLog.debugEnabled()) {
                    AppLog.d("update errorInfo to " + mErrInfo + " for requestId=" + mReqId);
                }
            }
            
            mDispatcherWorkRunner.postWork(new Runnable() {
                @Override
                public void run() {
                    mProcessor.continueProcess(ErrorInfoModityModule.this, mReqId, mParam);
                }
            });
        }
        
    }
    
    
    @Override
    public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
        if (param.getResponse().getThriftRespParts() == null) {
            return IRespModule.RESULT_OK;
        }
        
        ErrorInfo errInfo = null;
        for (FieldPart fieldPart : param.getResponse().getThriftRespParts().fields) {
            if (fieldPart.argField.id == 1 && fieldPart.argField.type == TType.STRUCT) {
                errInfo = new ErrorInfo();
                try {
                    errInfo.read(param.getRequest().getBackendProtocolFactory().getProtocol(
                                new TMemoryInputTransport(fieldPart.argBytes, fieldPart.offset, fieldPart.len)));
                } catch (TException e) {
                   AppLog.e(e.getMessage(), e);
                   break;
                }
                
                fieldPart.fieldBase = errInfo;
                break;
            }
        }
        
        if (errInfo == null || errInfo.getErrorCode() == 0) {
            return IRespModule.RESULT_OK;
        }
        
        try {
            new ErrorCodeAgentAsyncStub().getErrorCodeItem(0, 500
                    , param.getRequest().getServantName()
                    , errInfo.getErrorCode()
                    , new ErrorCodeCallback(processor, param, reqId, errInfo));
            return IRespModule.RESULT_PENDING;
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
        }
        
        return IRespModule.RESULT_OK;
    }

    @Override
    public void cancel(long reqId) {
    }

    @Override
    public String getModuleName() {
        return ErrorInfoModityModule.class.getSimpleName();
    }

}
