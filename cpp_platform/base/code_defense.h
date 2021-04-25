/*
 * code_defense.h
 *
 *  Created on: 2017年3月4日
 *      Author: 44385
 */

#ifndef BASE_CODE_DEFENSE_H_
#define BASE_CODE_DEFENSE_H_

#include "base/app_log.h"

#define CHECK(condition) \
	if (!(condition)) { \
		APPLOG_FATAL("check failed! {}", #condition); \
	}

#ifdef NDEBUG
#define DCHECK(condition)
#else
#define DCHECK(condition)  CHECK(condition)
#endif

/**
 *  算内存对齐数的大小
 */
inline size_t alignMemoryBytes(size_t size) {
    size_t align_size = size;
    size_t align_bytes = size % sizeof(int*) ;
    if (align_bytes != 0) {
        align_size += (sizeof(int*) - align_bytes);
    }
    return align_size;
}


#endif /* BASE_CODE_DEFENSE_H_ */
