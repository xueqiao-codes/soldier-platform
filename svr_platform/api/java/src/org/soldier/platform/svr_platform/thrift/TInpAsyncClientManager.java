package org.soldier.platform.svr_platform.thrift;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import org.apache.thrift.async.TAsyncClientManager;

public class TInpAsyncClientManager extends TAsyncClientManager {

	public TInpAsyncClientManager() throws IOException {
		super();
	}
	
	@Override
	public SelectionKey transferSelectionKey(SelectionKey key) {
		return (InpSocketSelectionKey)(key.attachment());
	}

}
