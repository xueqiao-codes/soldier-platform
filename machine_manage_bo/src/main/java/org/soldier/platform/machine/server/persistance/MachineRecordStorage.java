package org.soldier.platform.machine.server.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.machine.KeyType;
import org.soldier.platform.machine.Machine;
import org.soldier.platform.machine.MachineList;
import org.soldier.platform.machine.QueryMachineOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

public class MachineRecordStorage {
	private static MachineRecordStorage INSTANCE;
	
	public static MachineRecordStorage Global() {
		if (INSTANCE == null) {
			synchronized(MachineRecordStorage.class) {
				if (INSTANCE == null) {
					INSTANCE = new MachineRecordStorage();
				}
			}
		}
		return INSTANCE;
	}
	
	private String roleName;
	
	private MachineRecordStorage() {
		this.roleName = "role_machine";
	}
	
	private String getTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "tmachine", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode()
					, ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private static Map<String, String> propertiesStringToMap(String properties) {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		if (properties != null && !properties.isEmpty()) {
			Arrays.stream(properties.split("\n"))
				  .map((line)->{ return line.trim(); })
				  .filter((line)->{return !line.isEmpty();})
				  .forEach((line)-> {
					  int pos = line.indexOf("=");
					  if (pos <= 0 || pos >= line.length()) {
						  return ;
					  }
					  if (pos < line.length() - 1) {
						  propertiesMap.put(line.substring(0, pos), line.substring(pos + 1));
					  } else {
						  propertiesMap.put(line.substring(0, pos), "");
					  }
				  });
		}
		
		return propertiesMap;
	}
	
	private static String propertiesMapToString(Map<String, String> propertiesMap) {
		if (propertiesMap == null || propertiesMap.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder(64);
		propertiesMap.entrySet().stream().forEach((entry)->{
			builder.append(entry.getKey().trim());
			builder.append("=");
			builder.append(entry.getValue().trim());
			builder.append("\n");
		});
		return builder.toString();
	}
	
	public void addListQueryCondition(SqlQueryBuilder builder
			, String field, List<String> conditionList) {
		StringBuffer condition = new StringBuffer(128);
		condition.append(field + " IN (");
		Iterator<String> iterator = conditionList.iterator();
		while (iterator.hasNext()) {
			condition.append("?");
			iterator.next();
			if (iterator.hasNext()) {
				condition.append(",");
			}
		}
		condition.append(")");
		builder.addFieldCondition(ConditionType.AND, condition.toString(), conditionList.toArray());
	}
	
	public MachineList queryMachineList(TServiceCntl oCntl
			, QueryMachineOption option
			, int pageIndex
			, int pageSize) throws ErrorInfo {
		SqlQueryBuilder sqlBuilder = new SqlQueryBuilder();
		sqlBuilder.addFields("Fhost_name", "Fhost_inner_ip", "Fhost_desc"
				, "Fhost_admin", "Froot_password", "Fmachine_properties"
				, "Frelated_screen_id", "Fcreate_timestamp", "Flastmodify_timestamp");
		sqlBuilder.addTables(getTableName());
		if (option != null) {
			if (option.isSetHostNames() && option.getHostNames().size() > 0) {
				addListQueryCondition(sqlBuilder, "Fhost_name", option.getHostNames());
			}
			if (option.isSetHostInnerIPS() && option.getHostInnerIPS().size() > 0) {
				addListQueryCondition(sqlBuilder, "Fhost_inner_ip", option.getHostInnerIPS());
			}
			if (option.isSetHostAdmin() && !option.getHostAdmin().isEmpty()) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Fhost_admin like ?", "%" + option.getHostAdmin() + "%");
			}
			if (option.isSetHostDesc() && !option.getHostDesc().isEmpty()) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Fhost_desc like ?", "%" + option.getHostDesc() + "%");
			}
			if (option.isSetHostNamePartical() && !option.getHostNamePartical().isEmpty()) {
				sqlBuilder.addFieldCondition(ConditionType.AND, "Fhost_name like ?", "%" + option.getHostNamePartical() + "%");
			}
		}
		
		sqlBuilder.setOrder(OrderType.ASC, "Fhost_name");
		sqlBuilder.setPage(pageIndex, pageSize);
		
		MachineList resultList = new MachineList();
		List<Machine> machineList = null;
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			resultList.setTotalNum(runner.query(
					sqlBuilder.getTotalCountSql(), 
					new ScalarHandler<Long>(), 
					sqlBuilder.getParameterList()).intValue());
			
