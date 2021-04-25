/**
  *  dao for dal_set
  */
namespace java org.soldier.platform.dal_set.dao

include "../comm.thrift"

struct DalSetHost {
	1:required string name;
	2:optional string domain;
	3:optional i32 port;
	4:optional string desc;
	5:optional i32 createTimestamp;
	6:optional i32 lastmodifyTimestamp; 
}

struct DalSetHostList {
	1:required i32 totalNum;
	2:required list<DalSetHost> resultList;
}

struct QueryDalSetHostOption {
	1:optional string name;
	
	/**
	  * domain and port are always combination
	  */
	2:optional string domain;
	3:optional i32 port;
	
	10:optional string desc;
}

struct DalSetUser {
	1:required string key;
	2:optional string name;
	3:optional string password;
	4:optional string desc;
	5:optional i32 createTimestamp;
	6:optional i32 lastmodifyTimestamp;
	7:optional string plainPassword;
}

struct DalSetUserList {
	1:required i32 totalNum;
	2:required list<DalSetUser> resultList;
}

struct QueryDalSetUserOption {
	1:optional string key;
	2:optional string name;
	3:optional string desc;
}

struct DalSetTable {
	1:required string prefixName;
	2:optional i32 sliceNum;
	3:optional bool fillZero;
	4:optional string desc;
	5:optional i32 createTimestamp;
	6:optional i32 lastmodifyTimestamp;
}

struct DalSetTableList {
	1:required i32 totalNum;
	2:required list<DalSetTable> resultList;
}

struct QueryDalSetTableOption {
	1:optional string  prefixName;
	2:optional string desc;
}

enum DbType {
	Mysql = 1
}

struct DalSetRole {
	1:required string roleName;
	2:optional string dbName;
	3:optional DbType dbType;
	4:optional string desc;
	5:optional i32 createTimestamp;
	6:optional i32 lastmodifyTimestamp;
}

struct DalSetRoleList {
	1:required i32 totalNum;
	2:required list<DalSetRole> resultList;
}

struct QueryDalSetRoleOption {
	1:optional string roleName;
	2:optional string dbName;
	4:optional string desc;
}

struct RoleTableRelation {
	1:required string roleName;
	2:required string tablePrefixName;
	3:optional i32 createTimestamp;
}

struct QueryRoleTableRelationOption {
	1:optional string roleName;
	2:optional string tablePrefixName;
}

struct RoleTableRelationList {
	1:required i32 totalNum;
	2:required list<RoleTableRelation> resultList;
}

enum TypeInSet {
	Master = 1,
	Slave = 2
}

struct RoleSetRelation {
	1:required string hostName;
	2:required i32 setIndex;
	3:optional TypeInSet typeInSet;
	4:optional i32 weight;
	5:required string roleName;
	6:optional i32 createTimestamp;
	7:optional i32 lastmodifyTimestamp;
}

struct RoleRelatedOption {
	1:required string roleName;
	2:optional i32 setIndex;
}

struct QueryRoleSetRelationOption {
	1:optional RoleRelatedOption roleRelatedOption;
	3:optional string hostName;
}

struct RoleSetRelationList{
	1:required i32 totalNum;
	2:required list<RoleSetRelation> resultList;
}

enum ServiceRelatedType {
	NoType = 1,
	Master = 2,
	Slave = 3
}

struct RoleServiceRelation {
	1:required i32 serviceKey;
	2:required string interfaceName;
	3:required string roleName;
	4:optional string userKey;
	5:optional ServiceRelatedType relatedType;
	6:optional i32 createTimestamp;
	7:optional i32 lastmodifyTimestamp;
}

struct RoleServiceRelationList {
	1:required i32 totalNum;
	2:required list<RoleServiceRelation> resultList;
}

struct QueryRoleServiceRelationOption {
	1:optional i32 serviceKey;
	2:optional string interfaceName;
	3:optional string roleName;
	4:optional string userKey;
}

