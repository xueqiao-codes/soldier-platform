package org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core;

import java.util.List;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.IModule;

/**
 *  模块任务的基类，遍历逻辑统一
 * @author wileywang
 *
 */
public class ModuleTask <ModuleParamType> implements IModule.IModuleProcessor<ModuleParamType> {
	protected static enum State {
		INIT,
		PROCESSING,
		PENDING,
		FINISHED
	}
	
	private List<IModule<ModuleParamType>> mModuleList;
	private int mCurrentIndex = -1;
	
	private long mCurrentRequestId = -1;
	private ModuleParamType mCurrentParam;
	
	private IModuleTaskExecutor<ModuleParamType> mTaskExecutor;
	
	private State mState = State.INIT;
	
	public ModuleTask(List<IModule<ModuleParamType>> moduleList
			, long requestId
			, ModuleParamType param
			, IModuleTaskExecutor<ModuleParamType> taskExecutor) {
		this.mModuleList = moduleList;
		this.mCurrentRequestId = requestId;
		this.mCurrentParam = param;
		this.mTaskExecutor = taskExecutor;
	}
	
	public void start() {
		process();
	}
	
	public void cancel() {
		mModuleList.get(mCurrentIndex).cancel(mCurrentRequestId);
		setState(State.FINISHED);
	}
			
	protected void process() {
		setState(State.PROCESSING);
		
		mCurrentIndex += 1;
		while(mCurrentIndex < mModuleList.size()) {
			IModule<ModuleParamType> module = mModuleList.get(mCurrentIndex);
			
			try {
				IModule.Result result = module.onProcess(mCurrentRequestId, mCurrentParam, this);
				if (result.getResultType() == IModule.ResultType.E_PENDING) {
					setState(State.PENDING);
					return ;
				}
				if (result.getResultType() == IModule.ResultType.E_CANCEL) {
					setState(State.FINISHED);
					mTaskExecutor.onTaskCancelled(mCurrentRequestId, mCurrentParam, result.getResultCode(), result.getResultDescription(), module);
					return ;
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
			
			mCurrentIndex += 1;
		}
		
		setState(State.FINISHED);
		mTaskExecutor.onTaskFinished(mCurrentRequestId, mCurrentParam);
	}
	
	protected IModule<ModuleParamType> getCurrentProcessModule() {
		if (mCurrentIndex < 0 || mCurrentIndex >= mModuleList.size()) {
			return null;
		}
		return mModuleList.get(mCurrentIndex);
	}
	
	protected State getState() {
		return mState;
	}
	
	protected void setState(State state) {
		if (mState == State.FINISHED && state != mState) {
			throw new IllegalStateException("finished task cannot be reset state!");
		}
		
		mState = state;
	}
	
	@Override
	public void continueProcess(IModule<ModuleParamType> module, long reqId,
			ModuleParamType param) {
		if (!checkModuleCall(module, reqId)) {
			return ;
		}
		
		if (State.PENDING != getState()) {
			AppLog.w("module(" + module.getModuleName() + ") calls continueProcess but state is not PENDING");
			return ;
		}
		
		process();
	}

	@Override
	public void cancelProcess(IModule<ModuleParamType> module, long reqId,
			ModuleParamType param, int errorCode, String errorMsg) {
		if (!checkModuleCall(module, reqId)) {
			return ;
		}
		
		if (State.PENDING != getState()) {
			AppLog.w("module(" + module.getModuleName() + ") calls cancelProcess but state is not PENDING");
			return ;
		}
		
		setState(State.FINISHED);
		mTaskExecutor.onTaskCancelled(mCurrentRequestId, mCurrentParam, errorCode, errorMsg, getCurrentProcessModule());
	}
	
	private boolean checkModuleCall(IModule<ModuleParamType> module, long reqId) {
		if (module != getCurrentProcessModule()) {
			throw new IllegalStateException("unexpected module(" + module.getModuleName() + ") process");
		}
		
		if (reqId != mCurrentRequestId) {
			throw new IllegalStateException("module(" + module.getModuleName() + ") process but reqId=" + reqId
					 + ", mCurrentRequestId=" + mCurrentRequestId + " is not equals!");
		}
		
		return true;
	}
}
