package org.soldier.platform.dal_set;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.soldier.base.logger.AppLog;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;

public class BoneCpConnectionPool implements IConnectionPool {
    
    private static boolean isMiniMode() {
        try {
            return Qconf.getBatchKeys("platform/dal_set").contains("mini_mode");
        } catch (QconfException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
	public static class Args {
		private int minConnectionsPerPartition = 1;
		private int maxConnectionsPerPartition = 2;
		private int partitionCount = 2;
		private int connectionTimeoutMs = 50;
		private int idleConnectionTestPeriodInMinutes = 1;
		private int idleMaxAgeInMinutes = 3;
		private int poolAvailabilityThreshold = 0;
		private int acquireIncrement = 2;
		
		public Args() {
			if (isMiniMode()) {
				partitionCount = 2;
				maxConnectionsPerPartition = 2;
				poolAvailabilityThreshold = 0;
				acquireIncrement = 1;
			} else {
				int cpuCount = Runtime.getRuntime().availableProcessors();
				if (cpuCount <= 0) {
					AppLog.w("cpuCount <= 0 , jdk has some problems?");
				}
				partitionCount = cpuCount;
				if (partitionCount <= 1) {
					partitionCount = 2;
				}
			
				int expMaxConnectionCount = 16;
				int lessMaxConnectionCount = 4;
				maxConnectionsPerPartition = expMaxConnectionCount / partitionCount;
				if (maxConnectionsPerPartition < lessMaxConnectionCount) {
					maxConnectionsPerPartition = lessMaxConnectionCount;
				}
				
				poolAvailabilityThreshold = ((1 * 100) / maxConnectionsPerPartition);
				if (poolAvailabilityThreshold <= 0) {
					poolAvailabilityThreshold = 1;
				}
			}
		}
		
		public int getMinConnectionsPerPartition() {
			return minConnectionsPerPartition;
		}
		public void setMinConnectionsPerPartition(int minConnectionsPerPartition) {
			if (minConnectionsPerPartition >= 0) {
				this.minConnectionsPerPartition = minConnectionsPerPartition;
			}
		}
		
		public int getMaxConnectionsPerPartition() {
			return maxConnectionsPerPartition;
		}
		public void setMaxConnectionsPerPartition(int maxConnectionsPerPartition) {
			if (maxConnectionsPerPartition > 0 
					&& maxConnectionsPerPartition > this.minConnectionsPerPartition )  {
				this.maxConnectionsPerPartition = maxConnectionsPerPartition;
			}
		}
		
		public int getPartitionCount() {
			return partitionCount;
		}
		
		public void setPartitionCount(int partitionCount) {
			if (partitionCount > 0) {
				this.partitionCount = partitionCount;
			}
		}
		
		public int getConnectionTimeoutMs() {
			return connectionTimeoutMs;
		}
		public void setConnectionTimeoutMs(int connectionTimeoutMs) {
			if (connectionTimeoutMs > 0) {
				this.connectionTimeoutMs = connectionTimeoutMs;
			}
		}
		
		public int getIdleConnectionTestPeriodInMinutes() {
			return idleConnectionTestPeriodInMinutes;
		}
		public void setIdleConnectionTestPeriodInMinutes(
				int idleConnectionTestPeriodInMinutes) {
			if (idleConnectionTestPeriodInMinutes > 0) {
				this.idleConnectionTestPeriodInMinutes = idleConnectionTestPeriodInMinutes;
			}
		}
		
		public int getIdleMaxAgeInMinutes() {
			return idleMaxAgeInMinutes;
		}
		public void setIdleMaxAgeInMinutes(int idleMaxAgeInMinutes) {
			if (idleMaxAgeInMinutes > 0) {
				this.idleMaxAgeInMinutes = idleMaxAgeInMinutes;
			}
		}

		public int getPoolAvailabilityThreshold() {
			return poolAvailabilityThreshold;
		}

		public void setPoolAvailabilityThreshold(int poolAvailabilityThreshold) {
			if (poolAvailabilityThreshold < 0 || poolAvailabilityThreshold > 100) {
				return ;
			}
			this.poolAvailabilityThreshold = poolAvailabilityThreshold;
		}

		public int getAcquireIncrement() {
			return acquireIncrement;
		}

		public void setAcquireIncrement(int acquireIncrement) {
			this.acquireIncrement = acquireIncrement;
		}
		
	}
	
	private Hashtable<String, BoneCP> poolInstances = new Hashtable<String, BoneCP>();

	private Args args;
	
	public BoneCpConnectionPool(Args args) {
		if (args == null) {
			this.args = new Args();
		} else {
			this.args = args;
		}
	}
	
	
	@Override
	public Connection getConnection(ConnectionConfig config)
			throws SQLException {
		BoneCP connectionPool = poolInstances.get(config.toString());
		if(connectionPool != null){
			return connectionPool.getConnection();
		}
		
		if(!config.CheckConfig()){
			throw new SQLException("check config " + config + " failed");
		}
		
		connectionPool = InitConnectionPool(config);
		return connectionPool.getConnection();
	}
	
	@Override
	public void destory() {
		for(Entry<String, BoneCP> entry : poolInstances.entrySet()){
			entry.getValue().shutdown();
		}
		poolInstances.clear();
	}
	
	private synchronized BoneCP InitConnectionPool(ConnectionConfig config) 
				throws SQLException{
		// 防止多个线程同时连接同一个DB时， 其实已经有一个线程构建好，但是每个线程都去构建一个连接池实例
		BoneCP connectionPool = poolInstances.get(config.toString());
		if(connectionPool != null){
			return connectionPool;
		}
		
		try {
			Class.forName(config.getDriverClassName());
		} catch (ClassNotFoundException e) {
			throw new SQLException("class driver " + config.getDriverClassName() +
						       		" not found");
		}
	
		BoneCPConfig poolConfig = new BoneCPConfig();
		poolConfig.setJdbcUrl(config.getJDBCUrl());
		poolConfig.setUsername(config.getUserName());
		poolConfig.setPassword(config.getPasswd());		
		poolConfig.setMinConnectionsPerPartition(args.getMinConnectionsPerPartition());
		poolConfig.setMaxConnectionsPerPartition(args.getMaxConnectionsPerPartition());
		poolConfig.setPartitionCount(args.getPartitionCount());
		poolConfig.setConnectionTimeoutInMs(args.getConnectionTimeoutMs());
		poolConfig.setIdleConnectionTestPeriodInMinutes(args.getIdleConnectionTestPeriodInMinutes());
		poolConfig.setIdleMaxAge(args.getIdleMaxAgeInMinutes(), TimeUnit.MINUTES);
		poolConfig.setPoolAvailabilityThreshold(args.getPoolAvailabilityThreshold());
		poolConfig.setAcquireIncrement(args.getAcquireIncrement());
		poolConfig.setAcquireRetryAttempts(2);
		
		AppLog.d("newConnectionPool " + poolConfig + ", poolAvailability="
				+ poolConfig.getPoolAvailabilityThreshold() + ", acquireIncrement="
				+ poolConfig.getAcquireIncrement());
		
		connectionPool = new BoneCP(poolConfig);
		poolInstances.put(config.toString(), connectionPool);
		return connectionPool;
	}


}
