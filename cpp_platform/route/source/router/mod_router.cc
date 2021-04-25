/*
 * mod_router.cc
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */
#include "router/mod_router.h"
#include <stdlib.h>

namespace platform {

void ModRouter::getRoute(const RouteReq& req, RouteResp& resp) {
    long long routeKey = req.getRouteKey();
    if (routeKey < 0) {
        routeKey = -routeKey;
    }

    resp.setRouteIp(
         req.getConfigItems()->m_dwIpAddr[routeKey % req.getConfigItems()->m_dwIpCount]);
}


} // end namespace platform



