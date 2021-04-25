package org.soldier.platform.web_framework.statics.fis.freemarker;

import java.util.List;

import org.soldier.platform.web_framework.statics.fis.FisConfiguration;
import org.soldier.platform.web_framework.statics.fis.MapJson;
import org.soldier.platform.web_framework.statics.fis.Res;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class FisResTemplate implements TemplateMethodModelEx {
	public FisResTemplate() {
	}

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments.size() < 1) {
			return "";
		}
		
		String resPathParam = arguments.get(0).toString();
		if (resPathParam == null || resPathParam.isEmpty()) {
			return "";
		}
		
		boolean hasPrefixPath = !resPathParam.startsWith("@");
		String resPath = hasPrefixPath ? resPathParam : resPathParam.substring(1);
		
		String realResPath = getRealResPath(resPath);
		if (realResPath.startsWith("http://") || realResPath.startsWith("https://")) {
			return realResPath;
		}
		
		String defaultHost = FisConfiguration.getInstance().getDefaultHost();
		if (defaultHost != null && !defaultHost.isEmpty()) {
			StringBuffer uriBuffer = new StringBuffer(defaultHost.length()
					+ realResPath.length() + 10);
			uriBuffer.append(defaultHost);
			if (hasPrefixPath && FisConfiguration.getInstance().getDefaultPrefixPath() != null) {
				uriBuffer.append(FisConfiguration.getInstance().getDefaultPrefixPath());
			}
			
			if (!realResPath.startsWith("/") && !uriBuffer.toString().endsWith("/")) {
				uriBuffer.append("/");
			}
			uriBuffer.append(realResPath);

			return uriBuffer.toString();
		}
		
		return realResPath;
	}
	
	private String getRealResPath(String resPath) {
		List<MapJson> mapJsonList = FisConfiguration.getInstance().getMapJsonList();
		for (MapJson mapJson : mapJsonList) {
			Res res = mapJson.getRes(resPath);
			
			if (res != null && res.getUri() != null) {
				return res.getUri();
			}
		}
		
		return resPath;
	}

}
