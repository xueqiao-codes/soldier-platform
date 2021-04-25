package org.soldier.platform.snginx_agent;

import com.google.gson.Gson;

public class GsonFactory {
	private static Gson gsonInstance;
	
	public static Gson getGson() {
		if (gsonInstance == null) {
			synchronized(GsonFactory.class) {
				if (gsonInstance == null) {
					gsonInstance = new Gson();
				}
			}
		}
		return gsonInstance;
	}
}
