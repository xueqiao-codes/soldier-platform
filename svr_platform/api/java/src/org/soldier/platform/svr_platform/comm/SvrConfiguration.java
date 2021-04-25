package org.soldier.platform.svr_platform.comm;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.List;

import org.soldier.platform.svr_platform.container.InpService;

public class SvrConfiguration {
	private static boolean isUsingInpService;
	private static int logItemMaxLength = -1;
	private static List<InpService> inpServiceList;
	
	public static boolean getIsUsingInpService() {
		return isUsingInpService;
	}
	
	public static void setIsUsingInpService(boolean isUsing) {
		isUsingInpService = true;
	}

	public static int getLogItemMaxLength() {
		return logItemMaxLength;
	}

	public static void setLogItemMaxLength(int logItemMaxLength) {
		SvrConfiguration.logItemMaxLength = logItemMaxLength;
	}
	
	public static final List<InpService> getInpServiceList(){
		return inpServiceList;
	}
	
	public static boolean isServiceInProcess(int service_key) {
		if (inpServiceList != null) {
			for (InpService service : inpServiceList) {
				if (service.getServiceKey() == service_key) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void setInpServiceList(List<InpService> list) {
		inpServiceList = list;
		if (list != null && !list.isEmpty()) {
			try {
				Pipe p = Pipe.open();
				p.source().close();
				p.sink().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
