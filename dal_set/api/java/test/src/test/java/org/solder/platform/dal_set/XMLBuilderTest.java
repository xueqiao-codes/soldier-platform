package org.solder.platform.dal_set;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.soldier.platform.dal_set.ConnectionConfig;
import org.soldier.platform.dal_set.DalSetProxy;
import org.soldier.platform.dal_set.IConnectionPool;
import org.soldier.platform.dal_set.IVariableProvider;
import org.soldier.platform.dal_set.VariableException;
import org.soldier.platform.dal_set.VariableFactory;

public class XMLBuilderTest extends TestCase implements IConnectionPool {
	private String roleName = "test";
	
	@Override
	protected void setUp() throws Exception {
		VariableFactory.getInstance().setProvider(new IVariableProvider() {
			@Override
			public String getVariable(String variableName)
					throws VariableException {
				if (variableName.equals("slicenum")) {
					return "256";
				}
				if (variableName.equals("fillZero")) {
					return "true";
				}
				
				throw new VariableException("Variable Not Found For " + variableName);
			}
			
		});
		
		DalSetProxy.getInstance().loadFromXml(this.getClass().getResource("/dal_set1.xml").getFile());
	}
	
	@Test
	public void testGetSetNum() throws SQLException {
		Assert.assertEquals(2, DalSetProxy.getInstance().getSetNum(roleName));
	}
	
	@Test
	public void testGetTableName() throws SQLException {
		Assert.assertEquals("tuser_place_0",
				DalSetProxy.getInstance().getTableName(roleName, 
						"tuser_place_", 0));
		Assert.assertEquals("tuser_place_1023", 
				DalSetProxy.getInstance().getTableName(roleName, 
						"tuser_place_", 1023));
		
		Assert.assertEquals("tfill_zero_001", 
				DalSetProxy.getInstance().getTableName(roleName, 
						"tfill_zero_", 1));
		Assert.assertEquals("tfill_zero_127", 
				DalSetProxy.getInstance().getTableName(roleName, 
						"tfill_zero_", 127 + 128));
	}
	
	@Test
	public void testGetMasterConnectionConfig() throws SQLException {
		ConnectionConfig config = DalSetProxy.getInstance().getConnectionConfig(
				roleName, "800", false, 0);
		Assert.assertEquals("user", config.getDbName());
		Assert.assertEquals("192.168.0.1", config.getHost());
		Assert.assertEquals("mysql", config.getDbType());
		Assert.assertEquals("test", config.getUserName());
		Assert.assertEquals("test@test", config.getPasswd());
		Assert.assertEquals(3308, config.getPort());
	}
	
	@Test
	public void testRatioConnectionConfig() throws SQLException {
		int count = 0;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (++count <= 10000) {
			ConnectionConfig config = DalSetProxy.getInstance().getConnectionConfig(
					roleName, "800", true, 0);
			Integer value = map.get(config.getHost());
			if (value == null) {
				map.put(config.getHost(), 1);
			} else {
				map.put(config.getHost(), value + 1);
			}
		}
		count = count -1;
		Assert.assertEquals(2, map.size());
		double value = ((double)map.get("192.168.0.1")/count);
		Assert.assertTrue(0.45 <= value);
		Assert.assertTrue(0.55 >= value);
	}
	
	@Test
	public void testOnlyRelationConnectionConfig() throws SQLException {
		int count = 0;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (++count <= 1000) {
			ConnectionConfig config = DalSetProxy.getInstance().getConnectionConfig(
					roleName, "800_special", true, 1);
		
			Integer value = map.get(config.getHost());
			if (value == null) {
				map.put(config.getHost(), 1);
			} else {
				map.put(config.getHost(), value + 1);
			}
			
		}
		
		Assert.assertEquals(1, map.size());
		Assert.assertTrue(map.containsKey("192.168.0.254"));
	}
	
	@Test
	public void testGetHasWeightConnectionConfig() throws SQLException {
		int count = 0;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (++count <= 1000) {
			ConnectionConfig config = DalSetProxy.getInstance().getConnectionConfig(
					roleName, "800", true, 1);
		
			Integer value = map.get(config.getHost());
			if (value == null) {
				map.put(config.getHost(), 1);
			} else {
				map.put(config.getHost(), value + 1);
			}
			
		}
		
//		for (Entry<String, Integer> entry : map.entrySet()) {
//			System.out.println(entry.getKey() + "->" + entry.getValue());
//		}
		
		Assert.assertFalse(map.containsKey("192.168.0.254"));
	}
	
	public void testVariable() throws SQLException {
		Assert.assertEquals("tvariable_table_000",
				DalSetProxy.getInstance().getTableName(roleName, 
						"tvariable_table_", 0));
		Assert.assertEquals("tvariable_table_111",
				DalSetProxy.getInstance().getTableName(roleName, 
						"tvariable_table_", 111 + 256*10));
	}
	
	@Override
	protected void tearDown() {
		DalSetProxy.getInstance().destroy();
	}

	@Override
	public Connection getConnection(ConnectionConfig config)
			throws SQLException {
		
		return null;
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub
		
	}
}
