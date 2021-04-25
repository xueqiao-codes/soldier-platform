package org.soldier.platform.admin.freemarker;

import java.util.List;

import org.soldier.base.NetHelper;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

public class IpListConverter implements TemplateMethodModelEx {	
	private String spliter;
	public IpListConverter(String spliter) {
		this.spliter = spliter;
	}
	
	@Override
	public Object exec(List arg0) throws TemplateModelException {
		SimpleSequence ipList  = ((SimpleSequence)(arg0.get(0)));
		StringBuffer resultBuffer = new StringBuffer(64);
		if (ipList != null) {
			for (int index = 0; index < ipList.size(); ++index) {
				if (index > 0) {
					resultBuffer.append(spliter);
				}
				TemplateNumberModel model = (TemplateNumberModel)(ipList.get(index));
				resultBuffer.append(NetHelper.NetAddr(model.getAsNumber().longValue()));
			}
		}
		return resultBuffer.toString();
	}

}
