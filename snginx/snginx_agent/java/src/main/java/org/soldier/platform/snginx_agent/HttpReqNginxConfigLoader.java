package org.soldier.platform.snginx_agent;

import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.soldier.base.logger.AppLog;

public class HttpReqNginxConfigLoader implements INginxConfigLoader {
    private int timeout = 3000;
    private CloseableHttpClient httpClient;
    
    public HttpReqNginxConfigLoader() {
        httpClient = HttpClients.createDefault();
    }
    
    @Override
    public void load() throws Exception {
        configTest();
        reload();
    }
    
    private static class ResultInfo {
        private boolean result;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }
    
    private ResultInfo httpPost(String path) {
        HttpPost postMethod = new HttpPost("http://localhost:2233/" + path);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
        postMethod.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(postMethod);
            
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    return null;
                }
                
                HttpEntity entity = response.getEntity();
                InputStreamReader bufferedReader = new InputStreamReader(entity.getContent());
                try {
                    return GsonFactory.getGson().fromJson(
                        bufferedReader, ResultInfo.class);
                } finally {
                    EntityUtils.consumeQuietly(entity);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
        }
        
        return null;
    }
    
    private void configTest() throws Exception {
        ResultInfo resultInfo = httpPost("configtest");
        if (resultInfo == null || !resultInfo.isResult()) {
            throw new Exception("configtest failed!");
        }
    }
    
    private void reload() throws Exception {
        ResultInfo resultInfo = httpPost("reloadconfig");
        if (resultInfo == null || !resultInfo.isResult()) {
            throw new Exception("reloadconfig failed!");
        }
    }
    
}
