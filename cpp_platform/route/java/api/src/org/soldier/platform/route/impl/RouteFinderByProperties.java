package org.soldier.platform.route.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.soldier.platform.route.IRouteFinder;

public class RouteFinderByProperties implements IRouteFinder {
	private Map<Long, String> mapConfig = new HashMap<Long, String>();
	
	public RouteFinderByProperties(String path) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Entry<Object, Object> entry : props.entrySet()){
			try{
				Long serviceKey = Long.parseLong(entry.getKey().toString());
				mapConfig.put(serviceKey, entry.getValue().toString());
				System.out.println("PUT SERVICE [" + serviceKey + "=" + entry.getValue() + "]");
			}catch(NumberFormatException e){
				continue;
			}
		}
	}
	
	@Override
	public String GetRouteIp(long serviceKey, String methodName, long routeKey) {
		return mapConfig.get(serviceKey);
	}
	@Override
	public void updateCallInfo(long serviceKey, String methodName, String ip,
			int callResult) {
	}
}
