package org.soldier.platform.admin.web.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.data.DomainValidator;
import org.soldier.platform.admin.data.NginxConfigBuilder;
import org.soldier.platform.admin.data.StaticProjects;
import org.soldier.platform.admin.data.SyncNginxConfigResult;
import org.soldier.platform.admin.freemarker.IpListConverter;
import org.soldier.platform.admin.freemarker.StringListConverter;
import org.soldier.platform.svr_platform.client.AsyncCallRunner;
import org.soldier.platform.svr_platform.client.AsyncCallback;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web.config.dao.ConfigType;
import org.soldier.platform.web.config.dao.DeployType;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.client.WebConfigDaoAsyncStub;
import org.soldier.platform.web.config.dao.client.WebConfigDaoStub;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;
import com.google.common.base.Enums;
import com.google.common.base.Optional;

public class WebManage extends WebAuthController {
    private List<String> EMPTY_LIST = new ArrayList<>();
    
	public void show() throws Exception {
		render("web/WebManage.html");
	}
	
	public void showWebMenus() throws Exception {
		render("web/WebMenus.html");
	}
	
	public void showWebProjects() throws Exception {
		put("deployTypes", DeployType.values());
		put("avaiableHttpsCerts", getAvaiableHttpsCerts());
		
		render("web/WebProjects.html");
	}
	
