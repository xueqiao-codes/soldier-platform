/*
 * router_factory.cc
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */
#include "router/router_factory.h"

#include "router/counter_items.h"
#include "route_comms.h"
#include "router/mod_router.h"
#include "router/rr_router.h"
#include "router/xl_router.h"
#include "router/k8ssvr_router.h"
#include "router/k8smod_router.h"

namespace platform {

CounterItems s_oCounterItems;
RouterFactory RouterFactory::sInstance;

RouterFactory& RouterFactory::Global() {
    return sInstance;
}

RouterFactory::RouterFactory() {
    router_map_[ROUTE_BY_MODE] = new ModRouter();
    router_map_[ROUTE_BY_RR] = new RRRouter();
    router_map_[ROUTE_BY_XL] = new XLRouter();
    router_map_[ROUTE_BY_K8SSvr] = new K8SSvrRouter();
    router_map_[ROUTE_BY_K8SMod] = new K8SModRouter();
}

RouterFactory::~RouterFactory() {
    for(std::map<int, IRouter*>::iterator it = router_map_.begin()
            ; it != router_map_.end(); ++it) {
        if (it->second != NULL) {
            delete it->second;
        }
    }
    router_map_.clear();
}

IRouter* RouterFactory::GetRouter(int route_type) {
    std::map<int, IRouter*>::iterator it  = router_map_.find(route_type);
    if (it == router_map_.end()) {
        return NULL;
    }
    return it->second;
}

} // end namespace platform




