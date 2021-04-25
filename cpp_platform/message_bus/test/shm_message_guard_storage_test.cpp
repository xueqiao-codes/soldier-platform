/*
 * shm_message_guard_storage_test.cpp
 *
 *  Created on: 2018年1月4日
 *      Author: wileywang
 */


#include "message_bus/guard/shm_message_guard_storage.h"

#include <chrono>
#include <thread>
#include <iostream>

using namespace soldier::message_bus;

int main() {
    std::shared_ptr<ShmMessageGuardStorage> storage(ShmMessageGuardStorage::Get(4000));
    for (int index = 0; index < 1000000; ++index) {
        std::string guard_id = storage->prepareGuardMessage("TEST", "test", (uint8_t*)"TEST", 4, GuardPolicy());
        std::this_thread::sleep_for(std::chrono::milliseconds(10* (rand()%100 + 1)));
        storage->rmGuardMessage(guard_id);
        std::cout << "rm guard id=" << guard_id << ", free guard num="<< storage->getFreeGuardNum() << std::endl;
        std::this_thread::sleep_for(std::chrono::milliseconds(10* (rand()%100 + 1)));
    }
}

