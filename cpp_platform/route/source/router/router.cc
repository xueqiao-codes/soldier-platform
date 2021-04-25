/*
 * router.cc
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#include "router/router.h"

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

namespace platform {

void RouteResp::setRouteIp(uint32_t ipv4) {
    char buffer[20] = "";

    struct in_addr addr;
    addr.s_addr = ipv4;
    inet_ntop(AF_INET, (void *)&addr, buffer, 16);

    route_ip_ = std::string(buffer);
}


} // end namespace platform


