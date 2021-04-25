package org.soldier.platform.oa.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;
import org.soldier.platform.oa.menu.dao.client.OaMenuDaoStub;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;

import net.qihoo.qconf.Qconf;

public class Main extends OaAuthController {
	public void show() throws Exception {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		List<TSystemMenu> systemMenus = stub.getSystemMenus(RandomUtils.nextInt(), 2000, new QuerySystemMenuOption());

		put("systemMenus", systemMenus);
		
		int selectSystemMenuId = parameter("selectSystemMenuName", 0);
		if (selectSystemMenuId > 0) {
			put("subMenus", 
				stub.getSubMenus(RandomUtils.nextInt(), 2000, new QuerySubMenuOption(selectSystemMenuId)));
		} else {
			put("subMenus", new ArrayList<TSubMenu>());
		}
		
		put("userName", getUserName());
		put("selectSystemMenuId", selectSystemMenuId);
		
		put("environment", Qconf.getConf("platform/environment").toUpperCase());
		put("domain", this.getServletRequest().getServerName());
		
		render("main.html");
	}
	
	public void logout() throws Exception {
		new OaUserAoStub().logout(getUserId(), 1000, getUserId(), getUserName());
	}
}
