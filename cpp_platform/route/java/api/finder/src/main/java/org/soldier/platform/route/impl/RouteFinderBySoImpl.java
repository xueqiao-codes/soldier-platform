package org.soldier.platform.route.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RouteFinderBySoImpl {
	private static RouteFinderBySoImpl sInstance;
	public static RouteFinderBySoImpl getInstance(String libraryPath) {
		if (sInstance == null) {
			synchronized(RouteFinderBySoImpl.class) {
				if (sInstance == null) {
					sInstance = new RouteFinderBySoImpl(libraryPath);
				}
			}
		}
		return sInstance;
	}
	
	protected RouteFinderBySoImpl(String libraryPath) {
	    // ignore library path, load from jar
	    try {
            loadLib();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Error(e.getMessage(), e);
        }
	}
	
	public String GetRouteIp(
			long serviceKey, final String methodName, long routeKey) {
	    String ip = nativeGetRouteIp(serviceKey, methodName, routeKey);
	    if (ip == null || ip.isEmpty()) {
	        if ("true".equals(System.getenv("ENABLE_K8S_ROUTE"))) {
	            ip = "cmd" + serviceKey + ".soldier-service-alias.svc";
	        }
	    }
	    return ip;
	}
	
	public void updateCallInfo(
			long serviceKey, final String methodName, final String ip, int callResult) {
		nativeUpdateCallInfo(serviceKey, methodName, ip, callResult);
	}
    
    private void loadLib() throws IOException 
    {  
        String libFullName = "libjroute_agent.so";  
        InputStream in = null;  
        BufferedInputStream reader = null;  
        FileOutputStream writer = null;  

        File extractedLibFile = File.createTempFile("libjroute_agent",".so"); 
        try { 
            in = RouteFinderBySoImpl.class.getResourceAsStream("/linux/" + libFullName);  
            reader = new BufferedInputStream(in);  
            writer = new FileOutputStream(extractedLibFile);  
            byte[] buffer = new byte[1024];  
            while (reader.read(buffer) > 0){  
                writer.write(buffer);  
                buffer = new byte[1024];
            }  
            writer.flush();
        } catch (IOException e){  
            e.printStackTrace();  
        } finally {  
            if(in!=null)  
                in.close();  
            if(writer!=null)  
                writer.close();  
        }  
        System.out.println("loading " + extractedLibFile.toString());
        System.load(extractedLibFile.toString());  
        if (extractedLibFile.exists() && ! "1".equals(System.getenv("ENABLE_TMPSO_SAVE"))) {
            extractedLibFile.delete();
        }
    }
	
	private static native String nativeGetRouteIp(long serviceKey, String methodName, long routeKey);
	private static native void nativeUpdateCallInfo(long serviceKey, String methodName, 
			String ip, int callResult);
}
