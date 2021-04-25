package org.soldier.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PathService {
	private static String soldierHome;
	
	public static String getSoldierHome() {
		if (soldierHome != null) return soldierHome;
		
		if (OSHelper.isWindows()) {
			soldierHome = "C:";
			return soldierHome;
		}
		
		soldierHome = System.getenv("SOLDIER_HOME");
		if (soldierHome == null || soldierHome.isEmpty()) {
			File soldierHomeFile = new File("/etc/soldier_home");
			if(soldierHomeFile.exists() && soldierHomeFile.canRead()) {
				BufferedReader in = null;
				try {
					in= new BufferedReader(new FileReader(soldierHomeFile));
					soldierHome = in.readLine();
					in.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			if (soldierHome == null || soldierHome.isEmpty()) {
				soldierHome = "/usr/local/soldier";
			}
		}
		return soldierHome;
	}
}
