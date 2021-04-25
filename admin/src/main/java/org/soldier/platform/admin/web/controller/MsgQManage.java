package org.soldier.platform.admin.web.controller;

public class MsgQManage extends WebAuthController {
	public void show() throws Exception {
		render("msgq/MsgQManage.html");
	}
	
	public void showMsgQMenus() throws Exception {
		render("msgq/MsgQMenus.html");
	}
}
