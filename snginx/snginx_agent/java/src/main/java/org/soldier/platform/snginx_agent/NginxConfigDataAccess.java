package org.soldier.platform.snginx_agent;


import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.soldier.base.logger.AppLog;

public class NginxConfigDataAccess {
	private String url;
	private CloseableHttpClient httpClient;
	private int timeout;
	
	public NginxConfigDataAccess(String url) {
		this.url = url;
		this.timeout = 8000;
		httpClient = HttpClients.createDefault();
	}

	public NginxConfig getNginxConfig(int version) {
		return httpPost(version);
	}
	
	private NginxConfig httpPost(int version) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("version", String.valueOf(version)));
		
		HttpPost postMethod = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.build();
		postMethod.setConfig(requestConfig);
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params));
			CloseableHttpResponse response = httpClient.execute(postMethod);
			
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					return null;
				}
				
				HttpEntity entity = response.getEntity();
				InputStreamReader bufferedReader = new InputStreamReader(entity.getContent());
				try {
					return GsonFactory.getGson().fromJson(
						bufferedReader, NginxConfig.class);
				} finally {
					EntityUtils.consumeQuietly(entity);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		NginxConfigDataAccess access = new NginxConfigDataAccess(
				"http://localhost:8080/platform_admin/admin/WebConfigData/getLastestNginxConfig");
		
		NginxConfig result = access.getNginxConfig(6);
		if (result != null) {
			result.print();
		}
	}
}
