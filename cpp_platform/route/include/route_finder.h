/*
 * created by wileywang@2014-05-22 
 */
 
#ifndef ROUTE_FINDER_WILEY
#define ROUTE_FINDER_WILEY

#include <string>

namespace platform {

extern std::string GetRouteIp(int cmdKey, const std::string& methodName, long long routeKey);
extern void UpdateCallInfo(int cmdKey, const std::string& methodName, const std::string& ip, int callResult);
extern int InitRouteFinder();
}

#endif
