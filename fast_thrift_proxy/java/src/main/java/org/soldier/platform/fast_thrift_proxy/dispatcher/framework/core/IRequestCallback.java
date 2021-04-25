package org.soldier.platform.fast_thrift_proxy.dispatcher.framework.core;

import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Request;
import org.soldier.platform.fast_thrift_proxy.dispatcher.framework.Response;

public interface IRequestCallback {
	public void onRequestFinished(final long requestId, final Request request, final Response response);
}
