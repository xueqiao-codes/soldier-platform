/**
  *
  */
namespace java idmaker.dao

include "comm.thrift"

service(200) IdMaker{
	i64 CreateId(2:comm.PlatformArgs platformArgs, 1:i32 type)throws(1:comm.ErrorInfo err);
}