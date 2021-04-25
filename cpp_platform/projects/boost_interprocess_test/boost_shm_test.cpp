/*
 * boost_shm_test.cpp
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */

#include <boost/interprocess/shared_memory_object.hpp>
#include <iostream>

int main() {
    boost::interprocess::shared_memory_object shdmem(boost::interprocess::open_or_create, "shm_test", boost::interprocess::read_write);
    shdmem.truncate(1024);
    std::cout << shdmem.get_name() << std::endl;
    boost::interprocess::offset_t size;
    if (shdmem.get_size(size))
        std::cout << size << std::endl;

    getchar();
    return 0;
}

