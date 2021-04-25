package org.soldier.base;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

public class LibraryLoader {
	public static void addSearchPath(final String path) {
		try {     
	        Field field = ClassLoader.class.getDeclaredField("usr_paths");     
	        field.setAccessible(true);     
	        String[] paths = (String[])field.get(null);     
	        for (int i = 0; i < paths.length; i++) {     
	            if (path.equals(paths[i])) {     
	                return;     
	            }     
	        }     
	        String[] tmp = new String[paths.length+1];     
	        System.arraycopy(paths,0,tmp,0,paths.length);     
	        tmp[paths.length] = path;     
	        field.set(null, tmp);    
	        System.setProperty("java.library.path", StringUtils.join(tmp, ";"));
	    } catch (IllegalAccessException e) {     
	        e.printStackTrace();
	    } catch (NoSuchFieldException e) {     
	    	e.printStackTrace();
	    }
	}
}
