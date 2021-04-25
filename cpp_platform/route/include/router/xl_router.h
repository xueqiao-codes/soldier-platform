/*
 * xl_router.h
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#ifndef SOURCE_XL_ROUTER_H_
#define SOURCE_XL_ROUTER_H_

#include "router/router.h"

namespace platform {

class XLRouter : public IRouter {
public:
    XLRouter();
    ~XLRouter();

    virtual void getRoute(const RouteReq& req, RouteResp& resp);

private:
    int findRouteByQconf(const RouteReq& req, RouteResp& resp);

    IRouter* failed_router_;
};



} // end namespace platform




#endif /* SOURCE_XL_ROUTER_H_ */