	private List<String> getAvaiableHttpsCerts() {
	    File certsRootDir = new File("/certs/snginx");
	    if (!certsRootDir.exists() || !certsRootDir.isDirectory()) {
	        return EMPTY_LIST;
	    }
	    
	    File[] crtFiles = certsRootDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".crt");
            }
	    });
	    
	    if(crtFiles == null) {
	        return EMPTY_LIST;
	    }
	    
	    List<String> resultList = new ArrayList<String>();
	    for (File crtFile : crtFiles) {
	        resultList.add(
	                crtFile.getName().substring(0, crtFile.getName().lastIndexOf('.')));
	    }
	    return resultList;
	}
	
	public void showWebOptions() throws Exception {
		String project = parameter("project", "").trim();
		if (project.isEmpty()) {
			showUserError("无Web项目参数");
			return ;
		}
		
		WebConfigDaoStub stub = new WebConfigDaoStub();
		QueryWebConfigOption option = new QueryWebConfigOption();
		option.setWebProjectName(project);
		
		List<WebConfig> webConfigList = stub.queryConfig(project.hashCode(), 1500, option);
		if (webConfigList.isEmpty()) {
			showUserError("Web项目不存在");
			return ;
		}
		
		put("webConfig", webConfigList.get(0));
		
		render("web/WebOptions.html");
	}
	
	public void showHttpConfig() throws Exception {
		put("config",
				new WebConfigDaoStub().getLastestNginxConfig(RandomUtils.nextInt(), 1500, ConfigType.HttpConfig));
		
		render("web/WebHttp.html");
	}
	
	public void showSyncNginxConfig() throws Exception {
		render("web/SyncNginx.html");
	}
	
	public void webConfigData() throws Exception {
		int pageIndex = parameter("page", 1) ;
		if(pageIndex > 1){
			--pageIndex;
		} else {
			pageIndex = 0;
		}
		int pageSize = parameter("rp", 20);
		if(pageSize <= 0){
			pageSize = 20;
		}
		
		WebConfigDaoStub stub = new WebConfigDaoStub();
		QueryWebConfigOption option = new QueryWebConfigOption();
		
		String webProjectName = parameter("webProjectName", "").trim();
		if (!webProjectName.isEmpty()) {
			option.setWebProjectName(webProjectName);
		}
		
		int port = parameter("port", 0);
		if (port > 0) {
			option.setPort(port);
		}
		
		String backend = parameter("backend", "");
		if (!backend.isEmpty()) {
			option.setBackend(backend);
		}
		
		Optional<DeployType> deployType = Enums.getIfPresent(DeployType.class, parameter("deployType", "").trim());
		if (deployType.isPresent()) {
			option.setType(deployType.get());
		}
		
		String desc = parameter("remark", "").trim();
		if (!desc.isEmpty()) {
			option.setDesc(desc);
		}
		
		put("pageIndex", pageIndex);
		put("itemsResult",
				stub.queryConfigByPage(RandomUtils.nextInt(),
						3000, option, pageIndex, pageSize));
		put("ipList2Str", new IpListConverter(","));
		put("backendList2Str", new StringListConverter(","));
		put("domainList2Str", new StringListConverter(","));
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("web/WebConfigData.json");
	}
	
	public void addWebConfig() throws Exception {
		ErrorResult result = new ErrorResult();
		doAddWebConfig(result);
		echoJson(result);
	}
	
	public void updateWebConfig() throws Exception {
		ErrorResult result = new ErrorResult();
		doUpdateWebConfig(result);
		echoJson(result);
	}
	
	public void deleteWebConfig() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteWebConfig(result);
		echoJson(result);
	}
	
	private void doDeleteWebConfig(ErrorResult result) throws Exception {
		String webProjectName = parameter("webProjectName", "").trim();
		if (webProjectName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("请填写项目名称");
			return ;
		}
		
		try {
			new WebConfigDaoStub().deleteWebConfig(RandomUtils.nextInt(), 2000, webProjectName);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				      .setErrorMsg("记录不存在");
				return ;
			}
			throw err;
		}
	}
	
	private WebConfig checkWebConfig(ErrorResult result) throws Exception {
		WebConfig config = new WebConfig();
		
		String webProjectName = parameter("webProjectName", "").trim();
		if (webProjectName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("请填写项目名称");
			return null;
		}
		config.setWebProjectName(webProjectName);
		
		int port = parameter("port", 0);
		if (port <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("端口号未正确填写");
			return null;
		}
		config.setPort(port);
		
		Optional<DeployType> deployType = Enums.getIfPresent(DeployType.class, parameter("deployType", "").trim());
		if (!deployType.isPresent()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("请选择部署类型");
			return null;
		}
		config.setDeployType(deployType.get());
		
		List<String> backendList = Arrays.asList(StringUtils.split(parameter("backendList", ""), ","));
		if (backendList == null || backendList.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("后端列表不能为空");
			return null;
		}
		config.setBackendList(backendList);
		
		String httpsCertName = parameter("httpsCertName", "").trim();
		if (StringUtils.isNotEmpty(httpsCertName)) {
		    if ("none".equalsIgnoreCase(httpsCertName)) {
		        config.setHttpsCertName("");
		    } else {
		        config.setHttpsCertName(httpsCertName);
		    }
		}
		
		if ("true".equalsIgnoreCase(parameter("disableHttp", "").trim())) {
		    config.setDisableHttp(true);
		} else {
		    config.setDisableHttp(false);
		}
		
		List<String> configDomainList = new ArrayList<String>();
		String[] domainList = StringUtils.split(parameter("domain", "").trim(), ",");
		for (String domain : domainList) {
			if (!DomainValidator.isDomainValid(domain)){
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  	  	  .setErrorMsg("包含域名" + domain + "错误");
				return null;
			}
			if (StaticProjects.get().contains(domain)) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
		  	  	  .setErrorMsg("域名" + domain + "已经被占用");
				return null;
			}
			if (!configDomainList.contains(domain)) {
				configDomainList.add(domain);
			}
		}
		config.setDomainList(configDomainList);
		
		String indexPath = parameter("indexPath", "").trim();
		if (!indexPath.isEmpty() && !indexPath.startsWith("/")) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("首页路径必须以/开头");
			return null;
		}
		config.setIndexPath(indexPath);
		
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  	  .setErrorMsg("备注不少于6个字");
			return null;
		}
		config.setDesc(desc);
		
		if (config.getDeployType().getValue() == DeployType.Mitty.getValue()) {
			List<WebConfig> mittyConfigList = new WebConfigDaoStub().queryConfig(
					RandomUtils.nextInt(), 1500, 
					new QueryWebConfigOption().setPort(
							config.getPort()).setType(config.getDeployType()));
			if (!mittyConfigList.isEmpty() 
				&& !mittyConfigList.get(0).getWebProjectName().equals(config.getWebProjectName())) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  	  	  .setErrorMsg("重复的Mitty端口配置");
				return null;
			}
		}
		if (config.isSetDomainList() && !config.getDomainList().isEmpty()) {
			AsyncCallRunner runner = new AsyncCallRunner();
			
			WebConfigDaoAsyncStub asyncStub = new WebConfigDaoAsyncStub();
			Map<String, AsyncCallback<List<WebConfig>>> callBackMaps =
					new HashMap<String, AsyncCallback<List<WebConfig>>>();
			for (String domain : config.getDomainList()) {
				callBackMaps.put(domain, new AsyncCallback<List<WebConfig>>());
			}
			runner.start();
			for (Entry<String, AsyncCallback<List<WebConfig>>> entry : callBackMaps.entrySet()) {
				asyncStub.add_queryConfigCall(runner, 
						entry.getKey().hashCode(), 3000, new QueryWebConfigOption().setDomain(entry.getKey()) 
						, entry.getValue());
			}
			runner.run(5000);
			
			for (Entry<String, AsyncCallback<List<WebConfig>>> entry : callBackMaps.entrySet()) {
				for (WebConfig resultConfig : entry.getValue().getResponseData()) {
					if (resultConfig.getWebProjectName().equals(config.getWebProjectName())) {
						continue;
					}
					
					for (String domain : resultConfig.getDomainList()) {
						if (domain.equals(entry.getKey())) {
							result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
								  .setErrorMsg(resultConfig.getWebProjectName() + "已经存在域名" + entry.getKey());
							return null;
						}
					}
				}
			}
		}
		
		return config;
	}
	
	private void doAddWebConfig(ErrorResult result) throws Exception{
		WebConfig config = checkWebConfig(result);
		if (config == null) {
			return ;
		}
		
		List<WebConfig> webProjectConfigList = new WebConfigDaoStub().queryConfig(RandomUtils.nextInt(), 1500,
				new QueryWebConfigOption().setWebProjectName(config.getWebProjectName()));
		if (!webProjectConfigList.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  	.setErrorMsg("项目名称已经存在");
			return;
		}
		
		new WebConfigDaoStub().addWebConfig(RandomUtils.nextInt(), 1500, config);;
	}
	
	private void doUpdateWebConfig(ErrorResult result) throws Exception {
		WebConfig config = checkWebConfig(result);
		if (config == null) {
			return ;
		}
		
		try {
			new WebConfigDaoStub().updateWebConfig(
					RandomUtils.nextInt(), 2000, config);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				      .setErrorMsg("记录不存在");
				return ;
			}
			throw err;
		}
	}
	
	public void syncNginxConfig() throws Exception {
		SyncNginxConfigResult result = new SyncNginxConfigResult();
		doSyncNginxConfig(result);
		echoJson(result);
	}
	
	private void doSyncNginxConfig(SyncNginxConfigResult result) throws Exception {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		List<WebConfig> webConfigList =
				stub.queryConfig(RandomUtils.nextInt(), 10000, null);
		
		String httpConfig = stub.getLastestNginxConfig(RandomUtils.nextInt(), 2000, ConfigType.HttpConfig);
		
		NginxConfigBuilder builder = new NginxConfigBuilder(webConfigList);
		builder.setHttpConfig(httpConfig);
		
		String data = builder.build();
		
		String webServersDataFile = System.getenv("WEB_SERVERS_DATA_FILE");
		if (StringUtils.isEmpty(webServersDataFile)) {
		    FileUtils.writeStringToFile(new File("/data/snginx/etc/web_servers_test.conf"), data);
		} else {
		    FileUtils.writeStringToFile(new File(webServersDataFile), data);
		}
		
		StringBuffer logBuffer = new StringBuffer(128);
		if(0 != doSnginxConfigTest(logBuffer)) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("配置参数错误，nginx无法加载");
			result.setLog(logBuffer.toString());
			return ;
		}
		result.setLog(logBuffer.toString());
