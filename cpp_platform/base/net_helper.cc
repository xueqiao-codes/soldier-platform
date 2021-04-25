/*
 * net_helper.cc
 *
 *  Created on: 2017年3月26日
 *      Author: 44385
 */

#include "base/net_helper.h"

#include <cstring>
#include <stdexcept>
#include <stdio.h>
#include <unistd.h>
#include <ifaddrs.h>
#include <arpa/inet.h>


namespace soldier {
namespace base {

void getAllInetInterface(std::vector<InetInterface>& interfaces) {
    struct sockaddr_in *sin = NULL;
    struct ifaddrs *ifa = NULL, *ifList = NULL;

    interfaces.clear();
    if (getifaddrs(&ifList) < 0) return ;

    for (ifa = ifList; ifa != NULL; ifa = ifa->ifa_next) {
        if(ifa->ifa_addr->sa_family == AF_INET) {
            InetInterface interface;
            interface.name = ifa->ifa_name;

            sin = (struct sockaddr_in *)ifa->ifa_addr;
            interface.ip_addr = inet_ntoa(sin->sin_addr);

            sin = (struct sockaddr_in *)ifa->ifa_dstaddr;
            interface.broadcast_addr = inet_ntoa(sin->sin_addr);

            sin = (struct sockaddr_in *)ifa->ifa_netmask;
            interface.subnet_mask = inet_ntoa(sin->sin_addr);

            interfaces.push_back(interface);
        }
    }
}

std::string getHostName() {
    char buffer[256];
    int result = gethostname(buffer, sizeof(buffer)/sizeof(buffer[0]));
    if (result != 0) {
        throw std::runtime_error(std::string("can not get host name, ") + std::strerror(errno));
    }
    return std::string(buffer);
}

} // end namespace base
} // end namespace soldier
