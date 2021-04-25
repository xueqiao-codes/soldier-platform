package org.soldier.platform.db_helper.handler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TEnum;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.handler.values.BooleanValueProcessor;
import org.soldier.platform.db_helper.handler.values.DoubleValueProcessor;
import org.soldier.platform.db_helper.handler.values.FloatValueProcessor;
import org.soldier.platform.db_helper.handler.values.IntValueProcessor;
import org.soldier.platform.db_helper.handler.values.LongValueProcessor;
import org.soldier.platform.db_helper.handler.values.ShortValueProcessor;
import org.soldier.platform.db_helper.handler.values.StringValueProcessor;
import org.soldier.platform.db_helper.handler.values.TEnumValueProcessor;
import org.soldier.platform.db_helper.handler.values.ValueProcessor;

public class ValueProcessorFactory {
	private static Map<String, Class<? extends ValueProcessor> > PROCESSOR_MAP;
	
	static {
		PROCESSOR_MAP = new HashMap<String, Class<? extends ValueProcessor>>();
		
		put(BooleanValueProcessor.class);
		put(DoubleValueProcessor.class);
		put(FloatValueProcessor.class);
		put(IntValueProcessor.class);
		put(LongValueProcessor.class);
		put(ShortValueProcessor.class);
		put(StringValueProcessor.class);
	}
	
	private static void put(Class<? extends ValueProcessor> clazz){
		try {
			ValueProcessor processor = (ValueProcessor)(clazz.getConstructor(Object.class).newInstance(new Object()));
			PROCESSOR_MAP.put(processor.getTypeName(), clazz);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
		}
		
	}
	
	public static ValueProcessor getProcessor(Field field, Object target) throws Exception {
		if (field == null) {
			return null;
		}
		
		Class<? extends ValueProcessor> clazz = PROCESSOR_MAP.get(field.getType().getName());
		if (clazz == null) {
			Class<?>[] superInterfaces = field.getType().getInterfaces();
			if (superInterfaces != null && superInterfaces.length > 0
					&& superInterfaces[0].getName().equals(TEnum.class.getName())) {
				return new TEnumValueProcessor(target);
			}
			// 无法处理的类型，直接跳过, 交给ResultHook进行处理
			return null;
		} else {
			return (ValueProcessor)clazz.getConstructor(Object.class).newInstance(target);
		}
	}
}
