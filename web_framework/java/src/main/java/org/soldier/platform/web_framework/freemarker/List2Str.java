package org.soldier.platform.web_framework.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;

public class List2Str implements TemplateMethodModelEx {
	private String seperator;
	
	public List2Str(String seperator) {
		this.seperator = seperator;
	}
	
	
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments == null || arguments.size() < 1) {
			return "";
		}
		
		TemplateSequenceModel sequence  = ((TemplateSequenceModel)(arguments.get(0)));
		StringBuffer resultBuffer = new StringBuffer(64);
		if (sequence != null) {
			for (int index = 0; index < sequence.size(); ++index) {
				if (index != 0) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(sequence.get(index).toString());
			}
		}
		return resultBuffer.toString();
	}

}
