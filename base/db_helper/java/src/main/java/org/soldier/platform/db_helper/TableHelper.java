package org.soldier.platform.db_helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;

public abstract class TableHelper<T> implements RS2O<T> {
	
	private Connection conn;
	
	protected abstract String getTableName() throws SQLException;
	
	protected TableHelper(Connection conn) {
		this.conn = conn;
	}
	
	protected Connection getConnection() {
		return this.conn;
	}
	
	protected StringBuilder getInsertTableSqlBuffer(PreparedFields fields) throws SQLException{
		StringBuilder sqlBuilder = new StringBuilder(128);
		sqlBuilder.append("INSERT INTO ");
		sqlBuilder.append(getTableName());
		sqlBuilder.append(" SET ");
		sqlBuilder.append(fields.getPreparedSql());
		return sqlBuilder;
	}
	
	protected StringBuilder getTableUpdateSqlBuffer(PreparedFields fields) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder(128);
		sqlBuilder.append("UPDATE ");
		sqlBuilder.append(getTableName());
		sqlBuilder.append(" SET ");
		sqlBuilder.append(fields.getPreparedSql());
		sqlBuilder.append(" WHERE ");
		return sqlBuilder;
	}
	
	protected int insert(PreparedFields fields) throws SQLException {
		return new QueryRunner().update(conn
				, getInsertTableSqlBuffer(fields).toString()
				, fields.getParameters());
	}
	
	protected int update(PreparedFields fields
			, String whereCondition, Object... whereParameters) throws SQLException {
		StringBuilder sqlBuilder = getTableUpdateSqlBuffer(fields);
		sqlBuilder.append(whereCondition);
		return new QueryRunner().update(conn, sqlBuilder.toString()
				, ArrayUtils.addAll(fields.getParameters(), whereParameters));
	}
	
	public int deleteAll() throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder(128);
		sqlBuilder.append("DELETE FROM ").append(getTableName());
		return new QueryRunner().update(conn, sqlBuilder.toString());
	}
	
	protected int delete(String whereCondition, Object... whereParameters) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder(128);
		sqlBuilder.append("DELETE FROM ").append(getTableName())
				  .append(" WHERE ")
				  .append(whereCondition);
		return new QueryRunner().update(conn, sqlBuilder.toString(), whereParameters);
	}
	
	protected SqlQueryBuilder prepareSqlQueryBuilder() throws SQLException {
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.addFields("*");
		queryBuilder.addTables(getTableName());
		return queryBuilder;
	}
	
	protected int getTotalNum(SqlQueryBuilder builder) throws SQLException {
		return new QueryRunner().query(conn
				, builder.getTotalCountSql()
				, new ScalarHandler<Long>()
				, builder.getParameterList()).intValue();
	}
	
	protected List<T> getItemList(SqlQueryBuilder builder) throws SQLException {
		return getItemList(builder, false);
	}
	
	protected List<T> getItemList(SqlQueryBuilder builder, boolean forUpdate) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder(128);
		sqlBuilder.append(builder.getItemsSql());
		if (forUpdate) {
			sqlBuilder.append(" FOR UPDATE");
		}
		return new QueryRunner().query(conn, sqlBuilder.toString()
				, new AbstractListHandler<T>() {
					@Override
					protected T handleRow(ResultSet rs) throws SQLException {
						try {
							return TableHelper.this.fromResultSet(rs);
						} catch (SQLException se) {
							throw se;
						} catch (Throwable te) {
							throw new SQLException(te);
						}
					}
		}, builder.getParameterList());
	}
	
	protected T getItem(SqlQueryBuilder builder, boolean forUpdate) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder(128);
		sqlBuilder.append(builder.getItemsSql());
		if (forUpdate) {
			sqlBuilder.append(" FOR UPDATE");
		}
		return new QueryRunner().query(conn, sqlBuilder.toString()
				, new ResultSetHandler<T>() {
					@Override
					public T handle(ResultSet rs) throws SQLException {
						if (rs.next()) {
							try {
								return TableHelper.this.fromResultSet(rs);
							} catch (SQLException se) {
								throw se;
							} catch (Throwable te) {
								throw new SQLException(te);
							}
						}
						return null;
					}
			
		}, builder.getParameterList());
	}
	
	protected T getItem(SqlQueryBuilder builder) throws SQLException {
		return getItem(builder, false);
	}
	
}
