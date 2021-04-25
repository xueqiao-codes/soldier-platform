package org.soldier.base.logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggingEvent;
import org.soldier.base.OSHelper;

import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;

/**
 * Log类，包装Log4j，采用在初始化目录下建立debug.log, info.log, warn.log, error.log, fatal.log的文件滚动日志<br/>
 * 同级下可以建立no_debug, no_info, no_warn, no_error, no_fatal, 其级别也进行相应的调整<br/>
 * 例如，发现no_info， 则日志的级别调整到WARN的级别（日志文件中将不会发现INFO级别以及以下的Log
 * @author Xairy
 *
 */
public class AppLog extends Logger{ 
	private final static String[] levels = new String[]{
		"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"
	};
	
	private static final String FQCN = AppLog.class.getName();
	private static Logger loggerInstance;
	
	static {
		try {
			loggerInstance = Logger.getLogger("AppLog", 
					new LoggerFactory(){
				@Override
				public Logger makeNewLoggerInstance(String name) {
					return new AppLog(name);
				}
			});
		} catch (Exception e) {
			System.err.println("Can not new AppLog, using common Logger");
			loggerInstance = Logger.getLogger(AppLog.class);
		}
	}
//	private static AppLog loggerInstance = (AppLog)Logger.getLogger("AppLog", 
//			new LoggerFactory(){
//				@Override
//				public Logger makeNewLoggerInstance(String name) {
//					return new AppLog(name);
//				}
//	});
//	private static Logger loggerInstance = Logger.getLogger(AppLog.class);
	
	private class LogCheckThread extends Thread {
		private boolean running = true;
		
		public void end() {
			running = false;
			interrupt();
		}
		
