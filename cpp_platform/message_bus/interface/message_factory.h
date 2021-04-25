/*
 * message_factory.h
 *
 *  Created on: 2017年12月26日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_MESSAGE_FACTORY_H_
#define MESSAGE_BUS_MESSAGE_FACTORY_H_

#include <memory>
#include "message_bus/interface/message_graph.h"
#include "message_bus/interface/message_queue.h"
#include "message_bus/interface/message_guard_storage.h"

namespace soldier {
namespace message_bus {

class MessageFactory {
public:
    /**
     *  供broker创建
     */
    static std::shared_ptr<IMessageGuardStorage> createGuardStorage(
            const std::shared_ptr<BrokerItem>& broker_item);

    static std::shared_ptr<IMessageQueue> createBrokerQueue(
                const std::shared_ptr<BrokerItem>& broker_item);
    static std::shared_ptr<IMessageQueue> createAgentQueue(
                const std::shared_ptr<AgentItem>& agent_item);

    /**
     *  供consumer进行挂载
     */
    static std::shared_ptr<IMessageGuardStorage> attachGuardStorage(
            const std::shared_ptr<BrokerItem>& broker_item);
    static std::shared_ptr<IMessageQueue> attachBrokerQueue(
                const std::shared_ptr<BrokerItem>& broker_item);
    static std::shared_ptr<IMessageQueue> attachAgentQueue(
                const std::shared_ptr<AgentItem>& agent_item);


};


} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_MESSAGE_FACTORY_H_ */
