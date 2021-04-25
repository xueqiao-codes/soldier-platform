/*
 * rr_router.h
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#include "router/router.h"

namespace platform {

class RRRouter : public IRouter {
public:
    RRRouter() {}
    virtual ~RRRouter() {}

    virtual void getRoute(const RouteReq& req, RouteResp& resp);
};


} // end namespace platform


