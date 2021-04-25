/*
 * daemon_tool.h
 *
 *  Created on: 2012-5-17
 *      Author: Xairy
 */

#ifndef DAEMON_TOOL_H_XAIRY
#define DAEMON_TOOL_H_XAIRY

#include "v1/comm_define.h"

COMM_NAMESPACE_BEGIN

extern int init_daemon();
extern int lock_process(const char* pszLockFile);

COMM_NAMESPACE_END


#endif
