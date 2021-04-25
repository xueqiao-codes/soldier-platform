/*
 * empty_message_queue.h
 *
 *  Created on: 2017年12月27日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_QUEUE_EMPTY_MESSAGE_QUEUE_H_
#define MESSAGE_BUS_QUEUE_EMPTY_MESSAGE_QUEUE_H_

#include <chrono>
#include <thread>
#include "base/code_defense.h"
#include "message_bus/interface/message_queue.h"

namespace soldier {
namespace message_bus {

class EmptyMessageQueue : public IMessageQueue {
public:
    EmptyMessageQueue() = default;
    virtual ~EmptyMessageQueue() = default;

    virtual bool pushMessage(const std::string& topic
                , const std::string& from
                , const uint8_t* data
                , const size_t& length) {
        return false;
    }

    virtual bool peekMessage(std::string& topic, std::string& message_data) {
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
        return false;
    }
    virtual bool popMessage() {
        CHECK(false);
        return false;
    }

    virtual bool isBroken() { return false; }
    virtual void reset() {}

    virtual void getStatics(QueueStaticsData& data) const {}

    virtual bool isEmptyQueue() const { return true;}
};

} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_QUEUE_EMPTY_MESSAGE_QUEUE_H_ */
