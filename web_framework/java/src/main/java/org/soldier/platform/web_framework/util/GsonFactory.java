package org.soldier.platform.web_framework.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
	private static Gson gsonInstance;
	
	public static Gson getGson() {
		if (gsonInstance == null) {
			synchronized(GsonFactory.class) {
				if (gsonInstance == null) {
					gsonInstance = new GsonBuilder().disableHtmlEscaping().create();
				}
			}
		}
		return gsonInstance;
	}
}
