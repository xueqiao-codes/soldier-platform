/*
 * shm_message_queue.cpp
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */

#include "message_bus/queue/shm_message_queue.h"

#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>

#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/hash.h"
#include "base/os_helper.h"
#include "message_bus/interface/framework_topics.h"

namespace soldier {
namespace message_bus {

ShmMessageQueue* ShmMessageQueue::Init(
        const std::string& file_lock_dir
        , int shm_key
        , size_t shm_size) {
    std::string queue_lock_file
        = file_lock_dir + "/" + boost::lexical_cast<std::string>(shm_key) + "_queue";
    if (!boost::filesystem::exists(queue_lock_file)) {
        APPLOG_INFO("touch queue lock file {}", queue_lock_file);
        CHECK(soldier::base::OSHelper::touchFile(queue_lock_file));
    }

    std::unique_ptr<ShmMessageQueue> queue(new ShmMessageQueue());
    if (!queue->initInstance(shm_key, shm_size, queue_lock_file)) {
        return nullptr;
    }
    return queue.release();
}

ShmMessageQueue* ShmMessageQueue::Get(int shm_key) {
    std::unique_ptr<ShmMessageQueue> queue(new ShmMessageQueue());
    if (!queue->attachInstance(shm_key)) {
        return nullptr;
    }
    return queue.release();
}

bool ShmMessageQueue::initInstance(int shm_key
            , size_t shm_size
            , const std::string& queue_lock_file) {
    CHECK(shm_size > sizeof(ShmQueueHeader) + MAX_MESSAGE_SIZE * 2);

    size_t real_shm_size = alignMemoryBytes(shm_size);
    if (!shm_object_.createOrAttach(shm_key, real_shm_size)) {
        APPLOG_ERROR("shm create or attach failed, shm_key={}, real_shm_size={}, error={}"
                , shm_key, real_shm_size, shm_object_.getLastErrMsg());
        return false;
    }

    queue_process_lock_.reset(new boost::interprocess::file_lock(queue_lock_file.c_str()));

    shm_size_ = real_shm_size;
    queue_lock_file_ = queue_lock_file;

    {
        AutoQueueLock auto_queue_lock(*this);
        header_ = (ShmQueueHeader*)shm_object_.getShmAddr();
        if (header_->isInited()) {
            return true;
        }
        initHeader();
        CHECK(pushMessageUnLock(INIT_TOPIC, "ShmMessageQueue::initInstance", (uint8_t*)INIT_TOPIC.data(), INIT_TOPIC.length()));
    }

    return true;
}

bool ShmMessageQueue::attachInstance(int shm_key) {
    if (!shm_object_.attach(shm_key)) {
        APPLOG_ERROR("shm attach failed, shm_key={}, error={}" , shm_key, shm_object_.getLastErrMsg());
        return false;
    }

    header_ = (ShmQueueHeader*)shm_object_.getShmAddr();
    bool is_inited = header_->isInited();
    bool has_error = header_->hasError();
    if ((!is_inited) || has_error) {
        APPLOG_ERROR("shm has not inited or has error for attach,  shm_key={} is_inited={}, has_error={}"
                , shm_key, is_inited, has_error);
        return false;
    }

    queue_process_lock_.reset(new boost::interprocess::file_lock(header_->getQueueLockFilePath()));
    shm_size_ = header_->shm_size_;
    queue_lock_file_ = header_->getQueueLockFilePath();

    return true;
}

void ShmMessageQueue::initHeader() {
    header_ = new(shm_object_.getShmAddr()) ShmQueueHeader();
    header_->setShmSize(shm_size_);
    header_->setQueueFileLockPath(queue_lock_file_);
    header_->setMessageOffset(0, 0);
    header_->setInited();
}

bool ShmMessageQueue::pushMessage(const std::string& topic
        , const std::string& from
        , const uint8_t* data
        , const size_t& length) {
    if (topic.empty()) {
        APPLOG_WARN("pushMessage called, but topic is empty, from={}", from);
        return false;
    }
    if (length == 0) {
        APPLOG_WARN("pushMessage called, but length is 0, from={}", from);
        return false;
    }
    if (data == nullptr) {
        APPLOG_WARN("pushMessage called, but data is null, from={}", from);
        return false;
    }

    uint32_t need_size = alignMemoryBytes(sizeof(TMessageHeader) + topic.length() + length);
    if (need_size > MAX_MESSAGE_SIZE) {
        APPLOG_WARN("pushMessage called, but message size is too big, need_size={} > {} from={}"
                , length, MAX_MESSAGE_SIZE, from);
        return false;
    }

    {
        AutoQueueLock auto_queue_lock(*this);
        pushMessageUnLock(topic, from, data, length);
    }

    return true;
}

bool ShmMessageQueue::peekMessage(std::string& topic, std::string& message_data) {
    AutoQueueLock auto_queue_lock(*this);
    if (!checkHeader()) {
        return false;
    }

    uint32_t origin_offset_begin = 0;
    uint32_t origin_offset_end = 0;

    TMessageHeader* top_message = getTopMessage(origin_offset_begin, origin_offset_end);
    if (!top_message) {
        return false;
    }

    topic.resize(top_message->topic_length);
    message_data.resize(top_message->data_length);
    memcpy((void*)topic.data(), top_message->variant, top_message->topic_length);
    memcpy((void*)message_data.data(), top_message->variant + top_message->topic_length, top_message->data_length);

    return true;
}

bool ShmMessageQueue::popMessage() {
    AutoQueueLock auto_queue_lock(*this);
    if (!checkHeader()) {
        return false;
    }

    uint32_t origin_offset_begin = 0;
    uint32_t origin_offset_end = 0;
    TMessageHeader* top_message = getTopMessage(origin_offset_begin, origin_offset_end);
    if (!top_message) {
        return true;
    }

    uint32_t need_size = alignMemoryBytes(sizeof(TMessageHeader) + top_message->topic_length + top_message->data_length);
    uint32_t new_offset_begin = origin_offset_begin + need_size;
    if (new_offset_begin + MAX_MESSAGE_SIZE > header_->message_total_size_) {
        new_offset_begin = 0;
    }

    setOffset(new_offset_begin, origin_offset_end);
    return true;
}

void ShmMessageQueue::reset() {
    APPLOG_WARN("reset shm message queue, key={}", shm_object_.getShmKey());
    {
        AutoQueueLock auto_queue_lock(*this);
        initHeader();
        CHECK(pushMessageUnLock(INIT_TOPIC, "ShmMessageQueue::reset", (uint8_t*)INIT_TOPIC.data(), INIT_TOPIC.length()));
    }
}

bool ShmMessageQueue::pushMessageUnLock(const std::string& topic
            , const std::string& from
            , const uint8_t* data
            , const size_t& length) {
    uint32_t need_size = alignMemoryBytes(sizeof(TMessageHeader) + topic.length() + length);
    if (!checkHeader()) {
        return false;
    }

    uint32_t origin_offset_begin = 0;
    uint32_t origin_offset_end = 0;
    header_->getMessageOffset(origin_offset_begin, origin_offset_end);

    if (!checkOffset(origin_offset_begin, origin_offset_end)) {
        return false;
    }

    uint32_t tail_size = 0;
    if (origin_offset_begin > origin_offset_end) {
        tail_size = origin_offset_begin - origin_offset_end - 1;
    } else {
        tail_size =  header_->message_total_size_- origin_offset_end;
    }
    if (tail_size < need_size) {
        APPLOG_WARN("pushMessage called, but memory is full, from={}", from);
        // 对于消息满，无法接受消息，刺激应用层重置
        header_->setError();
        return false;
    }

    uint32_t new_offset_end = origin_offset_end + need_size;
    if (new_offset_end + MAX_MESSAGE_SIZE > header_->message_total_size_) {
        // 无法重置头部，相当于内存满
        if (origin_offset_begin == 0) {
            APPLOG_WARN("pushMessage called, but memory is full, from={}", from);
            header_->setError();
            return false;
        }
        new_offset_end = 0;
    }

    TMessageHeader* new_msg = (TMessageHeader*)(header_->message_queue_ + origin_offset_end);
    new_msg->message_flag = MESSAGE_FLAG;
    new_msg->topic_length = topic.length();
    new_msg->data_length = length;
    new_msg->message_crc32 = soldier::base::calCrc32((char*)data, length);

    memcpy(new_msg->variant, topic.data(), topic.length());
    memcpy(new_msg->variant + topic.length(), data, length);

    setOffset(origin_offset_begin, new_offset_end);
    return true;
}

bool ShmMessageQueue::checkOffset(const uint32_t& begin_offset, const uint32_t& end_offset) {
    if (begin_offset > header_->message_total_size_
        || end_offset > header_->message_total_size_
        || !(begin_offset % (sizeof(int*))) == 0
        || !(end_offset % (sizeof(int*))) == 0) {
        APPLOG_ERROR("offset error, shared memory error! key={}", shm_object_.getShmKey());
        header_->setError();
        return false;
    }
    return true;
}

bool ShmMessageQueue::checkHeader() {
    if (!header_->checkHeader() || header_->hasError()) {
        APPLOG_ERROR("header error, shared memory error, has_error={}! key={}"
                , header_->hasError(), shm_object_.getShmKey());
        header_->setError();
        return false;
    }
    return true;
}

bool ShmMessageQueue::checkMessage(const TMessageHeader* message) {
    if (message->message_flag != MESSAGE_FLAG) {
        APPLOG_ERROR("message flag error, shared memory error! key={}", shm_object_.getShmKey());
        header_->setError();
        return false;
    }
    if (message->topic_length <= 0 || message->data_length <= 0) {
        APPLOG_ERROR("message topic length or data length error! key={}", shm_object_.getShmKey());
        header_->setError();
        return false;
    }
    uint32_t need_size = alignMemoryBytes(sizeof(TMessageHeader) + message->topic_length + message->data_length);
    if (need_size > MAX_MESSAGE_SIZE) {
        APPLOG_ERROR("message length error, share memory error! key={}", shm_object_.getShmKey());
        header_->setError();
        return false;
    }
    int32_t crc32 = soldier::base::calCrc32((char*)(message->variant + message->topic_length), message->data_length);
    if (crc32 != message->message_crc32) {
        APPLOG_ERROR("message crc32 error, message_crc32={} is not equals to {} ! key={}", message->message_crc32
                , crc32, shm_object_.getShmKey());
        header_->setError();
        return false;
    }
    return true;
}

ShmMessageQueue::TMessageHeader* ShmMessageQueue::getTopMessage(uint32_t& origin_offset_begin, uint32_t& origin_offset_end) {
    header_->getMessageOffset(origin_offset_begin, origin_offset_end);
    if (!checkOffset(origin_offset_begin, origin_offset_end)) {
        return nullptr;
    }

    if (origin_offset_begin == origin_offset_end) {
        return nullptr;
    }

    TMessageHeader* top_message = (TMessageHeader*)(header_->message_queue_ + origin_offset_begin);
    if (!checkMessage(top_message)) {
        return nullptr;
    }
    return top_message;
}


void ShmMessageQueue::setOffset(const uint32_t& begin_offset, const uint32_t& end_offset) {
    header_->setMessageOffset(begin_offset, end_offset);
    APPLOG_DEBUG("setOffset shmKey={}, begin_offset={}, end_offset={}, message_offset_={}",
            shm_object_.getShmKey(), begin_offset, end_offset, header_->message_offset_);
}

} // end namespace message_bus
} // end namespace soldier


