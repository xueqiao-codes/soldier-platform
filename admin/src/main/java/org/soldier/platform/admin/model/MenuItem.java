package org.soldier.platform.admin.model;

/**
 * 菜单项
 * @author Xairy
 */
public class MenuItem {
	private String menuName ;  // 菜单名称
	private String menuSrc ;   // 菜单链接
	
	public MenuItem(final String name, final String src){
		this.menuName = name;
		this.menuSrc = src;
	}
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuSrc() {
		return menuSrc;
	}
	public void setMenuSrc(String menuSrc) {
		this.menuSrc = menuSrc;
	}
	
	
}
