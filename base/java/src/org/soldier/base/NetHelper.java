package org.soldier.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class NetHelper {
	/**
	 * 将整数的ip地址转换为字符串表示的形式
	 * 
	 * @param ipValue
	 * @return 111.222.123.122表示的地址表示形式
	 */
	public static String NetAddr(final Long ipValue) {
		StringBuffer buffer = new StringBuffer(32);
		buffer.append((ipValue & 0xFF000000) >> 24);
		buffer.append('.');
		buffer.append((ipValue & 0xFF0000) >> 16);
		buffer.append('.');
		buffer.append((ipValue & 0xFF00) >> 8);
		buffer.append('.');
		buffer.append(ipValue & 0xFF);
		return buffer.toString();
	}

	/**
	 * 将ip表示的字符串表示为整数
	 * 
	 * @param ipStr
	 * @return 正确返回相应的数值，否则返回-1
	 */
	public static Long AddrNet(final String ipStr) {
		Long ipValue = -1l;
		if (ipStr == null) {
			return ipValue;
		}
		
		String[] valueArray = ipStr.split("\\.");
		if (valueArray.length != 4) {
			return ipValue;
		}
		
		try {
			ipValue = Long.parseLong(valueArray[0]);
			for (int index = 1; index < 4; ++index) {
				ipValue = (ipValue << 8) + Long.parseLong(valueArray[index]);
			}
		} catch (NumberFormatException e) {
//			System.err.println(ipStr + "addr format is error");
			return -1L;
		}
		return ipValue;
	}
	
	public static List<Long> DotIpList(String dotIpList) {
		if (dotIpList == null) {
			return null;
		}
		List<Long> result = new ArrayList<Long>();
			
		String[] ipArray = StringUtils.split(dotIpList.trim(), ",");
		for (String ipStr : ipArray) {
			Long ipValue = NetHelper.AddrNet(ipStr);
			if (ipValue == -1 || ipValue == 0) {
				return null;
			}
			result.add(ipValue);
		}
			
		return result;
	}
	
	public static String IpList2Str(List<Long> ipList, String sep) {
		StringBuffer resultBuffer = new StringBuffer(56);
		for (int index = 0; index < ipList.size(); ++index) {
			if (index > 0) {
				resultBuffer.append(sep);
			}
			resultBuffer.append(NetHelper.NetAddr(ipList.get(index)));
		}
		return resultBuffer.toString();
	}
}
