package org.soldier.platform.web.mitty;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class MittyProperties {
	private static MittyProperties sInstance; 
	public static MittyProperties getInstance() {
		if (sInstance == null) {
			synchronized(MittyProperties.class) {
				if (sInstance == null) {
					sInstance = new MittyProperties();
				}
			}
		}
		return sInstance;
	}
	
	private Wini iniHandler;
	protected MittyProperties() {
	}
	
	public String getProperty(String section, String name) {
		if (iniHandler == null) {
			return null;
		}
		return iniHandler.get(section, name);
	}
	
	public String getProperty(String section, String name, String defaultValue) {
		if (iniHandler == null) {
			return defaultValue;
		}
		String propertyValue = getProperty(section, name);
		if (propertyValue == null || propertyValue.trim().isEmpty()) {
			return defaultValue;
		}
		return propertyValue.trim();
	}
	
	public void parse(File iniFile) throws InvalidFileFormatException, IOException {
		iniHandler = new Wini(iniFile);
	}
	
}
