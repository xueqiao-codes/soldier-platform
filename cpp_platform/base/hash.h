/*
 *
 *  Created on: 2017年4月17日
 *      Author: wileywang
 */

#ifndef BASE_HASH_H_
#define BASE_HASH_H_


#include <stdio.h>
#include <stdint.h>
#include <inttypes.h>

namespace soldier {
namespace base {

/*
 * Jump Consistent Hash from Google
 * http://arxiv.org/pdf/1406.2294.pdf */
int32_t jumpConsistentHash(uint64_t key, int32_t num_buckets);

/**
 *  采用uint32_t作为JSHash的种子，算出的结果与soldier_base中不一致
 */
uint32_t jsHash(const char *str, size_t len);

/**
 *  采用int32_t作为JSHash的种子，与soldier_base的java项目一致的JSHash
 */
int32_t  jsHashInt32(const char* str, size_t len);

uint32_t sdbMHash(const char *str, size_t len);

int32_t calCrc32(const char* data, size_t len);

} // end namespace base
} // end namespace soldier


#endif /* BASE_HASH_H_ */
