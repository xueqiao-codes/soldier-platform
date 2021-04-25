/*
 * route_test.cc
 *
 *  Created on: 2017年8月24日
 *      Author: wileywang
 */

#include <stdio.h>
#include <stdlib.h>
#include "route_finder.h"


int main(int argc, char* argv[]) {

    if (0 != platform::InitRouteFinder()) {
        printf("init route finder failed");
        return -1;
    }

    for (int64_t index = 0; ; ++index) {
        int serviceKey = rand() % 1000;
        if (index % 1000 == 0) {
           serviceKey = 3;
        }
        std::string ip = platform::GetRouteIp(serviceKey, "", 0);
        if (index % 1000 == 0) {
            printf("count %lld, serviceKey=%d, ip=%s\n", index, serviceKey, ip.c_str());
        }
    }

    return 0;
}

