/*
 * swig_interface.c
 *
 *  Created on: 2017年1月24日
 *      Author: Administrator
 */

#include "swig/swig_interface.h"

#include <stdio.h>
#include "route_finder.h"

extern "C" {

const char* GetRouteIp(int cmdKey, const char* methodName, long long routeKey) {
	//printf("GetRouteIp(%d,%s,%lld)\n", cmdKey, methodName, routeKey);
	return platform::GetRouteIp(cmdKey, methodName, routeKey).c_str();
}

void UpdateCallInfo(int cmdKey, const char* methodName, const char* serviceIp, int callResult) {
	//printf("UpdateCallInfo(%d, %s, %s, %d)\n", cmdKey, methodName, serviceIp, callResult);
	return platform::UpdateCallInfo(cmdKey, methodName, serviceIp, callResult);
}

int InitRouteFinder() {
	return platform::InitRouteFinder();
}

}

