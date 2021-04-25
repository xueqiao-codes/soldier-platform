/*
 * k8ssvr_router.cc
 *
 *  Created on: 2018年10月19日
 *      Author: wangli
 */

#include "router/k8ssvr_router.h"

#include <sstream>

namespace platform {

void K8SSvrRouter::getRoute(const RouteReq& req, RouteResp& resp) {
    std::stringstream ss;
    ss << "cmd" << req.getServiceKey() << ".soldier-service-alias.svc";
    resp.setRouteIp(ss.str());
}

} // end namespace platform


