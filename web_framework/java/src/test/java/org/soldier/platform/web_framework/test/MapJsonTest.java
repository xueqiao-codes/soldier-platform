package org.soldier.platform.web_framework.test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.soldier.platform.web_framework.freemarker.TemplateUtil;
import org.soldier.platform.web_framework.statics.fis.FisConfiguration;
import org.soldier.platform.web_framework.statics.fis.MapJson;
import org.soldier.platform.web_framework.statics.fis.freemarker.FisResTemplate;

public class MapJsonTest {
	public static void testMapJsonParse() {
		InputStream stream = MapJsonTest.class.getResourceAsStream("/map.json");
		
		MapJson map = new MapJson(new InputStreamReader(stream));
		
		System.out.println(map.getRes("none"));
		System.out.println(map.getRes("none"));
		System.out.println(map.getRes("scripts/main.js"));
		System.out.println(map.getRes(null));
	}
	
	public static void testFisResTemplate() {
		TemplateUtil.setTemplatesPath("E:/");
		
		InputStream stream = MapJsonTest.class.getResourceAsStream("/map.json");
		MapJson map = new MapJson(new InputStreamReader(stream));
		
		FisConfiguration.getInstance().addMapJson(map);
		FisConfiguration.getInstance().addMapJson(
				new MapJson(new InputStreamReader(MapJsonTest.class.getResourceAsStream("/map2.json"))));
		
		FisConfiguration.getInstance().setDefaultHost("http://test.1024-1024.com");
		FisConfiguration.getInstance().setDefaultPrefixPath("/galacard/weixin");
		
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		dataMap.put("fisRes", new FisResTemplate());
		try {
			System.out.println(TemplateUtil.getInstance().showTemplate("test.ftl", dataMap));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static void main(String[] args) {
//		testMapJsonParse();
		testFisResTemplate();
	}
}
