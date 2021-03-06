package org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core;

import java.io.IOException;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.WorkRunnerImpl;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.AttrModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.AuthRequestModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.ErrorInfoModityModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.ForbiddenReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.IPChooserModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.JoinReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.JoinRespModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.OaAuthModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.PlatformArgsModifyReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.SliceReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.SliceRespModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.TimeoutConfigReqModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.module.ext.TradeHostingTransferModule;
import org.soldier.platform.fast_thrift_proxy.dispatcher.transport.ServiceTransportLayer;

public class DispatcherImpl extends Dispatcher {
	private AttrModule mAttrModule = new AttrModule();
	
	public DispatcherImpl() {
		super(new WorkRunnerImpl().startWork());
		
		getDispatcherRunner().postWork(new Runnable() {
			@Override
			public void run() {
				initModules();
				initTransportLayer();
			}
		});
	}
		
	protected void initModules() {
		initReqModules();
		initRespModules();
	}
		
	protected void initTransportLayer() {
		try {
			mTransportLayer = new ServiceTransportLayer();
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
			throw new Error("can not init transport layer...");
		}
	}
		
	protected void initReqModules() {
		mReqModuleList.add(new SliceReqModule()); // ??????Thrift???????????????Field
		mReqModuleList.add(new ForbiddenReqModule()); // ?????????????????????????????????
		mReqModuleList.add(new TimeoutConfigReqModule()); // ?????????????????????????????????
		mReqModuleList.add(new IPChooserModule());   // ???????????????IP
		mReqModuleList.add(new OaAuthModule(this.getDispatcherRunner()));      // ??????OA????????????
		mReqModuleList.add(new AuthRequestModule()); // ?????????????????????
		mReqModuleList.add(new TradeHostingTransferModule());  // ???????????????????????????
		mReqModuleList.add(new PlatformArgsModifyReqModule()); // ????????????????????????
		mReqModuleList.add(new JoinReqModule()); // ??????????????????????????????
	}
		
	protected void initRespModules() {
		mRespModuleList.add(mAttrModule.getRespModule()); // ????????????
		mRespModuleList.add(new SliceRespModule());   // ???????????????????????????
		mRespModuleList.add(new ErrorInfoModityModule(this.getDispatcherRunner())); // ??????ErrorInfo?????????
		mRespModuleList.add(new JoinRespModule());  // ????????????
	}
		
}
