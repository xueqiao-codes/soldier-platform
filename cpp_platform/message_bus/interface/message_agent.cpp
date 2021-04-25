/*
 * message_agent.cpp
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#include "message_bus/interface/message_agent.h"

#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/time_helper.h"
#include "message_bus/interface/message_factory.h"

namespace soldier {
namespace message_bus {

std::shared_ptr<MessageAgent> MessageAgent::create(
            const std::string& agent_name
            , const std::shared_ptr<IMessageConsumer>& message_consumer
            , const std::shared_ptr<MessageGraphHolder>& message_graph_holder) {
    CHECK(message_graph_holder);
    CHECK(message_consumer);
    std::shared_ptr<MessageGraph> current_message_graph = message_graph_holder->current();
    CHECK(current_message_graph);

    auto consumer_it = current_message_graph->getAgents().find(agent_name);
    if (consumer_it == current_message_graph->getAgents().end()) {
        APPLOG_ERROR("failed to find agent={} in consumers", agent_name);
        return std::shared_ptr<MessageAgent>();
    }
    auto broker_it = current_message_graph->getBrokers().find(consumer_it->second->getBrokerName());
    if (broker_it == current_message_graph->getBrokers().end()) {
        APPLOG_ERROR("failed to find broker={} in brokers", consumer_it->second->getType());
        return std::shared_ptr<MessageAgent>();
    }

    std::shared_ptr<MessageAgent> message_agent(new MessageAgent(agent_name, message_consumer, message_graph_holder));
    if (!message_agent->init(broker_it->second, consumer_it->second)) {
        return std::shared_ptr<MessageAgent>();
    }

    message_graph_holder->addListener(message_agent);
    message_agent->onMessageGraphChanged();
    return message_agent;
}

MessageAgent::MessageAgent(const std::string& agent_name
            , const std::shared_ptr<IMessageConsumer>& message_consumer
            , const std::shared_ptr<MessageGraphHolder>& message_graph_holder)
    : agent_name_(agent_name)
     , message_graph_holder_(message_graph_holder)
     , message_consumer_(message_consumer) {
}

void MessageAgent::onMessageGraphChanged() {
    std::shared_ptr<MessageGraph> current_message_graph = message_graph_holder_->current();
    auto agent_it = current_message_graph->getAgents().find(agent_name_);
    if (agent_it == current_message_graph->getAgents().end()) {
        return;
    }

    auto loop_us_it = agent_it->second->getProperties().find("empty_loop_us");
    if (loop_us_it != agent_it->second->getProperties().end()) {
        try {
            empty_loop_us_ = boost::lexical_cast<int>(loop_us_it->second);
        } catch (boost::bad_lexical_cast& e) {
        }
    } else {
        empty_loop_us_ = DEFAULT_EMPTY_LOOP_US;
    }

    APPLOG_WARN("[NOTICE]update {}'s empty_loop_us_ to {}", agent_name_, empty_loop_us_);
}

bool MessageAgent::init(
        const std::shared_ptr<BrokerItem>& broker_item
        , const std::shared_ptr<AgentItem>& agent_item) {
    broker_queue_ = MessageFactory::attachBrokerQueue(broker_item);
    if (!broker_queue_) {
        return false;
    }
    guard_storage_ = MessageFactory::attachGuardStorage(broker_item);
    if (!guard_storage_) {
        return false;
    }
    agent_queue_ = MessageFactory::attachAgentQueue(agent_item);
    if (!agent_queue_) {
        return false;
    }

    // empty queue not receive thread
    if (!agent_queue_->isEmptyQueue()) {
        receive_thread_.reset(new soldier::base::TaskThread());
        receive_thread_->postTask(&MessageAgent::onReceiveStart, this);
    }
    return true;
}

bool MessageAgent::sendMessageDirectly(const TMessage& msg) {
    if (!broker_queue_) {
        return false;
    }

    return broker_queue_->pushMessage(msg.topic, msg.from, msg.message_data, msg.message_length);
}

std::string MessageAgent::prepareGuardMessage(const TMessage& msg, GuardPolicy policy) {
    if (!guard_storage_) {
        return "";
    }
    return guard_storage_->prepareGuardMessage(msg.topic, msg.from, msg.message_data, msg.message_length, policy);
}

bool MessageAgent::sendMessageAndRmGuard(const TMessage& msg, const std::string& guard_id) {
    bool result = sendMessageDirectly(msg);
    guard_storage_->rmGuardMessage(guard_id);
    return result;
}

bool MessageAgent::rmGuardMessage(const std::string& guard_id) {
    if (!guard_storage_) {
        return false;
    }

    return guard_storage_->rmGuardMessage(guard_id);
}

void MessageAgent::onReceiveStart() {
    IMessageConsumer::StartUpMode resultMode = message_consumer_->onStartUp();
    if (resultMode == IMessageConsumer::StartUpMode::CLEAR_QUEUE_INIT) {
        agent_queue_->reset();
    }

    receive_thread_->postTask(&MessageAgent::onReceiveLoop, this);
}

void MessageAgent::onReceiveLoop() {
    int64_t loop_startms = soldier::base::NowInMilliSeconds();
    while(true) {
        if (soldier::base::NowInMilliSeconds() - loop_startms > 100) {
            receive_thread_->postTask(&MessageAgent::onReceiveLoop, this);
            break;
        }

        std::string topic;
        std::string message_data;
        if (agent_queue_->peekMessage(topic, message_data)) {
            int retry_count = 0;
            APPLOG_DEBUG("peek msg topic={}, message length={}", topic, message_data.length());
            while((retry_count++) < 10) {
                IMessageConsumer::ConsumeResult result = message_consumer_->onConsumeMessage(topic, message_data);
                if (result == IMessageConsumer::ConsumeResult::CONSUME_OK
                        || result == IMessageConsumer::ConsumeResult::CONSUME_FAILED_DROP) {
                    agent_queue_->popMessage();
                    break;
                }
            }
        } else {
            std::this_thread::sleep_for(std::chrono::microseconds(empty_loop_us_));
        }
    }

}


} // end namespace message_bus
} // end namespace soldier



