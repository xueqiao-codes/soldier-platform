package org.soldier.platform.dal_set;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.soldier.base.PathService;
import org.soldier.base.file.FileWatcherFactory;
import org.soldier.base.file.IFileListener;
import org.soldier.base.file.IFileWatcher;
import org.soldier.base.logger.AppLog;
import org.soldier.watcher.file.FileWatcherModule;
import org.soldier.watcher.file.IFileWatcherListener;

/**
 * dal_set的功能类代理
 * @author Xairy
 */
public class DalSetProxy {
	private DalSet currentDalSet;
	private IConnectionPool connectionPool;
	private List<String> serviceNameList= new ArrayList<String>(5);
	private boolean bInited = false;
	private String initXmlPath = "";
	private IConnectionPoolFactory connectionPoolFactory
		= new IConnectionPoolFactory() {
			@Override
			public IConnectionPool createConnectionPool() {
				return new BoneCpConnectionPool(null);
			}
	};
	private boolean needWatchInitFile = true;
	private static DalSetProxy sInstance = null;
	
	public static DalSetProxy getInstance() {
		if (sInstance == null) {
			synchronized(DalSetProxy.class) {
				if (sInstance == null) {
					sInstance = new DalSetProxy();
				}
			}
		}
		return sInstance;
	}
	
	
	/**
	 *  负责虚拟机关闭是清理连接池
	 */
	protected  class DalSetShutdownHook extends Thread{
		@Override
		public void run(){
			DalSetProxy.getInstance().destroy();
		}
	}
	
	public synchronized void destroy() {
		if (connectionPool != null) {
			connectionPool.destory();
			connectionPool = null;
		}
		currentDalSet = null;
		bInited = false;
	}
	
	/**
	 *  主要对连接池重置,会将原有连接池销毁，建立一个空的连接池
	 *  该API会造成原有DB连接池中的连接突然断开
	 */
	public synchronized void reset(){
		IConnectionPool oldConnectionPool = connectionPool;
		if(connectionPool != null) {
			connectionPool = connectionPoolFactory.createConnectionPool();
		}
		
		if(oldConnectionPool != null){
			oldConnectionPool.destory();
		}
	}
	
	public boolean isDalSetInited() {
		return bInited;
	}
	
	public void reload() throws Exception {
		if(bInited) {
			AppLog.d("reload dal_set");
			loadFromXml(initXmlPath);
		}
	}
	
	public void setDefaultInitXmlPath(String path) {
		initXmlPath = path;
	}
	
	public void setConnectionPoolFactory(IConnectionPoolFactory factory) {
		connectionPoolFactory = factory;
	}
	
