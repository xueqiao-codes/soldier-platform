/*
 * message_agent.h
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_INTERFACE_MESSAGE_AGENT_H_
#define MESSAGE_BUS_INTERFACE_MESSAGE_AGENT_H_

#include <memory>
#include "base/thread_pool.h"
#include "message_bus/interface/message_graph_holder.h"
#include "message_bus/interface/message_guard_storage.h"
#include "message_bus/interface/message_consumer.h"
#include "message_bus/interface/message_sender.h"
#include "message_bus/interface/message_queue.h"

#define DEFAULT_EMPTY_LOOP_US 1000

namespace soldier {
namespace message_bus {

class MessageAgent : public IMessageSender, public IMessageGraphListener {
public:
    static std::shared_ptr<MessageAgent> create(
            const std::string& agent_name
            , const std::shared_ptr<IMessageConsumer>& message_consumer
            , const std::shared_ptr<MessageGraphHolder>& message_graph_holder);

    virtual ~MessageAgent() = default;
    virtual bool sendMessageDirectly(const TMessage& msg);
    virtual std::string prepareGuardMessage(const TMessage& msg, GuardPolicy policy);
    virtual bool sendMessageAndRmGuard(const TMessage& msg, const std::string& guard_id);
    virtual bool rmGuardMessage(const std::string& guard_id);

    virtual void onMessageGraphChanged();

private:
    bool init(
            const std::shared_ptr<BrokerItem>& broker_item
            , const std::shared_ptr<AgentItem>& agent_item);

    MessageAgent(const std::string& agent_name
            , const std::shared_ptr<IMessageConsumer>& message_consumer
            , const std::shared_ptr<MessageGraphHolder>& message_graph_holder);
    void onReceiveStart();
    void onReceiveLoop();

    std::string agent_name_;

    std::shared_ptr<MessageGraphHolder> message_graph_holder_;

    std::shared_ptr<IMessageQueue> broker_queue_;
    std::shared_ptr<IMessageGuardStorage> guard_storage_;
    std::shared_ptr<IMessageQueue> agent_queue_;
    std::shared_ptr<IMessageConsumer> message_consumer_;

    std::unique_ptr<soldier::base::TaskThread> receive_thread_;

    int empty_loop_us_ = DEFAULT_EMPTY_LOOP_US;
};


} // end namespace message_bus
} // end namespace soldier





#endif /* MESSAGE_BUS_INTERFACE_MESSAGE_AGENT_H_ */
