package org.soldier.platform.svr_platform.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;

public class DaemonContainer {
	private static Properties daemonProperties;
	
	public static final Properties getDaemonProperties(){
		return daemonProperties;
	}
	
	private static void printHelp() {
		System.out.println("java " + DaemonContainer.class.getName() 
				+ " daemon.properties[config]");
	}
	
	private static Method getDaemonMainMethod(Class<?> daemonMainClass) {
		try {
			Method daemonMainMethod = daemonMainClass.getMethod("main", 
					new Class<?>[]{String[].class});
			if(Modifier.isStatic(daemonMainMethod.getModifiers()) 
			   && daemonMainMethod.getReturnType() == void.class){
				return daemonMainMethod;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void main(String[] args) {
		if(args.length < 1) {
			printHelp();
			System.exit(1);
		}
		
		InputStream in = null;
		File daemonPropertiesFile = null;
		try {
			daemonPropertiesFile = new File(args[0]);
			in = new FileInputStream(daemonPropertiesFile);
		} catch (FileNotFoundException e) {
			System.err.println("CONFIG OPEN FAILED, " + e.getMessage());
			System.exit(2);
		}
		daemonProperties = new Properties();
		try {
			daemonProperties.load(in);
		} catch (IOException e) {
			System.err.println("LOAD CONFIG " + args[0] + " ERR, "
					+ e.getMessage());
			System.exit(3);
		}
		Daemon.sDaemonProperties = daemonProperties;

		//从properties文件里面拿取Main-Class信息
		String daemonMainClassName = daemonProperties.getProperty("Main-Class");
		if(daemonMainClassName == null || daemonMainClassName.isEmpty()) {
			System.err.println("Main-Class is not set in daemon.properties");
			System.exit(4);
		}
		Class<?> daemonMainClass = null;
		try {
			daemonMainClass = Class.forName(daemonMainClassName.trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(5);
		}
		
		// 在/data/applog目录下建立和Main-Class结尾标识的部署名一致的Log目录
		String logModule = "daemon/" + daemonPropertiesFile.getParentFile().getName();
		System.out.println("Init AppLog module " + logModule);
		AppLog.init(logModule);
		
		Method daemonMainMethod = getDaemonMainMethod(daemonMainClass);
		if(daemonMainMethod == null) {
			System.err.println("main method is not found in class " + daemonMainClass);
			System.exit(6);
		}
		
		AppLogStream.redirectSystemPrint();
		long mainStartTime = System.currentTimeMillis();
		try {
			// 调用jar包中所需的main函数
			daemonMainMethod.invoke(null, (Object)args);
		} catch (Exception e) {
			if (System.currentTimeMillis() - mainStartTime < 2000) {
				AppLogStream.restoreSystemPrint();
				e.printStackTrace();
			} else {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
}
