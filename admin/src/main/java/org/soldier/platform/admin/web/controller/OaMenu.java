package org.soldier.platform.admin.web.controller;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.oa.menu.dao.QuerySubMenuOption;
import org.soldier.platform.oa.menu.dao.QuerySystemMenuOption;
import org.soldier.platform.oa.menu.dao.TSubMenu;
import org.soldier.platform.oa.menu.dao.TSystemMenu;
import org.soldier.platform.oa.menu.dao.client.OaMenuDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;

public class OaMenu extends WebAuthController {
	public void showSystemMenus() throws Exception {
		OaMenuDaoStub stub = new OaMenuDaoStub();
		List<TSystemMenu> menus = 
				stub.getSystemMenus(RandomUtils.nextInt(), 2000, new QuerySystemMenuOption());
		put("systemMenus", menus);
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("oa/SystemMenus.html");
	}
	
	public static class AddSytemMenuResult extends ErrorResult {
		private int systemMenuId;

		public void setSystemMenuId(int systemMenuId) {
			this.systemMenuId = systemMenuId;
		}
		
		public int getSystemMenuId() {
			return this.systemMenuId;
		}
	}
	
	public void addSystemMenu() throws Exception {
		AddSytemMenuResult result = new AddSytemMenuResult();
		doAddSystemMenu(result);
		echoJson(result);
	}
	
	private void doAddSystemMenu(AddSytemMenuResult result) throws Exception{
		TSystemMenu menu = new TSystemMenu();
		
		String menuName = parameter("menuName", "").trim();
		if (menuName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("必须填写系统菜单名称");
			return ;
		}
		menu.setSystemMenuName(menuName);
		
		int orderWeight = parameter("menuWeight", 0);
		if (orderWeight > 0) {
			menu.setOrderWeight(orderWeight);
		}
		
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		List<TSystemMenu> menus = 
				stub.getSystemMenus(RandomUtils.nextInt(), 2000, 
						new QuerySystemMenuOption().setSystemMenuName(menuName));
		if (menus.size() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("系统菜单已经存在");
			return ;
		}
		
		result.setSystemMenuId(
				stub.addSystemMenu(RandomUtils.nextInt(), 2000, menu));
	}
	
	public void updateSystemMenu() throws Exception {
		ErrorResult result = new ErrorResult();
		doUpdateSystemMenu(result);
		echoJson(result);
	}
	
	private void doUpdateSystemMenu(ErrorResult result) throws Exception {
		int menuId = parameter("menuId", 0);
		if (menuId <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("请选择正确的系统菜单");
			return ;
		}
		
		TSystemMenu menu = new TSystemMenu();
		menu.setSystemMenuId(menuId);
		
		String menuName = parameter("menuName", "").trim();
		if (!menuName.isEmpty()) {
			menu.setSystemMenuName(menuName);
		}
		
		int orderWeight = parameter("menuWeight", 0);
		if (orderWeight > 0) {
			menu.setOrderWeight(orderWeight);
		}
		
		new OaMenuDaoStub().updateSystemMenu(menuId, 1500, menu);
	}
	
	public void deleteSystemMenu() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteSystemMenu(result);
		echoJson(result);
	}
	
	private void doDeleteSystemMenu(ErrorResult result) throws Exception {
		int menuId = parameter("menuId", 0);
		if (menuId <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("请选择正确的菜单");
			return ;
		}
		
		new OaMenuDaoStub().deleteSystemMenu(RandomUtils.nextInt(), 2000, menuId);
	}
	
	public void showSubMenus() throws Exception {
		int systemMenuId = parameter("systemMenuId", 0);
		
		OaMenuDaoStub stub = new OaMenuDaoStub();
		List<TSystemMenu> systemMenus = 
				stub.getSystemMenus(RandomUtils.nextInt(), 2000, 
						new QuerySystemMenuOption().setSystemMenuId(systemMenuId));
		if (systemMenus.size() <= 0) {
			showUserError("系统菜单不存在");
			return ;
		}
		put("systemMenu", systemMenus.get(0));
		
		put("subMenus", stub.getSubMenus(systemMenuId, 2000,
				new QuerySubMenuOption().setSystemMenuId(systemMenuId)));
		
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("oa/SubMenus.html");
	}
	
	public void addSubMenu() throws Exception {
		ErrorResult result = new ErrorResult();
		doAddSubMenu(result);
		echoJson(result);
	}
	
	private void doAddSubMenu(ErrorResult result) throws Exception{
		TSubMenu menu = new TSubMenu();
		
		int systemMenuId = parameter("systemMenuId", 0);
		if (systemMenuId <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("必须选择正确的系统菜单");
			return ;
		}
		menu.setSystemMenuId(systemMenuId);
		
		String menuName = parameter("menuName", "").trim();
		if (menuName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  .setErrorMsg("必须选择菜单名称");
			return ;
		}
		menu.setMenuName(menuName);
		
		String menuSrc = parameter("menuSrc", "").trim();
		if (menuSrc.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("必须填写链接");
			return ;
		}
		menu.setMenuSrc(menuSrc);
		
		int orderWeight = parameter("menuWeight", 0);
		if (orderWeight > 0) {
			menu.setOrderWeight(orderWeight);
		}
		
		OaMenuDaoStub stub = new OaMenuDaoStub();
		
		List<TSubMenu> subMenus = stub.getSubMenus(systemMenuId, 1500, 
				new QuerySubMenuOption().setMenuName(menuName).setSystemMenuId(systemMenuId));
		if (subMenus.size() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  	  .setErrorMsg("重复的菜单名称");
			return ;
		}
	
		stub.addSubMenu(systemMenuId, 1500, menu);
	}
	
	public void deleteSubMenu() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteSubMenu(result);
		echoJson(result);
	}
	
	private void doDeleteSubMenu(ErrorResult result) throws Exception {
		int menuId = parameter("menuId", 0);
		if (menuId <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("无效的菜单ID");
			return ;
		}
		
		new OaMenuDaoStub().deleteSubMenu(menuId, 1500, menuId);
	}
	
	public void updateSubMenu() throws Exception {
		ErrorResult result = new ErrorResult();
		doUpdateSubMenu(result);
		echoJson(result);
	}
	
	private void doUpdateSubMenu(ErrorResult result) throws Exception {
		TSubMenu menu = new TSubMenu();
		
		int menuId = parameter("menuId", 0);
		if (menuId <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("无效的菜单ID");
			return ;
		}
		menu.setMenuId(menuId);
		
		String menuName = parameter("menuName", "").trim();
		if (menuName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
			  .setErrorMsg("必须选择菜单名称");
			return ;
		}
		menu.setMenuName(menuName);
		
		String menuSrc = parameter("menuSrc", "").trim();
		if (menuSrc.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("必须填写链接");
			return ;
		}
		menu.setMenuSrc(menuSrc);
		
		int orderWeight = parameter("menuWeight", 0);
		if (orderWeight > 0) {
			menu.setOrderWeight(orderWeight);
		}
		
		try {
			new OaMenuDaoStub().updateSubMenu(menuId, 1500, menu); 
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				  .setErrorMsg("必须填写链接");
				return ;
			}
		}
	}
	
}
