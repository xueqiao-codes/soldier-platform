/*
 * router.h
 *
 *  Created on: 2017年8月3日
 *      Author: wileywang
 */

#ifndef SOURCE_ROUTER_H_
#define SOURCE_ROUTER_H_

#include "route_agent.h"

namespace platform {

class RouteReq {
public:
    RouteReq()
        : service_key_(-1)
          , config_items_(NULL)
          , route_key_(0) {}
    ~RouteReq() {}

    inline RouteReq& setServiceKey(int service_key) {
        this->service_key_ = service_key;
        return *this;
    }

    inline const int& getServiceKey() const {
        return service_key_;
    }

    inline RouteReq& setConfigItems(const platform::config::IpItem* config_items) {
        this->config_items_ = config_items;
        return *this;
    }

    inline const platform::config::IpItem* getConfigItems() const {
        return this->config_items_;
    }

    inline RouteReq& setRouteKey(long long route_key) {
        this->route_key_ = route_key;
        return *this;
    }

    inline const long long& getRouteKey() const {
        return route_key_;
    }

private:
    int service_key_;
    const platform::config::IpItem* config_items_;
    long long route_key_;
};

class RouteResp {
public:
    RouteResp() {}
    ~RouteResp() {}

    void setRouteIp(uint32_t ipv4);
    inline void setRouteIp(const std::string& ip_addr) {
        this->route_ip_ = ip_addr;
    }

    const std::string& getRouteIp() { return route_ip_; }
private:
    std::string route_ip_;
};

class IRouter {
public:
    virtual ~IRouter() {}

    virtual void getRoute(const RouteReq& req, RouteResp& resp) = 0;

protected:
    IRouter() {}
};



} // end namespace platform





#endif /* SOURCE_ROUTER_H_ */
