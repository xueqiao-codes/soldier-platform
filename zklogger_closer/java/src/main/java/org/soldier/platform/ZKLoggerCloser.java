package org.soldier.platform;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.soldier.base.logger.AppLog;

public class ZKLoggerCloser implements Watcher {
    private static final String ZK_LOGGER_ROOT = "/logger";
    private static final String[] RESERVED_FLAGS = new String[] {
            "log_open", "no_warn", "no_error", "no_fatal"
    };
    
    private boolean mConnected = false;
    private ZooKeeper mInstance;
    private Semaphore mNotifySem = new Semaphore(0);
    
    public ZKLoggerCloser(String connectString
            , int sessionTimeout) throws IOException {
        mInstance = new ZooKeeper(connectString, sessionTimeout, this);
    }
    
    
    @Override
    public void process(WatchedEvent event) {
        AppLog.i("WatchedEvent event=" + event);
        if (event.getState() == KeeperState.SyncConnected) {
            mConnected = true;
        } 
        mNotifySem.release();    
    }
    
    private void rmr(String nodePath) throws InterruptedException, KeeperException {
        List<String> childList = mInstance.getChildren(nodePath, false);
        if (childList == null) {
            return ;
        }
        
        for (String child : childList) {
            rmr(nodePath + "/" + child);
        }
        
        mInstance.delete(nodePath, -1);
        AppLog.i("delete node " + nodePath);
    }
    
    
    private void process() throws Exception {
        List<String> childList = mInstance.getChildren(ZK_LOGGER_ROOT, false);
        if (childList == null) {
            return ;
        }
        
        for (String child : childList) {
            StringBuilder childPathBuilder = new StringBuilder(128);
            childPathBuilder.append(ZK_LOGGER_ROOT)
                            .append("/").append(child);
            
            List<String> loggerSwitchs = mInstance.getChildren(childPathBuilder.toString(), false);
            if (loggerSwitchs == null || loggerSwitchs.isEmpty()) {
                rmr(childPathBuilder.toString());
                continue;
            }
            
            boolean shouldReserved = false;
            for (String reserveFlag : RESERVED_FLAGS) {
                if (loggerSwitchs.contains(reserveFlag)) {
                    shouldReserved = true;
                    break;
                }
            }
            
            if (!shouldReserved) {
                rmr(childPathBuilder.toString());
            }
        }
        
    }
    
    public int runWork() throws Exception {
        mNotifySem.acquire();
        
        try {
            if (!mConnected) {
                return -1;
            }
        
            process();
            
            return 0;
        } finally {
            mInstance.close();
        }
        
       
    }
    
    public static void main(String[] args) throws IOException, Exception {
        AppLog.init("zklogger_closer");
        
        String connectString = System.getenv("ZK_ADDRS");
        if (connectString == null) {
            connectString = "zk-client.soldier-platform.svc:2181";
        }
        
        String sessionTimeoutStr = System.getenv("ZK_SESSION_TIMEOUT");
        if (sessionTimeoutStr == null) {
            sessionTimeoutStr = "6000";
        }
        int sessionTimeout = Integer.parseInt(sessionTimeoutStr);
        
        System.exit(new ZKLoggerCloser(connectString, sessionTimeout).runWork());
    }

   
}
