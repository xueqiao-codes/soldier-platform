/*
 * k8ssvr_router.h
 *
 *  Created on: 2018年10月19日
 *      Author: wangli
 */

#ifndef SOURCE_ROUTER_K8SSVR_ROUTER_H_
#define SOURCE_ROUTER_K8SSVR_ROUTER_H_

#include "router/router.h"

namespace platform {

class K8SSvrRouter : public IRouter {
public:
    K8SSvrRouter() {}
    virtual ~K8SSvrRouter() {}

    virtual void getRoute(const RouteReq& req, RouteResp& resp);
};


} // end namespace platform



#endif /* SOURCE_ROUTER_K8SSVR_ROUTER_H_ */
