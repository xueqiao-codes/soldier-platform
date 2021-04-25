package org.soldier.platform.dal_set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolTest {
	public static void main(String[] argv){
		IConnectionPool pool = new BoneCpConnectionPool(null);
		
		ConnectionConfig mysqlConnection = new MysqlConnectionConfig();
		mysqlConnection.setHost("10.96.54.103");
		mysqlConnection.setUserName("wileywang");
		mysqlConnection.setPasswd("wiley@home");
		mysqlConnection.setDbName("test");
		mysqlConnection.setPort(3306);
		
		long current = System.currentTimeMillis();
		for(int index = 0; index < 10000; ++index)
		{
			Connection conn = null;
			try {
				conn = pool.getConnection(mysqlConnection);
//				conn = MysqlStorage.CreateConnection(mysqlConnection.getHost(), 
//						mysqlConnection.getPort(), mysqlConnection.getDbName(), 
//						mysqlConnection.getUserName(), mysqlConnection.getPasswd());
				
				PreparedStatement statement = conn.prepareStatement("SHOW TABLES");
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()){
					System.out.println(resultSet.getString(1));
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("spend=" + ( System.currentTimeMillis() - current) + "ms");
		pool.destory();
	}
}
