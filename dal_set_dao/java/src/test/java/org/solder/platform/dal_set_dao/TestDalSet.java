package org.solder.platform.dal_set_dao;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetHostList;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetRoleList;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.DalSetUserList;
import org.soldier.platform.dal_set.dao.DbType;
import org.soldier.platform.dal_set.dao.QueryDalSetHostOption;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryDalSetUserOption;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.ServiceRelatedType;
import org.soldier.platform.dal_set.dao.TypeInSet;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestDalSet {
	public static void addHost() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetHost host = new DalSetHost();
		host.setName("TestDaoSetHostToDelete");
		host.setDomain("10.0.0.5");
		host.setPort(3306);
		host.setDesc("测试的DB机");
		try {
			stub.addDalSetHost(RandomUtils.nextInt(), 1500, host);
			System.out.println("add Success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void queryHost() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		QueryDalSetHostOption option = new QueryDalSetHostOption();
		try {
			DalSetHostList resultList = stub.queryDalSetHosts(RandomUtils.nextInt(), 
					1500, 0, 10, option);
			System.out.println(resultList);
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void modifyHost() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetHost host = new DalSetHost();
		host.setName("TestDaoSetHost");
		host.setDomain("10.0.0.6");
		try {
			stub.updateDalSetHost(RandomUtils.nextInt(), 1500, host);
			System.out.println("update success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void addUser() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetUser user = new DalSetUser();
		user.setKey("TestUserKeyToDelete");
		user.setName("testUserName");
		user.setPassword("testPassword");
		user.setDesc("This is a testing!");
		
		try {
			stub.addDalSetUser(RandomUtils.nextInt(), 1500, user);
			System.out.println("add Success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void updateUser() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetUser user = new DalSetUser();
		user.setKey("TestUserKey");
		
		user.setDesc("Hahahahahaha");
		try {
			stub.updateDalSetUser(RandomUtils.nextInt(), 1500, user);
			System.out.println("update success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void queryUser() {
		DalSetDaoStub stub = new DalSetDaoStub();
		QueryDalSetUserOption option = new QueryDalSetUserOption();
		
		try {
			DalSetUserList resultList = stub.queryDalSetUsers(RandomUtils.nextInt(), 1500,
					0, 10, option);
			System.out.println(resultList);
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addTable() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetTable table = new DalSetTable();
		table.setPrefixName("t_table_test_to_delete_");
		table.setSliceNum(128);
		table.setFillZero(false);
		table.setDesc("测试DAL SET DAO表");
		
		try {
			stub.addDalSetTable(RandomUtils.nextInt(), 1500, table);
			System.out.println("add table success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void modifyTable() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetTable table = new DalSetTable();
		table.setPrefixName("t_table_test_");
		table.setSliceNum(0);
		table.setFillZero(true);
		table.setDesc("Hahaha");
		
		try {
			stub.updateDalSetTable(RandomUtils.nextInt(), 1500, table);
			System.out.println("modify success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void queryTable() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		QueryDalSetTableOption option = new QueryDalSetTableOption();
		
		try {
			DalSetTableList resultList = stub.queryDalSetTables(RandomUtils.nextInt(),
					1500, 0, 10, option);
			System.out.println(resultList);
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addRole() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetRole role = new DalSetRole();
		role.setRoleName("dal_set_test_to_delete");
		role.setDbName("platform");
		role.setDbType(DbType.Mysql);
		role.setDesc("测试的角色名称");
		
		try {
			stub.addDalSetRole(RandomUtils.nextInt(), 1000, role);
			System.out.println("success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void updateRole() {
		DalSetRole role = new DalSetRole();
		role.setRoleName("dal_set_test");
		role.setDbName("dal_set_test_db");
		role.setDesc("haha update test");
		
		DalSetDaoStub stub = new DalSetDaoStub();
		try {
			stub.updateDalSetRole(RandomUtils.nextInt(), 1000, role);
			System.out.println("update success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void queryRole() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		try {
			DalSetRoleList resultList = stub.queryDalSetRoles(RandomUtils.nextInt(),
					2000, 0, 10, null);
			System.out.println(resultList);
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addRoleTableRelation() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		RoleTableRelation relation = new RoleTableRelation();
		relation.setRoleName("dal_set_test");
		relation.setTablePrefixName("t_table_test_");
		
		try {
			stub.addTableRoleRelation(RandomUtils.nextInt(),
					1000, relation);
			System.out.println("add Success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void queryRoleTableRelation() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		try {
			System.out.println(stub.queryTableRoleRelations(RandomUtils.nextInt(), 
					2000, 0, 10, null));
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addRoleSetRelation() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		RoleSetRelation relation = new RoleSetRelation();
		relation.setHostName("TestDaoSetHost");
		relation.setSetIndex(0);
		relation.setTypeInSet(TypeInSet.Master);
		relation.setRoleName("dal_set_test");
		relation.setWeight(10);
		
		try {
			stub.addRoleSetRelation(RandomUtils.nextInt(), 1000, relation);
			System.out.println("Success!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteRoleSetRelation() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		RoleSetRelation relation = new RoleSetRelation();
		relation.setHostName("TestDaoSetHost");
		relation.setSetIndex(0);
		relation.setRoleName("dal_set_test");
		
		try {
			stub.deleteRoleSetRelation(RandomUtils.nextInt(), 1000, relation);
			System.out.println("Success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void queryRoleSetRelation() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		try {
			System.out.println(stub.queryRoleSetRelations(
					RandomUtils.nextInt(), 1500, 0, 10, null));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void addRoleServiceRelation() {
		DalSetDaoStub stub = new DalSetDaoStub();
		
		RoleServiceRelation relation = new RoleServiceRelation();
		relation.setServiceKey(1998);
		relation.setInterfaceName("NoSuchMethod");
//		relation.setServiceKey(0);
//		relation.setInterfaceName("NoDaemon");
		relation.setRoleName("dal_set_test");
		relation.setRelatedType(ServiceRelatedType.Master);
		relation.setUserKey("TestUserKey");
		
		try {
			stub.addRoleServiceRelation(relation.getServiceKey(), 1500, relation);
			System.out.println("success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void updateRoleServiceRelation() {
		RoleServiceRelation relation = new RoleServiceRelation();
//		relation.setServiceKey(1998);
//		relation.setInterfaceName("NoSuchMethod");
		relation.setServiceKey(0);
		relation.setInterfaceName("NoDaemon");
		relation.setRoleName("dal_set_test");
		relation.setRelatedType(ServiceRelatedType.Master);
		relation.setUserKey("epower_user");
		
		try {
			new DalSetDaoStub().updateRoleServiceRelation(
					RandomUtils.nextInt(), 1500, relation);
			System.out.println("success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void deleteRoleServiceRelation() {
		RoleServiceRelation relation = new RoleServiceRelation();
		
		relation.setServiceKey(1998);
		relation.setInterfaceName("NoSuchMethod");
		relation.setRoleName("dal_set_test");
		
		try {
			new DalSetDaoStub().deleteRoleServiceRelation(
					RandomUtils.nextInt(), 1500, relation);
			System.out.println("delete success");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void queryRoleServiceRelation() {
		try {
			System.out.println(new DalSetDaoStub().queryRoleServiceRelations(
					RandomUtils.nextInt(), 3000, 0, 10, null));
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void deleteDalSetHost() {
		try {
			new DalSetDaoStub().deleteDalSetHost(RandomUtils.nextInt(), 1000, "TestDaoSetHostToDelete");
			System.out.println("delete Success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteDalSetUser() {
		try {
			new DalSetDaoStub().deleteDalSetUser(RandomUtils.nextInt(),
					1000, "TestUserKeyToDelete");
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void deleteDalSetRole() {
		try {
			new DalSetDaoStub().deleteDalSetRole(RandomUtils.nextInt(),
					1000, "dal_set_test");
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteDalSetTable() {
		try {
			new DalSetDaoStub().deleteDalSetTable(RandomUtils.nextInt(), 
					2000, "t_table_test_to_delete_");
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
//		addHost();
//		queryHost();
//		modifyHost();
		
//		addUser();
//		updateUser();
//		queryUser();
		
//		addTable();
//		modifyTable();
//		queryTable();
		
//		addRole();
//		updateRole();
//		queryRole();
		
//		addRoleTableRelation();
//		queryRoleTableRelation();
//		addRoleSetRelation();
//		deleteRoleSetRelation();
//		queryRoleSetRelation();
		
//		addRoleServiceRelation();
//		deleteRoleServiceRelation();
//		queryRoleServiceRelation();
//		updateRoleServiceRelation();
		
//		deleteDalSetHost();
//		deleteDalSetUser();
//		deleteDalSetRole();
		deleteDalSetTable();
	}
}
