package org.soldier.platform.route.dao.server.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.route.dao.server.RouteDaoAdaptor;
import org.soldier.platform.route.dao.QueryRouteOption;
import org.soldier.platform.route.dao.RouteInfo;
import org.soldier.platform.route.dao.RouteInfoList;
import org.soldier.platform.route.dao.RouteType;
import org.soldier.platform.route.dao.SimpleRouteInfo;
import org.soldier.platform.route.dao.route_daoConstants;

import com.antiy.error_code.ErrorCodeInner;

public class RouteDaoHandler extends RouteDaoAdaptor {
	private String roleName;
	
	@Override
	public int InitApp(Properties props) {
		roleName = props.getProperty("roleName", "platform");
		
		try {
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			System.out.println("init dal_set failed, " + e.getMessage());
			return -1;
		}
		
		return 0;
	}
	

	private void checkServiceKey(int serviceKey) throws ErrorInfo {
		if (serviceKey < 0 || serviceKey >= route_daoConstants.MAX_SERVICE_KEY_VALUE) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"serviceKey must be >=0 and < " + route_daoConstants.MAX_SERVICE_KEY_VALUE);
		}
	}
	
	private void checkServiceName(RouteInfo route) throws ErrorInfo {
		if (!route.isSetServiceName() || route.getServiceName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"serviceName is empty");
		}
	}
	
	private void checkIpList(RouteInfo route) throws ErrorInfo {
		if (!route.isSetIpList() || route.getIpList().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"ipList is empty");
		}
		
		for (Long ip : route.getIpList()) {
			if (0l == ip) {
				throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
						"ipList containes 0");
			}
		}
	}
	
	private String ipListToStr(List<Long> ipList) {
		StringBuffer resultBuffer = new StringBuffer(64);
		for (int index = 0; index < ipList.size(); ++index) {
			if (index > 0) {
				resultBuffer.append(",");
			}
			resultBuffer.append(NetHelper.NetAddr(ipList.get(index)));
		}
		return resultBuffer.toString();
	}
	
	private List<Long> strToIpList(String ipList) {
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
	
	
	private RouteInfo routeInfoFromResultSet(ResultSet rs) throws SQLException {
		RouteInfo result = new RouteInfo();
		result.setServiceKey(rs.getInt("Fservice_key"));
		result.setServiceName(rs.getString("Fservice_name"));
		result.setIpList(strToIpList(rs.getString("Fip_list")));
		result.setDesc(rs.getString("Fservice_desc"));
		result.setServiceAdminList(Arrays.asList(StringUtils.split(rs.getString("Fservice_admin"), ",")));
		result.setIdlRelativePath(rs.getString("Fidl_relative_path"));
		result.setRelatedScreenId(rs.getString("Frelated_screen_id"));
		result.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		result.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		result.setRouteType(RouteType.findByValue(rs.getInt("Froute_type")));
		return result;
	}
	
	private String getRouteInfoTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "troute_info", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
				ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getRouteVersionTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "troute_version", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
				ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}

	
	@Override
	protected void addRoute(TServiceCntl oCntl, RouteInfo route)
			throws ErrorInfo, TException {
		checkServiceKey(route.getServiceKey());
		checkServiceName(route);
		checkIpList(route);
		
		StringBuffer addSqlBuffer = new StringBuffer(128);
		addSqlBuffer.append("INSERT INTO ");
		addSqlBuffer.append(getRouteInfoTableName());
		addSqlBuffer.append(" SET ");
		
		PreparedFields fields = new PreparedFields();
		fields.addInt("Fservice_key", route.getServiceKey());
		fields.addString("Fservice_name", route.getServiceName());
		fields.addString("Fip_list", ipListToStr(route.getIpList()));
		if (route.isSetDesc()) {
			fields.addString("Fservice_desc", route.getDesc());
		}
		if (route.isSetRouteType()) {
			fields.addInt("Froute_type", route.getRouteType().getValue());
		}
		if (route.isSetIdlRelativePath()) {
			fields.addString("Fidl_relative_path", route.getIdlRelativePath().trim());
		}
		if (route.isSetServiceAdminList()) {
			fields.addString("Fservice_admin", StringUtils.join(route.getServiceAdminList(), ','));
		}
		if (route.isSetRelatedScreenId()) {
			fields.addString("Frelated_screen_id", route.getRelatedScreenId().trim());
		}
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		addSqlBuffer.append(fields.getPreparedSql());
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), false, route.getServiceKey()));
		try {
			runner.update(addSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateRoute(TServiceCntl oCntl, RouteInfo route)
			throws ErrorInfo, TException {
		checkServiceKey(route.getServiceKey());
		if (route.isSetServiceName()) {
			checkServiceName(route);
		}
		if (route.isSetIpList()) {
			checkIpList(route);
		}
		
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getRouteInfoTableName());
		updateSqlBuffer.append(" SET ");
		
		PreparedFields fields = new PreparedFields();
		if (route.isSetServiceName()) {
			fields.addString("Fservice_name", route.getServiceName());
		}
		if (route.isSetIpList()) {
			fields.addString("Fip_list", ipListToStr(route.getIpList()));
		}
		if (route.isSetDesc()) {
			fields.addString("Fservice_desc", route.getDesc());
		}
		if (route.isSetRouteType()) {
			fields.addInt("Froute_type", route.getRouteType().getValue());
		}
		if (route.isSetIdlRelativePath()) {
			fields.addString("Fidl_relative_path", route.getIdlRelativePath().trim());
		}
		if (route.isSetServiceAdminList()) {
			fields.addString("Fservice_admin", StringUtils.join(route.getServiceAdminList(), ','));
		}
		if (route.isSetRelatedScreenId()) {
			fields.addString("Frelated_screen_id", route.getRelatedScreenId().trim());
		}
		if (fields.getSize() == 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"no update to set");
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fservice_key=" +  route.getServiceKey());
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), false, route.getServiceKey()));
		try {
			runner.update(updateSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected RouteInfoList queryRouteInfoList(TServiceCntl oCntl,
			int pageIndex, int pageSize, QueryRouteOption option)
			throws ErrorInfo, TException {
		if (pageIndex < 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageIndex should not < 0");
		}
		if (pageSize <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"pageSize should not <= 0");
		}
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Fservice_key", "Fservice_name", "Fip_list", "Froute_type"
				, "Fservice_desc", "Fservice_admin"
				, "Fidl_relative_path", "Frelated_screen_id"
				, "Fcreate_timestamp", "Flastmodify_timestamp");
		queryBuilder.addTables(getRouteInfoTableName());
		if (option.isSetServiceKey()) {
			queryBuilder.addFieldCondition(ConditionType.AND, 
					"Fservice_key=?", option.getServiceKey());
		} else {
			if (option.isSetServiceName()) {
				queryBuilder.addFieldCondition(
						ConditionType.AND, "Fservice_name like ?", "%" + option.getServiceName() + "%");
			}
			if (option.isSetIp()) {
				queryBuilder.addFieldCondition(
						ConditionType.AND, "Fip_list like ?", "%" + option.getIp() + "%");
			}
			if (option.isSetDesc()) {
				queryBuilder.addFieldCondition(ConditionType.AND
						, "Fservice_desc like ?", "%" + option.getDesc() + "%");
			}
			if (option.isSetServiceAdmin()) {
				queryBuilder.addFieldCondition(ConditionType.AND
						, "Fservice_admin like ?", "%" + option.getServiceAdmin() + "%");
			}
		}
		queryBuilder.setOrder(OrderType.ASC, "Fservice_key");
		queryBuilder.setPage(pageIndex, pageSize);
		
		RouteInfoList result = new RouteInfoList();
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			result.setTotalCount(
					runner.query(queryBuilder.getTotalCountSql(), 
							new ScalarHandler<Long>(), 
							queryBuilder.getParameterList()).intValue());
			
			result.setResultList(runner.query(
					queryBuilder.getItemsSql(), 
					new AbstractListHandler<RouteInfo>() {
						@Override
						protected RouteInfo handleRow(ResultSet rs) throws SQLException {
							return routeInfoFromResultSet(rs);
						}
					},
					queryBuilder.getParameterList()));
			
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		return result;
	}

	@Override
	public void destroy() {
	}


	@Override
	protected void syncRoute(TServiceCntl oCntl, String config) throws ErrorInfo, TException {
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getRouteVersionTableName());
		
		PreparedFields fields = new PreparedFields();
		if (config != null) {
			fields.addString("Fconfig", config);
		}
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		updateSqlBuffer.append(" SET Fversion=Fversion+1,");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fid=1");
	
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(updateSqlBuffer.toString(), fields.getParameters());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}


	@Override
	protected int getLastRouteVersion(TServiceCntl oCntl) throws ErrorInfo,
			TException {
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Fversion");
		queryBuilder.addTables(getRouteVersionTableName());
		queryBuilder.addFieldCondition(ConditionType.AND, "Fid=?", 1);
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(
					queryBuilder.getItemsSql(), 
					new ScalarHandler<Long>(), 
					queryBuilder.getParameterList()).intValue();
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}
	
	private SimpleRouteInfo simpleRouteInfoFromResultSet(ResultSet rs) throws SQLException {
		SimpleRouteInfo result = new SimpleRouteInfo();
		result.setServiceKey(rs.getInt("Fservice_key"));
		result.setIpList(strToIpList(rs.getString("Fip_list")));
		return result;
	}

	@Override
	protected List<SimpleRouteInfo> getAllSimpleRouteInfo(TServiceCntl oCntl)
			throws ErrorInfo, TException {
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("Fservice_key", "Fip_list");
		queryBuilder.addTables(getRouteInfoTableName());
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(
					queryBuilder.getItemsSql(), 
					new AbstractListHandler<SimpleRouteInfo>() {
						@Override
						protected SimpleRouteInfo handleRow(ResultSet rs) throws SQLException {
							return simpleRouteInfoFromResultSet(rs);
						}
					});
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}


	@Override
	protected void deleteRoute(TServiceCntl oCntl, int serviceKey)
			throws ErrorInfo, TException {
		checkServiceKey(serviceKey);
		
		StringBuffer sqlDeleteBuffer = new StringBuffer(128);
		sqlDeleteBuffer.append("DELETE FROM ");
		sqlDeleteBuffer.append(getRouteInfoTableName());
		sqlDeleteBuffer.append(" WHERE Fservice_key=?");
		
		QueryRunner runner = new QueryRunner(new DalSetDataSource(
				roleName, oCntl.getDalSetServiceName(), false, serviceKey));
		
		try {
			runner.update(sqlDeleteBuffer.toString(), serviceKey);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
	}


	@Override
	protected String getLastestRouteConfig(TServiceCntl oCntl)
			throws ErrorInfo, TException {
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		builder.addFields("Fconfig");
		builder.addTables(getRouteVersionTableName());
		builder.addFieldCondition(ConditionType.AND, "Fid=?", 1);
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		try {
			return runner.query(builder.getItemsSql(), new ScalarHandler<String>(), builder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_SELECT_FAILED.getErrorMsg());
		}
	}
}
