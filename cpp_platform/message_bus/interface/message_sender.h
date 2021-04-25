/*
 * message_sender.h
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_INTERFACE_MESSAGE_SENDER_H_
#define MESSAGE_BUS_INTERFACE_MESSAGE_SENDER_H_

#include <string>

namespace soldier {
namespace message_bus {

struct TMessage {
    std::string from;
    std::string topic;
    const uint8_t* message_data = nullptr;
    size_t message_length = 0;
};

enum class GuardPolicyType {
    TIMEOUT_POLICY
};

struct GuardPolicy {
    GuardPolicyType type = GuardPolicyType::TIMEOUT_POLICY;
    int32_t timeout_seconds = 3;
};


class IMessageSender {
public:
    virtual ~IMessageSender() = default;

    /**
     *  直接发送一个消息
     */
    virtual bool sendMessageDirectly(const TMessage& msg) = 0;

    /**
     *  准备备用守护消息
     */
    virtual std::string prepareGuardMessage(const TMessage& msg, GuardPolicy policy) = 0;
    /**
     *  发送消息，并移除备用守护消息
     */
    virtual bool sendMessageAndRmGuard(const TMessage& msg, const std::string& guard_id) = 0;

    /**
     *  移除守护消息
     */
    virtual bool rmGuardMessage(const std::string& guard_id) = 0;

protected:
    IMessageSender() = default;
};

} // end namespace message_bus
} // end namespace soldier


#endif /* MESSAGE_BUS_INTERFACE_MESSAGE_SENDER_H_ */
