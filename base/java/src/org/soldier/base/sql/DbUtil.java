package org.soldier.base.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
/**
 * 一些帮助函数
 */
public class DbUtil {
	private static Logger logger = Logger.getRootLogger();
	public static void closeQuietly(final Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public static void closeQuietly(final Statement statement){
		if(statement != null){
			try{
				statement.close();
			} catch (SQLException e){
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public static void closeQuietly(final ResultSet rs){
		if(rs != null){
			try{
				rs.close();
			} catch (SQLException e){
				logger.error(e.getMessage(), e);
			}
		}
	}
}
