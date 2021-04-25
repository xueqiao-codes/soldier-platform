package org.soldier.platform.attr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AttrReporterLibraryLoader {
	private static boolean isInited = false;
	
	public static void init() {
		if (!isInited) {
			synchronized(AttrReporterLibraryLoader.class) {
				if (!isInited) {
					try {
						loadLib();
					} catch (IOException e) {
						e.printStackTrace();
						Runtime.getRuntime().exit(1);
					}
					isInited = true;
				}
			}
		}
	}
	
	private static void loadLib() throws IOException 
    {  
        String libFullName = "libAttrReporter_java.so";  
        InputStream in = null;  
        BufferedInputStream reader = null;  
        FileOutputStream writer = null;  

        File extractedLibFile = File.createTempFile("libAttrReporter_java",".so"); 
        try { 
            in = AttrReporterLibraryLoader.class.getResourceAsStream("/linux/" + libFullName);  
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
        if (extractedLibFile.exists()) {
            extractedLibFile.delete();
        }
    }  
}
