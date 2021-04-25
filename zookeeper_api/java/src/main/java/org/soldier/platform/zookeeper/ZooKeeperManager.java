package org.soldier.platform.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.zookeeper.IConfProvider.ConfException;
import org.soldier.platform.zookeeper.IConfProvider.ZooConf;

import com.google.common.eventbus.EventBus;

public class ZooKeeperManager implements Watcher {
	private volatile ZooKeeper mActiveZooKeeper;
	private final IConfProvider mConfProvider;
	private final EventBus mEventBus;
	
	public ZooKeeperManager(EventBus eventBus
			, IConfProvider confProvider) throws ConfException, IOException {
		this.mEventBus = eventBus;
		this.mConfProvider = confProvider;
		initClient();
	}
	
	public ZooKeeper getZooKeeper() {
		return mActiveZooKeeper;
	}
	
	private synchronized void initClient() throws ConfException, IOException {
		ZooConf conf = mConfProvider.getActiveConf();
		
		if (mActiveZooKeeper != null) {
			try {
				mActiveZooKeeper.close();
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
		
		mActiveZooKeeper = new ZooKeeper(conf.getAddrs(), conf.getSessionTimeout(), this);
	}
	
	public void onConfChanged() {
		try {
			initClient();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		AppLog.i("zookeeper event type=" + event.getType() + ", state=" + event.getState());
		try {
			if (event.getState() == KeeperState.SyncConnected) {
				ZooConnectedEvent connectedEvent = new ZooConnectedEvent();
				connectedEvent.zooClient = mActiveZooKeeper;
				if (mEventBus != null) {
					mEventBus.post(connectedEvent);
				}
			} else if (event.getState() == KeeperState.Expired) {
				ZooExpiredEvent expiredEvent = new ZooExpiredEvent();
				expiredEvent.zooClient = mActiveZooKeeper;
				if (mEventBus != null) {
					mEventBus.post(expiredEvent);
				}
				initClient();
			}
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
}
