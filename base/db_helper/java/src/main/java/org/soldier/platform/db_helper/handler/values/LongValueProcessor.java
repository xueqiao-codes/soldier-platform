package org.soldier.platform.db_helper.handler.values;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class LongValueProcessor extends ValueProcessor {

	public LongValueProcessor(Object target) {
		super(target);
	}

	@Override
	public void fillValueByMethod(Class<?> setType, Method setMethod, int column, ResultSet rs)
			throws Exception {
		setMethod.invoke(target, rs.getLong(column));
	}

	@Override
	public String getTypeName() {
		return long.class.getName();
	}

	@Override
	public void fillValueByField(Field field, int column, ResultSet rs)
			throws Exception {
		field.setLong(target, rs.getLong(column));
	}
	
}
