package org.soldier.platform.web_framework.statics.fis;

import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MapJson {
	private JsonObject rootResObject;
	
	private ConcurrentHashMap<String, Res> cachedMap;
	
	public MapJson(Reader jsonReader) {
		JsonObject document  = (JsonObject)new JsonParser().parse(jsonReader);
		if (document == null) {
			return ;
		}
		rootResObject = document.getAsJsonObject("res");
		cachedMap = new ConcurrentHashMap<String, Res>();
	}
	
	public Res getRes(String resPath) {
		if (rootResObject == null) {
			return null;
		}
		if (resPath == null || resPath.isEmpty()) {
			return null;
		}
		
		Res res = cachedMap.get(resPath);
		if (res != null) {
			if (res.getUri() == null) {
				return null;
			}
			return res;
		}
		
		JsonObject node = rootResObject.getAsJsonObject(resPath);
		res = new Res();
		if (node != null) {
			res.setUri(node.get("uri").getAsString());
			res.setType(node.get("type").getAsString());
			
			return res;
		}
		
		cachedMap.put(resPath, res);
		return null;
	}
}
