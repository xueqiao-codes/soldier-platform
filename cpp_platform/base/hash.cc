/*
 * jchash.cc
 *
 *  Created on: 2017年4月17日
 *      Author: wileywang
 */
#include "hash.h"

#include <boost/crc.hpp>

namespace soldier {
namespace base {

int32_t jumpConsistentHash(uint64_t key, int32_t num_buckets) {
    int64_t b = -1;
    int64_t j = 0;
    while (j < num_buckets) {
        b = j;
        key = key * 2862933555777941757ULL + 1;
        j = (b + 1) * ((double)(1LL << 31) / (double)((key >> 33) + 1));
    }
    return b;
}

uint32_t jsHash(const char *str, size_t len) {
    uint32_t hash = 1315423911; // nearly a prime - 1315423911 = 3 * 438474637
    for (size_t index = 0; index < len; ++index) {
        hash ^= ((hash << 5) + (str[index]) + (hash >> 2));
    }
    return (hash & 0x7FFFFFFF);
}

int32_t  jsHashInt32(const char* str, size_t len) {
    int32_t hash = 1315423911;
    for (size_t index = 0; index < len; ++index) {
        hash ^= ((hash << 5) + (str[index]) + (hash >> 2));
    }
    return (hash & 0x7FFFFFFF);
}

uint32_t sdbMHash(const char *str, size_t len) {
    uint32_t hash = 0;
    for (size_t index = 0; index < len; ++index) {
        // equivalent to: hash = 65599*hash + (*str++);
        hash = (str[index]) + (hash << 6) + (hash << 16) - hash;
    }
    return (hash & 0x7FFFFFFF);
}

int32_t calCrc32(const char* data, size_t len) {
    boost::crc_32_type result;
    result.process_bytes(data, len);
    return result.checksum();
}

} // end namespace base
} // end namespace soldier


