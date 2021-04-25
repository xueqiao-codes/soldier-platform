package org.soldier.platform.oa.menu.dao.server.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.DbUtil;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.dal_set.DalSetDataSource;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;
import org.soldier.platform.oa.menu.dao.server.OaMenuDaoAdaptor;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;
import com.mysql.jdbc.Statement;

public class OaMenuDaoHandler extends OaMenuDaoAdaptor {
	private String roleName;
	
	@Override
	public int InitApp(Properties props) {
		roleName = props.getProperty("roleName", "role_oa_menu");
		System.out.println("roleName=" + roleName);
		
		try {
			DalSetProxy.getInstance().loadFromXml();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}
	
	private String getSystemMenuTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName,
							"toa_system_menu", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private String getSubMenuTableName() throws ErrorInfo {
		try {
			return DalSetProxy.getInstance().getTableName(roleName, "toa_sub_menu", 0);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DAL_SET_ERROR.getErrorCode(),
					ErrorCodeInner.DAL_SET_ERROR.getErrorMsg());
		}
	}
	
	private TSystemMenu systemMenuFromRS(ResultSet rs) throws SQLException {
		TSystemMenu menu = new TSystemMenu();
		menu.setSystemMenuId(rs.getInt("Fsystem_menu_id"));
		menu.setSystemMenuName(rs.getString("Fsystem_menu_name"));
		menu.setOrderWeight(rs.getInt("Forder_weight"));
		menu.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		menu.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return menu;
	}
	
	private TSubMenu subMenuFromRS(ResultSet rs) throws SQLException {
		TSubMenu menu = new TSubMenu();
		menu.setMenuId(rs.getInt("Fmenu_id"));
		menu.setSystemMenuId(rs.getInt("Fsystem_menu_id"));
		menu.setMenuName(rs.getString("Fmenu_name"));
		menu.setMenuSrc(rs.getString("Fmenu_src"));
		menu.setOrderWeight(rs.getInt("Forder_weight"));
		menu.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		menu.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
		return menu;
	}
	
	private void prepareSystemMenu(PreparedFields fields, TSystemMenu menu) {
		if (menu.isSetSystemMenuName()) {
			fields.addString("Fsystem_menu_name", menu.getSystemMenuName().trim());
		}
		if (menu.isSetOrderWeight()) {
			fields.addInt("Forder_weight", menu.getOrderWeight());
		}
	}
	
	private void prepareSubMenu(PreparedFields fields, TSubMenu menu) {
		if (menu.isSetMenuSrc()) {
			fields.addString("Fmenu_src", menu.getMenuSrc().trim());
		}
		if (menu.isSetMenuName()) {
			fields.addString("Fmenu_name", menu.getMenuName().trim());
		}
		if (menu.isSetOrderWeight()) {
			fields.addInt("Forder_weight", menu.getOrderWeight());
		}
		if (menu.isSetSystemMenuId()) {
			fields.addInt("Fsystem_menu_id", menu.getSystemMenuId());
		}
	}

	@Override
	protected List<TSystemMenu> getSystemMenus(TServiceCntl oCntl,
			QuerySystemMenuOption option)
			throws ErrorInfo, TException {
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		builder.addFields("Fsystem_menu_id, Fsystem_menu_name, Forder_weight, Fcreate_timestamp, Flastmodify_timestamp");
		builder.addTables(getSystemMenuTableName());
		if (option != null) {
			if (option.isSetSystemMenuId()) {
				builder.addFieldCondition(ConditionType.AND,
						"Fsystem_menu_id=?", option.getSystemMenuId());
			}
			if (option.isSetSystemMenuName()) {
				builder.addFieldCondition(ConditionType.AND,
						"Fsystem_menu_name=?", option.getSystemMenuName());
			}
		}
		builder.setOrder(OrderType.ASC, "Forder_weight");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		
		try {
			return runner.query(
					builder.getItemsSql(), 
						new AbstractListHandler<TSystemMenu>() {
							@Override
							protected TSystemMenu handleRow(ResultSet rs) throws SQLException {
								return systemMenuFromRS(rs);
							}
						}, builder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_CONNECT_FAILED.getErrorMsg());
		}
	}

