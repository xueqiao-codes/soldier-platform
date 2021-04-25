/*
 * message_factory.cpp
 *
 *  Created on: 2017年12月26日
 *      Author: wileywang
 */

#include "message_bus/interface/message_factory.h"

#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"
#include "message_bus/queue/empty_message_queue.h"
#include "message_bus/queue/shm_message_queue.h"
#include "message_bus/guard/shm_message_guard_storage.h"

namespace soldier {
namespace message_bus {

static const std::string SHARE_MEMORY_TYPE = "share_memory";
static const std::string EMPTY_QUEUE_TYPE = "empty";
static const std::string DEFAULT_FILE_LOCKS_DIR = "/data/message_bus/locks";

std::shared_ptr<IMessageGuardStorage> MessageFactory::createGuardStorage(
            const std::shared_ptr<BrokerItem>& broker_item) {
    CHECK(broker_item);

    if (SHARE_MEMORY_TYPE == broker_item->getType()) {
        std::string share_guard_memory_key_str = broker_item->getProperties()["share_guard_memory_key"];
        std::string file_lock_dir = broker_item->getProperties()["file_lock_dir"];
        if (file_lock_dir.empty()) {
            file_lock_dir = DEFAULT_FILE_LOCKS_DIR;
        }
        try {
            return std::shared_ptr<IMessageGuardStorage>(
                    ShmMessageGuardStorage::Init(file_lock_dir
                                , boost::lexical_cast<int>(share_guard_memory_key_str)));
        } catch (...) {
            APPLOG_ERROR("create {} broker guard storage failed, type={} share_guard_memory_key_str={}"
                    ", file_lock_dir={}"
                    , broker_item->getName()
                    , broker_item->getType()
                    , share_guard_memory_key_str
                    , file_lock_dir);
        }
    } else {
        APPLOG_ERROR("broker type={} is not supported!", broker_item->getType());
    }
    return std::shared_ptr<IMessageGuardStorage>();
}

std::shared_ptr<IMessageQueue> MessageFactory::createBrokerQueue(
                const std::shared_ptr<BrokerItem>& broker_item) {
    CHECK(broker_item);

    if (SHARE_MEMORY_TYPE == broker_item->getType()) {
        std::string share_queue_memory_key_str = broker_item->getProperties()["share_queue_memory_key"];
        std::string share_queue_memory_size_str = broker_item->getProperties()["share_queue_memory_size"];
        std::string file_lock_dir = broker_item->getProperties()["file_lock_dir"];
        if (file_lock_dir.empty()) {
            file_lock_dir = DEFAULT_FILE_LOCKS_DIR;
        }
        try {
            return std::shared_ptr<IMessageQueue>(
                    ShmMessageQueue::Init(file_lock_dir
                            , boost::lexical_cast<int>(share_queue_memory_key_str)
                            , boost::lexical_cast<size_t>(share_queue_memory_size_str)));
        } catch (...) {
            APPLOG_ERROR("create {} broker queue failed, type={} share_memory_key={}"
                    ", share_memory_size={}, file_lock_dir={}"
                    , broker_item->getName()
                    , broker_item->getType()
                    , share_queue_memory_key_str
                    , share_queue_memory_size_str
                    , file_lock_dir);
        }
    } else {
        APPLOG_ERROR("broker type={} is not supported!", broker_item->getType());
    }

    return std::shared_ptr<IMessageQueue>();
}

std::shared_ptr<IMessageQueue> MessageFactory::createAgentQueue(
                const std::shared_ptr<AgentItem>& agent_item) {
    CHECK(agent_item);

    if (SHARE_MEMORY_TYPE == agent_item->getType()) {
        std::string share_memory_key_str = agent_item->getProperties()["share_queue_memory_key"];
        std::string share_memory_size_str = agent_item->getProperties()["share_queue_memory_size"];
        std::string file_lock_dir = agent_item->getProperties()["file_lock_dir"];
        if (file_lock_dir.empty()) {
            file_lock_dir = DEFAULT_FILE_LOCKS_DIR;
        }
        try {
            return std::shared_ptr<IMessageQueue>(
                    ShmMessageQueue::Init(file_lock_dir
                            , boost::lexical_cast<int>(share_memory_key_str)
                            , boost::lexical_cast<size_t>(share_memory_size_str)));
        } catch (...) {
            APPLOG_ERROR("create {} agent queue failed! type={}, share_memory_key={}"
                    ", share_memory_size={}, file_lock_dir={}"
                    , agent_item->getName()
                    , agent_item->getType()
                    , share_memory_key_str
                    , share_memory_size_str
                    , file_lock_dir);
        }
    } else if (EMPTY_QUEUE_TYPE == agent_item->getType()) {
        return std::shared_ptr<IMessageQueue>(new EmptyMessageQueue());
    } else {
        APPLOG_ERROR("agent type={} is not supported!", agent_item->getType());
    }

    return std::shared_ptr<IMessageQueue>();
}

std::shared_ptr<IMessageGuardStorage> MessageFactory::attachGuardStorage(
            const std::shared_ptr<BrokerItem>& broker_item) {
    CHECK(broker_item);

    if (SHARE_MEMORY_TYPE == broker_item->getType()) {
        std::string share_guard_memory_key_str = broker_item->getProperties()["share_guard_memory_key"];
        try {
            return std::shared_ptr<IMessageGuardStorage>(ShmMessageGuardStorage::Get(
                        boost::lexical_cast<int>(share_guard_memory_key_str)));
        } catch (...) {
            APPLOG_ERROR("attach {} broker guard storage failed, type={} share_guard_memory_key={}"
                    , broker_item->getName()
                    , broker_item->getType()
                    , share_guard_memory_key_str);
        }
    } else {
        APPLOG_ERROR("broker type={} is not supported!", broker_item->getType());
    }

    return std::shared_ptr<IMessageGuardStorage>();
}

std::shared_ptr<IMessageQueue> MessageFactory::attachBrokerQueue(
                const std::shared_ptr<BrokerItem>& broker_item) {
    CHECK(broker_item);

    if (SHARE_MEMORY_TYPE == broker_item->getType()) {
        std::string share_queue_memory_key_str = broker_item->getProperties()["share_queue_memory_key"];
        try {
            return std::shared_ptr<IMessageQueue>(
                    ShmMessageQueue::Get(boost::lexical_cast<int>(share_queue_memory_key_str)));
        } catch (...) {
            APPLOG_ERROR("attach {} broker queue failed, type={} share_queue_memory_key={}"
                    , broker_item->getName()
                    , broker_item->getType()
                    , share_queue_memory_key_str);
        }
    } else {
        APPLOG_ERROR("broker type={} is not supported!", broker_item->getType());
    }

    return std::shared_ptr<IMessageQueue>();
}

std::shared_ptr<IMessageQueue> MessageFactory::attachAgentQueue(
                const std::shared_ptr<AgentItem>& agent_item) {
    CHECK(agent_item);

    if (SHARE_MEMORY_TYPE == agent_item->getType()) {
        std::string share_queue_memory_key_str = agent_item->getProperties()["share_queue_memory_key"];

        try {
            return std::shared_ptr<IMessageQueue>(
                    ShmMessageQueue::Get(boost::lexical_cast<int>(share_queue_memory_key_str)));
        } catch (...) {
            APPLOG_ERROR("attach {} agent queue failed, name={}, share_queue_memory_key={}"
                    , agent_item->getType(), agent_item->getName()
                    , share_queue_memory_key_str);
        }
    } else if (EMPTY_QUEUE_TYPE == agent_item->getType()) {
        return std::shared_ptr<IMessageQueue>(new EmptyMessageQueue());
    } else {
        APPLOG_ERROR("consumer type={} is not supported!", agent_item->getType());
    }

    return std::shared_ptr<IMessageQueue>();
}


} // end namespace message_bus
} // end namespace soldier


