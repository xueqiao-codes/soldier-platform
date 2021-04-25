package org.soldier.platform.dal_set;

import java.sql.Connection;
import java.sql.SQLException;

import org.soldier.platform.db_helper.TableHelper;

public abstract class DalSetTableHelper<T> extends TableHelper<T> {
	
	protected String roleName;
	protected long tableKey;
	
	protected DalSetTableHelper(Connection conn, String roleName) {
		super(conn);
		this.roleName = roleName;
	}
	
	protected void setTableKey(long tableKey) {
		this.tableKey = tableKey;
	}
	
	protected long getTableKey() {
		return this.tableKey;
	}
	
	protected abstract String getTableNamePrefix();
	
	protected String getTableName() throws SQLException {
		return DalSetProxy.getInstance().getTableName(roleName, getTableNamePrefix(), tableKey);
	}
	
}
