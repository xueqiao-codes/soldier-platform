package org.soldier.platform.fast_thrift_proxy.dispatcher.module.ext;

import java.io.File;

import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorageVariable;
import xueqiao.trade.hosting.storage.thriftapi.client.TradeHostingStorageStub;

public class TradeHostingStorageApiStub extends TradeHostingStorageStub {
    private static File STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingStorageVariable.serviceKey + ".sock");
    
    public TradeHostingStorageApiStub() {
        this.setSocketFile(STUB_SOCKET_FILE);
    }
}
