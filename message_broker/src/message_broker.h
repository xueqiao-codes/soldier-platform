/*
 * message_broker.h
 *
 *  Created on: 2017年12月26日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_BROKER_MESSAGE_BROKER_H_
#define MESSAGE_BUS_BROKER_MESSAGE_BROKER_H_

#include <map>
#include <memory>
#include "base/thread_pool.h"
#include "message_bus/interface/message_graph_holder.h"
#include "message_bus/interface/message_queue.h"
#include "message_bus/interface/message_guard_storage.h"

namespace soldier {
namespace message_bus {

class MessageBroker : public IMessageGraphListener {
public:
    static std::shared_ptr<MessageBroker> create(const std::string& broker_name
            , const std::shared_ptr<MessageGraphHolder>& message_graph_holder);

    virtual ~MessageBroker();

    virtual void onMessageGraphChanged();

private:
    MessageBroker(const std::string& broker_type, const std::shared_ptr<MessageGraphHolder>& message_graph_holder);
    bool init();

    void onGuard();
    void onReceiveLoop();
    void onHandleMessageGraphChanged();
    void processGuardMessages();
    void dispatchMessage(const std::string& topic, const std::string& message_data);

    std::string broker_name_;

    std::shared_ptr<MessageGraphHolder> message_graph_holder_;

    std::unique_ptr<soldier::base::TaskThread> transfer_thread_;

    std::shared_ptr<IMessageQueue> broker_queue_;
    std::shared_ptr<IMessageGuardStorage> guard_storage_;
    std::map<std::string, std::shared_ptr<IMessageQueue>> agent_queues_;
    std::map<std::string, std::shared_ptr<std::set<std::string>>> topic_queues_;
};


} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_BROKER_MESSAGE_BROKER_H_ */
