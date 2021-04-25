package org.soldier.platform.admin.controller.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.soldier.platform.admin.controller.CController;
import org.soldier.platform.admin.model.MenuItem;

public class Menu extends CController {
	private static List<MenuItem> menu = new ArrayList<MenuItem>(5);
	
	static{
		menu.add(new MenuItem("服务管理", "html/RouteManage"));
		menu.add(new MenuItem("机器管理", "MachineManage/show"));
		menu.add(new MenuItem("DALSET管理", "html/DalSetManage"));
		menu.add(new MenuItem("IDMAKER管理", "html/IdMakerManage"));
		menu.add(new MenuItem("文件存储管理", "html/FileStorageManage"));
		menu.add(new MenuItem("Web项目管理", "WebManage/show"));
		menu.add(new MenuItem("消息队列管理", "MsgQManage/show"));
		menu.add(new MenuItem("OA菜单管理", "OaMenu/showSystemMenus"));
	}
	
	public boolean needCheckSession() {
		return false;
	}
	
	@Override
	public int doModel(Map<Object, Object> dataModel) {
		dataModel.put("menu", menu);
		return 200;
	}

}
