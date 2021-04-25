/*
 * rr_router.cc
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#include "router/rr_router.h"

#include "router/counter_items.h"
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <cstdlib>

namespace platform {


void RRRouter::getRoute(const RouteReq& req, RouteResp& resp) {
    uint16_t value = __sync_add_and_fetch (&(s_oCounterItems.counter[req.getServiceKey()]), 1);
    resp.setRouteIp(req.getConfigItems()->m_dwIpAddr[value % req.getConfigItems()->m_dwIpCount]);
}


} // end namespace platform


