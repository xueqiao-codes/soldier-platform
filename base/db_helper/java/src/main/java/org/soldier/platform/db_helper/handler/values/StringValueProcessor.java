package org.soldier.platform.db_helper.handler.values;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class StringValueProcessor extends ValueProcessor {

	public StringValueProcessor(Object target) {
		super(target);
	}

	@Override
	public void fillValueByMethod(Class<?> setType, Method setMethod, int column, ResultSet rs)
			throws Exception {
		setMethod.invoke(target, rs.getString(column));
	}

	@Override
	public String getTypeName() {
		return String.class.getName();
	}

	@Override
	public void fillValueByField(Field field, int column, ResultSet rs)
			throws Exception {
		field.set(target, rs.getString(column));
	}

}
