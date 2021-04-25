package org.soldier.platform.fast_thrift_proxy.dispatcher.module;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IReqModule;

public class AuthRequestModule implements IReqModule {

    @Override
    public Result onProcess(long reqId, Param param, IModuleProcessor<Param> processor) {
        if (param.getRequest().getAuthRequest() == null) {
            return IReqModule.RESULT_OK;
        }
        
        String[] keys = StringUtils.split(param.getRequest().getAuthRequest());
        for (String key : keys) {
            param.getRequest().putExtraParam(key
                    , param.getRequest().getHttpHeaders().get(key, ""));
        }
        
        return IReqModule.RESULT_OK;
    }

    @Override
    public void cancel(long reqId) {
    }

    @Override
    public String getModuleName() {
        return AuthRequestModule.class.getSimpleName();
    }

}
