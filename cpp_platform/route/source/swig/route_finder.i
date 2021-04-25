%module route_finder

%{
#include "swig/swig_interface.h"
%}

extern int InitRouteFinder();
extern void UpdateCallInfo(int cmdKey, const char* methodName, const char* service_ip, int callResult);
extern const char* GetRouteIp(int cmdKey, const char* methodName, long long routeKey);
