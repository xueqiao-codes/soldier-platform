/*
 * shm_message_guard_storage.cpp
 *
 *  Created on: 2018年1月2日
 *      Author: wileywang
 */

#include "message_bus/guard/shm_message_guard_storage.h"

#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "base/hash.h"
#include "base/os_helper.h"
#include "base/string_util.h"
#include "base/time_helper.h"

namespace soldier {
namespace message_bus {

class ShmMessageGuardIterator : public IMessageGuardStorage::IIterator {
public:
    ShmMessageGuardIterator() = default;
    virtual ~ShmMessageGuardIterator() = default;

    virtual bool hasNext() {
        if (current_ < guard_num_ - 1) {
            return true;
        }
        return false;
    }
    virtual std::string next() {
        current_ += 1;
        return entries_[current_].guard_id;
    }
    virtual bool shouldSend(const int32_t& current_timestamp_s) {
        if (current_timestamp_s >
            entries_[current_].prepared_timestamp_s + entries_[current_].policy.timeout_seconds) {
            return true;
        }
        return false;
    }

    void addGuardEntry(std::string guard_id
        , GuardPolicy policy
        , int32_t prepared_timestamp_s) {
        entries_[guard_num_].guard_id = guard_id;
        entries_[guard_num_].policy = policy;
        entries_[guard_num_].prepared_timestamp_s = prepared_timestamp_s;
        guard_num_ += 1;
    }

private:
    struct Entry {
        std::string guard_id;
        GuardPolicy policy;
        int32_t prepared_timestamp_s;
    };

