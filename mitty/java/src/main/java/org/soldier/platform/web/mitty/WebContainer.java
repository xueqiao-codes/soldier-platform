package org.soldier.platform.web.mitty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.JarURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebContainer {
	private File warFile;
	private boolean forceReplaceMittyIni;
	
	public WebContainer(File warFile,
			boolean forceReplaceMittyIni) {
		this.warFile = warFile;
		this.forceReplaceMittyIni = forceReplaceMittyIni;
	}
	
	private String readIniFile() throws IOException {
		URL	jarURL = new URL("jar:file:" + warFile.getAbsolutePath() + "!/WEB-INF/mitty.ini");
		JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection(); 
		InputStream inputStream = jarCon.getInputStream();
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n"); 
			}
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
		return builder.toString();
	}
	
	private void preConfigure() throws IOException {
		File mittyIniFile = new File(warFile.getParentFile().getAbsolutePath() + "/mitty.ini");
		if (!mittyIniFile.exists() || (forceReplaceMittyIni && mittyIniFile.isFile())) {
			String iniFileContent = readIniFile();
			
			if (!mittyIniFile.exists()) {
				mittyIniFile.createNewFile();
			}
			OutputStreamWriter outputWriter = new OutputStreamWriter(
					new FileOutputStream(mittyIniFile));
			try {
				outputWriter.write(iniFileContent);
			} finally {
				outputWriter.close();
			}
		}
		
		MittyProperties.getInstance().parse(mittyIniFile);
	}
	
	private int getHttpPort() {
		String httpPort = MittyProperties.getInstance().getProperty("port", "http");
		if (httpPort == null || httpPort.trim().isEmpty()) {
			return 8080;
		}
		return Integer.parseInt(httpPort);
	}
	
	private int getIdleConnectionTimeOut() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty(
					"connector", "idlConnectionTimeOut", "30000"));
	}
	
	private int getMinThreadNum() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty("threadPool", "minThreadNum", "20"));
	}
	
	private int getMaxThreadNum() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty("threadPool", "maxThreadNum", "200"));
	}
	
	private int getIdleThreadTimeOut() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty("threadPool", "idlThreadTimeOut", "60000"));
	}
	
	private String getHost() {
		return MittyProperties.getInstance().getProperty("connector", "host", "");
	}
	
	private int getAcceptQueueSize() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty("connector", "acceptQueueSize", "0"));
	}
	
	private int getMaxFormContentSize() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty("web", "maxFormContentSize", "2000000"));
	}
	
	private int getMaxFormKeys() {
		return Integer.parseInt(MittyProperties.getInstance().getProperty("web", "maxFormKeys", "1000"));
	}
	
	private String getContextPath() {
		String contextPath =  MittyProperties.getInstance().getProperty("web", "contextPath");
		if (contextPath == null || contextPath.isEmpty()) {
			return "/";
		}
		return contextPath;
	}
	
	private void dumpConfiguration() {
		System.out.println("==============================================================");
		System.out.println("httpPort=" + getHttpPort());
		System.out.println("idlConnectionTimeOut=" + getIdleConnectionTimeOut());
		System.out.println("host=" + getHost());
		System.out.println("acceptQueueSize=" + getAcceptQueueSize());
		System.out.println("minThreadNum=" + getMinThreadNum());
		System.out.println("maxThreadNum=" + getMaxThreadNum());
		System.out.println("idleThreadTimeOut=" + getIdleThreadTimeOut());
		System.out.println("contextPath=" + getContextPath());
		System.out.println("maxFormContentSize=" + getMaxFormContentSize());
		System.out.println("maxFormKeys=" + getMaxFormKeys());
		System.out.println("tempDir=" + Variables.getWebAppTempDir());
		System.out.println("==============================================================");
	}
	
	private void runApp() throws Exception {
		Server server = new Server(new QueuedThreadPool(getMaxThreadNum(), 
				getMinThreadNum(), getIdleThreadTimeOut()));
		server.setStopAtShutdown(true);
		server.setDumpAfterStart(false);
		server.setDumpBeforeStop(false);
		
		ServerConnector httpConnector = new ServerConnector(server);
		httpConnector.setPort(getHttpPort());
		if (!getHost().isEmpty()) {
			httpConnector.setHost(getHost());
		}
		httpConnector.setIdleTimeout(getIdleConnectionTimeOut());
		httpConnector.setAcceptQueueSize(getAcceptQueueSize());
		
		server.setConnectors(new Connector[]{httpConnector});
		
		WebAppContext appContext = new WebAppContext();
		appContext.setContextPath(getContextPath());
		appContext.setExtractWAR(true);
		appContext.setWar(warFile.getAbsolutePath());
		appContext.setDefaultsDescriptor(Variables.getMittyHome() + "/etc/webdefault.xml");
		appContext.setMaxFormContentSize(getMaxFormContentSize());
		appContext.setMaxFormKeys(getMaxFormKeys());
		
		if (Variables.getWebAppTempDir() != null) {
			appContext.setTempDirectory(new File(Variables.getWebAppTempDir()));
		}
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.addHandler(appContext);

		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { contexts, new DefaultHandler() });
				
		server.setHandler(handlers);
		try {
			dumpConfiguration();
			server.start();
			server.join();
		} catch (Exception e) {
			server.stop();
		}
	}
	
	public void start() throws Exception {
		preConfigure();
		runApp();
	}
}
