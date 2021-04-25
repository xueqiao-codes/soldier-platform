package org.soldier.platform.db_helper.handler.values;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public abstract class ValueProcessor {
	protected Object target;
	
	public ValueProcessor(Object target) {
		this.target = target;
	}
	
	public abstract String getTypeName();
	
	/**
	 *  使用set方法进行映射
	 */
	public abstract void fillValueByMethod(Class<?> setType, Method setMethod, int column, ResultSet rs) throws Exception;
	
	
	/**
	 *  通过字段名称进行映射
	 */
	public abstract void fillValueByField(Field field, int column, ResultSet rs) throws Exception;
}
