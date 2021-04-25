package org.soldier.platform.admin.data;

import java.util.ArrayList;
import java.util.List;

public class StaticProjects {
	private static List<String> staticProjectList = new ArrayList<String>();
	
	static {
		staticProjectList.add("1024-1024.com");
		staticProjectList.add("www.1024-1024.com");
		staticProjectList.add("1024online.com");
		staticProjectList.add("www.1024online.com");
		staticProjectList.add("www.galacard.me");
		staticProjectList.add("galacard.me");
		staticProjectList.add("staticres.1024-1024.com");
		staticProjectList.add("staticres.1024online.com");
		staticProjectList.add("staticres.galacard.me");
		staticProjectList.add("cdnstatic.1024-1024.com");
		staticProjectList.add("cdnstatic.1024online.com");
		staticProjectList.add("cdnstatic.galacard.me");
	}
	
	public static List<String> get() {
		return staticProjectList;
	}
}
