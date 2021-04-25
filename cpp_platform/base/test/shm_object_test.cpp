/*
 * shm_object_test.cpp
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */

#include "base/shm_object.h"

#include <iostream>

int main() {
    soldier::base::ShmObject shm_object;
    if (!shm_object.create(0x2000, 2048, 0666)) {
        std::cout << "shm object create or attach failed, " << shm_object.getLastErrMsg() << std::endl;
        return -1;
    }

    std::cout << "shm addr=" << shm_object.getShmAddr() << std::endl;
    return 0;
}


