/*
 * k8smod_router.h
 *
 *  Created on: 2018年10月19日
 *      Author: wangli
 */

#ifndef SOURCE_ROUTER_K8SMOD_ROUTER_H_
#define SOURCE_ROUTER_K8SMOD_ROUTER_H_


#include "router/router.h"

namespace platform {

class K8SModRouter : public IRouter {
public:
    K8SModRouter() {}
    virtual ~K8SModRouter() {}

    virtual void getRoute(const RouteReq& req, RouteResp& resp);
};


} // end namespace platform


#endif /* SOURCE_ROUTER_K8SMOD_ROUTER_H_ */
