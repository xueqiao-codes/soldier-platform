package org.soldier.platform.fast_thrift_proxy.dispatcher.framework;

import java.nio.ByteBuffer;

/**
 *  回复对象的封装
 * @author wileywang
 *
 */
public class Response implements ISupportUserData {
	private int mErrorCode;
	private String mErrorMsg;
	
	private UserData mUserData;
	private int mSubErrorCode = 0;  // 用于表示大错误码下的子错误码, 用于内部错误细分
	
	private byte[] mResponseContent;
	
	// 回复处理中间过程得到
	private ThriftRespParts mThriftRespParts;
	private ByteBuffer mOutputData; //输出的内容
	
	public Response() {
	}
	
	public Response(int errorCode, String errorMsg) {
		mErrorCode = errorCode;
		mErrorMsg = errorMsg;
	}
	
	public int getErrorCode() {
		return mErrorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.mErrorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return mErrorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.mErrorMsg = errorMsg;
	}
	
	public byte[] getResponseContent() {
		return mResponseContent;
	}
	
	public void setResponseContent(byte[] responseContent) {
		this.mResponseContent = responseContent;
	}
	
	public int getSubErrorCode() {
		return mSubErrorCode;
	}
	
	public void setSubErrorCode(int subErrorCode) {
		this.mSubErrorCode = subErrorCode;
	}

	@Override
	public UserData getUserData() {
		if (mUserData == null) {
			synchronized(this) {
				if (mUserData == null) {
					mUserData = new UserData();
				}
			}
		}
		return mUserData;
	}

    public ThriftRespParts getThriftRespParts() {
        return mThriftRespParts;
    }

    public void setThriftRespParts(ThriftRespParts thriftRespParts) {
        this.mThriftRespParts = thriftRespParts;
    }

    public ByteBuffer getOutputData() {
        return mOutputData;
    }

    public void setOutputData(ByteBuffer outputData) {
        this.mOutputData = outputData;
    }
}
