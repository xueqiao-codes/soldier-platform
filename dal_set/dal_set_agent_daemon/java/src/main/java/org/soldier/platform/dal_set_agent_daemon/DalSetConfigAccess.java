package org.soldier.platform.dal_set_agent_daemon;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
import org.soldier.base.StringFactory;
import org.soldier.base.logger.AppLog;

public class DalSetConfigAccess {
	private String url;
	private CloseableHttpClient httpClient;
	private int timeout;
	
	public DalSetConfigAccess(String url) {
		this.url = url;
		this.timeout = 10000;
		this.httpClient = HttpClients.createDefault();
	}
	
	public DalSetConfigResult getDalSetConfig(int version) {
		DalSetConfigResult result = httpPost(version);
		if (result != null) {
			result.setDalSetConfig(
					StringFactory.newUtf8String(Base64.decodeBase64(result.getDalSetConfig())));
		}
		return result;
	}
	
	public DalSetConfigResult httpPost(int version) {
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
						bufferedReader, DalSetConfigResult.class);
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
		DalSetConfigAccess access = new DalSetConfigAccess(
				"http://localhost:8080/admin/admin/json/LastestDalSetConfig");
		
		DalSetConfigResult result = access.getDalSetConfig(0);
		if (result != null) {
			result.print();
		}
	}
}
