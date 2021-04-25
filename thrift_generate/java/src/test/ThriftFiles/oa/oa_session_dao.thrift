/**
  * dao for oa user session
  */
namespace java org.soldier.platform.oa.session.dao

include "../comm.thrift"

struct TSession {
	1:optional i32 userId;
	2:optional string sessionKey;
}

service(8) OaSessionDao {
	void 1:updateSession(1:comm.PlatformArgs platformArgs, 2:TSession session) throws (1:comm.ErrorInfo err);
	
	list<TSession> 2:getSession(1:comm.PlatformArgs platformArgs, 2:i32 userId) throws (1:comm.ErrorInfo err);
	
	void 3:deleteSession(1:comm.PlatformArgs platformArgs, 2:i32 userId) throws (1:comm.ErrorInfo err);
}
