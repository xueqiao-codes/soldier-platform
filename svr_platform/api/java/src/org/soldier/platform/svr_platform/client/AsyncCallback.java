package org.soldier.platform.svr_platform.client;

import java.lang.reflect.Method;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.IMethodCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;


public class AsyncCallback <T> implements IMethodCallback {
	private T responseData;
	private Exception exception;
	private ErrorInfo err;
	
	public boolean isError() {
		return (exception != null) || (err != null);
	}
	
	public int getErrorCode() {
		if (exception != null) {
			return -1;
		}
		if (err != null) {
			return err.getErrorCode();
		}
		return 0;
	}
	
	public String getErrorMsg() {
		if (exception != null) {
			return "Callback failed";
		}
		if (err != null) {
			return err.getErrorMsg();
		}
		return "SUCCESS";
	}
	
	@Override
	public void onComplete(long callId, TBase req, TBase resp) {
		try {
			Method methodIsSetErr = resp.getClass().getMethod("isSetErr");
			methodIsSetErr.setAccessible(true);
			Method methodGetErr = resp.getClass().getMethod("getErr");
			methodGetErr.setAccessible(true);
			Method methodGetSuccess = resp.getClass().getMethod("getSuccess");
			methodGetSuccess.setAccessible(true);
			
			if ((Boolean) methodIsSetErr.invoke(resp)) {
				this.err = (ErrorInfo)methodGetErr.invoke(resp);
				AppLog.e(this.err.getErrorMsg(), this.err);
			} else {
				this.responseData = (T)methodGetSuccess.invoke(resp);
			}
			
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			this.exception = e;
		}
	}

	@Override
	public void onError(long callId, TBase req, Exception e) {
		AppLog.e(e.getMessage(), e);
		exception = e;
	}

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}

}
