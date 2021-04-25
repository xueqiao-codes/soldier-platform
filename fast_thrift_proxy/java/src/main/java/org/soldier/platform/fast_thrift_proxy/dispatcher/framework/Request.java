package org.soldier.platform.fast_thrift_proxy.dispatcher.framework;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.platform.svr_platform.comm.EClientLang;

import io.netty.handler.codec.http.HttpHeaders;

/**
 *  请求对象的封装
 * @author wileywang
 *
 */
public class Request implements ISupportUserData {
	
	private RequestOption mRequestOption;
	private UserData mUserData;
	private HttpHeaders mHttpHeaders;
	private String mOaAuth;  // 特定的OA鉴权方式
	private String mAuthRequest; // 特定的远程进行鉴权的方式
	
	private byte[] mReqBytes; 
	private int mServiceKey;
	private String mXForwardAddress;
	private String mServantName;
	private String[] mForbiddenMethodRegList;
	private String mFunctionConfPrefix;
	
	// 中间过程得到
	private ThriftReqParts mThriftReqParts;
	private ByteBuffer mTransportData;
	private Map<String, String> mExtraParams = new HashMap<String, String>();
	private EClientLang mClientLang;
	
	private String mMethodName; // 调用方法名
	private String mRemoteAddress;  // 请求IP
	private int mRemotePort;   // 请求端口
	
	private TProtocolFactory mFrontProtocolFactory;
	private TProtocolFactory mBackendProtocolFactory;
	
	private long mCreateTimestampMs;
	private long mTransportStartTimestampMs;
	private long mTransportFinishedTimestampMs;
	private long mFinishedTimestampMs;
	
	public Request(byte[] reqBytes
			, int serviceKey
			, TProtocolFactory frontProtocolFactory
			, TProtocolFactory backendProtocolFactory) {
		if (reqBytes == null) {
			throw new IllegalArgumentException("reqBytes should not be null");
		}
		if (serviceKey <= 0) {
			throw new IllegalArgumentException("serviceKey should not <= 0");
		}
		if (frontProtocolFactory == null) {
			throw new IllegalArgumentException("frontProtocolFactory should not be null");
		}
		if (backendProtocolFactory == null) {
			throw new IllegalArgumentException("backendProtocolFactory should not be null");
		}
		
		mReqBytes = reqBytes;
		mServiceKey = serviceKey;
		
		mRequestOption = new RequestOption();
		mFrontProtocolFactory = frontProtocolFactory;
		mBackendProtocolFactory = backendProtocolFactory;
		
		mCreateTimestampMs = System.currentTimeMillis();
	}
	
	public long getCreateTimestampMs() {
		return mCreateTimestampMs;
	}
	
	public byte[] getReqBytes() {
		return mReqBytes;
	}
	
	public RequestOption getRequestOption() {
		return mRequestOption;
	}
	
	public ByteBuffer getTransportData() {
		return mTransportData;
	}
	
	public Request setTransportData(ByteBuffer transportData) {
		this.mTransportData = transportData;
		return this;
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

	public int getServiceKey() {
		return mServiceKey;
	}

	public void setServiceKey(int serviceKey) {
		this.mServiceKey = serviceKey;
	}

	public TProtocolFactory getFrontProtocolFactory() {
		return mFrontProtocolFactory;
	}

	public void setFrontProtocolFactory(TProtocolFactory mFrontProtocolFactory) {
		this.mFrontProtocolFactory = mFrontProtocolFactory;
	}

	public TProtocolFactory getBackendProtocolFactory() {
		return mBackendProtocolFactory;
	}

	public void setBackendProtocolFactory(TProtocolFactory mBackendProtocolFactory) {
		this.mBackendProtocolFactory = mBackendProtocolFactory;
	}

	public String getMethodName() {
		return mMethodName;
	}

	public void setMethodName(String methodName) {
		this.mMethodName = methodName;
	}

	public String getRemoteAddress() {
		return mRemoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.mRemoteAddress = remoteAddress;
	}

	public int getRemotePort() {
		return mRemotePort;
	}

	public void setRemotePort(int remotePort) {
		this.mRemotePort = remotePort;
	}
	
	public String getXForwardAddress() {
		return this.mXForwardAddress;
	}
	
	public void setXForwaredAdddress(String xForwardAddress) {
		this.mXForwardAddress = xForwardAddress;
	}

	public String getServantName() {
		return mServantName;
	}

	public void setServantName(String servantName) {
		this.mServantName = servantName;
	}

	public ThriftReqParts getThriftReqParts() {
		return mThriftReqParts;
	}

	public void setThriftReqParts(ThriftReqParts thriftReqParts) {
		this.mThriftReqParts = thriftReqParts;
	}

	public long getTransportStartTimestampMs() {
		return mTransportStartTimestampMs;
	}

	public void setTransportStartTimestampMs(long transportStartTimestampMs) {
		this.mTransportStartTimestampMs = transportStartTimestampMs;
	}

	public long getTransportFinishedTimestampMs() {
		return mTransportFinishedTimestampMs;
	}

	public void setTransportFinishedTimestampMs(long transportFinishedTimestampMs) {
		this.mTransportFinishedTimestampMs = transportFinishedTimestampMs;
	}

	public long getFinishedTimestampMs() {
		return mFinishedTimestampMs;
	}

	public void setFinishedTimestampMs(long finishedTimestampMs) {
		this.mFinishedTimestampMs = finishedTimestampMs;
	}

	public String[] getForbiddenMethodRegList() {
		return mForbiddenMethodRegList;
	}

	public void setForbiddenMethodRegList(String[] forbiddenRegMethodList) {
		this.mForbiddenMethodRegList = forbiddenRegMethodList;
	}

	public String getFunctionConfPrefix() {
		return mFunctionConfPrefix;
	}

	public void setFunctionConfPrefix(String functionConfPrefix) {
		this.mFunctionConfPrefix = functionConfPrefix;
	}

    public HttpHeaders getHttpHeaders() {
        return mHttpHeaders;
    }

    public void setHttpHeaders(HttpHeaders headers) {
        this.mHttpHeaders = headers;
    }

    public String getOaAuth() {
        return mOaAuth;
    }

    public void setOaAuth(String oaAuth) {
        this.mOaAuth = oaAuth;
    }

    public String getAuthRequest() {
        return mAuthRequest;
    }

    public void setAuthRequest(String mAuthRequest) {
        this.mAuthRequest = mAuthRequest;
    }
    
    public Map<String, String> getExtraParams() {
        return mExtraParams;
    }
    
    public void putExtraParam(String key, String value) {
        mExtraParams.put(key, value);
    }

    public EClientLang getClientLang() {
        return mClientLang;
    }

    public void setClientLang(EClientLang clientLang) {
        this.mClientLang = clientLang;
    }
}
