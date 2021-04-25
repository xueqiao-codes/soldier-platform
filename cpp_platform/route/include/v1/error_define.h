/*
 * error_define.h
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */

#ifndef ERROR_DEFINE_H_XAIRY
#define ERROR_DEFINE_H_XAIRY

#include "v1/comm_define.h"

COMM_NAMESPACE_BEGIN

enum EErrorCode{
	ERROR_Success = 0,                // 成功
	ERROR_Param = 1,                  // 输入参数错误
	ERROR_OpenFile = 2,               // 打开文件错误
	ERROR_ReadFile = 3,               // 读取文件错误
	ERROR_ShmGet = 4,                 // 打开共享内存错误
	ERROR_ShmAt = 5,                  // 挂载共享内存地址失败
	ERROR_NoShm = 6,                  // 共享内存不存在
	ERROR_NotInit = 7,                // 未初始化
	ERROR_AlreadyExists = 8,          // 已经存在
	ERROR_Fork = 9,                   // 创建子进程失败
	ERROR_SetSid = 10,
	ERROR_Format = 11,
	ERROR_Overflow = 12,
	ERROR_NUMS
};
COMM_NAMESPACE_END

#endif
