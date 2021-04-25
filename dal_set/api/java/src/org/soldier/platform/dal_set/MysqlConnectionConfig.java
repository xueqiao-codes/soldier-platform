package org.soldier.platform.dal_set;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Mysql的连接配置
 * @author Xairy
 */
public class MysqlConnectionConfig extends ConnectionConfig{
	private static final Map<String, String> optionsMap 
				= new HashMap<String, String>();
	
	 static {
		optionsMap.put("zeroDateTimeBehavior", "round");
		optionsMap.put("characterEncoding", "utf8");
	}

	@Override
	public String getDbType() {
		return "mysql";
	}

	@Override
	public String getDriverClassName() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public Map<String, String> getOptions() {
		return optionsMap;
	}

	@Override
	public String getJDBCUrl() {
		StringBuffer urlBuffer = new StringBuffer(128);
		urlBuffer.append("jdbc:mysql://");
		urlBuffer.append(this.getHost());
		urlBuffer.append(":");
		urlBuffer.append(this.getPort());
		urlBuffer.append("/");
		urlBuffer.append(this.getDbName());
		if(optionsMap.size() >= 1){
			urlBuffer.append("?");
		}
		for(Entry<String,String> option : optionsMap.entrySet()){
			urlBuffer.append(option.getKey());
			urlBuffer.append("=");
			urlBuffer.append(option.getValue());
			urlBuffer.append("&");
		}
		return urlBuffer.toString();
	}

}