    int32_t current_ = -1;
    int32_t guard_num_ = 0;
    Entry entries_[MAX_GUARD_NUM];
};


ShmMessageGuardStorage* ShmMessageGuardStorage::Init(
        const std::string& file_lock_dir
        , int shm_key) {
    std::string guard_lock_file
        = file_lock_dir + "/" + boost::lexical_cast<std::string>(shm_key) + "_guard";
    if (!boost::filesystem::exists(guard_lock_file)) {
        APPLOG_INFO("touch guard lock file {}", guard_lock_file);
        CHECK(soldier::base::OSHelper::touchFile(guard_lock_file));
    }

    std::unique_ptr<ShmMessageGuardStorage> storage(new ShmMessageGuardStorage());
    if (!storage->initInstance(shm_key, guard_lock_file)) {
        return nullptr;
    }
    return storage.release();
}

ShmMessageGuardStorage* ShmMessageGuardStorage::Get(int shm_key) {
    std::unique_ptr<ShmMessageGuardStorage> storage(new ShmMessageGuardStorage());
    if (!storage->attachInstance(shm_key)) {
        return nullptr;
    }
    return storage.release();
}

bool ShmMessageGuardStorage::initInstance(int32_t shm_key, const std::string& guard_lock_file) {
    if (!shm_object_.createOrAttach(shm_key, sizeof(ShmMessageGuardHeader))) {
        APPLOG_ERROR("shm create or attach failed, shm_key={},, error={}"
                , shm_key, shm_object_.getLastErrMsg());
        return false;
    }

    guard_process_lock_.reset(new boost::interprocess::file_lock(guard_lock_file.c_str()));
    guard_lock_file_ = guard_lock_file;

    {
        AutoGuardLock auto_lock(*this);
        header_ = (ShmMessageGuardHeader*)shm_object_.getShmAddr();
        if (header_->isInited()) {
            return true;
        }
        initHeader();
    }

    return true;
}

bool ShmMessageGuardStorage::attachInstance(int32_t shm_key) {
    if (!shm_object_.attach(shm_key)) {
        APPLOG_ERROR("shm attach failed, shm_key={}, error={}" , shm_key, shm_object_.getLastErrMsg());
        return false;
    }

    header_ = (ShmMessageGuardHeader*)shm_object_.getShmAddr();
    bool is_inited = header_->isInited();
    bool has_error = header_->hasError();
    if ((!is_inited) || has_error) {
        APPLOG_ERROR("shm has not inited or has error for attach,  shm_key={} is_inited={}, has_error={}"
                , shm_key, is_inited, has_error);
        return false;
    }

    guard_process_lock_.reset(new boost::interprocess::file_lock(header_->getGuardLockFilePath()));
    guard_lock_file_ = header_->getGuardLockFilePath();

    return true;
}

void ShmMessageGuardStorage::initHeader() {
    header_ = new(shm_object_.getShmAddr()) ShmMessageGuardHeader();
    for (int index = 0; index < MAX_GUARD_NUM; ++index) {
        header_->items[index].next_item = index + 1;
        header_->items[index].prev_item = index - 1;
        header_->items[index].cur_index = index;
        header_->items[index].message_id = -1;
    }
    header_->items[0].prev_item = -1;
    header_->items[MAX_GUARD_NUM - 1].next_item = -1;
    header_->free_guard_item_ = 0;
    header_->free_guard_num_ = MAX_GUARD_NUM;
    header_->last_message_id_ = 0;
    header_->used_guard_item_ = -1;
    header_->setGuardFileLockPath(guard_lock_file_);
    header_->setInited();
}

std::string ShmMessageGuardStorage::prepareGuardMessage(
        const std::string& topic
        , const std::string& from
        , const uint8_t* data
        , const size_t& length
        , const GuardPolicy& policy) {
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

    uint32_t need_size = topic.length() + length;
    if (need_size > MAX_MESSAGE_SIZE) {
        APPLOG_WARN("pushMessage called, but message size is too big, need_size={} > {} from={}"
                , length, MAX_MESSAGE_SIZE, from);
        return false;
    }

    {
        AutoGuardLock auto_lock(*this);
        if (header_->free_guard_item_ < 0) {
            APPLOG_ERROR("no free guard item for guard..., key={}", shm_object_.getShmKey());
            if (header_->free_guard_num_ > 0) {
                header_->setError();
                APPLOG_ERROR("share memory error! no free guard item, but free guard num > 0, key={}", shm_object_.getShmKey());
            }
            return "";
        }
        if (!checkHeader()) {
            return "";
        }

        ShmMessageGuardItem* free_item = header_->items + header_->free_guard_item_;
        if (free_item->cur_index != header_->free_guard_item_) {
            header_->setError();
            APPLOG_ERROR("free guard item error, index not right! key={}", shm_object_.getShmKey());
            return "";
        }
        if (free_item->guard_flag != GUARD_ITEM_FLAG) {
            header_->setError();
            APPLOG_ERROR("free guard item flag error! key={}", shm_object_.getShmKey());
            return "";
        }
        if (free_item->prev_item >= MAX_GUARD_NUM
             || free_item->next_item >= MAX_GUARD_NUM) {
            header_->setError();
            APPLOG_ERROR("free guard item linked item error! key={}", shm_object_.getShmKey());
            return "";
        }

        int64_t new_message_id = (++header_->last_message_id_);

        free_item->topic_length = topic.length();
        free_item->message_length = length;
        free_item->message_crc32 = soldier::base::calCrc32((char*)data, length);
        free_item->policy = policy;
        free_item->prepared_timestamp_s = soldier::base::NowInSeconds();
        free_item->message_id = new_message_id;

        memcpy(free_item->data, topic.data(), topic.length());
        memcpy(free_item->data + topic.length(), data, length);

        // remove from free item linked list
        header_->free_guard_item_ = free_item->next_item;
        if (header_->free_guard_item_ >= 0) {
            header_->items[header_->free_guard_item_].prev_item = -1;
        }

        // add to used guard item
        free_item->prev_item = -1;
        free_item->next_item = header_->used_guard_item_;
        if (header_->used_guard_item_ >= 0) {
            header_->items[header_->used_guard_item_].prev_item = free_item->cur_index;
        }
        header_->used_guard_item_ = free_item->cur_index;

        header_->free_guard_num_ -= 1;

        return createGuardId(free_item->cur_index, new_message_id);
    } // END LOCK BLOCK
}

bool ShmMessageGuardStorage::getGuardMessage(
        const std::string& guard_id
        , std::string& topic
        , std::string& message_data) {
    int32_t index = -1;
    int64_t message_id = -1;
    if (!sliceGuardId(guard_id, index, message_id)) {
        APPLOG_WARN("getGuardMessage called, but get invalid guard_id={}", guard_id);
        return false;
    }
    if (index < 0 || index >= MAX_GUARD_NUM) {
        APPLOG_WARN("getGuardMessage, but gurad_id={} is invalid, index={}", guard_id, index);
        return false;
    }

    {
        AutoGuardLock auto_lock(*this);
        if (!checkHeader()) {
            return false;
        }

        ShmMessageGuardItem* item = header_->items + index;
        if (!checkGuardItem(*item, index)) {
            return false;
        }
        if (item->message_id != message_id) {
            return false;
        }

        topic.resize(item->topic_length);
        message_data.resize(item->message_length);
        memcpy((char*)topic.data(), item->data, item->topic_length);
        memcpy((char*)message_data.data(), item->data + item->topic_length, item->message_length);

        return true;
    }
}

bool ShmMessageGuardStorage::rmGuardMessage(const std::string& guard_id) {
    int32_t index = -1;
    int64_t message_id = -1;
    if (!sliceGuardId(guard_id, index, message_id)) {
        APPLOG_WARN("rmGuardMessage called, but get invalid guard_id={}", guard_id);
        return false;
    }
    if (index < 0 || index >= MAX_GUARD_NUM) {
        APPLOG_WARN("rmGuardMessage, but gurad_id={} is invalid, index={}", guard_id, index);
        return false;
    }

    {
           AutoGuardLock auto_lock(*this);
           if (!checkHeader()) {
               return false;
           }

           ShmMessageGuardItem* item = header_->items + index;
           if (item->message_id != message_id) {
               APPLOG_INFO("rmGuardMessage, guard_id={}, message_id is not equals, message maybe removed!");
               return false;
           }
           if (!checkGuardItem(*item, index)) {
               return false;
           }

           item->message_id = -1;
           item->topic_length = 0;
           item->message_length = 0;
           item->message_crc32 = 0;
           item->prepared_timestamp_s = 0;

           // remove from used linked list
           if (item->prev_item >= 0) {
               header_->items[item->prev_item].next_item = item->next_item;
           } else {
               if (header_->used_guard_item_ != item->cur_index) {
                   header_->setError();
                   APPLOG_ERROR("inner error! no prev item, but is not the top item! key={}", shm_object_.getShmKey());
                   return false;
               }
               header_->used_guard_item_ = item->next_item;
           }
           if (item->next_item >= 0) {
              header_->items[item->next_item].prev_item = item->prev_item;
           }

           // add to free linked list
           item->next_item = header_->free_guard_item_;
           item->prev_item = -1;
           header_->free_guard_item_ = index;

           header_->free_guard_num_ += 1;

           return true;
    }
}

void ShmMessageGuardStorage::reset() {
    APPLOG_WARN("reset guard storage, key={}", shm_object_.getShmKey());
    {
        AutoGuardLock auto_lock(*this);
        initHeader();
    }
}

std::shared_ptr<IMessageGuardStorage::IIterator> ShmMessageGuardStorage::iterator() {
    std::unique_ptr<ShmMessageGuardIterator> guard_iterator(new ShmMessageGuardIterator());
    {
        AutoGuardLock auto_lock(*this);
        int32_t item_index = header_->used_guard_item_;
        int loop_count = 0;
        while(item_index >= 0) {
            ShmMessageGuardItem* item = header_->items + item_index;
            if (!checkGuardItem(*item, item_index)) {
                return std::shared_ptr<IMessageGuardStorage::IIterator>();
            }
            guard_iterator->addGuardEntry(
                    createGuardId(item->cur_index, item->message_id)
                    , item->policy
                    , item->prepared_timestamp_s);
            item_index = item->next_item;
            ++loop_count;

            if (loop_count > MAX_GUARD_NUM) {
                header_->setError();
                APPLOG_ERROR("iterator can not go out loop, share memory error! key={}", shm_object_.getShmKey());
                return std::shared_ptr<IMessageGuardStorage::IIterator>();
            }
        }
    }
    return std::shared_ptr<IMessageGuardStorage::IIterator>(guard_iterator.release());
}

bool ShmMessageGuardStorage::checkHeader() {
    if (!header_->checkHeader()) {
        header_->setError();
        APPLOG_ERROR("header error, share memory error! key={}", shm_object_.getShmKey());
        return false;
    }
    if (header_->free_guard_item_ > MAX_GUARD_NUM) {
        header_->setError();
        APPLOG_ERROR("free guard item error, share memory error! key={}", shm_object_.getShmKey());
        return false;
    }
    if (header_->used_guard_item_ > MAX_GUARD_NUM) {
        header_->setError();
        APPLOG_ERROR("used guard item error, share memory error! key={}", shm_object_.getShmKey());
        return false;
    }
    return true;
}

bool ShmMessageGuardStorage::checkGuardItem(const ShmMessageGuardItem& guard_item, int32_t index) {
    if (guard_item.guard_flag != GUARD_ITEM_FLAG) {
        header_->setError();
        APPLOG_ERROR("guard item flag error, share memory error! key={}", shm_object_.getShmKey());
        return false;
    }
    if (guard_item.cur_index != index) {
        header_->setError();
        APPLOG_ERROR("guard item index error, share memory error! key={}", shm_object_.getShmKey());
        return false;
    }
    if (guard_item.message_length <= 0
        || guard_item.topic_length <= 0
        || (guard_item.topic_length + guard_item.message_length) > MAX_MESSAGE_SIZE) {
        header_->setError();
        APPLOG_ERROR("guard item length error, share memory error, message_length={}, topic_length={} ! key={}"
                , guard_item.message_length
                , guard_item.topic_length
                , shm_object_.getShmKey());
        return false;
    }
    if (guard_item.prev_item >= MAX_GUARD_NUM
        || guard_item.next_item >= MAX_GUARD_NUM) {
        header_->setError();
        APPLOG_ERROR("guard item prev or next error, share memory error! key={}", shm_object_.getShmKey());
        return false;
    }

    int32_t crc32 = soldier::base::calCrc32((char*)(guard_item.data + guard_item.topic_length), guard_item.message_length);
    if (crc32 != guard_item.message_crc32) {
        header_->setError();
        APPLOG_ERROR("guard message crc32 error, message_crc32={} is not equals to {} ! key={}"
                , guard_item.message_crc32
                , crc32
                , shm_object_.getShmKey());
        return false;
    }
    return true;
}

std::string ShmMessageGuardStorage::createGuardId(int32_t index, int64_t message_id) {
    return boost::lexical_cast<std::string>(index)
            .append("|")
            .append(boost::lexical_cast<std::string>(message_id));
}

bool ShmMessageGuardStorage::sliceGuardId(const std::string& guardId, int32_t& index, int64_t& message_id) {
    std::vector<std::string> tokens;
    soldier::base::StringUtil::tokenize(guardId, tokens, "|", true);
    if (tokens.size() != 2) {
        return false;
    }

    try {
        index = boost::lexical_cast<int32_t>(tokens[0]);
        message_id = boost::lexical_cast<int32_t>(tokens[1]);
    } catch (boost::bad_lexical_cast& e){
        return false;
    }
    return true;
}


} // end namespace message_bus
} // end namespace soldier


