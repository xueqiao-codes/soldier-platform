/*
 * message_agent_sender.cpp
 *
 *  Created on: 2017年12月27日
 *      Author: wileywang
 */

#include <boost/lexical_cast.hpp>
#include <chrono>
#include <iostream>
#include <thread>
#include "base/app_log.h"
#include "base/time_helper.h"
#include "message_bus/interface/message_agent.h"

using namespace soldier::message_bus;

class PrintMessageConsumer : public IMessageConsumer {
public:
    virtual ConsumeResult onConsumeMessage(
               const std::string& topic
               , const std::string& message) {
        std::cout << "receive topic=" << topic << ", message length=" << message.length() << std::endl;
        return IMessageConsumer::ConsumeResult::CONSUME_OK;
    }

    virtual StartUpMode onStartUp() {
        return StartUpMode::CLEAR_QUEUE_INIT;
    }
};

int main() {
    std::shared_ptr<MessageGraphHolder> graph_holder
        = MessageGraphHolder::create("/data/configs/qconf/xueqiao/trade/hosting/message_graph.json");
    std::shared_ptr<IMessageConsumer> message_consumer(new PrintMessageConsumer());
    std::shared_ptr<MessageAgent> message_agent =
            MessageAgent::create("only_send", message_consumer, graph_holder);

    while(true) {
        std::string topic = "Test3Event";
        std::string message_data = "current time is " + boost::lexical_cast<std::string>(soldier::base::NowInSeconds());

        TMessage message;
        message.topic = topic;
        message.message_data = (uint8_t*)message_data.data();
        message.message_length = message_data.length();

        if (message_agent->sendMessageDirectly(message)) {
//            std::cout << "send message topic=" << topic << ", message=" << message_data << std::endl;
        } else {
            std::cout << "send message failed!" << std::endl;
        }
//        std::this_thread::sleep_for(std::chrono::seconds(1));
    }
    return 0;

    return 0;
}



