/**
  *  dao for oa user
  */
namespace java org.soldier.platform.oa.user.dao

include "../comm.thrift"
include "oa_user.thrift"

service(10) OaUserDao {
	oa_user.OaUserPage 1:queryUserByPage(1:comm.PlatformArgs platformArgs,
		2:oa_user.QueryUserOption option, 3:i32 pageIndex, 4:i32 pageSize)
		throws (1:comm.ErrorInfo err);
	
	i32 2:addUser(1:comm.PlatformArgs platformArgs, 2:oa_user.OaUser user) throws (1:comm.ErrorInfo err);
	
	void 3:updateUser(1:comm.PlatformArgs platformArgs, 2:oa_user.OaUser user) throws (1:comm.ErrorInfo err);
	
	void 4:deleteUser(1:comm.PlatformArgs platformArgs, 2:i32 userId) throws (1:comm.ErrorInfo err);
	
	list<oa_user.OaUser> 5:queryUser(1:comm.PlatformArgs platformArgs, 2:oa_user.QueryUserOption option)
		throws (1:comm.ErrorInfo err);
}
 