	private static void dalSetFileChanged() {
	    int retryCount = 0;
        boolean reloadSuccess = false;
        do {
            try {
                DalSetProxy.getInstance().reload();
                reloadSuccess = true;
                break;
            } catch (Exception e) {
                AppLog.e(e.getMessage(), e);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        } while (retryCount++ < 3);
        if (!reloadSuccess) {
            AppLog.e("reload dal_set failed!");
        }
	}
	
	private class DalSetFileListener implements IFileListener {
		public File dalSetFile;
		
		public DalSetFileListener(File dalSetFile) {
			this.dalSetFile = dalSetFile;
		}
		
		@Override
		public void fileRenamed(int watchId, String rootPath, String oldName,
				String newName) {
		}

		@Override
		public void fileModified(int watchId, String rootPath, String name) {
		    AppLog.d("fileModified rootPath=" + rootPath + ", name=" + name + ", dalSetFile.getName()=" + dalSetFile.getName());
			if (dalSetFile.getName().equals(name)) {
			    dalSetFileChanged();
			}
		}

		@Override
		public void fileDeleted(int watchId, String rootPath, String name) {
		}

		@Override
		public void fileCreated(int watchId, String rootPath, String name) {
		}
	}
	
	private IConnectionPool getConnectionPool() {
		if (connectionPool == null) {
			synchronized(DalSetProxy.class) {
				if (connectionPool == null) {
					connectionPool = connectionPoolFactory.createConnectionPool();
				}
			}
		}
		return connectionPool;
	}
	
	/**
	 *  从本地配置XML文件中进行加载
	 */
	public synchronized void loadFromXml(final String xmlPath)
			throws Exception{
		if(bInited) {
			if(!xmlPath.equals(initXmlPath)) {
				throw new Exception("DalSet Inited, but reload with different path, old=" 
					+ initXmlPath + ", new=" + xmlPath);
			} 
		}
		
		IDalSetBuilder builder = new XmlDalSetBuilder();
		File xmlFile = new File(xmlPath);
		InputStream input = new FileInputStream(xmlFile);
		
		/*
		 *  这里可以直接切换正在使用的dalset
		 *  由于不同线程中可能正利用dalset获取配置，java可能并不会gc掉原有dalset实例，
		 *  直接利用赋值操作到一个新的dalset中，后续线程从dalset中获取最新的切换过后的配置
		 */
		try {
			currentDalSet = constructDalSet(builder, input);
		} finally {
			input.close();
		}
		
		if(!bInited){
			Runtime.getRuntime().addShutdownHook(new DalSetShutdownHook());
			initXmlPath = xmlPath;
			
			if (needWatchInitFile) {
			    FileWatcherModule.Instance().addWatchFile(xmlFile, new IFileWatcherListener() {
                    @Override
                    public void onHandleFileChanged(File xmlFile) {
                        dalSetFileChanged();
                    }                    
			    });
//				FileWatcherFactory.getFileWatcher().addWatch(
//					xmlFile.getParent(), IFileWatcher.FILE_MODIFIED, false, 
//					new DalSetFileListener(xmlFile));
			}
		}
					
		bInited = true;
	}
	
	public void loadFromXml()throws Exception{
		String xmlPath = null;
		if (initXmlPath != null && !initXmlPath.isEmpty()) {
			xmlPath = initXmlPath;
		} else {
			xmlPath = PathService.getSoldierHome() + "/dal_set/config/dal_set.xml";
//			if(OSHelper.isWindows()){
//				xmlPath = "./winconfig/dal_set.xml";
//			}
			// 定义到环境变量中
			String xmlEnvPath = System.getenv("DAL_SET_PATH");
			if(xmlEnvPath != null && !xmlEnvPath.isEmpty()) {
				xmlPath = xmlEnvPath;
			} 
		}
		loadFromXml(xmlPath);
	}
	
	public ConnectionConfig getConnectionConfig(
				final String roleName,
				final String serviceName,
				final boolean isReadOnly,
				final long setKey) throws SQLException {
		if (currentDalSet == null) {
			throw new SQLException("dalset is not load!");
		}
		return currentDalSet.getConnectionConfig(roleName, 
				serviceName, isReadOnly, setKey);
	}
	
	public Connection getConnection(
			final String roleName,
			final String serviceName,
			final boolean isReadOnly,
			final long setKey)throws SQLException{
		if (currentDalSet == null) {
			throw new SQLException("dalset is not load!");
		}
		return currentDalSet.getConnection(roleName, serviceName, isReadOnly, setKey);
	}
	
	public String getTableName(
			final String roleName, 
			final String tableNamePrefix,
			final long tableKey) throws SQLException{
		if (currentDalSet == null) {
			throw new SQLException("dalset is not load!");
		}
		return currentDalSet.getTableName(roleName, tableNamePrefix, tableKey);
	}
	
	public int getSetNum(final String roleName) throws SQLException{
		if (currentDalSet == null) {
			throw new SQLException("dalset is not load!");
		}
		return currentDalSet.getSetNum(roleName);
	}
	
	public int getTableSliceNum(final String roleName, 
			final String tableNamePrefix) throws SQLException{
		if (currentDalSet == null) {
			throw new SQLException("dalset is not load!");
		}
		return currentDalSet.getTableSliceNum(roleName, tableNamePrefix);
	}
	
	private  DalSet constructDalSet(IDalSetBuilder builder,
				InputStream input)throws Exception{
		builder.doBuild(input, serviceNameList);
		return new DalSet(getConnectionPool(), builder.getUsersMap(),
				builder.getRolesDbSet(), builder.getRelationMap());
	}

	public boolean isNeedWatchInitFile() {
		return needWatchInitFile;
	}

	public void setNeedWatchInitFile(boolean needWatchInitFile) {
		this.needWatchInitFile = needWatchInitFile;
	}
	
	public void testXmlConfiguration(InputStream input) throws Exception {
		IDalSetBuilder builder = new XmlDalSetBuilder();
		List<String> testServiceNameList = new ArrayList<String>(5);
		builder.doBuild(input, testServiceNameList);
	}
}
