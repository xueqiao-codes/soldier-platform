/*
 * libmemcached_test.cc
 *
 *  Created on: 2017年9月21日
 *      Author: wileywang
 */

#include <memory>
#include <iostream>

#include "libmemcached/memcached.hpp"

int main() {

    memcached_return rc = MEMCACHED_SUCCESS;

    std::string config = "--SERVER=172.17.225.28:22201";
    memcached_st * mc = memcached(config.c_str(), config.length());

    std::string key =  "quotation_37";
    std::string value = "GANTRVMYDUNNRXxGfEVDOzE3MTAXUGuad5wi8z8XtaZ5xyk68z8XGw3gLZAg8z8Xk6mCUUkd8z8Xk6mCUUkd8z8WABbGFxbuBRcAAAAAAAAAABaW+4yQ1VcXSuoENBE28z8WChc2GsBbIMHzPxfvOEVHcnnyPxcAAAAAAAAAAEmnEhQ/xtw18z/ZPXlYqDXzP6Bns+pzNfM/aJHtfD818z8vuycPCzXzPxWMSuoENPM/awn5oGcz8z8zMzMzMzPzP8KGp1fKMvM/UdobfGEy8z8ZpgoKDAoKMB4wHjAZp7uWkA96NvM/9GxWfa428z8sQxzr4jbzP39Iv30dOPM/YaHWNO848z8LJCh+jDnzP0T67evAOfM/taZ5xyk68z8mUwWjkjrzP14pyxDHOvM/GaYKCg4wTh4wHjAeBsgB2uD5j9VXGA1nYXB1c2hzZXJ2ZXIxFOBiBvAB2uD5j9VXAA==";


    rc = memcached_set(mc, key.c_str(), key.length(), value.c_str(), value.length(), 0, 0);
    if (rc != 0) {
        std::cout << "memcached_set failed, rc=" << rc << ", " << memcached_strerror(mc, rc) << std::endl;
    } else {
        std::cout << "memcached_set success" << std::endl;
    }

//    uint32_t flags = 0;
//    size_t out_length = 0;
//    char* out_value = memcached_get(mc, key.c_str(), key.length(), &out_length, &flags, &rc);
//    if (out_value == NULL || rc != 0) {
//        std::cout << "memcached_get failed, rc=" << rc << ", " << memcached_strerror(mc, rc) << std::endl;
//    } else {
//        std::cout << "memcached_get success! out_length=" << out_length << ", out_value="
//                  << std::string(out_value, out_length) << std::endl;
//        free(out_value);
//    }

    memcached_free(mc);
    return 0;
}

