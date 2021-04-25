/*
 * net_helper.h
 *
 *  Created on: 2017年3月26日
 *      Author: 44385
 */

#ifndef BASE_NET_HELPER_H_
#define BASE_NET_HELPER_H_

#include <string>
#include <vector>

namespace soldier {
namespace base {

struct InetInterface {
    std::string name;
    std::string ip_addr;
    std::string broadcast_addr;
    std::string subnet_mask;
};

void getAllInetInterface(std::vector<InetInterface>& interfaces);
std::string getHostName();


} // end namespace base
} // end namespace soldier;



#endif /* BASE_NET_HELPER_H_ */
