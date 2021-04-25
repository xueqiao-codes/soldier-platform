/**
  * file storage information dao
  */
namespace java org.soldier.platform.file_storage_info.dao

include "../comm.thrift"

enum AccessAttribute {
	PublicRead,
	PrivateRead
}

struct StorageInfo {
	1:required string storageKey;
	2:optional AccessAttribute accessAttribute; 
	3:optional string domain; 
	4:optional string desc;
	5:optional i32 createTimestamp;
	6:optional i32 lastmodifyTimestamp;
}

struct StorageInfoList {
	1:required i32 totalNum;
	2:list<StorageInfo> resultList;
}

struct QueryStorageInfoListOption {
	1:optional string storageKey;
	2:optional string desc;
}

service(16) FileStorageInfoDao {
	void 1:addStorage(1:comm.PlatformArgs platformArgs, 2:StorageInfo storageInfo)
		throws (1:comm.ErrorInfo err);
	
	StorageInfoList 2:queryStorageList(1:comm.PlatformArgs platformArgs, 
		2:i32 pageIndex, 3:i32 pageSize, 4:QueryStorageInfoListOption option)
		throws (1:comm.ErrorInfo err);
		
	void 3:deleteStorage(1:comm.PlatformArgs platformArgs, 2:string storageKey) throws (1:comm.ErrorInfo err);
	
	void 4:updateStorage(1:comm.PlatformArgs platformArgs, 2:StorageInfo storageInfo) throws (1:comm.ErrorInfo err);
}

