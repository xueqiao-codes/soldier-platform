package org.soldier.platform.route;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.route_agent_daemon.GsonFactory;
import org.soldier.platform.route_agent_daemon.RouteConfig;
import org.soldier.platform.zookeeper.IConfProvider;
import org.soldier.platform.zookeeper.ZooKeeperManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;

public class TickWorker implements IConfProvider {
	private final static String ROUTE_SERVICES_PATH = "/route/services";
	
	private volatile Map<Integer, Boolean> mLocalServiceKeys 
		= new HashMap<Integer, Boolean>();
	
	private EventBus mEventBus;
	private ZooKeeperManager mRouteZooKeeper;
	private String mHostAddress;
	
	private HeartBeatWorker mHeartBeatWorker = new HeartBeatWorker();
	
	private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
	
	public TickWorker(RouteConfig initConfig) throws ConfException, IOException {
		mHostAddress = InetAddress.getLocalHost().getHostAddress().trim();
		if ("127.0.0.1".equals(mHostAddress)) {
			throw new IOException("hostname has bind to local ip");
		}
		
		mEventBus = new EventBus();
		mRouteZooKeeper = new ZooKeeperManager(mEventBus, this);
		
		onLoadRouteConfig(initConfig);
		
		AppLog.i("tick worker hostAddress=" + mHostAddress);
	}
	
