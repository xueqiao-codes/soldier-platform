/*
 * jchash_test.cpp
 *
 *  Created on: 2017年4月17日
 *      Author: wileywang
 */

#include <iostream>
#include "../hash.h"

using namespace soldier::base;

void jchashPrint(uint64_t key, int32_t buckets) {
    std::cout << "key:" << key << ", buckets:" << buckets << "->"
              << jumpConsistentHash(key, buckets)
              << std::endl;
}

void hashPrint(const std::string& str) {
    std::cout << "jsHash(" << str << ")=" << jsHash(str.c_str(), str.length()) << std::endl;
    std::cout << "sdbMHash(" << str << ")=" << sdbMHash(str.c_str(), str.length()) << std::endl;
}

int main () {
    int tests[] = {1, 2, 3, 4, 5, 6, 7, 8 ,9, 10};

    for (int bucket = 5; bucket >=4 ; --bucket) {
        for (int t = 0 ; t < (int)(sizeof(tests)/sizeof(tests[0])); ++t) {
            jchashPrint(tests[t], bucket);
        }

        std::cout << "bucket = " << bucket << " end..." << std::endl;
    }

    hashPrint("abc");
    hashPrint("");
    hashPrint("bcd");
    hashPrint("192.168.1.101");
    hashPrint("192.168.1.102");
    hashPrint("192.168.1.103");

    return 0;
}

