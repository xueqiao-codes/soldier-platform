package org.soldier.platform.machine.server.impl;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Properties;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.MachineManageBoError;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.machine.server.MachineManageBoAdaptor;
import org.soldier.platform.machine.server.persistance.MachineDeleteMonitor;
import org.soldier.platform.machine.server.persistance.MachineRecordStorage;
import org.soldier.platform.machine.server.persistance.MachineUpdateMonitor;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.PlatformArgs;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

import net.qihoo.qconf.Qconf;
import net.qihoo.qconf.QconfException;

public class MachineManageBoHandler extends MachineManageBoAdaptor {
	
	private ExecutorService background = Executors.newCachedThreadPool();
	
	@Override
	public int InitApp(Properties props) {
		try {
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			return -1;
		}
		return 0;
	}

	@Override
	protected MachineList queryMachineList(TServiceCntl oCntl, QueryMachineOption option, int pageIndex, int pageSize)
			throws ErrorInfo, TException {
		if (pageIndex < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "pageIndex should not < 0");
		}
		if (pageSize <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "pageSize should not <= 0");
		}
		
		MachineList resultList = MachineRecordStorage.Global().queryMachineList(oCntl, option, pageIndex, pageSize);
		for (Entry<String, Machine> entry : resultList.getMachinesMap().entrySet()) {
			entry.getValue().setRelatedScreenURL(getRelatedScreenURL(entry.getValue().getRelatedScreenId()));
		}
		return resultList;
	}
	
	private String getRelatedScreenURL(String relatedScreenId) throws ErrorInfo {
		if (relatedScreenId == null  || relatedScreenId.isEmpty()) {
			return null;
		}
		
		StringBuilder urlBuilder = new StringBuilder(128);
		try {
			urlBuilder.append(Qconf.getConf("open-falcon/export/dashboard_basic_url"));
		} catch (QconfException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "qconf config get error!");
		}
		urlBuilder.append("/screen/").append(relatedScreenId).append("?legend=on&cols=4");
		return urlBuilder.toString();
	}
	
	private void checkMachine(Machine machine) throws ErrorInfo {
		if (machine == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "machine should not be null");
		}
	}
	
	private void checkMachineHost(Machine machine) throws ErrorInfo {
		if (!machine.isSetHostName() || machine.getHostName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "machine's hostName should not be null or empty");
		}
	}
	
	private void checkMachineInnerIP(Machine machine) throws ErrorInfo {
		if (machine.isSetHostInnerIP() && machine.getHostInnerIP().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "machine's inner ip should not be empty");
		}
	}
	
	private void checkHostNameUnique(TServiceCntl oCntl, String hostName) throws ErrorInfo {
		MachineList machineList = MachineRecordStorage.Global().queryMachineList(oCntl
				, new QueryMachineOption().setHostNames(Arrays.asList(hostName))
				, 0, 10);
		if (machineList.getTotalNum() > 0) {
			throw new ErrorInfo(MachineManageBoError.ERROR_DUPLICATE_HOSTNAME.getValue(), "duplicate host name");
		}
	}
	
	private void checkInnerIPUnique(TServiceCntl oCntl
			, String hostInnerIP
			, String originHostName) throws ErrorInfo {
		MachineList machineList = MachineRecordStorage.Global().queryMachineList(oCntl
				, new QueryMachineOption().setHostInnerIPS(Arrays.asList(hostInnerIP))
				, 0, 10);
		if (machineList.getTotalNum() > 0) {
			if (null == machineList.getMachinesMap().get(originHostName)) {
				throw new ErrorInfo(MachineManageBoError.ERROR_DUPLICATE_HOSTINNER_IP.getValue(), "duplicate host inner ip");
			}
		}
	}

	@Override
	protected void addMachine(TServiceCntl oCntl, Machine newMachine)
			throws ErrorInfo, TException {
		checkMachine(newMachine);
		checkMachineHost(newMachine);
		if (!newMachine.isSetHostInnerIP()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "addMachine should input the inner ip");
		}
		checkMachineInnerIP(newMachine);
		
		checkHostNameUnique(oCntl, newMachine.getHostName().trim());
		checkInnerIPUnique(oCntl, newMachine.getHostInnerIP().trim(), "");
		
		MachineRecordStorage.Global().addMachine(oCntl, newMachine);
		
		background.execute(new Runnable() {
			@Override
			public void run() {
				try {
					updateMachineRelatedMonitor(oCntl, newMachine.getHostName().trim());
				} catch (Throwable e) {
					AppLog.e(e.getMessage(), e);
				}
			}
		});
	}

	@Override
	protected void updateMachine(TServiceCntl oCntl, Machine updateMachine)
			throws ErrorInfo, TException {
		checkMachine(updateMachine);
		checkMachineHost(updateMachine);
		if (updateMachine.isSetHostInnerIP()) {
			checkInnerIPUnique(oCntl, updateMachine.getHostInnerIP().trim(), updateMachine.getHostName());
		}
		
		if (0 == MachineRecordStorage.Global().updateMachine(oCntl, updateMachine)) {
			throw new ErrorInfo(MachineManageBoError.ERROR_NO_SUCH_MACHINE.getValue(), "machine is not existed");
		}
	}

	@Override
	protected void deleteMachine(TServiceCntl oCntl, String hostName)
			throws ErrorInfo, TException {
		if (hostName == null || hostName.trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "hostName should not be null or empty");
		}
		
		// 删除机器前，优先删除监控关联数据
		deleteMachineRelatedMonitor(oCntl, hostName);
		
		if (0 == MachineRecordStorage.Global().deleteMachine(oCntl, hostName)) {
			throw new ErrorInfo(MachineManageBoError.ERROR_NO_SUCH_MACHINE.getValue(), "machine is not existed");
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	protected void updateMachineRelatedMonitor(TServiceCntl oCntl, String hostName) throws ErrorInfo, TException {
		if (hostName == null || hostName.trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "hostName should not be null or empty");
		}
		
		MachineList machineList = MachineRecordStorage.Global().queryMachineList(oCntl
				, new QueryMachineOption().setHostNames(Arrays.asList(hostName.trim()))
				, 0, 10);
		
		Machine updateMachine = machineList.getMachinesMap().get(hostName);
		if (updateMachine == null) {
			throw new ErrorInfo(MachineManageBoError.ERROR_NO_SUCH_MACHINE.getValue(), "machine is not existed");
		}
		
		try {
			MachineUpdateMonitor monitor = new MachineUpdateMonitor(updateMachine);
			monitor.update();
			
			if (!String.valueOf(monitor.getScreen().getId()).equals(updateMachine.getRelatedScreenId())) {
				updateMachine.setRelatedScreenId(String.valueOf(monitor.getScreen().getId()));
				MachineRecordStorage.Global().updateMachine(oCntl, updateMachine);
			}
			
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), e.getMessage());
		}
	}

	@Override
	protected void deleteMachineRelatedMonitor(TServiceCntl oCntl, String hostName) throws ErrorInfo, TException {
		if (hostName == null || hostName.trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "hostName should not be null or empty");
		}
		
		MachineList machineList = MachineRecordStorage.Global().queryMachineList(oCntl
				, new QueryMachineOption().setHostNames(Arrays.asList(hostName.trim()))
				, 0, 10);
		Machine deleteMachine = machineList.getMachinesMap().get(hostName);
		if (deleteMachine == null) {
			return ;
		}
		
		try {
			new MachineDeleteMonitor(deleteMachine).delete();
		} catch (Exception e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), e.getMessage());
		}
	}
}
