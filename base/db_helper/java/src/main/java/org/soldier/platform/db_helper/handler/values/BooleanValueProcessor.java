package org.soldier.platform.db_helper.handler.values;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class BooleanValueProcessor extends ValueProcessor {
	public BooleanValueProcessor(Object target) {
		super(target);
	}

	@Override
	public void fillValueByMethod(Class<?> setType, Method setMethod, int column, ResultSet rs)
			throws Exception {
		if (0 == rs.getInt(column)) {
			setMethod.invoke(target, false);
		} else {
			setMethod.invoke(target, true);
		}
	}

	@Override
	public String getTypeName() {
		return boolean.class.getName();
	}

	@Override
	public void fillValueByField(Field field, int column, ResultSet rs)
			throws Exception {
		if (0 == rs.getInt(column)) {
			field.setBoolean(target, false);
		} else {
			field.setBoolean(target, true);
		}
	}

}
