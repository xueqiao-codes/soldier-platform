/*
 * message_agent_test.cpp
 *
 *  Created on: 2017年12月27日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_TEST_MESSAGE_AGENT_TEST_CPP_
#define MESSAGE_BUS_TEST_MESSAGE_AGENT_TEST_CPP_

#include <chrono>
#include <iostream>
#include <thread>
#include "base/app_log.h"
#include "message_bus/interface/message_agent.h"

using namespace soldier::message_bus;

class PrintMessageConsumer : public IMessageConsumer {
public:
    virtual ConsumeResult onConsumeMessage(
               const std::string& topic
               , const std::string& message) {
        std::cout << "receive topic=" << topic << ", message length=" << message.length()
                  << ", message data=" << message
                  << std::endl;
        return IMessageConsumer::ConsumeResult::CONSUME_OK;
    }

    virtual StartUpMode onStartUp()  {
        return StartUpMode::CLEAR_QUEUE_INIT;
    }
};


int main() {
    std::shared_ptr<MessageGraphHolder> graph_holder
        = MessageGraphHolder::create("/data/configs/qconf/xueqiao/trade/hosting/message_graph.json");
    std::shared_ptr<IMessageConsumer> message_consumer(new PrintMessageConsumer());
    std::shared_ptr<MessageAgent> message_agent =
            MessageAgent::create("test3_shm", message_consumer, graph_holder);

    while(true) {
        std::this_thread::sleep_for(std::chrono::seconds(10));
    }
    return 0;
}



#endif /* MESSAGE_BUS_TEST_MESSAGE_AGENT_TEST_CPP_ */
