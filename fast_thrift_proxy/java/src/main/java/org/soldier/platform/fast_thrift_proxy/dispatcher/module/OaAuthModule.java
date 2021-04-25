package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.ErrorCode;
import org.soldier.platform.fast_thrift_proxy.dispatcher.IWorkRunner;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;
import org.soldier.platform.oa.user.ao.ECheckResult;
import org.soldier.platform.oa.user.ao.OaUserAo;
import org.soldier.platform.oa.user.ao.OaUserAo.checkSessionAndGroups_args;
import org.soldier.platform.oa.user.ao.OaUserAo.checkSessionAndGroups_result;
import org.soldier.platform.oa.user.ao.client.OaUserAoAsyncStub;
import org.soldier.platform.svr_platform.client.IMethodCallback;

import io.netty.handler.codec.http.HttpHeaders;

public class OaAuthModule implements IReqModule {
    private static final String GROUPS_PREFIX = "groups=";
    
    private IWorkRunner mDispatcherWorkRunner;
    
    public OaAuthModule(IWorkRunner dispatcherWorkRunner) {
        this.mDispatcherWorkRunner = dispatcherWorkRunner;
    }
    
    private class OaAuthCallback implements IMethodCallback<OaUserAo.checkSessionAndGroups_args, OaUserAo.checkSessionAndGroups_result>  {
        
        private long mReqId;
        private IModuleProcessor<Param> mProcessor;
        private Param mParam;
        
        public OaAuthCallback(IModuleProcessor<Param> processor, Param param, long reqId) {
            this.mProcessor = processor;
            this.mParam = param;
            this.mReqId = reqId;
        }
        
        @Override
        public void onComplete(long arg
                , checkSessionAndGroups_args req
                , checkSessionAndGroups_result resp) {
            mDispatcherWorkRunner.postWork(new Runnable() {

                @Override
                public void run() {
                    if (resp.getErr() != null) {
                        mProcessor.cancelProcess(OaAuthModule.this, mReqId
                                , mParam
                                , ErrorCode.INNER_ERROR
                                , resp.getErr().getErrorMsg());
                        return ;
                    }
                    
                    if (resp.getSuccess() == ECheckResult.OK) {
                        mProcessor.continueProcess(OaAuthModule.this, mReqId, mParam);
                        return ;
                    } else if (resp.getSuccess() == ECheckResult.SESSION_INVALID) {
                        mProcessor.cancelProcess(OaAuthModule.this, mReqId
                                , mParam
                                , ErrorCode.ERROR_NOT_AUTH
                                , "Session Invalid");
                        return ;
                    }
                    mProcessor.cancelProcess(OaAuthModule.this, mReqId
                            , mParam
                            , ErrorCode.ERROR_FORBIDDEN
                            , "Forbidden, No Permission");
                    
                }
                
            });
            
        }

        @Override
        public void onError(long arg, checkSessionAndGroups_args arg1, Exception e) {
            AppLog.e(e.getMessage(), e);
            
            mDispatcherWorkRunner.postWork(new Runnable() {

                @Override
                public void run() {
                    mProcessor.cancelProcess(OaAuthModule.this, mReqId
                            , mParam
                            , ErrorCode.INNER_ERROR
                            , "Inner exception occurs!");
                }
                
            });
            
        }
        
    }
    
    private int getOaUserId(HttpHeaders headers) {
        try {
            return Integer.parseInt(headers.get("X-Oa-User-Id", "0"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private String getOaUserName(HttpHeaders headers) {
        return headers.get("X-Oa-User-Name", "");
    }
    
    private String getOaUserSecret(HttpHeaders headers) {
        return headers.get("X-Oa-User-Secret", "");
    }
    
    
    @Override
    public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
        if (param.getRequest().getOaAuth() == null) {
            return IReqModule.RESULT_OK;
        }
        
        int oaUserId = getOaUserId(param.getRequest().getHttpHeaders());
        String oaUserName = getOaUserName(param.getRequest().getHttpHeaders());
        String oaUserSecret = getOaUserSecret(param.getRequest().getHttpHeaders());
        if (AppLog.traceEnabled()) {
            AppLog.t("onProcess oaUserId=" + oaUserId + ", oaUserName=" + oaUserName
                     + ", oaUserSecret=" + oaUserSecret);
        }
        if (oaUserId <= 0 || StringUtils.isEmpty(oaUserName) || StringUtils.isEmpty(oaUserSecret)) {
            Result result = new Result(ResultType.E_CANCEL);
            result.setResultCode(ErrorCode.ERROR_NOT_AUTH);
            result.setResultDescription("OA not login!");
            return result;
        }
        
        Set<String> groups = new HashSet<>();
        String[] splits = StringUtils.split(param.getRequest().getOaAuth());
        for (String split : splits) {
            if (split.startsWith(GROUPS_PREFIX)) {
                String[] groupSplits = StringUtils.split(split.substring(GROUPS_PREFIX.length()), '|');
                for (String groupSplit : groupSplits) {
                    groups.add(groupSplit);
                }
                break;
            }
        }
        
        try {
            new OaUserAoAsyncStub().checkSessionAndGroups(0, 1000
                    , oaUserId
                    , oaUserName
                    , oaUserSecret
                    , groups
                    , new OaAuthCallback(processor, param, reqId));
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            Result result = new Result(ResultType.E_CANCEL);
            result.setResultCode(ErrorCode.INNER_ERROR);
            result.setResultDescription("Call checkSessionAndGroups failed!");
            return result;
        }
        
        return IReqModule.RESULT_PENDING;
    }

    @Override
    public void cancel(long reqId) {
    }

    @Override
    public String getModuleName() {
        return OaAuthModule.class.getSimpleName();
    }
    
    public static void main(String[] args) {
        String[] splits = StringUtils.split("");
        for (String split : splits) {
            System.out.println("split=" + split);
        }
    }
}
