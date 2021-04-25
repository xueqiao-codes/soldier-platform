package org.soldier.platform.admin.data;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.web.config.dao.WebConfig;

public class NginxConfigBuilder {
	private final String UPSTREAM_SUFFIX = "_servers_auto";
	private List<WebConfig> webConfigList;
	private String httpConfig;
	
	public NginxConfigBuilder(List<WebConfig> webConfigList) {
		this.webConfigList = webConfigList;
	}
	
	private String getItemUpstreamName(WebConfig item) {
		return item.getWebProjectName() + UPSTREAM_SUFFIX;
	}
	
	private void buildUpStream(StringBuffer buffer, WebConfig item) {
		buffer.append("upstream ");
		buffer.append(getItemUpstreamName(item));
		buffer.append(" {\n");
		for (String backend : item.getBackendList()) {
			buffer.append("\tserver ");
			buffer.append(backend);
			if (item.getPort() != 80) {
				buffer.append(":");
				buffer.append(item.getPort());
			}
			buffer.append(";\n");
		}
		buffer.append("}\n\n");
	}
	
	private void buildHttpForwardHttps(StringBuffer buffer, WebConfig item) {
	    buffer.append("server {\n");
	    buffer.append("\tlisten 80;\n");
        buildServerName(buffer, item);
	    buffer.append("\trewrite ^(.*)$  https://$host$1 permanent;\n");
	    buffer.append("}\n\n");
	}
	
	private void buildServerName(StringBuffer buffer, WebConfig item) {
	    buffer.append("\tserver_name ");
        for (String domain : item.getDomainList()) {
            buffer.append(domain);
            buffer.append(" ");
        }
        buffer.append(";\n\n");
	}
	
	private void buildServerOption(StringBuffer buffer, WebConfig item) {
	    if (!item.getServerOptions().isEmpty()) {
            buffer.append(item.getServerOptions());
            buffer.append("\n");
        }
	}
	
	private void buildLocationProxy(StringBuffer buffer, WebConfig item, boolean isHttps) {
	    buffer.append("\tlocation / {\n");
        if (!item.getLocationOptions().isEmpty()) {
            buffer.append(item.getLocationOptions());
            buffer.append("\n");
        }
        buffer.append("\t\tproxy_pass http://" + getItemUpstreamName(item) + "/;\n");
        buffer.append("\t\tinclude proxy.conf;\n");
        if (isHttps) {
            buffer.append("\t\tproxy_set_header X-Forwarded-Proto https;\n");
        }
        buffer.append("\t}\n");
        
        if (item.isSetIndexPath() && item.getIndexPath().length() > 1 && item.getIndexPath().startsWith("/")) {
            buffer.append("\n");
            buffer.append("\tlocation = / {\n");
            if (isHttps) {
                buffer.append("\t\tproxy_set_header X-Forwarded-Proto https;\n");
            }
            if (!item.getLocationOptions().isEmpty()) {
                buffer.append(item.getLocationOptions());
                buffer.append("\n");
            }
            buffer.append("\t\tproxy_pass http://" + getItemUpstreamName(item) + item.getIndexPath() + ";\n");
            buffer.append("\t\tinclude proxy.conf;\n");
            buffer.append("\t}\n");
        }
	}
	
	private void buildHttpsServer(StringBuffer buffer, WebConfig item) {
	    buffer.append("server {\n");
	    buffer.append("\tlisten 443 ssl;\n");
	    buildServerName(buffer, item);
	    
	    buffer.append("\tssl on;\n");
	    buffer.append("\tssl_certificate /certs/snginx/").append(item.getHttpsCertName()).append(".crt;\n");
	    buffer.append("\tssl_certificate_key /certs/snginx/").append(item.getHttpsCertName()).append(".key;\n");
	    buffer.append("\tssl_ciphers HIGH:!aNULL:!MD5:!EXPORT56:!EXP;\n");
	    buffer.append("\tssl_session_timeout 5m;\n");
	    buffer.append("\tssl_protocols SSLv2 SSLv3 TLSv1 TLSv1.1 TLSv1.2;\n");
	    buffer.append("\tssl_prefer_server_ciphers on;\n");
	    buffer.append("\tproxy_redirect http:// https://;\n");
	    
	    buildServerOption(buffer, item);
        buildLocationProxy(buffer, item, true);
        buffer.append("}\n\n");
	}
	
	private void buildHttpServerNormal(StringBuffer buffer, WebConfig item) {
		buffer.append("server {\n");
		buffer.append("\tlisten 80;\n");
		buildServerName(buffer, item);
		buildServerOption(buffer, item);
        buildLocationProxy(buffer, item, false);
		buffer.append("}\n\n");
	}
	
	private void buildItem(StringBuffer buffer, WebConfig item) {
		if (!item.isSetWebProjectName() || item.getWebProjectName().isEmpty()) {
			return ;
		}
		if (!item.isSetDomainList() || item.getDomainList().isEmpty()) {
			return ;
		}
		for (String domain : item.getDomainList()) {
			if (!DomainValidator.isDomainValid(domain)) {
				AppLog.e("webAppProject " + item.getWebProjectName() + " contains invalid domain " 
							+ domain);
				return ;
			}
		}
		if (!item.isSetBackendList() || item.getBackendList().isEmpty()) {
			return ;
		}
		if (!item.isSetPort() || item.getPort() <= 0) {
			return ;
		}
		
		buildUpStream(buffer, item);
		buffer.append("\n");
		if (item.isDisableHttp()) {
		    buildHttpForwardHttps(buffer, item);
		} else {
		    buildHttpServerNormal(buffer, item);
		}
		buffer.append("\n");
		if (StringUtils.isNotBlank(item.getHttpsCertName())) {
		    buildHttpsServer(buffer, item);
		}
		buffer.append("\n");
	}
	
	public String build() {
		StringBuffer buffer = new StringBuffer(256);
		
		String[] sections = null;
		if (httpConfig != null && !httpConfig.isEmpty()) {
			
			sections = httpConfig.split("@last@");
			
			if (sections.length > 0) {
				buffer.append(sections[0]);
				buffer.append("\n");
			}
		}
		
		for (WebConfig item : webConfigList) {
			buildItem(buffer, item);
		}
		
		if (sections != null && sections.length > 1) {
			for (int index = 1; index < sections.length; ++index) {
				buffer.append(sections[index]);
			}
		}
		
		return buffer.toString();
	}

	public String getHttpConfig() {
		return httpConfig;
	}

	public void setHttpConfig(String httpConfig) {
		this.httpConfig = httpConfig;
	}
}
