/*
 * k8smod_router.cc
 *
 *  Created on: 2018年10月19日
 *      Author: wangli
 */
#include "router/k8smod_router.h"

#include <boost/lexical_cast.hpp>
#include <errno.h>
#include <stdio.h>
#include <sstream>
#include <netdb.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#include "base/string_util.h"

namespace platform {

static void fillRouteResp(struct hostent* hptr, const RouteReq& req, RouteResp& resp) {
    std::string service_domain = hptr->h_name;

    size_t service_name_pos = service_domain.find_first_of('.');
    if (service_name_pos == std::string::npos) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, no service name founded!\n", req.getServiceKey());
        return ;
    }

    std::string service_name = service_domain.substr(0, service_name_pos);

    size_t statefulset_name_pos = service_name.find_last_of('-');
    if (statefulset_name_pos == std::string::npos || statefulset_name_pos == service_name.length() - 1) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, service_name=%s failed to found statefulset_name\n"
                , req.getServiceKey(), service_name.c_str());
        return ;
    }

    if (0 != strcmp("service", service_name.c_str() + statefulset_name_pos + 1)) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, service_name=%s failed to found statefulset_name\n"
                , req.getServiceKey(), service_name.c_str());
        return ;
    }

    std::string statefulset_name = service_name.substr(0, statefulset_name_pos);

    // 从别名中获取副本数的配置
    std::string service_with_replicas_domain;
    for(char** pptr = hptr->h_aliases; *pptr != NULL; pptr++) {
        std::string compare_alias = *pptr;
        if (soldier::base::StringUtil::startsWith(compare_alias, service_name)) {
            if (!service_with_replicas_domain.empty()) {
                fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, service_name=%s has multi alias for replicas\n"
                        , req.getServiceKey(), service_name.c_str());
                return ;
            }
            service_with_replicas_domain = compare_alias;
        }
    }

    // 提取副本数
    if (service_with_replicas_domain.length() < service_name.length() + 2
            || '-' != service_with_replicas_domain[service_name.length()]) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, service_with_replicas_domain=%s is invalid for service_name=%s\n"
              , req.getServiceKey(), service_with_replicas_domain.c_str(), service_name.c_str());
        return ;
    }

    std::string replicas;
    size_t pos = service_with_replicas_domain.find_first_of(".");
    if (pos == std::string::npos || service_name.length() + 1 >= pos) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, service_with_replicas_domain=%s failed to found replicas section\n"
                      , req.getServiceKey(), service_with_replicas_domain.c_str());
        return ;
    } else {
        replicas = service_with_replicas_domain.substr(service_name.length() + 1
                , (pos - service_name.length() - 1));
    }

    int replica_num = -1;
    if (!soldier::base::StringUtil::boostCast(replicas, replica_num) || replica_num <= 0) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d"
                        ", service_with_replicas_domain=%s is invalid for service_name=%s, replica_num=%d\n"
                        , req.getServiceKey(), service_with_replicas_domain.c_str()
                        , service_name.c_str(), replica_num);
        return ;
    }

    std::stringstream rs;
    rs << statefulset_name << "-" << req.getRouteKey()%replica_num
       << "." << service_domain;
    resp.setRouteIp(rs.str());
}

void K8SModRouter::getRoute(const RouteReq& req, RouteResp& resp) {
    std::stringstream hostname;
    hostname << "cmd" << req.getServiceKey() << ".soldier-service-alias.svc";

    // 获取域名的信息，从中提取后端的地址列表
    struct hostent he, *hptr;
    int herr = 0, ret = 0, bufsz = 512;

    char *buff = NULL;
    do {
        char *new_buff = (char *)realloc(buff, bufsz);
        if (new_buff == NULL) {
            fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, no memory!!! \n", req.getServiceKey());
            if (buff != NULL) {
                free(buff);
            }
            return ;
        }

        buff = new_buff;
        ret = gethostbyname_r(hostname.str().c_str(), &he, buff, bufsz, &hptr, &herr);
        bufsz *= 2;
    } while (ret == ERANGE);

    if (ret != 0) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, gethostname_r ret=%d, herr=%d\n"
                , req.getServiceKey(), ret, herr);
        free(buff);
        return ;
    }

    if (hptr == NULL || hptr->h_name == NULL || hptr->h_addr_list == NULL) {
        fprintf(stderr, "K8SModRouter getRoute failed for serviceKey=%d, no record found for %s\n"
                , req.getServiceKey()
                , hostname.str().c_str());
        free(buff);
        return ;
    }

    fillRouteResp(hptr, req, resp);
    free(buff);
    return ;
}

} // end namespace platform