		@Override
		public void run(){
			while(running) {
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					if (!running) {
						break;
					}
				}
			
				// 检测
				Level logLevel = checkLogLevel();
				if(logLevel != repository.getThreshold()) {
				    System.err.println("Normal notice, LogCheckThread checkLevel newLevel=" 
				               + logLevel + ", oldLevel="+ repository.getThreshold());
					repository.setThreshold(logLevel);
				}
			}
		}
	}
	// 本地log方式， 不通过云端
	private static String DEFAULT_LOGGER_BASE_DIR = "/data/applog";
	
	private LogCheckThread checkThread;
	
	private String logBaseDir;
	private String logModule;
	private boolean hasInit;
	
	private boolean isLocalFileMode() {
	    if ("true".equals(System.getenv("APPLOG_STDOUT_MODE"))) {
	        return false;
	    }
	    return true;
	}
	
	private String[] generateLogPropertity(final String level, final String name){
	    return new String[]{
	              "log4j.appender." + name + "=org.apache.log4j.RollingFileAppender",
	              "log4j.appender." + name + ".File=" + logBaseDir + "/" + logModule + "/" + level.toLowerCase() + ".log",
	              "log4j.appender." + name + ".MaxFileSize=10000KB",
	              "log4j.appender." + name + ".Threshold=" + level,
	              "log4j.appender." + name + ".MaxBackupIndex=10",
	              "log4j.appender." + name + ".layout = org.apache.log4j.PatternLayout",
	              "log4j.appender." + name + ".layout.ConversionPattern = [%-d{yyyy-MM-dd HH:mm:ss,SSS}][%-5p:%t][%F:%L] %m%n"
	    };
	}
		
	public static void init(final String logModule){
		init(logModule, DEFAULT_LOGGER_BASE_DIR);
	}
	
	/**
	 * 初始化AppLog, 日志文件所在的目录实际为 logBaseDir + "/" + logModule 构成
	 * @param logModule 日志模块
	 * @param logBaseDir 日志模块所在的基本目录
	 */
	public static synchronized void init(final String logModule,
			final String logBaseDir){
		if (loggerInstance instanceof AppLog) {
			((AppLog)loggerInstance).doInit(logModule, logBaseDir);
		}
	}
	
	public static synchronized void destroy () {
		if (loggerInstance instanceof AppLog) {
			((AppLog)loggerInstance).doDestroy();
		}
	}
	
	public static void t(Object message) {
	    loggerInstance.trace(message);
	}
	
	public static void t(Object message, Throwable th) {
	    loggerInstance.trace(message, th);
	}
	
	public static void d(Object message){
		loggerInstance.debug(message);
	}
	
	public static void d(Object message, Throwable t){
		loggerInstance.debug(message, t);
	}
	
	public static void i(Object message){
		loggerInstance.info(message);
	}
	
	public static void i(Object message, Throwable t){
		loggerInstance.info(message, t);
	}
	
	public static void w(Object message){
		loggerInstance.warn(message);
	}
	
	public static void w(Object message, Throwable t){
		loggerInstance.warn(message, t);
	}
	
	public static void e(Object message){
		loggerInstance.error(message);
	}
	
	public static void e(Object message, Throwable t){
		loggerInstance.error(message, t);
	}
	
	public static void f(Object message) {
		loggerInstance.fatal(message);
	}
	
	public static boolean traceEnabled() {
	    return loggerInstance.isEnabledFor(Level.TRACE);
	}
	
	public static boolean debugEnabled(){
		return loggerInstance.isDebugEnabled();
	}
	
	public static boolean infoEnabled(){
		return loggerInstance.isInfoEnabled();
	}
	
	public static boolean warnEnabled(){
		return loggerInstance.isEnabledFor(Level.WARN);
	}
	
	public static boolean errorEnabled(){
		return loggerInstance.isEnabledFor(Level.ERROR);
	}
	
	public static boolean fatalEnabled(){
		return loggerInstance.isEnabledFor(Level.FATAL);
	}
	
	public static void f(Object message, Throwable t){
		loggerInstance.fatal(message, t);
	}
	
	private AppLog(String name) {
		super(name);
		hasInit = false;
		
		checkThread = new LogCheckThread();
		checkThread.setName("logCheckThread");
		checkThread.setDaemon(true);
		checkThread.setPriority(Thread.MIN_PRIORITY);
	}
	
	@Override
	protected
	void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
	    callAppenders(new LoggingEvent(FQCN, this, level, message, t));
	}
	
	private void doInit(final String logModule, final String logBaseDir) {
		if(hasInit && logModule.equals(this.logModule) 
			&& logBaseDir.equals(this.logBaseDir)){
			return ;
		}
		
		hasInit = false;
		this.logModule = logModule;
		this.logBaseDir = logBaseDir;
		doConfigure();
		hasInit = true;
		repository.setThreshold(checkLogLevel());
		System.out.println("AppLog doInit, setLogLevel to " + repository.getThreshold());
//		setLevel(checkLogLevel());
		
		if(!checkThread.isAlive()) {
			checkThread.start();
		}
	}
	
	private void doDestroy() {
		if (checkThread.isAlive()) {
			checkThread.end();
		}
	}
	
	private void doConfigure(){
		Properties props = new Properties();
		try {
			props.load(configurePropertiesInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
	}
	
	private ByteArrayInputStream configurePropertiesInputStream(){
		StringBuffer configureBuffer = new StringBuffer(128);
		
		List<String> appenderNameList = new ArrayList<String>(levels.length);
		if (isLocalFileMode()) {
		    for(String level : levels){
		        String name = level.toLowerCase() + "_out";
		        appenderNameList.add(name);
		        String[] properties = generateLogPropertity(level, name);
		        for(String property : properties){
		            configureBuffer.append(property);
		            configureBuffer.append("\n");
		        }
		    }
		} else {
		    appenderNameList.add("stdout");
		    configureBuffer.append("log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n")
		                   .append("log4j.appender.stdout.layout = org.apache.log4j.PatternLayout\n")
		                   .append("log4j.appender.stdout.layout.ConversionPattern = [%-d{yyyy-MM-dd HH:mm:ss,SSS}][%-5p:%t][%F:%L]")
		                   .append(" %m%n\n");
		}
		configureBuffer.append("log4j.rootLogger=");
		configureBuffer.append(Level.ALL);
		for(String appenderName : appenderNameList) {
			configureBuffer.append(",");
			configureBuffer.append(appenderName);
		}
		return new ByteArrayInputStream(configureBuffer.toString().getBytes());
	}
	
	private Level checkLogLevel() {
	    if (isLocalFileMode()) {
	        return checkLogLevelByLocalFileMode();
	    } 
	    
	    try {
            return checkLogLevelByQConf();
        } catch (Throwable e) {
            e.printStackTrace();
            return repository.getThreshold();
        }
	}
	
	private Level checkLogLevelByQConf() throws QconfException {
	    if (OSHelper.isWindows() || OSHelper.isMac()) {
	        return Level.ALL;
	    }
	    
	    StringBuilder qconfPath = new StringBuilder(64);
	    qconfPath.append("logger");
	    ArrayList<String> moduleKeys = Qconf.getBatchKeys(qconfPath.toString());
	    
	    String moduleKey = null;
	    if (logBaseDir.equals(DEFAULT_LOGGER_BASE_DIR)) {
	        moduleKey = logModule.replace('/', '|');
	    } else {
	        File loggerModuleDir = new File(new File(logBaseDir), logModule);
	        moduleKey = loggerModuleDir.getAbsolutePath().replace('/', '|');
	    }
	    
	    if (!moduleKeys.contains(moduleKey)) {
	        return Level.WARN;
	    }
	    qconfPath.append("/").append(moduleKey);
	    
	    ArrayList<String> levelKeys = Qconf.getBatchKeys(qconfPath.toString());
	    if (levelKeys.contains("no_fatal")) {
	        return Level.OFF;
	    }
	    if (levelKeys.contains("no_error")) {
	        return Level.FATAL;
	    }
	    if (levelKeys.contains("no_warn")) {
	        return Level.ERROR;
	    }
	    if (levelKeys.contains("no_info")) {
	        return Level.WARN;
	    }
	    if (levelKeys.contains("no_debug")) {
	        return Level.INFO;
	    }
	    if (levelKeys.contains("no_trace")) {
	        return Level.DEBUG;
	    }
	    
	    return Level.ALL;
	}
	
	private Level checkLogLevelByLocalFileMode(){
		File checkDir = new File(this.logBaseDir + "/" + logModule);
		if(!checkDir.exists()) {
			return Level.ALL;
		}
		
		File noFatalFile = new File(checkDir.getAbsolutePath() + "/no_fatal");
		if(noFatalFile.exists()) {
			return Level.OFF;
		}
		
		File noErrorFile = new File(checkDir.getAbsolutePath() + "/no_error");
		if(noErrorFile.exists()) {
			return Level.FATAL;
		}
		
		File noWarnFile = new File(checkDir.getAbsolutePath() + "/no_warn");
		if(noWarnFile.exists()) {
			return Level.ERROR;
		}
		
		File noRunFile = new File(checkDir.getAbsolutePath() + "/no_info");
		if(noRunFile.exists()) {
			return Level.WARN;
		}
		
		File noDebugFile = new File(checkDir.getAbsolutePath() + "/no_debug");
		if(noDebugFile.exists()){
			return Level.INFO;
		}
		
		File noTradeFile = new File(checkDir.getAbsoluteFile() + "/no_trace");
		if (noTradeFile.exists()) {
		    return Level.DEBUG;
		}
		
		return Level.ALL;
	}
}
