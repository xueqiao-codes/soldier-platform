/*
 * shm_message_guard.h
 *
 *  Created on: 2018年1月2日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_GUARD_SHM_MESSAGE_GUARD_STORAGE_H_
#define MESSAGE_BUS_GUARD_SHM_MESSAGE_GUARD_STORAGE_H_

#include <mutex>
#include <boost/interprocess/sync/file_lock.hpp>
#include "base/code_defense.h"
#include "base/shm_object.h"
#include "message_bus/interface/message_queue.h"
#include "message_bus/interface/message_guard_storage.h"

namespace soldier {
namespace message_bus {

#define GUARD_MAGIC_FLAG 0x1122334455667788
#define MAX_GUARD_NUM 1000
#define MAX_GUARD_FILE_LOCK_PATH_LENGTH 63
#define GUARD_ITEM_FLAG 0x32FFBEEE

struct ShmMessageGuardItem {
    int32_t prev_item = -1;
    int32_t next_item = -1;
    int32_t cur_index = -1;
    int32_t guard_flag = GUARD_ITEM_FLAG;
    uint32_t topic_length = 0;
    uint32_t message_length = 0;
    int32_t message_crc32 = 0;
    int32_t prepared_timestamp_s = 0;
    int64_t message_id = -1;
    GuardPolicy policy;
    char data[MAX_MESSAGE_SIZE] = {0};
};

class ShmMessageGuardHeader {
public:
    ShmMessageGuardHeader() = default;
    ~ShmMessageGuardHeader() = default;

    inline void setInited() { error_flag_ = 0; magic_ = GUARD_MAGIC_FLAG; }
    inline bool isInited() const { return magic_ == GUARD_MAGIC_FLAG; }

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
        return magic_ == GUARD_MAGIC_FLAG;
    }

    inline void setGuardFileLockPath(const std::string& path) {
        CHECK(!path.empty() && path.length() <= MAX_GUARD_FILE_LOCK_PATH_LENGTH);
        memcpy(guard_lock_file_path_, path.c_str(), path.length());
        guard_lock_file_path_[path.length()] = 0;
    }

    inline const char* getGuardLockFilePath() const {
        return guard_lock_file_path_;
    }

    inline int32_t getFreeGuardNum() const {
        return free_guard_num_;
    }

private:
    friend class ShmMessageGuardStorage;

    int64_t magic_ = 0;
    int64_t error_flag_ = 1;
    int64_t last_message_id_ = 0;
    char guard_lock_file_path_[MAX_GUARD_FILE_LOCK_PATH_LENGTH + 1] = {0};
    int32_t free_guard_item_ = -1;
    int32_t used_guard_item_ = -1;
    int32_t free_guard_num_ = MAX_GUARD_NUM;
    int32_t reserve_ = 0;
    ShmMessageGuardItem items[MAX_GUARD_NUM];
};


class ShmMessageGuardStorage : public IMessageGuardStorage {
public:
    virtual ~ShmMessageGuardStorage() = default;

    static ShmMessageGuardStorage* Init(
            const std::string& file_lock_dir
            , int shm_key);

    static ShmMessageGuardStorage* Get(int shm_key);

    virtual std::string prepareGuardMessage(const std::string& topic
                , const std::string& from
                , const uint8_t* data
                , const size_t& length
                , const GuardPolicy& policy);

    virtual bool getGuardMessage(const std::string& guard_id
                , std::string& topic
                , std::string& message_data);

    virtual bool rmGuardMessage(const std::string& guard_id);

    virtual bool isBroken() {
        return header_->hasError();
    }

    virtual void reset();

    virtual std::shared_ptr<IIterator> iterator();

    inline int32_t getFreeGuardNum() const {
        return header_->getFreeGuardNum();
    }

    virtual void getStatics(GuardStaticsData& data) const {
        data.free_num_num = header_->getFreeGuardNum();
    }

private:
    ShmMessageGuardStorage() = default;

    bool initInstance(int32_t shm_key, const std::string& guard_lock_file);
    bool attachInstance(int32_t shm_key);
    void initHeader();

    std::string createGuardId(int32_t index, int64_t message_id);
    bool sliceGuardId(const std::string& guardId, int32_t& index, int64_t& message_id);

    bool checkHeader();
    bool checkGuardItem(const ShmMessageGuardItem& guard_item, int32_t index);

    struct AutoGuardLock {
        ShmMessageGuardStorage& ref_;
        AutoGuardLock(ShmMessageGuardStorage& ref)
        : ref_(ref) {
            ref.guard_thread_lock_.lock();
            ref.guard_process_lock_->lock();
        }

        ~AutoGuardLock() {
            ref_.guard_process_lock_->unlock();
            ref_.guard_thread_lock_.unlock();
        }
    };

    soldier::base::ShmObject shm_object_;
    ShmMessageGuardHeader* header_ = nullptr;

    std::string guard_lock_file_;
    std::mutex guard_thread_lock_; // 线程级别锁
    std::unique_ptr<boost::interprocess::file_lock> guard_process_lock_;  //进程级别锁
};


} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_GUARD_SHM_MESSAGE_GUARD_STORAGE_H_ */
