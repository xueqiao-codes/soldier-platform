/*
 * message_graph_holder.cpp
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#include "message_bus/interface/message_graph_holder.h"

#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/smart_ptr_helper.h"
#include "watcher/file_watcher_module.h"
#include "rapidjson/document.h"
#include "rapidjson/reader.h"
#include "rapidjson/filereadstream.h"
#include "rapidjson/error/en.h"

namespace soldier {
namespace message_bus {

std::shared_ptr<MessageGraphHolder> MessageGraphHolder::create(const std::string& graph_file) {
    std::shared_ptr<MessageGraphHolder> instance(new MessageGraphHolder(graph_file));
    soldier::watcher::FileWatcherModule::Instance().addWatchFile(graph_file, instance);
    {
        std::unique_lock<std::mutex> auto_lock(instance->lock_);
        instance->current_graph_ = instance->consturctMessageGraph();
        CHECK(instance->current_graph_);
    }

    return instance;
}

MessageGraphHolder::MessageGraphHolder(const std::string& graph_file)
    : graph_file_(graph_file) {
}

void MessageGraphHolder::onFileChanged(const std::string& file_path) {
    std::unique_lock<std::mutex> auto_lock(lock_);

    APPLOG_WARN("[NOTICE]on {} changed...", file_path);

    int count = 0;
    std::shared_ptr<MessageGraph> new_graph;
    while((count++) < 3) {
        new_graph = consturctMessageGraph();
        if (!new_graph) {
            std::this_thread::sleep_for(std::chrono::seconds(1));
        }
    }

    if (!new_graph) {
        return ;
    }

    current_graph_ = new_graph;
    std::vector<std::shared_ptr<IMessageGraphListener>> listenerList;
    getListeners(listenerList);
    for (auto& listener : listenerList) {
        listener->onMessageGraphChanged();
    }
}

std::shared_ptr<MessageGraph> MessageGraphHolder::consturctMessageGraph() {
    std::unique_ptr<FILE, soldier::base::FileDeleter> file(fopen(graph_file_.c_str(), "rb"));

    char buffer[1024];
    rapidjson::FileReadStream stream(file.get(), buffer, sizeof(buffer)/sizeof(char));

    try {
        rapidjson::Document root;
        root.ParseStream(stream);

        if (root.HasParseError()) {
            APPLOG_ERROR("config error {}, offset={}"
                    , rapidjson::GetParseError_En(root.GetParseError()), root.GetErrorOffset());
            return std::shared_ptr<MessageGraph>();
        }

        std::shared_ptr<MessageGraph> message_graph(new MessageGraph());

        const rapidjson::Value& brokers_array = root["brokers"];
        APPLOG_INFO("parse brokers, brokers size={}", brokers_array.Size());
        for(rapidjson::SizeType broker_index = 0; broker_index < brokers_array.Size(); ++broker_index) {
            const rapidjson::Value& broker_object = brokers_array[broker_index];

            std::shared_ptr<BrokerItem> broker_item(new BrokerItem());
            broker_item->setName(broker_object["name"].GetString());
            broker_item->setType(broker_object["type"].GetString());

            const rapidjson::Value& broker_properties_object = broker_object["properties"];
            for (rapidjson::Value::ConstMemberIterator itprop = broker_properties_object.MemberBegin();
                    itprop != broker_properties_object.MemberEnd(); ++itprop) {
                broker_item->getProperties()[itprop->name.GetString()] = itprop->value.GetString();
            }

            APPLOG_INFO("broker index={}, name={}, type={}", broker_index
                    , broker_item->getName(), broker_item->getType());

            message_graph->getBrokers()[broker_item->getName()] = broker_item;
        }

        const rapidjson::Value& agents_array = root["agents"];
        APPLOG_INFO("parse agents, agents size={}", agents_array.Size());
        for(rapidjson::SizeType agent_index = 0
                ; agent_index < agents_array.Size(); ++agent_index) {
            const rapidjson::Value& consumer_object = agents_array[agent_index];

            std::shared_ptr<AgentItem> agent_item(new AgentItem());
            agent_item->setName(consumer_object["name"].GetString());
            agent_item->setType(consumer_object["type"].GetString());
            agent_item->setBrokerName(consumer_object["broker_name"].GetString());

            const rapidjson::Value& consumer_topics_array = consumer_object["topics"];
            for (rapidjson::SizeType consumer_topic_index = 0
                    ; consumer_topic_index < consumer_topics_array.Size(); ++consumer_topic_index) {
                agent_item->getTopics().insert(consumer_topics_array[consumer_topic_index].GetString());
            }

            const rapidjson::Value& consumer_properties_object = consumer_object["properties"];
            for (rapidjson::Value::ConstMemberIterator itprop = consumer_properties_object.MemberBegin();
                    itprop != consumer_properties_object.MemberEnd(); ++itprop) {
                agent_item->getProperties()[itprop->name.GetString()] = itprop->value.GetString();
            }

            APPLOG_INFO("agent index={}, name={}, type={}", agent_index
                    , agent_item->getName(), agent_item->getType());
            message_graph->getAgents()[agent_item->getName()] = agent_item;
        }
        return message_graph;
    } catch (std::exception& e) {
        APPLOG_ERROR("ParseConfig Error!{}", e.what());
    }

    return std::shared_ptr<MessageGraph>();
}



} // end namespace message_bus
} // end namespace soldier

