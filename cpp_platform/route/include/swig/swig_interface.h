/*
 * swig_interface.h
 *
 *  Created on: 2017年1月24日
 *      Author: Administrator
 */

#ifndef SWIG_INTERFACE_H_
#define SWIG_INTERFACE_H_


#ifdef __cplusplus
extern "C" {
#endif


extern const char* GetRouteIp(int cmdKey, const char* methodName, long long routeKey);
extern void UpdateCallInfo(int cmdKey, const char* methodName, const char* service_ip, int callResult);
extern int InitRouteFinder();


#ifdef __cplusplus
}
#endif



#endif /* SWIG_INTERFACE_H_ */
