/*
 * router_factory.h
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#ifndef SOURCE_ROUTER_ROUTER_FACTORY_H_
#define SOURCE_ROUTER_ROUTER_FACTORY_H_

#include "router/router.h"
#include <map>

namespace platform {

class RouterFactory {
public:
    ~RouterFactory();

    static RouterFactory& Global();
    IRouter* GetRouter(int route_type);

private:
    RouterFactory();

    static RouterFactory sInstance;

    std::map<int, IRouter*> router_map_;
};


} // end namespace platform



#endif /* SOURCE_ROUTER_ROUTER_FACTORY_H_ */
