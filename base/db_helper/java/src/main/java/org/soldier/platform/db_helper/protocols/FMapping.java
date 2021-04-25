package org.soldier.platform.db_helper.protocols;

import org.soldier.platform.db_helper.IDataAccessMapping;

/**
 *  基于Ffield_name <->fieldName的规则映射
 * @author wileywang
 */
public class FMapping implements IDataAccessMapping {
	@Override
	public String getDbFieldName(String classFieldName) {
		if (classFieldName == null || classFieldName.isEmpty()) {
			return classFieldName;
		}
		
		StringBuilder dbFieldNameBuilder = new StringBuilder(classFieldName.length() + 20);
		dbFieldNameBuilder.append("F");
		for (int index = 0; index < classFieldName.length(); ++index) {
			if (Character.isUpperCase(classFieldName.charAt(index))) {
				dbFieldNameBuilder.append("_");
			}
			dbFieldNameBuilder.append(Character.toLowerCase(classFieldName.charAt(index)));
		}
		return dbFieldNameBuilder.toString();
	}

	@Override
	public String getClassFieldName(String dbFieldName) {
		if (dbFieldName == null || dbFieldName.length() < 2) {
			return dbFieldName;
		}
		if (!dbFieldName.startsWith("F") ) {
			return dbFieldName;
		}
		
		StringBuilder classFieldNameBuilder = new StringBuilder(dbFieldName.length());
		for(int index = 1; index < dbFieldName.length(); ++index) {
			if (dbFieldName.charAt(index) == '_') {
				index += 1;
				classFieldNameBuilder.append(Character.toUpperCase(dbFieldName.charAt(index)));
			} else {
				classFieldNameBuilder.append(Character.toLowerCase(dbFieldName.charAt(index)));
			}
		}
		
		return classFieldNameBuilder.toString();
	}

}