	@Override
	protected List<TSubMenu> getSubMenus(TServiceCntl oCntl,
			QuerySubMenuOption option)
			throws ErrorInfo, TException {
		if (option == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"option should not be null");
		}
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.addFields("Fmenu_id", "Fmenu_name", "Fmenu_src", "Fsystem_menu_id", 
				"Forder_weight", "Fcreate_timestamp", "Flastmodify_timestamp");
		builder.addTables(getSubMenuTableName());
		
		builder.addFieldCondition(ConditionType.AND,
				"Fsystem_menu_id=?", option.getSystemMenuId());
		if (option.isSetMenuId()) {
			builder.addFieldCondition(ConditionType.AND,
					"Fmenu_id=?", option.getMenuId());
		}
		if (option.isSetMenuName()) {
			builder.addFieldCondition(ConditionType.AND,
					"Fmenu_name=?", option.getMenuName());
		}
		builder.setOrder(OrderType.ASC, "Forder_weight");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), true, 0));
		
		try {
			return runner.query(
					builder.getItemsSql(), 
						new AbstractListHandler<TSubMenu>() {
							@Override
							protected TSubMenu handleRow(ResultSet rs) throws SQLException {
								return subMenuFromRS(rs);
							}
						}, builder.getParameterList());
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_SELECT_FAILED.getErrorCode(),
					ErrorCodeInner.DB_CONNECT_FAILED.getErrorMsg());
		}
		
	}

	@Override
	protected int addSystemMenu(TServiceCntl oCntl, TSystemMenu menu)
			throws ErrorInfo, TException {
		if (menu == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "menu should not be null");
		}
		if (!menu.isSetSystemMenuName() || menu.getSystemMenuName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "systemMenuName is not set or empty");
		}
		
		PreparedFields fields = new PreparedFields();
		prepareSystemMenu(fields, menu);
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer addSqlBuilder = new StringBuffer(128);
		addSqlBuilder.append("INSERT INTO ");
		addSqlBuilder.append(getSystemMenuTableName());
		addSqlBuilder.append(" SET ");
		addSqlBuilder.append(fields.getPreparedSql());
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = DalSetProxy.getInstance().getConnection(roleName, oCntl.getDalSetServiceName(), false, 0);
			
			statement = conn.prepareStatement(addSqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
			Object[] parameters = fields.getParameters();
			for (int index = 0; index < fields.getSize(); ++index) {
				statement.setObject(index + 1, parameters[index]);
			}
			
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
	        if(rs.next()){
	           return rs.getInt(1);
	        }
			return 0;
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(), 
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		} finally {
			DbUtil.closeQuietly(rs);
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(conn);
		}
	}

	@Override
	protected void deleteSystemMenu(TServiceCntl oCntl, int systemMenuId)
			throws ErrorInfo, TException {
		if (systemMenuId <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"systemMenuId should not equals 0");
		}
		
		StringBuffer deleteSystemMenuSql = new StringBuffer(128);
		deleteSystemMenuSql.append("DELETE FROM ");
		deleteSystemMenuSql.append(getSystemMenuTableName());
		deleteSystemMenuSql.append(" WHERE Fsystem_menu_id=?");
		
		StringBuffer deleteSubMenuSql = new StringBuffer(128);
		deleteSubMenuSql.append("DELETE FROM ");
		deleteSubMenuSql.append(getSubMenuTableName());
		deleteSubMenuSql.append(" WHERE Fsystem_menu_id=?");
		
		Connection conn = null;
		
		QueryRunner runner = new QueryRunner();
		try {
			conn = DalSetProxy.getInstance().getConnection(roleName, oCntl.getDalSetServiceName(), false, 0);
			
			boolean success = false;
			try {
				conn.setAutoCommit(false);
				
				runner.update(conn, deleteSystemMenuSql.toString(), systemMenuId);
				runner.update(conn, deleteSubMenuSql.toString(), systemMenuId);
				success = true;
			} finally {
				if (success) {
					conn.commit();
				} else {
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		} finally {
			DbUtil.closeQuietly(conn);
		}
	}

	@Override
	protected int addSubMenu(TServiceCntl oCntl, TSubMenu menu)
			throws ErrorInfo, TException {
		if (menu == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menu should not be null");
		}
		if (!menu.isSetMenuName() || menu.getMenuName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menuName should not be null or empty!");
		}
		if (!menu.isSetMenuSrc() || menu.getMenuSrc().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menuSrc should not be null or empty!");
		}
		
		PreparedFields fields = new PreparedFields();
		prepareSubMenu(fields, menu);
		fields.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis() / 1000));
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer addSqlBuilder = new StringBuffer(128);
		addSqlBuilder.append("INSERT INTO ");
		addSqlBuilder.append(getSubMenuTableName());
		addSqlBuilder.append(" SET ");
		addSqlBuilder.append(fields.getPreparedSql());
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = DalSetProxy.getInstance().getConnection(roleName, oCntl.getDalSetServiceName(), false, 0);
			
			statement = conn.prepareStatement(addSqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
			Object[] parameters = fields.getParameters();
			for (int index = 0; index < fields.getSize(); ++index) {
				statement.setObject(index + 1, parameters[index]);
			}
			
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
	        if(rs.next()){
	           return rs.getInt(1);
	        }
			return 0;
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(), 
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		} finally {
			DbUtil.closeQuietly(rs);
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(conn);
		}
	}

	@Override
	protected void deleteSubMenu(TServiceCntl oCntl, int menuId)
			throws ErrorInfo, TException {
		if (menuId <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menuId should not <= 0");
		}
		
		StringBuffer buffer = new StringBuffer(128);
		buffer.append("DELETE FROM ");
		buffer.append(getSubMenuTableName());
		buffer.append(" WHERE Fmenu_id=?");
		
		QueryRunner runner = new QueryRunner(
			new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			runner.update(buffer.toString(), menuId);
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateSubMenu(TServiceCntl oCntl, TSubMenu menu)
			throws ErrorInfo, TException {
		if (menu == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menu should not be null");
		}
		if (!menu.isSetMenuId() || menu.getMenuId() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menuId shoud set and not <= 0");
		}
		
		PreparedFields fields = new PreparedFields();
		prepareSubMenu(fields, menu);
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
	
		StringBuffer updateSqlBuffer = new StringBuffer(128);
		updateSqlBuffer.append("UPDATE ");
		updateSqlBuffer.append(getSubMenuTableName());
		updateSqlBuffer.append(" SET ");
		updateSqlBuffer.append(fields.getPreparedSql());
		updateSqlBuffer.append(" WHERE Fmenu_id=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			int rows = runner.update(updateSqlBuffer.toString(), 
					ArrayUtils.add(fields.getParameters(), menu.getMenuId()));
			if (rows <= 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_UPDATE_FAILED.getErrorMsg());
		}
	}

	@Override
	protected void updateSystemMenu(TServiceCntl oCntl, TSystemMenu menu)
			throws ErrorInfo, TException {
		if (menu == null) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"menu should not be null");
		}
		if (!menu.isSetSystemMenuId() || menu.getSystemMenuId() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"systemMenuId should be set and not <= 0");
		}
		
		PreparedFields fields = new PreparedFields();
		prepareSystemMenu(fields, menu);
		fields.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis() / 1000));
		
		StringBuffer updateBuffer = new StringBuffer(128);
		updateBuffer.append("UPDATE ");
		updateBuffer.append(getSystemMenuTableName());
		updateBuffer.append(" SET ");
		updateBuffer.append(fields.getPreparedSql());
		updateBuffer.append(" WHERE Fsystem_menu_id=?");
		
		QueryRunner runner = new QueryRunner(
				new DalSetDataSource(roleName, oCntl.getDalSetServiceName(), false, 0));
		
		try {
			int rs = runner.update(updateBuffer.toString(), 
					ArrayUtils.add(fields.getParameters(), menu.getSystemMenuId()));
			if (rs <= 0) {
				throw new ErrorInfo(ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeInner.RECORD_NOT_FOUND.getErrorMsg());
			}
		} catch (SQLException e) {
			AppLog.e(e.getMessage(), e);
			throw new ErrorInfo(ErrorCodeInner.DB_OP_FAILED.getErrorCode(),
					ErrorCodeInner.DB_OP_FAILED.getErrorMsg());
		}
		
	}
	
	@Override
	public void destroy() {
	}
}
