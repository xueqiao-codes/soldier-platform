/*
 * url_util.h
 *
 *  Created on: 2017年11月30日
 *      Author: wileywang
 */

#ifndef BASE_URL_UTIL_H_
#define BASE_URL_UTIL_H_


#include <string>

namespace soldier {
namespace base {

std::string urlEncode(const std::string& str);
std::string urlDecode(const std::string& str);

} // end namespace base
} // end namespace soldier



#endif /* BASE_URL_UTIL_H_ */
