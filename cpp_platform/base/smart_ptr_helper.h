/*
 * smart_ptr_helper.h
 *
 *  Created on: 2017年4月22日
 *      Author: 44385
 */

#ifndef BASE_SMART_PTR_HELPER_H_
#define BASE_SMART_PTR_HELPER_H_

#include <stdio.h>

namespace soldier {
namespace base {

struct FileDeleter {
    void operator()(FILE* file) {
        if (file) {
            fclose(file);
        }
    }
};

} // end namespace base
} // end namespace soldier



#endif /* BASE_SMART_PTR_HELPER_H_ */
