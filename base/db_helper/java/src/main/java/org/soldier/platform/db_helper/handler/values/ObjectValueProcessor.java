package org.soldier.platform.db_helper.handler.values;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class ObjectValueProcessor extends ValueProcessor {
	public ObjectValueProcessor(Object target) {
		super(target);
	}

	@Override
	public String getTypeName() {
		return "";
	}

	@Override
	public void fillValueByMethod(Class<?> setType, Method setMethod, int column, ResultSet rs)
			throws Exception {
		setMethod.invoke(target, rs.getObject(column));
	}

	@Override
	public void fillValueByField(Field field, int column, ResultSet rs)
			throws Exception {
		field.set(target, rs.getObject(column));
	}

}
