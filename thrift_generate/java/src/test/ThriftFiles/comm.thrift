/*
 * 平台级别公用IDL, 不允许随意变更
 */
namespace java org.soldier.platform.svr_platform.comm

exception ErrorInfo{
	1:required i32 errorCode;
	2:required string errorMsg;
}

/**
  *  平台级参数，方便以后进行治理和差错
  */
struct PlatformArgs{
	1:optional string sourceDesc;
	2:optional i32 sourceIpV4;
	3:optional string remoteAddress;
	4:optional i32 remotePort;
}
