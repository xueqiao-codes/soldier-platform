package org.soldier.platform.fast_thrift_proxy.dispatcher.framework;


/**
 *  请求处理模块
 * @author wileywang
 *
 */
public interface IReqModule extends IModule <IReqModule.Param> {
	public static class Param {
		private Request mRequest;
		
		public Param(Request request) {
			this.mRequest = request;
		}
		
		public Request getRequest() {
			return mRequest;
		}
	}
}
