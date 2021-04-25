package org.soldier.platform.db_helper;

public interface IDataAccessMapping {
	public String getDbFieldName(String classFieldName);
	
	public String getClassFieldName(String dbFieldName);
}
