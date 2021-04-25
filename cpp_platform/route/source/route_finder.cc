#include "route_finder.h"
#include "route_agent.h"
#include "router/router_factory.h"

namespace platform {

static platform::config::CRouteAgent s_oAgent;

__attribute__ ((visibility("default"))) std::string GetRouteIp(int serviceKey
        , const std::string& methodName
        , long long routeKey) {
	const platform::config::IpItem* pszItem = s_oAgent.Query((uint16_t)serviceKey);
//	printf("serviceKey=%d, pszItem=%p\n", serviceKey, pszItem);
	if (pszItem == NULL || pszItem->m_dwIpCount == 0) {
	    return "";
	}

	IRouter* router = RouterFactory::Global().GetRouter(pszItem->m_routeType);
//	printf("routeType=%d, router=%p\n", pszItem->m_routeType, router);
	if (router == NULL) {
	    return "";
	}

	RouteReq req;
	RouteResp resp;
	req.setConfigItems(pszItem)
	   .setServiceKey(serviceKey)
	   .setRouteKey(routeKey);
	router->getRoute(req, resp);

	return resp.getRouteIp();
}

__attribute__ ((visibility("default"))) void UpdateCallInfo(int serviceKey, const std::string& methodName, const std::string& ip, int callResult) {
}

__attribute__ ((visibility("default"))) int InitRouteFinder() {
	return s_oAgent.InitAttach();
}

} // namespace platform
