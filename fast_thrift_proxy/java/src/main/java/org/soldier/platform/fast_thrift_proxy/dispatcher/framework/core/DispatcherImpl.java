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
		mReqModuleList.add(new SliceReqModule()); // 切割Thrift请求的各个Field
		mReqModuleList.add(new ForbiddenReqModule()); // 判定请求方法是否被禁止
		mReqModuleList.add(new TimeoutConfigReqModule()); // 细化每个请求的超时设置
		mReqModuleList.add(new IPChooserModule());   // 选择转发的IP
		mReqModuleList.add(new OaAuthModule(this.getDispatcherRunner()));      // 处理OA权限验证
		mReqModuleList.add(new AuthRequestModule()); // 自定义鉴权模块
		mReqModuleList.add(new TradeHostingTransferModule());  // 托管机直接交易接入
		mReqModuleList.add(new PlatformArgsModifyReqModule()); // 修正平台请求参数
		mReqModuleList.add(new JoinReqModule()); // 重新拼接序列化的结构
	}
		
	protected void initRespModules() {
		mRespModuleList.add(mAttrModule.getRespModule()); // 上报模块
		mRespModuleList.add(new SliceRespModule());   // 切开回复的各个字段
		mRespModuleList.add(new ErrorInfoModityModule(this.getDispatcherRunner())); // 变更ErrorInfo的内容
		mRespModuleList.add(new JoinRespModule());  // 组合回包
	}
		
}
