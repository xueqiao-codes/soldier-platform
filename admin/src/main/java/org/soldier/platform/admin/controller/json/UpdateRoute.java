package org.soldier.platform.admin.controller.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.machine.client.MachineManageBoStub;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.RouteType;
import org.soldier.platform.route.dao.client.RouteDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;
import com.google.common.base.Enums;
import com.google.common.base.Optional;

public class UpdateRoute extends CJsonAjaxOpController {
	
	private int checkServiceKey() throws AjaxOpException {
		int serviceKey = parameter("cmdNum", -1);
		if (serviceKey < 0 || serviceKey > 2000) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"命令号参数错误");
		}
		return serviceKey;
	}
	
	private String checkServiceName() throws AjaxOpException {
		String serviceName = parameter("serviceName", "").trim();
		if (serviceName.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"服务名称长度应不小于6个字符");
		}
		return serviceName;
	}
	
	private List<Long> checkIpList() throws AjaxOpException {
		List<Long> result = new ArrayList<Long>();
		
		String[] hostNameArray = StringUtils.split(parameter("ipList", "").trim(), ',');
		if (hostNameArray.length == 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "IP列表未输入");
		}
		
		// 查找机器列表
		MachineManageBoStub stub = new MachineManageBoStub();
		QueryMachineOption option = new QueryMachineOption();
		option.setHostNames(Arrays.asList(hostNameArray));
		MachineList machineList;
		try {
			machineList = stub.queryMachineList(RandomUtils.nextInt(), 2000, option, 0, hostNameArray.length + 1);
		
			for (String hostName : hostNameArray) {
				if (hostName.equalsIgnoreCase("localhost")) {
					result.add(NetHelper.AddrNet("127.0.0.1"));
					continue;
				}
				if (!machineList.getMachinesMap().containsKey(hostName)) {
					throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
							hostName + "的机器不存在");
				}
				result.add(NetHelper.AddrNet(machineList.getMachinesMap().get(hostName).getHostInnerIP()));
			}
		} catch (Throwable e) {
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), e.getMessage());
		}
		
		return result;
	}
	
	private String checkDesc() throws AjaxOpException {
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"备注长度应不小于6个字符");
		}
		
		return desc;
	}
	
	private RouteType checkRouteType() throws AjaxOpException {
		Optional<RouteType> routeType = Enums.getIfPresent(RouteType.class, parameter("routeType", "").trim());
		if (!routeType.isPresent()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"应该选择合适的路由");
		}
		return routeType.get();
	}
	
	private List<String> checkServiceAdminList() throws AjaxOpException {
		String serviceAdminInput = parameter("serviceAdmin", "").trim();
		List<String> serviceAdminListInput =  Arrays.asList(StringUtils.split(serviceAdminInput, ","));
		Iterator<String> it = serviceAdminListInput.iterator();
		List<String> serviceAdminList = new ArrayList<String>();
		while(it.hasNext()) {
			String value = it.next();
			System.err.println(StringUtils.isBlank(value));
			if (!value.isEmpty()) {
				serviceAdminList.add(value);
			}
		}
//		if (serviceAdminList.isEmpty()) {
//			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
//					"请输入负责人");
//		}
		return serviceAdminList;
	}
	
	@Override
	protected void doAdd() throws AjaxOpException {
		int serviceKey = checkServiceKey();
		String serviceName = checkServiceName();
		List<Long> ipList = checkIpList();
		String desc = checkDesc();
		RouteType routeType = checkRouteType();
		
		List<String> serviceAdminList = checkServiceAdminList();
		String idlRelativePath = parameter("idlRelativePath", "").trim();
		
		RouteDaoStub stub = new RouteDaoStub();
		
		QueryRouteOption option = new QueryRouteOption();
		option.setServiceKey(serviceKey);
		RouteInfoList resultList = null;
		try {
			resultList = stub.queryRouteInfoList(RandomUtils.nextInt(), 
					1500, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		
		if (resultList.totalCount > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"重复注册的命令号");
		}
		
		RouteInfo info = new RouteInfo();
		info.setServiceKey(serviceKey);
		info.setServiceName(serviceName);
		info.setIpList(ipList);
		info.setDesc(desc);
		info.setRouteType(routeType);
		info.setServiceAdminList(serviceAdminList);
		info.setIdlRelativePath(idlRelativePath);
		try {
			stub.addRoute(RandomUtils.nextInt(), 1500, info);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}

	@Override
	protected void doUpdate() throws AjaxOpException {
		int serviceKey = checkServiceKey();
		String serviceName = checkServiceName();
		List<Long> ipList = checkIpList();
		String desc = checkDesc();
		RouteType routeType = checkRouteType();
		List<String> serviceAdminList = checkServiceAdminList();
		
		String idlRelativePath = parameter("idlRelativePath", "").trim();
		
		RouteDaoStub stub = new RouteDaoStub();
		
		QueryRouteOption option = new QueryRouteOption();
		option.setServiceKey(serviceKey);
		RouteInfoList resultList = null;
		try {
			resultList = stub.queryRouteInfoList(RandomUtils.nextInt(), 
					1500, 0, 10, option);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
		
		if (resultList.totalCount == 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"重复注册的命令号");
		}
		
		RouteInfo info = new RouteInfo();
		info.setServiceKey(serviceKey);
		info.setServiceName(serviceName);
		info.setIpList(ipList);
		info.setDesc(desc);
		info.setRouteType(routeType);
		info.setServiceAdminList(serviceAdminList);
		info.setIdlRelativePath(idlRelativePath);
		try {
			stub.updateRoute(RandomUtils.nextInt(), 1500, info);
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new AjaxOpException(ErrorCodeOuter.SERVER_BUSY.getErrorCode(),
					ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
		}
	}
	
	@Override
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		int serviceKey = checkServiceKey();
		new RouteDaoStub().deleteRoute(serviceKey, 2000, serviceKey);
	}

}
