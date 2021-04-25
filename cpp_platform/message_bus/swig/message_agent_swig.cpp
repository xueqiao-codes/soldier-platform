/*
 * message_agent_swig.cpp
 *
 *  Created on: 2017年12月27日
 *      Author: wileywang
 */

#include "message_bus/swig/message_agent_swig.h"

#include <map>
#include <memory>
#include "base/app_log.h"
#include "message_bus/interface/message_agent.h"
#include "message_bus/interface/message_consumer.h"
#include "message_bus/interface/message_graph_holder.h"

namespace soldier {
namespace message_bus {

class MessageConsumerAdaptor : public IMessageConsumer {
public:
    MessageConsumerAdaptor(IMessageConsumerSwig* callback)
        : callback_(callback) {
    }

    virtual ~MessageConsumerAdaptor() = default;

    virtual StartUpMode onStartUp() {
        int result = callback_->onStartUp();
        if (result == STARTUPMODE_CLEAR_QUEUE_INIT) {
            return StartUpMode::CLEAR_QUEUE_INIT;
        }
        return StartUpMode::RESERVE_QUEUE;
    }

    virtual ConsumeResult onConsumeMessage(
                const std::string& topic
                , const std::string& message) {
        int result = callback_->onConsumeMessage(topic, (uint8_t*)message.data(), message.size());
        if (result == MESSAGE_CONSUME_OK) {
            return ConsumeResult::CONSUME_OK;
        } else if (result == MESSAGE_CONSUME_FAILED_DROP) {
            return ConsumeResult::CONSUME_FAILED_DROP;
        }
        return ConsumeResult::CONSUME_RETRY;
    }

private:
    IMessageConsumerSwig* callback_;
};

struct MessageAgentEntry {
    std::shared_ptr<MessageConsumerAdaptor> consumer_adaptor;
    std::shared_ptr<MessageAgent> message_agent;
};

static std::atomic_flag swig_lock_ = ATOMIC_FLAG_INIT;
static std::map<std::string, std::shared_ptr<MessageGraphHolder>> message_graph_holders;
static std::map<std::string, std::shared_ptr<MessageAgentEntry>> message_agents;

void MessageAgentSwig::initNativeLogName(const std::string& name) {
    soldier::base::AppLog::Init("message_agent_swig/" + name);
}

bool MessageAgentSwig::initMessageAgent(
        const std::string& agent_name
        , const std::string& message_graph_path
        , IMessageConsumerSwig* consumer) {
    while(std::atomic_flag_test_and_set_explicit(
                                    &swig_lock_, std::memory_order_seq_cst));

    auto message_agent_it = message_agents.find(agent_name);
    if (message_agent_it != message_agents.end()) {
        swig_lock_.clear();
        return false;
    }

    auto message_graph_holder_it = message_graph_holders.find(message_graph_path);
    if (message_graph_holder_it == message_graph_holders.end()) {
        message_graph_holder_it = message_graph_holders.insert(
                std::pair<std::string, std::shared_ptr<MessageGraphHolder>>(
                        message_graph_path
                        , MessageGraphHolder::create(message_graph_path))
                ).first;
    }

    std::shared_ptr<MessageAgentEntry> entry(new MessageAgentEntry());
    entry->consumer_adaptor.reset(new MessageConsumerAdaptor(consumer));
    std::shared_ptr<MessageAgent> message_agent = MessageAgent::create(
                agent_name, entry->consumer_adaptor, message_graph_holder_it->second);
    if (!message_agent) {
        swig_lock_.clear();
        return false;
    }
    entry->message_agent = message_agent;
    message_agents[agent_name] = entry;

    swig_lock_.clear();
    return true;
}

void MessageAgentSwig::destroyMessageAgent(const std::string& agent_name) {
    while(std::atomic_flag_test_and_set_explicit(
                                        &swig_lock_, std::memory_order_seq_cst));
    message_agents.erase(agent_name);
    swig_lock_.clear();
}

bool MessageAgentSwig::sendMessageDirectly(
        const std::string& agent_name
        , const std::string& topic
        , uint8_t* data
        , size_t size) {
    std::shared_ptr<MessageAgentEntry> message_agent_entry;
    while(std::atomic_flag_test_and_set_explicit(
                                &swig_lock_, std::memory_order_seq_cst));
    message_agent_entry = message_agents[agent_name];
    swig_lock_.clear();

    if (!message_agent_entry) {
        return false;
    }

    TMessage msg;
    msg.topic = topic;
    msg.from = "message_agent_swig";
    msg.message_data = data;
    msg.message_length = size;

    return message_agent_entry->message_agent->sendMessageDirectly(msg);
}

std::string MessageAgentSwig::prepareGuardMessage(const std::string& agent_name
            , const std::string& topic
            , uint8_t* data
            , size_t size
            , GuardPolicySwig policy) {
    std::shared_ptr<MessageAgentEntry> message_agent_entry;
    while(std::atomic_flag_test_and_set_explicit(
                                    &swig_lock_, std::memory_order_seq_cst));
    message_agent_entry = message_agents[agent_name];
    swig_lock_.clear();

    if (!message_agent_entry) {
        return "";
    }

    TMessage msg;
    msg.topic = topic;
    msg.from = "message_agent_swig";
    msg.message_data = data;
    msg.message_length = size;

    GuardPolicy nativePolicy;
    nativePolicy.type = GuardPolicyType::TIMEOUT_POLICY;
    if (policy.timeout_seconds > 0) {
        nativePolicy.timeout_seconds = policy.timeout_seconds;
    }

    return message_agent_entry->message_agent->prepareGuardMessage(msg, nativePolicy);
}

bool MessageAgentSwig::sendMessageAndRmGuard(const std::string& agent_name
            , const std::string& topic
            , uint8_t* data
            , size_t size
            , const std::string& guard_id) {
    std::shared_ptr<MessageAgentEntry> message_agent_entry;
    while(std::atomic_flag_test_and_set_explicit(
                                       &swig_lock_, std::memory_order_seq_cst));
    message_agent_entry = message_agents[agent_name];
    swig_lock_.clear();

    if (!message_agent_entry) {
        return false;
    }

    TMessage msg;
    msg.topic = topic;
    msg.from = "message_agent_swig";
    msg.message_data = data;
    msg.message_length = size;

    return message_agent_entry->message_agent->sendMessageAndRmGuard(msg, guard_id);
}

bool MessageAgentSwig::rmGuardMessage(const std::string& agent_name, const std::string& guard_id) {
    std::shared_ptr<MessageAgentEntry> message_agent_entry;
    while(std::atomic_flag_test_and_set_explicit(
            &swig_lock_, std::memory_order_seq_cst));
    message_agent_entry = message_agents[agent_name];
    swig_lock_.clear();

    if (!message_agent_entry) {
        return false;
    }

    return message_agent_entry->message_agent->rmGuardMessage(guard_id);
}


} // end namespace message_bus
} // end namespace soldier