//		AppLog.d("Log: " + logBuffer.toString());
		
		stub.updateNginxConfig(RandomUtils.nextInt(), 1500, data, ConfigType.AllConfig);
	}
	
	private int doSnginxConfigTest(StringBuffer logBuffer) {
		ProcessBuilder builder = new ProcessBuilder();
		
		String nginxTestCommand = System.getenv("SNGINX_TEST_COMMAND");
		if (StringUtils.isEmpty(nginxTestCommand)) {
		    builder.command("sudo", "/usr/local/soldier/snginx/snginx", "configtest");
		} else {
		    builder.command(StringUtils.split(nginxTestCommand));
		}
		try {
			File logFile = new File("/tmp/snginx_config_test_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId());
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			builder.redirectError(logFile);
			builder.redirectOutput(logFile);
			Process process = builder.start();
			
			int result = 0;
			try {
				result = process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			logBuffer.append(FileUtils.readFileToString(logFile));
			return result;
		} catch (IOException e) {
			AppLog.e(e.getMessage(), e);
			return -1;
		}
	}
	
	public void saveWebOptions() throws Exception {
		ErrorResult result = new ErrorResult();
		doSaveWebOptions(result);
		echoJson(result);
	}
	
	private void doSaveWebOptions(ErrorResult result) throws Exception {
		String project = parameter("project", "").trim();
		String serverOptions = parameter("serverOptions", "").trim();
		String locationOptions = parameter("locationOptions", "").trim();
		
		if (project.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("项目名称为空");
			return ;
		}
		
		WebConfig config = new WebConfig();
		config.setWebProjectName(project);
		config.setServerOptions(serverOptions);
		config.setLocationOptions(locationOptions);
		
		new WebConfigDaoStub().updateWebConfig(
				RandomUtils.nextInt(), 2000, config);
	}
	
	public void saveHttpConfig() throws Exception {
		ErrorResult result = new ErrorResult();
		doSaveHttpConfig(result);
		echoJson(result);
	}
	
	private void doSaveHttpConfig(ErrorResult result) throws Exception {
		String httpConfig = parameter("httpConfig", "").trim();
		
		new WebConfigDaoStub().updateNginxConfig(RandomUtils.nextInt(), 2000, httpConfig, ConfigType.HttpConfig);
	}
}
