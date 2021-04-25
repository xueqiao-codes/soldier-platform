package org.soldier.platform.web_framework.freemarker;

import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class UnixTimestampConverter implements TemplateMethodModelEx {
	private static UnixTimestampConverter sInstance;
	public static UnixTimestampConverter getInstance() {
		if (sInstance == null) {
			synchronized(UnixTimestampConverter.class) {
				if (sInstance == null) {
					sInstance = new UnixTimestampConverter();
				}
			}
		}
		return sInstance;
	}
	
	private String timeFormatter;
	
	protected UnixTimestampConverter() {
		this("yyyy-MM-dd HH:mm:ss");
	}
	
	public UnixTimestampConverter(String timeFormatter) {
		this.timeFormatter = timeFormatter;
	}
	
	@Override
	public Object exec(List parameters) throws TemplateModelException {
		if (parameters.size() < 1) {
			return "";
		}
		long unixTimestamp = ((SimpleNumber)(parameters.get(0))).getAsNumber().longValue();
		return DateFormatUtils.format(unixTimestamp * 1000, timeFormatter);
	}

}
