/**
  * dao for oa menu
  */
namespace java org.soldier.platform.oa.menu.dao

include "../comm.thrift"

struct TSystemMenu {
	1:optional i32 systemMenuId;
	2:optional string systemMenuName;
	3:optional i32 orderWeight;
	10:optional i32 createTimestamp;
	11:optional i32 lastmodifyTimestamp;
}

struct TSubMenu {
	1:optional i32 menuId;
	2:optional string menuName;
	3:optional i32 systemMenuId;
	4:optional string menuSrc;
	5:optional i32 orderWeight;
	10:optional i32 createTimestamp;
	11:optional i32 lastmodifyTimestamp;
}

struct QuerySystemMenuOption {
	1:optional i32 systemMenuId;
	2:optional string systemMenuName;
}

struct QuerySubMenuOption {
	1:required i32 systemMenuId;
	2:optional i32 menuId;
	3:optional string menuName;
}

service(7) OaMenuDao {
	list<TSystemMenu> 1:getSystemMenus(1:comm.PlatformArgs platformArgs, 2:QuerySystemMenuOption option) throws (1:comm.ErrorInfo err);
	list<TSubMenu> 2:getSubMenus(1:comm.PlatformArgs platformArgs, 2:QuerySubMenuOption option) throws (1:comm.ErrorInfo err);
	
	i32 3:addSystemMenu(1:comm.PlatformArgs platformArgs, 2:TSystemMenu menu) throws (1:comm.ErrorInfo err);
	void 4:deleteSystemMenu(1:comm.PlatformArgs platformArgs, 2:i32 systemMenuId) throws (1:comm.ErrorInfo err);
	
	i32 5:addSubMenu(1:comm.PlatformArgs platformArgs, 2:TSubMenu menu) throws (1:comm.ErrorInfo err);
	void 6:deleteSubMenu(1:comm.PlatformArgs platformArgs 2:i32 menuId) throws (1:comm.ErrorInfo err);
	void 7:updateSubMenu(1:comm.PlatformArgs platformArgs, 2:TSubMenu menu) throws (1:comm.ErrorInfo err);
	
	void 8:updateSystemMenu(1:comm.PlatformArgs platformArgs, 2:TSystemMenu menu) throws (1:comm.ErrorInfo err);
}
