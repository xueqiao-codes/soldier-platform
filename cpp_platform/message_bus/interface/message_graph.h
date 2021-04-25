/*
 * message_graph.h
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_MESSAGE_GRAPH_H_
#define MESSAGE_BUS_MESSAGE_GRAPH_H_

#include <map>
#include <memory>
#include <set>
#include <string>

namespace soldier {
namespace message_bus {

class BrokerItem {
public:
    const std::string& getName() const {
        return name_;
    }

    void setName(const std::string& name) {
        name_ = name;
    }

    const std::string& getType() const {
        return type_;
    }

    void setType(const std::string& type) {
        type_ = type;
    }

    std::map<std::string, std::string>& getProperties() {
        return properties_;
    }

private:
    std::string name_;
    std::string type_;
    std::map<std::string, std::string> properties_;
};

class AgentItem {
public:
    AgentItem() = default;
    ~AgentItem() = default;

    const std::string& getName() const{
        return name_;
    }

    void setName(const std::string& name) {
        name_ = name;
    }

    const std::string& getType() {
        return type_;
    }

    const std::string& getBrokerName() {
        return broker_name_;
    }

    void setBrokerName(const std::string& broker_name) {
        broker_name_ = broker_name;
    }

    void setType(const std::string& type) {
        type_ = type;
    }

    std::set<std::string>& getTopics() {
        return topics_;
    }

    std::map<std::string, std::string>& getProperties() {
        return properties_;
    }

private:
    std::string name_;
    std::string type_;
    std::string broker_name_;
    std::set<std::string> topics_;

    std::map<std::string, std::string> properties_;
};

/**
 *  描述所有的订阅关系
 */
class MessageGraph {
public:
    MessageGraph() = default;
    ~MessageGraph() = default;

    std::map<std::string, std::shared_ptr<BrokerItem>>& getBrokers() {
        return brokers_;
    }

    std::map<std::string, std::shared_ptr<AgentItem>>& getAgents() {
        return agents_;
    }

private:
    /**
     * key : broker_name
     */
    std::map<std::string, std::shared_ptr<BrokerItem>> brokers_;

    /**
     *  key : consumer_name
     */
    std::map<std::string, std::shared_ptr<AgentItem>> agents_;
};


} // end namespace message_bus
} // end namespace



#endif /* MESSAGE_BUS_MESSAGE_GRAPH_H_ */