			machineList = (runner.query(sqlBuilder.getItemsSql(),
					new AbstractListHandler<Machine>() {
						@Override
						protected Machine handleRow(ResultSet rs) throws SQLException {
							Machine machine = new Machine();
							machine.setHostName(rs.getString("Fhost_name"));
							machine.setHostInnerIP(rs.getString("Fhost_inner_ip"));
							machine.setHostDesc(rs.getString("Fhost_desc"));
							machine.setHostAdmin(rs.getString("Fhost_admin"));
							machine.setRelatedScreenId(rs.getString("Frelated_screen_id"));
							machine.setMachineProperties(propertiesStringToMap(rs.getString("Fmachine_properties")));
							machine.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
							machine.setLastModifyTimestamp(rs.getInt("Flastmodify_timestamp"));
							return machine;
						}
					}, sqlBuilder.getParameterList()));
			
			if (AppLog.debugEnabled()) {
				AppLog.d("execute sql " + sqlBuilder.getItemsSql()+ ", machineList.size=" + machineList.size());
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode()
					, ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		};
		
		Map<String, Machine> machinesMap = new HashMap<String, Machine>();
		if (machineList != null) {
			if (option != null && option.getKeyType() == KeyType.KEY_HOSTINNER_IP) {
				for (Machine m : machineList) {
					machinesMap.put(m.getHostInnerIP(), m);
				}
			} else {
				for (Machine m : machineList) {
					machinesMap.put(m.getHostName(), m);
				}
			}
		}
		resultList.setMachinesMap(machinesMap);
		
		return resultList;
	}
	
	public void addMachine(TServiceCntl oCntl, Machine newMachine) throws ErrorInfo {
		StringBuilder addSqlBuilder = new StringBuilder(256);
		addSqlBuilder.append("INSERT INTO ").append(getTableName());
		
		PreparedFields fields = new PreparedFields();
		fields.addString("Fhost_name", newMachine.getHostName().trim());
		fields.addString("Fhost_inner_ip", newMachine.getHostInnerIP().trim());
		if (newMachine.isSetHostDesc()) {
			fields.addString("Fhost_desc", newMachine.getHostDesc().trim());
		}
		if (newMachine.isSetHostAdmin()) {
			fields.addString("Fhost_admin", newMachine.getHostAdmin().trim());
		}
		fields.addString("Fmachine_properties", propertiesMapToString(newMachine.getMachineProperties()));
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		addSqlBuilder.append(" SET ").append(fields.getPreparedSql());
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			runner.update(addSqlBuilder.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	
	public int updateMachine(TServiceCntl oCntl, Machine updateMachine) throws ErrorInfo {
		StringBuilder updateSqlBuilder = new StringBuilder(256);
		updateSqlBuilder.append("UPDATE ").append(getTableName());
		
		PreparedFields fields = new PreparedFields();
		if (updateMachine.isSetHostInnerIP()) {
			fields.addString("Fhost_inner_ip", updateMachine.getHostInnerIP().toString());
		}
		if (updateMachine.isSetHostAdmin()) {
			fields.addString("Fhost_admin", updateMachine.getHostAdmin().trim());
		}
		if (updateMachine.isSetMachineProperties()) {
			fields.addString("Fmachine_properties", propertiesMapToString(updateMachine.getMachineProperties()));
		}
		if (updateMachine.isSetHostDesc()) {
			fields.addString("Fhost_desc", updateMachine.getHostDesc().trim());
		}
		if (updateMachine.isSetRelatedScreenId()) {
			fields.addString("Frelated_screen_id", updateMachine.getRelatedScreenId());
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		updateSqlBuilder.append(" SET ").append(fields.getPreparedSql())
						.append(" WHERE Fhost_name=?");
		
		if (AppLog.debugEnabled()) {
			AppLog.d("execute sql " + updateSqlBuilder.toString());
		}
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			return runner.update(updateSqlBuilder.toString(),
					ArrayUtils.add(fields.getParameters(), updateMachine.getHostName()));
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	
	public int deleteMachine(TServiceCntl oCntl, String hostName) throws ErrorInfo {
		StringBuilder deleteSqlBuilder = new StringBuilder(256);
		deleteSqlBuilder.append("DELETE FROM ").append(getTableName())
						.append(" WHERE Fhost_name=?");
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		try {
			return runner.update(deleteSqlBuilder.toString(), hostName.trim());
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}
	
}
