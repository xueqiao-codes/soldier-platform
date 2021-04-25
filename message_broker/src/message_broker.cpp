/*
 * message_broker.cpp
 *
 *  Created on: 2017年12月26日
 *      Author: wileywang
 */

#include "message_broker.h"

#include "base/app_log.h"
#include "base/time_helper.h"
#include "base/code_defense.h"
#include "message_bus/interface/message_factory.h"
#include "message_bus/interface/framework_topics.h"

namespace soldier {
namespace message_bus {

std::shared_ptr<MessageBroker> MessageBroker::create(
        const std::string& broker_name
        , const std::shared_ptr<MessageGraphHolder>& message_graph_holder) {
    std::shared_ptr<MessageBroker> instance(new MessageBroker(broker_name, message_graph_holder));
    message_graph_holder->addListener(instance);
    instance->onMessageGraphChanged();
    return instance;
}

MessageBroker::MessageBroker(const std::string& broker_name
        , const std::shared_ptr<MessageGraphHolder>& message_graph_holder)
    : broker_name_(broker_name)
      , message_graph_holder_(message_graph_holder) {
    transfer_thread_.reset(new soldier::base::TaskThread());
}

MessageBroker::~MessageBroker() {
}

void MessageBroker::onMessageGraphChanged() {
    transfer_thread_->postTask(&MessageBroker::onHandleMessageGraphChanged, this);
}

void MessageBroker::onHandleMessageGraphChanged() {
    APPLOG_INFO("onHandleMessageGraphChanged...");

    std::shared_ptr<MessageGraph> current_message_graph = message_graph_holder_->current();
    if (!current_message_graph) {
        APPLOG_FATAL("no current message graph !");
        return ;
    }

    auto broker_it = current_message_graph->getBrokers().find(broker_name_);
    if (broker_it == current_message_graph->getBrokers().end()) {
        APPLOG_FATAL("no config for broker_name={}", broker_name_);
        return ;
    }

    guard_storage_ = MessageFactory::createGuardStorage(broker_it->second);
    if (!guard_storage_) {
        APPLOG_FATAL("failed to create guard storage for broker_name={}", broker_name_);
        return ;
    }

    broker_queue_ = MessageFactory::createBrokerQueue(broker_it->second);
    if (!broker_queue_) {
        APPLOG_FATAL("failed to create broker queue for name={}", broker_name_);
        return ;
    }
    APPLOG_INFO("create broker queue success, name={} ", broker_it->second->getName());

    agent_queues_.clear();
    topic_queues_.clear();
    for (auto agent_it = current_message_graph->getAgents().begin()
            ; agent_it != current_message_graph->getAgents().end(); ++agent_it) {
        CHECK(agent_it->second);
        if (agent_it->second->getBrokerName() != broker_name_) {
            continue;
        }

        std::shared_ptr<IMessageQueue> agent_queue
            = MessageFactory::createAgentQueue(agent_it->second);
        if (!agent_queue) {
            APPLOG_FATAL("failed to create agent queue for name={}", agent_it->second->getName());
            return ;
        }
        APPLOG_INFO("create agent_queue success, name={}", agent_it->second->getName());

        agent_queues_[agent_it->second->getName()] = agent_queue;
        for (auto topic : agent_it->second->getTopics()) {
            auto topic_set_it = topic_queues_.find(topic);
            if (topic_set_it == topic_queues_.end()) {
                topic_set_it = topic_queues_.insert(
                        std::pair<std::string, std::shared_ptr<std::set<std::string>>>(
                                topic,
                                std::shared_ptr<std::set<std::string>>(new std::set<std::string>())
                                )).first;
            }
            topic_set_it->second->insert(agent_it->second->getName());
        }
    }

    transfer_thread_->postTask(&MessageBroker::onReceiveLoop, this);
}

void MessageBroker::onReceiveLoop() {
    int64_t loop_startms = soldier::base::NowInMilliSeconds();
    processGuardMessages();
    while(true) {
        if (soldier::base::NowInMilliSeconds() - loop_startms > 1000) {
            transfer_thread_->postTask(&MessageBroker::onReceiveLoop, this);
            break;
        }
        std::string topic;
        std::string message_data;
        if (broker_queue_->peekMessage(topic, message_data)) {
            dispatchMessage(topic, message_data);
            broker_queue_->popMessage();
        } else {
            if (guard_storage_->isBroken()) {
                APPLOG_WARN("guard storage is broken, reset guard storage! let all agent queues init for sure");
                guard_storage_->reset();
                for (auto agent_queue : agent_queues_) {
                    agent_queue.second->pushMessage(
                            INIT_TOPIC, "GuardStorage::Broken", (uint8_t*)INIT_TOPIC.data(), INIT_TOPIC.length());
                }
            }

            if (broker_queue_->isBroken()) {
                APPLOG_WARN("broker queue is broken, reset broker queue and all agent queues");
                broker_queue_->reset();
                for (auto agent_queue : agent_queues_) {
                    agent_queue.second->reset();
                }
            } else {
                for(auto agent_queue : agent_queues_) {
                    if (agent_queue.second->isBroken()) {
                        APPLOG_WARN("agent_queue {} is broker, reset it!",
                                agent_queue.first);
                        agent_queue.second->reset();
                    }
                }
            }
            std::this_thread::sleep_for(std::chrono::microseconds(100));
        }
    }
}

void MessageBroker::dispatchMessage(const std::string& topic, const std::string& message_data) {
    APPLOG_INFO("receive message topic={}, message_length={}", topic, message_data.length());

    auto agent_set_it = topic_queues_.find(topic);
    if (agent_set_it == topic_queues_.end()) {
        APPLOG_INFO("no consumer for topic={}", topic);
        return ;
    }
    for (auto agent_name : *(agent_set_it->second)) {
        std::shared_ptr<IMessageQueue> queue = agent_queues_[agent_name];
        if (queue) {
            if (queue->pushMessage(topic, "MessageBroker", (uint8_t*)message_data.data(), message_data.length())) {
                APPLOG_INFO("dispatch Message topic={} message_length={} to agent={} success"
                        , topic, message_data.length(), agent_name);
            } else {
                APPLOG_WARN("dispatch Message topic={} message_length={} to agent={} failed"
                        , topic, message_data.length(), agent_name);
            }
        } else {
            APPLOG_ERROR("find agent={}, but no message queue for this", agent_name);
        }
    }
}

void MessageBroker::processGuardMessages() {
    std::shared_ptr<IMessageGuardStorage::IIterator> it = guard_storage_->iterator();
    if (!it) {
        APPLOG_ERROR("get guard storage iterator failed! maybe guard storage has error");
        return;
    }

    int64_t current_timestamp_s = soldier::base::NowInSeconds();
    while(it->hasNext()) {
        std::string guard_id = it->next();
        if (it->shouldSend(current_timestamp_s)) {
            std::string topic;
            std::string message_data;
            if (!guard_storage_->getGuardMessage(guard_id, topic, message_data)) {
                continue;
            }

            broker_queue_->pushMessage(topic, "GUARD", (uint8_t*)message_data.c_str(), message_data.length());
            guard_storage_->rmGuardMessage(guard_id);
        }
    }

}


} // end namespace message_bus
} // end namespace soldier


