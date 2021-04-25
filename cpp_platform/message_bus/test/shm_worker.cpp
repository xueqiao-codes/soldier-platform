/*
 * shm_worker.cpp
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */


#include <boost/interprocess/sync/scoped_lock.hpp>
#include <boost/interprocess/sync/file_lock.hpp>
#include <chrono>
#include <iostream>
#include <thread>

#include "../queue/shm_queue_header.h"
#include "base/shm_object.h"

using namespace soldier::message_bus;

int main() {

    soldier::base::ShmObject shm_object;

    if (!shm_object.attach(0x2000)) {
        std::cout << "attach shm failed!" << std::endl;
        return 1;
    }

    ShmQueueHeader* header = (ShmQueueHeader*)shm_object.getShmAddr();
    if (!header->isInited()) {
        std::cout << "shm not inited, wait for inited..." << std::endl;
        return 2;
    }

    boost::interprocess::file_lock lock(header->getQueueLockFilePath());
    while(true) {
        lock.lock();
        std::cout << "worker sleep begin..." << std::endl;
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::cout << "worker sleep end..." << std::endl;
        lock.unlock();
    }

    return 0;
}


