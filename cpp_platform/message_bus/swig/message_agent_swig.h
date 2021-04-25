/*
 * message_agent_swig.h
 *
 *  Created on: 2017年12月27日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_SWIG_MESSAGE_AGENT_SWIG_H_
#define MESSAGE_BUS_SWIG_MESSAGE_AGENT_SWIG_H_

#include <string>

#define MESSAGE_CONSUME_OK 0
#define MESSAGE_CONSUME_FAILED_DROP 1
#define MESSAGE_RETRY  2

#define STARTUPMODE_CLEAR_QUEUE_INIT 0
#define STARTUPMODE_RESERVE_QUEUE 1

#define GUARD_POLICY_TIMEOUT 0

namespace soldier {
namespace message_bus {

struct GuardPolicySwig {
    int type;
    int timeout_seconds;
};

class IMessageConsumerSwig {
public:
    virtual ~IMessageConsumerSwig() {}

    virtual int onStartUp() = 0;

    virtual int onConsumeMessage(const std::string& topic
            , uint8_t* data, size_t size) = 0;

protected:
    IMessageConsumerSwig() {}
};


class MessageAgentSwig {
public:
    static void initNativeLogName(const std::string& name);

    static bool initMessageAgent(const std::string& agent_name
            , const std::string& message_graph_path
            , IMessageConsumerSwig* consumer);
    static void destroyMessageAgent(const std::string& agent_name);

    static std::string prepareGuardMessage(const std::string& agent_name
            , const std::string& topic
            , uint8_t* data
            , size_t size
            , GuardPolicySwig policy);

    static bool sendMessageDirectly(const std::string& agent_name
            , const std::string& topic
            , uint8_t* data
            , size_t size);

    static bool sendMessageAndRmGuard(const std::string& agent_name
            , const std::string& topic
            , uint8_t* data
            , size_t size
            , const std::string& guard_id);

    static bool rmGuardMessage(const std::string& agent_name, const std::string& guard_id);
};



} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_SWIG_MESSAGE_AGENT_SWIG_H_ */
