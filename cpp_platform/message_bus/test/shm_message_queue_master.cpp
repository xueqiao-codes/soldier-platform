/*
 * shm_message_queue_test.cpp
 *
 *  Created on: 2017年12月22日
 *      Author: wileywang
 */

#include "message_bus/queue/shm_message_queue.h"

#include "base/time_helper.h"
#include <boost/lexical_cast.hpp>
#include <iostream>
#include <thread>
#include <chrono>

using namespace soldier::message_bus;

#define IS_TOPIC_EXISTED(name) std::cout << "topic " << name << " is existed="\
        << queue->containsTopic(name) << std::endl

int main() {
    soldier::base::AppLog::Init("test/shm_message_queue_master");

    std::unique_ptr<ShmMessageQueue> queue(ShmMessageQueue::Init("/data/broker/locks", 0x4000, 50*1024));
//    std::unique_ptr<ShmMessageQueue> queue(ShmMessageQueue::Get(0x3000));
    if (!queue) {
        std::cout << "message queue init failed!" << std::endl;
        return -1;
    }

    std::cout << "ShmHeaderSize=" << sizeof(ShmQueueHeader) << std::endl
              << ", message_queue_start=" << (int64_t)(queue->getMessageQueue()) << std::endl;

//    while(true) {
//        std::string topic = "haha";
//        std::string message = "time is ";
//        message.append(boost::lexical_cast<std::string>(soldier::base::NowInSeconds()));
//        message.append(", random.........................................................................................");
//        message.append(boost::lexical_cast<std::string>(rand()));
//
//        if (!queue->pushMessage(topic, __FILE__, (uint8_t*)message.data(), message.size())) {
//            std::cout << "push message failed" << std::endl;
//        }
//
//        std::this_thread::sleep_for(std::chrono::microseconds(100));
//
//        StaticsData data;
//        queue->getStatics(data);
//        std::cout << "topic_num=" << data.topic_num << ", free_size=" << data.free_size << std::endl;
//    }
//    queue->addTopic("1234");
//    queue->addTopic("3356");
//    queue->addTopic("abcd123123");

//    IS_TOPIC_EXISTED("3356");
//    IS_TOPIC_EXISTED("123123");
//    IS_TOPIC_EXISTED("abcd123123");

//    for (int index = 0; index < 1000; ++index) {
//        IS_TOPIC_EXISTED(boost::lexical_cast<std::string>(index));
//    }

    return 0;
}


