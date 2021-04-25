package org.soldier.platform.web.config.dao.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.NetHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web.config.dao.DeployType;
import org.soldier.platform.web.config.dao.QueryWebConfigOption;
import org.soldier.platform.web.config.dao.WebConfig;
import org.soldier.platform.web.config.dao.client.WebConfigDaoStub;

public class TestMain {
	public static void queryWebConfig() {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		QueryWebConfigOption option = new QueryWebConfigOption();
		
		try {
			System.out.println(stub.queryConfig(RandomUtils.nextInt(), 2000, option));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void queryWebConfigPage() {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		QueryWebConfigOption option = new QueryWebConfigOption();
		
		try {
			System.out.println(stub.queryConfigByPage(RandomUtils.nextInt(), 2000, option, 0, 10));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void addWebConfig() {
		WebConfig config = new WebConfig();
		config.setWebProjectName("123_web");
		config.setDeployType(DeployType.Apache);
		
		List<String> domainList = new ArrayList<String>();
		domainList.add("test.1024-1024.com");
		config.setDomainList(domainList);
		config.setDesc("this is test");
		config.setIndexPath("/index.jsp");
		
		List<Long> ipList = new ArrayList<Long>();
		ipList.add(NetHelper.AddrNet("10.0.0.10"));
		config.setIpList(ipList);
		config.setPort(8080);
		
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		try {
			stub.addWebConfig(RandomUtils.nextInt(), 1500, config);
			System.out.println("add success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateWebConfig() {
		WebConfig config = new WebConfig();
		config.setWebProjectName("test_web");
		
		config.setDeployType(DeployType.Jetty);
		config.setPort(8090);
		config.setDomainList(new ArrayList<String>());
		config.setIndexPath("");
		
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		try {
			stub.updateWebConfig(RandomUtils.nextInt(), 1500, config);
			System.out.println("update success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteWebConfig() {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		try {
			stub.deleteWebConfig(RandomUtils.nextInt(), 1500, "test_web");
			System.out.println("delete success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateNginxConfig() {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		try {
			stub.updateNginxConfig(RandomUtils.nextInt(), 1500, "123", null);
			System.out.println("update success!");
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void getLastestVersion() {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		try {
			System.out.println(stub.getLastVersion(RandomUtils.nextInt(), 1500, null));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	public static void getLastestNginxConfig() {
		WebConfigDaoStub stub = new WebConfigDaoStub();
		
		try {
			System.out.println(stub.getLastestNginxConfig(RandomUtils.nextInt(), 1500, null));
		} catch (ErrorInfo e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
//		queryWebConfig();
//		queryWebConfigPage();
//		addWebConfig();
//		updateWebConfig();
//		deleteWebConfig();
		updateNginxConfig();
//		getLastestVersion();
//		getLastestNginxConfig();
	}

}
