/*
 * file_util.h
 *
 *  Created on: 2017年4月19日
 *      Author: wileywang
 */

#ifndef BASE_FILE_UTIL_H_
#define BASE_FILE_UTIL_H_

#include <string>

namespace soldier {
namespace base {

class FileUtil {
public:
    static std::string readFileAll(const std::string& file_path);
};


} // end namespace base
} // end namespace soldier



#endif /* BASE_FILE_UTIL_H_ */
