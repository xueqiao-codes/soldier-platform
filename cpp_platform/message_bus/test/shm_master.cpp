/*
 * shm_master.cpp
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */

#include <boost/interprocess/sync/scoped_lock.hpp>
#include <boost/interprocess/sync/file_lock.hpp>
#include <chrono>
#include <iostream>
#include <thread>
#include <memory>

#include "../queue/shm_queue_header.h"
#include "base/shm_object.h"

using namespace soldier::message_bus;

std::unique_ptr<boost::interprocess::file_lock> global_lock_;

void Work() {
    while(true) {
        global_lock_->lock();
        std::cout << "master[worker] sleep begin..." << std::endl;
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::cout << "master[worker] sleep end..." << std::endl;
        global_lock_->unlock();
        std::this_thread::sleep_for(std::chrono::microseconds(100));
    }
}

int main() {

    soldier::base::ShmObject shm_object;

    std::cout << "sizeof(ShmHeader)=" << sizeof(ShmQueueHeader) << std::endl;

    if (!shm_object.createOrAttach(0x2000, sizeof(ShmQueueHeader), 0666)) {
        std::cout << "create or attach shm failed!" << shm_object.getLastErrMsg() << std::endl;
        return 1;
    }

    ShmQueueHeader* header = (ShmQueueHeader*)shm_object.getShmAddr();
    if (!header->isInited()) {
        header = new(shm_object.getShmAddr()) ShmQueueHeader();
        header->setShmSize(2048);
        header->setQueueFileLockPath("/data/broker/locks/0x2000_queue");
        header->setInited();
    } else {
        std::cout << "header already inited!" << std::endl;
    }

    global_lock_.reset(new boost::interprocess::file_lock(header->getQueueLockFilePath()));

    std::thread work_thread(&Work);
    while(true) {
        global_lock_->lock();
        std::cout << "master[main] sleep begin..." << std::endl;
        std::this_thread::sleep_for(std::chrono::microseconds(100));
        std::cout << "master[main] sleep end..." << std::endl;
        global_lock_->unlock();
        std::this_thread::sleep_for(std::chrono::microseconds(100));
    }

    work_thread.join();

    return 0;
}



