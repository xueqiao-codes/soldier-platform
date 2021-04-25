package org.soldier.platform.oa.menu.dao.test;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;
import org.soldier.platform.oa.menu.dao.client.OaMenuDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class TestMain {
	
	public static void querySystemMenus() {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		QuerySystemMenuOption option = new QuerySystemMenuOption();
		
		try {
			System.out.println(stub.getSystemMenus(RandomUtils.nextInt(), 2000, option));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void querySubMenus() {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		QuerySubMenuOption option = new QuerySubMenuOption();
		option.setSystemMenuId(1);
		
		try {
			System.out.println(stub.getSubMenus(RandomUtils.nextInt(), 2000, option));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addSystemMenu() {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		TSystemMenu menu = new TSystemMenu();
		menu.setSystemMenuName("GalaCard");
		
		try {
			System.out.println(stub.addSystemMenu(
					RandomUtils.nextInt(), 2000, menu));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSubMenu() {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		TSubMenu menu = new TSubMenu();
		menu.setMenuName("Test");
		menu.setMenuSrc("http://www.qq.com");
		menu.setSystemMenuId(1004);
		
		try {
			System.out.println(stub.addSubMenu(RandomUtils.nextInt(), 2000, menu));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteSystemMenu(int systemMenuId) {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		try {
			stub.deleteSystemMenu(RandomUtils.nextInt(), 2000, systemMenuId);
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSubMenu() {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		TSubMenu menu = new TSubMenu();
		menu.setMenuId(1001);
		menu.setMenuName("百度");
		menu.setMenuSrc("http://www.baidu.com");
		try {
			stub.updateSubMenu(RandomUtils.nextInt(), 2000, menu);
			System.out.println("update success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteSubMenu(int menuId) {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		try {
			stub.deleteSubMenu(RandomUtils.nextInt(), 2000, menuId);
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
//		querySystemMenus();
//		querySubMenus();
//		addSystemMenu();
//		addSubMenu();
//		deleteSystemMenu(1003);
//		updateSubMenu();
		deleteSubMenu(1001);
	}

}
