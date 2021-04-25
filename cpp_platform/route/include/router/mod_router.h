/*
 * mod_router.h
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#ifndef SOURCE_ROUTER_MOD_ROUTER_H_
#define SOURCE_ROUTER_MOD_ROUTER_H_

#include "router/router.h"

namespace platform {

class ModRouter : public IRouter {
public:
    ModRouter() {}
    virtual ~ModRouter() {}

    virtual void getRoute(const RouteReq& req, RouteResp& resp);
};


} // end namespace platform



#endif /* SOURCE_ROUTER_MOD_ROUTER_H_ */
