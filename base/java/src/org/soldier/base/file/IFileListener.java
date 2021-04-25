package org.soldier.base.file;

public interface IFileListener {
	public void fileRenamed(int watchId, String rootPath, String oldName,  
            String newName);
	
	public void fileModified(int watchId, String rootPath, String name);
	
	public void fileDeleted(int watchId, String rootPath, String name);
	
	public void fileCreated(int watchId, String rootPath, String name);
}
