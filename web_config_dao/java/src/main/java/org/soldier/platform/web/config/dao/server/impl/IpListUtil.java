package org.soldier.platform.web.config.dao.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.NetHelper;

public class IpListUtil {
	public static String ipListToStr(List<Long> ipList) {
		StringBuffer resultBuffer = new StringBuffer(64);
		for (int index = 0; index < ipList.size(); ++index) {
			if (index > 0) {
				resultBuffer.append(",");
			}
			resultBuffer.append(NetHelper.NetAddr(ipList.get(index)));
		}
		return resultBuffer.toString();
	}
	
	public static List<Long> strToIpList(String ipList) {
		List<Long> resultList = new ArrayList<Long>();
		
		String[] ipStrArray = StringUtils.split(ipList, ",");
		for (String ipStr : ipStrArray) {
			Long value = NetHelper.AddrNet(ipStr);
			if (-1 != value) {
				resultList.add(value);
			}
		}
		
		return resultList;
	}
}
