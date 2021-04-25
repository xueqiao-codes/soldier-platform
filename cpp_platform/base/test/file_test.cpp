/*
 * file_test.cpp
 *
 *  Created on: 2017年4月19日
 *      Author: wileywang
 */

#include "base/file_util.h"

#include <iostream>

using namespace soldier::base;


int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cout << "please input the file path" << std::endl;
        return 1;
    }

    std::string content = FileUtil::readFileAll(argv[1]);

    std::cout << "content length=" << content.length() << std::endl
              << "content is " << std::endl
              << "capacity " << content.capacity() << std::endl
              << content.c_str() << std::endl;

    return 0;
}