	@Subscribe
	public void onRouteConfigUpdated(final RouteConfig newConfig) {
		mExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					onLoadRouteConfig(newConfig);
				} catch (Throwable e) {
					AppLog.e(e.getMessage(), e);
				}
			}
			
		});
	}
	
	private void onLoadRouteConfig(RouteConfig config) {
		if (config.getConfig() == null || config.getConfig().isEmpty()) {
			return ;
		}
		
		AppLog.i("onLoadRouteConfig version=" + config.getVersion());
		RouteList list = GsonFactory.getGson().fromJson(config.getConfig(), RouteList.class);
		if (list == null) {
			return ;
		}
		if (list.getItems().isEmpty()) {
			return ;
		}
		
		Map<Integer, Boolean> localServiceKeys = new HashMap<Integer, Boolean>();
		for (RouteItem item : list.getItems()) {
			String[] ipList = item.getIpList().split(",");
			if (ipList != null && ipList.length > 0) {
				for (String ip : ipList) {
					if (ip.trim().equals(mHostAddress)) {
						localServiceKeys.put(item.getServiceKey(), true);
					}
				}
			}
		}
		
		Map<Integer, Boolean> oldLocalServiceKeys = mLocalServiceKeys;
		mLocalServiceKeys = localServiceKeys;
		
		for (Integer serviceKey : oldLocalServiceKeys.keySet()) {
			if (!mLocalServiceKeys.containsKey(serviceKey)) {
				try {
					cleanServiceKey(String.valueOf(serviceKey));
				} catch (Throwable e) {
					AppLog.e(e.getMessage(), e);
				}
			}
		}
	}
	
	private void cleanServiceKey(String serviceKey) throws KeeperException, InterruptedException {
		AppLog.i("clean serviceKey=" + serviceKey);
		
		String serviceKeyPath = getServiceKeyZooPath(serviceKey);
		List<String> serversStatus
			= mRouteZooKeeper.getZooKeeper().getChildren(serviceKeyPath, null);
		if (serversStatus != null && !serversStatus.isEmpty()) {
			for (String status : serversStatus) {
				if (status.startsWith(mHostAddress + "$")) {
					mRouteZooKeeper.getZooKeeper().delete(serviceKeyPath + "/" + status, -1);
				}
			}
		}
	}
	
	/**
	 *  检查各个服务心跳
	 */
	class TickTask implements Runnable {
		@Override
		public void run() {
			try {
				long startTimestamp = System.currentTimeMillis();
				tick();
				AppLog.i("tick time=" + (System.currentTimeMillis() - startTimestamp) + "ms");
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
			startTickTaskDelayed();
		}
	}
	
	/**
	 *  删除的服务清理
	 */
	class CleanTask implements Runnable {
		@Override
		public void run() {
			try {
				long startTimestamp = System.currentTimeMillis();
				clean();
				AppLog.i("clean run time=" + (System.currentTimeMillis() - startTimestamp) + "ms");
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
			
			startCleanDelayed();
		}
		
	}
	
	private void startTickTaskDelayed() {
		int tickTime = getTickTime();
		AppLog.i("start tick task after " + tickTime + "ms");
		mExecutor.schedule(new TickTask(), tickTime, TimeUnit.MILLISECONDS);
	}
	
	private void startCleanDelayed() {
		int cleanSeconds = getCleanSeconds();
		AppLog.i("start clean task after " + cleanSeconds + "s");
		mExecutor.schedule(new CleanTask(), cleanSeconds, TimeUnit.SECONDS);
	}
	
	public void startWork() {
		mExecutor.schedule(new TickTask(), 1, TimeUnit.SECONDS);
		startCleanDelayed();
	}
	
	private void clean() throws KeeperException, InterruptedException {
		AppLog.i("clean...");
		List<String> serviceKeys = mRouteZooKeeper.getZooKeeper().getChildren(ROUTE_SERVICES_PATH, null);
		if (serviceKeys == null || serviceKeys.isEmpty()) {
			 return ;
		}
		
		Map<Integer, Boolean> localServiceKeys = mLocalServiceKeys;
		for (String serviceKey : serviceKeys) {
			if (!localServiceKeys.containsKey(Integer.parseInt(serviceKey))) {
				cleanServiceKey(serviceKey);
			}
		}
	}
	
	private void tick() throws TException {
		int timeout = 5000;
		try {
			timeout = NumberUtils.toInt(Qconf.getConf("route/agent/daemon/heartBeatTimeout"));
		} catch (QconfException e) {
			AppLog.e(e.getMessage(), e);
		}
		
		AppLog.i("start tick heartBeatTimeout=" + timeout);
		
		// must copy reference
		Map<Integer, Boolean> localServiceKeys = mLocalServiceKeys;
		if (localServiceKeys.isEmpty()) {
			return ;
		}
		
		mHeartBeatWorker.run(localServiceKeys.keySet(), timeout);
		
		for (Integer serviceKey : localServiceKeys.keySet()) {
			try {
				long result = timeout;
				if (mHeartBeatWorker.getResults().containsKey(serviceKey)) {
					result = mHeartBeatWorker.getResults().get(serviceKey);
				}
				
				AppLog.i("heartBeat serviceKey=" + serviceKey 
						+ ", timeout=" + timeout
						+ ", result=" + result);
				handleTick(serviceKey, timeout, result);
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);;
			}
		}
	}
	
	private String getServiceKeyZooPath(String serviceKey) {
		return ROUTE_SERVICES_PATH + "/" + serviceKey;
	}
	
	private void handleTick(int serviceKey
			, int timeout
			, long result) throws KeeperException, InterruptedException {
		String serviceKeyPath = getServiceKeyZooPath(String.valueOf(serviceKey));
		
		List<String> serversStatus = new ArrayList<String>();
		Stat stat = mRouteZooKeeper.getZooKeeper().exists(serviceKeyPath, false);
		if (stat == null) {
			mRouteZooKeeper.getZooKeeper().create(serviceKeyPath
					, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			serversStatus = mRouteZooKeeper.getZooKeeper().getChildren(serviceKeyPath, null);
		}
		
		StringBuilder heartBeatResultBuilder = new StringBuilder(64);
		if (result < 0) {
			heartBeatResultBuilder.append(mHostAddress + "$FAILED");
		} else if (result >= timeout) {
			heartBeatResultBuilder.append(mHostAddress + "$TIMEOUT");
		} else {
			int level = 10;
			if (result <= 100) {
				level = 0;
			} else if (result > 100 && result <= 500) {
				level = 1;
			} else if (result > 500 && result < 1000) {
				level = 2;
			} else {
				level = 3;
			} 
			
			heartBeatResultBuilder.append(mHostAddress + "$" + level);
		}
		
		boolean existed = false;
		for (String status : serversStatus) {
			if (status.startsWith(mHostAddress + "$")) {
				if (heartBeatResultBuilder.toString().equals(status)) {
					existed = true;
					continue;
				}

				mRouteZooKeeper.getZooKeeper().delete(serviceKeyPath + "/" + status, -1);
				AppLog.i("delete " + status + " node for " + serviceKeyPath);
			}
		}
		if (!existed) {
			mRouteZooKeeper.getZooKeeper().create(serviceKeyPath + "/" + heartBeatResultBuilder.toString()
					, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			AppLog.d("create " + heartBeatResultBuilder.toString() + "  node for " + serviceKeyPath);
		}
	}
	
	private int getTickTime() {
		String tickTimeStr = null;
		try {
			tickTimeStr = Qconf.getConf("route/agent/daemon/tickTime");
		} catch (QconfException e) {
			AppLog.e(e.getMessage(), e);
			return 10000;
		}
		
		int tickTime = NumberUtils.toInt(tickTimeStr);
		if (tickTime <= 0) {
			return 10000;
		}
		
		return tickTime;
	}
	
	private int getCleanSeconds() {
		String cleanTimeStr = null;
		try {
			cleanTimeStr = Qconf.getConf("route/agent/daemon/cleanSeconds");
		} catch (QconfException e) {
			AppLog.e(e.getMessage(), e);
			return 900;
		}
		
		int cleanTime = NumberUtils.toInt(cleanTimeStr);
		if (cleanTime <= 0) {
			return 900;
		}
		
		return cleanTime;
	}
	
	@Override
	public ZooConf getActiveConf() throws ConfException {
		try {
			Map<String, String> configs = Qconf.getBatchConf("route/zookeeper");
			String connectString = configs.get("connectString");
			if (connectString == null || connectString.isEmpty()) {
				throw new ConfException("connectString is null or empty");
			}
			String sessionTimeoutStr = configs.get("sessionTimeout");
			if (sessionTimeoutStr == null || sessionTimeoutStr.isEmpty()) {
				throw new ConfException("sessionTimeout is null or empty");
			}
			int sessionTimeout = NumberUtils.toInt(sessionTimeoutStr);
			if (sessionTimeout <= 0) {
				throw new ConfException("illegal sessionTimeout value");
			}	
			
			ZooConf zooConf = new ZooConf();
			zooConf.setAddrs(connectString);
			zooConf.setSessionTimeout(sessionTimeout);
			AppLog.i("Zookeeper Client, connectString=" + connectString + ", sessionTimeout=" + sessionTimeout);
			return zooConf;
		} catch (Throwable e) {
			throw new ConfException(e);
		}
	}
}
