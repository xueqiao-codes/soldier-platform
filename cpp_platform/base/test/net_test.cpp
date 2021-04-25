/*
 * net_test.cpp
 *
 *  Created on: 2017年3月26日
 *      Author: 44385
 */
#include "base/net_helper.h"
#include "base/os_helper.h"
#include <iostream>
using namespace soldier::base;

int main() {

    std::vector<InetInterface> interfaces;
    getAllInetInterface(interfaces);

    for (auto& interface : interfaces) {
        std::cout << interface.name << ":" << interface.ip_addr
                  << ", broadcast=" << interface.broadcast_addr
                  << ", netmask=" << interface.subnet_mask
                  << std::endl;
    }

    std::cout << "hostname is " << getHostName() << std::endl;
    std::cout << "current process name is " << OSHelper::getProcessName() << std::endl;

    return 0;
}

