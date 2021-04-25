/*
 * shm_message_queue_receivor.cpp
 *
 *  Created on: 2017年12月22日
 *      Author: wileywang
 */

#include "message_bus/queue/shm_message_queue.h"

#include <boost/lexical_cast.hpp>
#include <iostream>
#include <thread>
#include <chrono>

using namespace soldier::message_bus;
#define IS_TOPIC_EXISTED(name) std::cout << "topic " << name << " is existed="\
        << queue->containsTopic(name) << std::endl


int main() {

    soldier::base::AppLog::Init("test/shm_message_queue_receivor");

//    std::unique_ptr<ShmMessageQueue> queue(ShmMessageQueue::Init("/data/broker/locks", 0x3000, 10*1024*1024));
    std::unique_ptr<ShmMessageQueue> queue(ShmMessageQueue::Get(0x4000));
    if (!queue) {
        std::cout << "message queue init failed!" << std::endl;
        return -1;
    }

    while(true) {
        std::string topic;
        std::string message_data;
        if (queue->peekMessage(topic, message_data)) {
            std::cout << "peek message, topic=" << topic << ", message_data=" << message_data << std::endl;
            queue->popMessage();
        } else {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
        }
    }

    return 0;
}