service(18) DalSetDao {
	DalSetHostList 1:queryDalSetHosts(1:comm.PlatformArgs platformArgs, 
		2:i32 pageIndex, 3:i32 pageSize, 4:QueryDalSetHostOption option)
		throws (1:comm.ErrorInfo err);	
	void 2:addDalSetHost(1:comm.PlatformArgs platformArgs, 2:DalSetHost host)
		throws (1:comm.ErrorInfo err);
	void 3:updateDalSetHost(1:comm.PlatformArgs platformArgs, 2:DalSetHost host)
		throws (1:comm.ErrorInfo err);
	void 4:deleteDalSetHost(1:comm.PlatformArgs platformArgs, 2:string hostKey)
		throws (1:comm.ErrorInfo err);
		
	DalSetUserList 5:queryDalSetUsers(1:comm.PlatformArgs platformArgs,
		2:i32 pageIndex, 3:i32 pageSize, 4:QueryDalSetUserOption option)
		throws (1:comm.ErrorInfo err);
	void 6:addDalSetUser(1:comm.PlatformArgs platformArgs, 2:DalSetUser user)
		throws (1:comm.ErrorInfo err);
	void 7:updateDalSetUser(1:comm.PlatformArgs platformArgs, 2:DalSetUser user)
		throws (1:comm.ErrorInfo err);	
	void 8:deleteDalSetUser(1:comm.PlatformArgs platformArgs, 2:string userKey)
		throws (1:comm.ErrorInfo err);
		
	DalSetTableList 9:queryDalSetTables(1:comm.PlatformArgs platformArgs,
		2:i32 pageIndex, 3:i32 pageSize, 4:QueryDalSetTableOption option)
		throws (1:comm.ErrorInfo err);
	void 10:addDalSetTable(1:comm.PlatformArgs platformArgs, 2:DalSetTable table)
		throws (1:comm.ErrorInfo err);
	void 11:updateDalSetTable(1:comm.PlatformArgs platformArgs, 2:DalSetTable table)
		throws (1:comm.ErrorInfo err);
	void 12:deleteDalSetTable(1:comm.PlatformArgs platformArgs, 2:string tablePrefixName)
		throws (1:comm.ErrorInfo err);
		
	DalSetRoleList 13:queryDalSetRoles(1:comm.PlatformArgs platformArgs, 2:i32 pageIndex,
		3:i32 pageSize, 4:QueryDalSetRoleOption option)
		throws (1:comm.ErrorInfo err);
	void 14:addDalSetRole(1:comm.PlatformArgs platformArgs, 2:DalSetRole role) throws (1:comm.ErrorInfo err);
	void 15:updateDalSetRole(1:comm.PlatformArgs platformArgs, 2:DalSetRole role) throws (1:comm.ErrorInfo err);
	void 16:deleteDalSetRole(1:comm.PlatformArgs platformArgs, 2:string roleName) throws (1:comm.ErrorInfo err);
	
	void 17:addTableRoleRelation(1:comm.PlatformArgs platformArgs, 2:RoleTableRelation relation) throws(1:comm.ErrorInfo err);
	void 18:deleteTableRoleRelation(1:comm.PlatformArgs platformArgs, 2:RoleTableRelation relation) throws(1:comm.ErrorInfo err);
	RoleTableRelationList 19:queryTableRoleRelations(1:comm.PlatformArgs platformArgs, 2:i32 pageIndex, 3:i32 pageSize, 
		4:QueryRoleTableRelationOption option) throws (1:comm.ErrorInfo err);
		
	RoleSetRelationList 20:queryRoleSetRelations(1:comm.PlatformArgs platformArgs,
		2:i32 pageIndex, 3:i32 pageSize, 4:QueryRoleSetRelationOption option) throws (1:comm.ErrorInfo err);
	void 21:addRoleSetRelation(1:comm.PlatformArgs platformArgs, 2:RoleSetRelation relation) throws (1:comm.ErrorInfo err);
	void 22:deleteRoleSetRelation(1:comm.PlatformArgs platformArgs, 2:RoleSetRelation relation) throws (1:comm.ErrorInfo err);
	void 23:updateRoleSetRelation(1:comm.PlatformArgs platformArgs, 2:RoleSetRelation relation) throws (1:comm.ErrorInfo err);
	
	RoleServiceRelationList 24:queryRoleServiceRelations(1:comm.PlatformArgs platformArgs,
		2:i32 pageIndex, 3:i32 pageSize, 4:QueryRoleServiceRelationOption option) throws (1:comm.ErrorInfo err);
	void 25:addRoleServiceRelation(1:comm.PlatformArgs platformArgs, 2:RoleServiceRelation relation) throws (1:comm.ErrorInfo err);
	void 26:updateRoleServiceRelation(1:comm.PlatformArgs platformArgs, 2:RoleServiceRelation relation) throws (1:comm.ErrorInfo err);
	void 27:deleteRoleServiceRelation(1:comm.PlatformArgs platformArgs, 2:RoleServiceRelation relation) throws(1:comm.ErrorInfo err);
	
	void 28:updateDalSetXml(1:comm.PlatformArgs platformArgs, 2:string xml) throws (1:comm.ErrorInfo err);
	i32 29:getLastVersion(1:comm.PlatformArgs platformArgs) throws (1:comm.ErrorInfo err);
	string 30:getLastXml(1:comm.PlatformArgs platformArgs) throws (1:comm.ErrorInfo err);
}
 
