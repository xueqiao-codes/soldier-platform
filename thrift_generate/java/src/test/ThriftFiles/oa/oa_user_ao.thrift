/**
  * oa user ao
  */
namespace java org.soldier.platform.oa.user.ao

include "../comm.thrift"
include "oa_user.thrift"

struct LoginResult {
	1:required i32 userId;
	2:required string secretKey;
	3:required string userName;
}

enum OaUserErrorCode {
	UserNameOrPasswordError = 60001,
	UserNameAlreadyExist= 60002,
	UserNotExist = 60003
}

service(9) OaUserAo {
	LoginResult 1:login(1:comm.PlatformArgs platformArgs, 2:string userName, 3:string passwordMd5)
		throws (1:comm.ErrorInfo err);
		
	i32 2:registerUser(1:comm.PlatformArgs platformArgs, 2:oa_user.OaUser user, 3:string operationUserName)
		throws (1:comm.ErrorInfo err);
	
	bool 3:checkSession(1:comm.PlatformArgs platformArgs, 2:i32 userId, 3:string userName, 4:string secretKey)
		throws (1:comm.ErrorInfo err);
		
	void 4:updateUser(1:comm.PlatformArgs platformArgs, 2:oa_user.OaUser user)
		throws (1:comm.ErrorInfo err);
	
	void 5:deleteUser(1:comm.PlatformArgs platformArgs, 2:i32 userId, 3:string operationUserName)
		throws (1:comm.ErrorInfo err);
		
	void 6:logout(1:comm.PlatformArgs platformArgs, 2:i32 userId) throws (1:comm.ErrorInfo err);
	
	list<oa_user.OaUser> 7:queryUser(1:comm.PlatformArgs platformArgs, 2:oa_user.QueryUserOption option) throws (1:comm.ErrorInfo err);
	oa_user.OaUserPage 8:queryUserByPage(1:comm.PlatformArgs platformArgs, 2:oa_user.QueryUserOption option, 3:i32 pageIndex, 4:i32 pageSize)
		throws (1:comm.ErrorInfo err);
}
