/*
 * message_queue.h
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_QUEUE_MESSAGE_QUEUE_H_
#define MESSAGE_BUS_QUEUE_MESSAGE_QUEUE_H_

#define MAX_MESSAGE_SIZE 4096

namespace soldier {
namespace message_bus {

struct QueueStaticsData {
    int32_t topic_num = 0;
    uint64_t free_size = 0;
};

class IMessageQueue {
public:
    virtual ~IMessageQueue() = default;

    virtual bool pushMessage(const std::string& topic
            , const std::string& from
            , const uint8_t* data
            , const size_t& length) = 0;

    virtual bool peekMessage(std::string& topic, std::string& message_data) = 0;
    virtual bool popMessage() = 0;

    /**
     *  队列是否被破坏
     */
    virtual bool isBroken() = 0;

    /**
     *  重置队列
     */
    virtual void reset() = 0;

    virtual void getStatics(QueueStaticsData& data) const = 0;

    virtual bool isEmptyQueue() const = 0;

protected:
    IMessageQueue() = default;

};


} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_QUEUE_MESSAGE_QUEUE_H_ */
