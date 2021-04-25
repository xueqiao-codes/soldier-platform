package org.soldier.platform.svr_platform.client;

import org.apache.thrift.TBase;

public interface IMethodCallback<Req extends TBase<?,?>, Resp extends TBase<?,?>> {
	void onComplete(long callId, Req req, Resp resp);
	void onError(long callId, Req req, Exception e);
}
