/*
 * shm_queue_header.h
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_SHM_HEADER_H_
#define MESSAGE_BUS_SHM_HEADER_H_

#include <boost/interprocess/sync/interprocess_mutex.hpp>

#include "base/app_log.h"
#include "base/code_defense.h"
#include "message_bus/interface/message_queue.h"

namespace soldier {
namespace message_bus {

#define QUEUE_MAGIC_FLAG  0x01E2D3B4A5667788
#define MAX_QUEUE_FILE_LOCK_PATH_LENGTH 63


class ShmQueueHeader {
public:
    ShmQueueHeader() {
        setMessageOffset(0, 0);
    }
    ~ShmQueueHeader() = default;

    inline void setInited() { error_flag_ = 0; magic_begin_ = QUEUE_MAGIC_FLAG; }
    inline bool isInited() const { return magic_begin_ == QUEUE_MAGIC_FLAG; }

    inline void setShmSize(const uint32_t& shm_size) {
        shm_size_ = shm_size;
        message_total_size_ = shm_size_ - sizeof(ShmQueueHeader);
    }
    inline uint32_t getShmSize() const { return shm_size_; }

    inline void setQueueFileLockPath(const std::string& path) {
        CHECK(!path.empty() && path.length() <= MAX_QUEUE_FILE_LOCK_PATH_LENGTH);
        memcpy(queue_file_lock_path_, path.c_str(), path.length());
        queue_file_lock_path_[path.length()] = 0;
    }

    inline const char* getQueueLockFilePath() const {
        return queue_file_lock_path_;
    }

    inline void setMessageOffset(const uint32_t& begin_offset
            , const uint32_t& end_offset) {
        uint64_t value = (uint64_t)begin_offset;
        value = (value << 32);
        value = (value | end_offset);
        __sync_lock_test_and_set(&message_offset_, value);
    }

    inline void getMessageOffset(uint32_t& begin_offset, uint32_t& end_offset) const {
        uint64_t value = message_offset_;
        begin_offset = (uint32_t)(value >> 32);
        end_offset = (uint32_t)(value&0xFFFFFFFF);
    }

    /**
     *  共享内存错误标志
     */
    inline void setError() {
        error_flag_ = 1;
    }
    inline void clearError() {
        error_flag_ = 0;
    }
    inline bool hasError() const {
        return (error_flag_ != 0);
    }

    bool checkHeader() const {
        return (magic_begin_ == QUEUE_MAGIC_FLAG
                && magic_middle_ == QUEUE_MAGIC_FLAG
                && magic_end_ == QUEUE_MAGIC_FLAG
                && message_total_size_ == (shm_size_ - sizeof(ShmQueueHeader)));
    }

    void getStatics(QueueStaticsData& data) const {
        uint32_t begin_offset = 0;
        uint32_t end_offset = 0;
        getMessageOffset(begin_offset, end_offset);
        if (end_offset >= begin_offset) {
            data.free_size = message_total_size_ - (end_offset - begin_offset);
        } else {
            data.free_size = begin_offset - end_offset;
        }
    }

    const uint8_t* getMessageQueue() const {
        return message_queue_;
    }

private:
    friend class ShmMessageQueue;

    struct TopicItem {
        uint32_t hash_code_ = 0;
        TopicItem() = default;
        TopicItem(const TopicItem&) = default;

        TopicItem& operator=(const TopicItem& item) {
            this->hash_code_ = item.hash_code_;
            return *this;
        }
    };

    volatile int64_t magic_begin_ = 0;
    volatile int64_t error_flag_ = 1;
    char queue_file_lock_path_[MAX_QUEUE_FILE_LOCK_PATH_LENGTH + 1];
    volatile uint32_t shm_size_ = 0;
    volatile uint32_t message_total_size_ = 0;
    volatile int64_t magic_middle_ = QUEUE_MAGIC_FLAG; // 防范共享内存写乱
    /**
     *  begin:高32位表示
     *  end:低32位
     */
    volatile int64_t magic_end_ = QUEUE_MAGIC_FLAG; // 防范共享内存写乱
    volatile uint64_t message_offset_ = 0;
    uint8_t message_queue_[0];
};


} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_SHM_HEADER_H_ */
