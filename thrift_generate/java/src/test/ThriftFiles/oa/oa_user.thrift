/**
  * information about oa user
  */
namespace java org.soldier.platform.oa.user

struct OaUser {
	1:optional i32 userId;
	2:optional string userName;
	3:optional string userPassword;
	4:optional string userNickName;
	5:optional string bindToyEmail;
	6:optional i32 bindToyUserId;
	20:optional i32 createTimestamp;
	21:optional i32 lastmofiyTimestamp;
}

struct QueryUserOption {
	1:optional i32 userId;
	2:optional string userName;
}

struct OaUserPage {
	1:required i32 totalCount;
	2:required list<OaUser> pageRecords;
}

const i32 MAX_USER_NAME_LENGTH=16;
const i32 MAX_PASSWORD_LENGTH=16;
const i32 MAX_NICK_NAME_LENGTH=64;
