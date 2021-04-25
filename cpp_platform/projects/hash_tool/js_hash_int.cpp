/*
 * js_hash.cpp
 *
 *  Created on: 2017年9月30日
 *      Author: wileywang
 */

#include "base/hash.h"

#include <iostream>
#include <string.h>

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cout << "command is : "<< argv[0] << " hashstr" << std::endl;
        return 1;
    }

    std::cout << soldier::base::jsHashInt32(argv[1], strlen(argv[1])) << std::endl;

    return 0;
}


