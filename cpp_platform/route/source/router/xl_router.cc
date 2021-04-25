/*
 * xl_router.cc
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */
#include "router/xl_router.h"

#include <sstream>
#include <string>
#include <vector>
#include <limits.h>
#include <mutex>
#include "base/string_util.h"
#include "router/rr_router.h"
#include "router/counter_items.h"
#include "qconf.h"

namespace platform {

static std::mutex s_qconf_lock;
static bool s_qconf_inited = false;

XLRouter::XLRouter() {
    failed_router_ = new RRRouter();
}

XLRouter::~XLRouter() {
    delete failed_router_;
    failed_router_ = NULL;
}

void XLRouter::getRoute(const RouteReq& req, RouteResp& resp) {
    if (QCONF_OK != findRouteByQconf(req, resp)) {
        failed_router_->getRoute(req, resp);
    }
}

int XLRouter::findRouteByQconf(const RouteReq& req, RouteResp& resp) {
    int ret = QCONF_OK;

    if (!s_qconf_inited) {
        std::unique_lock<std::mutex> qconf_auto_lock(s_qconf_lock);
        if (!s_qconf_inited) {
            ret = qconf_init();
            if (ret == QCONF_OK) {
                s_qconf_inited = true;
            }
        }
    }
    if (ret != QCONF_OK) {
        return ret;
    }

    string_vector_t service_host_keys;
    ret = init_string_vector(&service_host_keys);
    if (ret != QCONF_OK) {
        return ret;
    }

    std::stringstream sspath;
    sspath << "/route/services/";
    sspath <<  req.getServiceKey();

    ret = qconf_aget_batch_keys(sspath.str().c_str(), &service_host_keys, NULL);
    if (ret != QCONF_OK) {
        destroy_string_vector(&service_host_keys);
        return ret;
    }

    if (service_host_keys.count <= 0) {
        return -1;
    }

    int miniumLoadValue = INT_MAX;
    std::vector<std::string> minimumLoadMachines;
    for (int index = 0; index < service_host_keys.count; ++index) {
        std::vector<std::string> splits;
        soldier::base::StringUtil::tokenize(std::string(service_host_keys.data[index]), splits, "$", true);
        if (splits.size() != 2) {
            continue;
        }

        if (splits[1] == "FAILED" || splits[1] == "TIMEOUT") {
            continue;
        }

        int value = INT_MAX;
        if (!soldier::base::StringUtil::boostCast(splits[1], value)) {
            continue;
        }

        if (value > miniumLoadValue) {
            continue;
        }

        if (value == miniumLoadValue) {
            minimumLoadMachines.push_back(splits[0]);
            continue;
        }

        miniumLoadValue = value;
        minimumLoadMachines.clear();
        minimumLoadMachines.push_back(splits[0]);
    }

    destroy_string_vector(&service_host_keys);
    if (minimumLoadMachines.empty()) {
        return -1;
    }

    uint16_t value = __sync_add_and_fetch (&(s_oCounterItems.counter[req.getServiceKey()]), 1);
    resp.setRouteIp(minimumLoadMachines[value%(minimumLoadMachines.size())]);
    return QCONF_OK;
}

} // end namespace platform

