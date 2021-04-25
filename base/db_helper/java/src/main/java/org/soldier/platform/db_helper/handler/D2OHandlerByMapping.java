package org.soldier.platform.db_helper.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.D2OHandler;
import org.soldier.platform.db_helper.IDataAccessMapping;
import org.soldier.platform.db_helper.handler.values.ValueProcessor;

public class D2OHandlerByMapping<T> implements D2OHandler<T> {
	private Class<T> clazz;
	private IDataAccessMapping dataAccessMapping;
	private Map<Integer, Field> fieldMapping = new HashMap<Integer, Field>();
	private Map<Integer, Method> setMethodMapping = new HashMap<Integer, Method>();
	private int columnCount = 0;
	
	public D2OHandlerByMapping(Class<T> clazz, IDataAccessMapping mapping){
		this.clazz = clazz;
		this.dataAccessMapping = mapping;
	}
	
	
	public void prepare(ResultSet rs) throws SQLException{
		ResultSetMetaData metaData = rs.getMetaData();
		columnCount = metaData.getColumnCount();
		for (int column = 1; column <= metaData.getColumnCount(); ++column) {
			String columnName = metaData.getColumnName(column);
			try {
				String classFieldName = dataAccessMapping.getClassFieldName(columnName);
				Field field = clazz.getField(classFieldName);
				field.setAccessible(true);
				fieldMapping.put(column, field);
				
				StringBuilder setMethodNameBuilder = new StringBuilder(classFieldName.length() + 4);
				setMethodNameBuilder.append("set");
				setMethodNameBuilder.append(Character.toUpperCase(classFieldName.charAt(0)));
				setMethodNameBuilder.append(classFieldName.substring(1));
				
				try {
					Method setMethod = clazz.getDeclaredMethod(setMethodNameBuilder.toString(), field.getType());
					setMethodMapping.put(column, setMethod);
				} catch (NoSuchMethodException e) {
					continue;
				}
			} catch (NoSuchFieldException e) {
				continue;
			} catch (SecurityException e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public T toObject(ResultSet rs) throws SQLException {
		try {
			Object target = clazz.newInstance();
			for (int column = 1; column <= columnCount; ++column) {
				Field field = fieldMapping.get(column);
				if (field == null) {
					continue ;
				}
					
				ValueProcessor processor  = ValueProcessorFactory.getProcessor(field, target);
				if (processor == null) {
					continue ;
				}
				
				Method setMethod = setMethodMapping.get(column);
				if (setMethod != null) {
					processor.fillValueByMethod(field.getType(), setMethod, column, rs);
				} else {
					processor.fillValueByField(field, column, rs);
				}
			}
			return clazz.cast(target);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new SQLException(e.getMessage(), e);
		}
	}


	@Override
	public void finish() {
		fieldMapping.clear();
		setMethodMapping.clear();
		columnCount = 0;
	}

}
