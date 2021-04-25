/*
 * url_test.cpp
 *
 *  Created on: 2017年11月30日
 *      Author: wileywang
 */

#ifndef BASE_TEST_URL_TEST_CPP_
#define BASE_TEST_URL_TEST_CPP_


#include "base/url_util.h"

#include <iostream>

using namespace soldier::base;

#define URL_ENCODE_TEST(str) std::cout << "encode " << str << " to " << urlEncode(str) << std::endl
#define URL_DECODE_TEST(str) std::cout << "decode " << str << " to " << urlDecode(str) << std::endl

int main() {

    URL_ENCODE_TEST("1234");
    URL_DECODE_TEST("1234");

    URL_ENCODE_TEST("abcd");

    URL_ENCODE_TEST("中国人");
    URL_DECODE_TEST("%E4%B8%AD%E5%9B%BD%E4%BA%BA");

    URL_ENCODE_TEST("COMEX|S|HG;1805&1906");
    URL_DECODE_TEST("COMEX%7CS%7CHG%3B1805%261906");

    return 0;
}



#endif /* BASE_TEST_URL_TEST_CPP_ */
