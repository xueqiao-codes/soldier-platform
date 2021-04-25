package org.soldier.platform.db_helper.handler.values;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

import org.apache.commons.lang.NotImplementedException;
import org.apache.thrift.TEnum;

public class TEnumValueProcessor extends ValueProcessor {
	public TEnumValueProcessor(Object target) {
		super(target);
	}

	@Override
	public void fillValueByMethod(Class<?> setType, Method setMethod, int column, ResultSet rs)
			throws Exception {
		Method valueMethod = setType.getMethod("findByValue", int.class);
		setMethod.invoke(target, valueMethod.invoke(null, rs.getInt(column)));
	}

	@Override
	public String getTypeName() {
		return TEnum.class.getName();
	}

	@Override
	public void fillValueByField(Field field, int column, ResultSet rs)
			throws Exception {
		throw new NotImplementedException();
	}

}
