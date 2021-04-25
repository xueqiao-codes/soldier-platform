/*
 * comm_define.h
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */

#ifndef COMM_DEFINE_H_XAIRY
#define COMM_DEFINE_H_XAIRY

#define COMM_NAMESPACE router::v1
#define COMM_NAMESPACE_BEGIN namespace router{ namespace v1{
#define COMM_NAMESPACE_END }}

#include <unistd.h>
#include <sys/types.h>
#include <stdint.h>
#include <stdio.h>

#define ERROR_OUTPUT(format, args...)	do{ \
												fprintf(stderr, "[%s:%d]", __FILE__, __LINE__); \
												fprintf(stderr, format, ##args); \
												fprintf(stderr, "\n"); \
											}while(0)

#define RETURN_IF_ERROR(func )  do{ uint32_t dwFuncRetB = func; if(dwFuncRetB) return dwFuncRetB;}while(0);

#endif
