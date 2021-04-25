package org.soldier.platform.admin.freemarker;

import java.util.List;

import org.soldier.base.NetHelper;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateSequenceModel;

public class StringListConverter implements TemplateMethodModelEx {	
	private String spliter;
	public StringListConverter(String spliter) {
		this.spliter = spliter;
	}
	
	@Override
	public Object exec(List arg0) throws TemplateModelException {
		SimpleSequence strList  = ((SimpleSequence)(arg0.get(0)));
		StringBuffer resultBuffer = new StringBuffer(64);
		if (strList != null) {
			for (int index = 0; index < strList.size(); ++index) {
				if (index > 0) {
					resultBuffer.append(spliter);
				}
				resultBuffer.append(strList.get(index).toString());
			}
		}
		return resultBuffer.toString();
	}

}
