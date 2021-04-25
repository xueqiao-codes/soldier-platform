/*
 * message_consumer.h
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */
#ifndef MESSAGE_BUS_INTERFACE_MESSAGE_CONSUMER_H_
#define MESSAGE_BUS_INTERFACE_MESSAGE_CONSUMER_H_

#include <string>

namespace soldier {
namespace message_bus {


class IMessageConsumer {
public:
    enum class StartUpMode {
        CLEAR_QUEUE_INIT,  // clear queue, and init
        RESERVE_QUEUE,     // reserve queue messages
    };

    enum class ConsumeResult {
        CONSUME_OK,
        CONSUME_FAILED_DROP,
        CONSUME_RETRY
    };

    virtual ~IMessageConsumer() = default;

    virtual StartUpMode onStartUp() = 0;

    virtual ConsumeResult onConsumeMessage(
            const std::string& topic
            , const std::string& message) = 0;

protected:
    IMessageConsumer() = default;

};



} // end namespace message_bus
} // end namespace soldier

#endif // MESSAGE_BUS_INTERFACE_MESSAGE_CONSUMER_H_

