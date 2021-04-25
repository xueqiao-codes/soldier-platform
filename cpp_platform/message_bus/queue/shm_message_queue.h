/*
 * shm_message_queue.h
 *
 *  Created on: 2017年12月21日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_SHM_MESSAGE_QUEUE_H_
#define MESSAGE_BUS_SHM_MESSAGE_QUEUE_H_

#include <mutex>
#include <boost/interprocess/sync/file_lock.hpp>
#include "base/shm_object.h"
#include "message_bus/interface/message_queue.h"
#include "message_bus/queue/shm_queue_header.h"

namespace soldier {
namespace message_bus {

#define MESSAGE_FLAG 0x32FFBCD2

class ShmMessageQueue : public IMessageQueue {
public:
    ~ShmMessageQueue() = default;

    static ShmMessageQueue* Init(
            const std::string& file_lock_dir
            , int shm_key
            , size_t shm_size);

    static ShmMessageQueue* Get(int shm_key);

    /**
     * 压入一个消息
     */
    virtual bool pushMessage(const std::string& topic
            , const std::string& from
            , const uint8_t* data
            , const size_t& length);

    /**
     * 获取消息，但是不从队列中弹出
     */
    virtual bool peekMessage(std::string& topic, std::string& message_data);

    /**
     *  将消息从队列中弹出
     */
    virtual bool popMessage();

    virtual bool isBroken() { return header_->hasError(); }
    virtual void reset();

    virtual void getStatics(QueueStaticsData& data) const { header_->getStatics(data); }

    const uint8_t* getMessageQueue() const {
        return header_->getMessageQueue();
    }

    virtual bool isEmptyQueue() const { return false;}

    /**
     *  破坏测试
     */
    void broken() {
        header_->setError();
    }

private:
    ShmMessageQueue() = default;
    bool initInstance(int shm_key
            , size_t shm_size
            , const std::string& queue_lock_file);
    bool attachInstance(int shm_key);
    int32_t findNearest(const uint32_t& val);
    void initHeader();

    /**
     *  从start_index开始的元素向后移动一位
     */
    void moveBackworkOneStep(int32_t start_index);

    /**
     *  从start_index开始的元素向前移动一位
     */
    void moveForwardOneStep(int32_t start_index);

    bool checkOffset(const uint32_t& begin_offset, const uint32_t& end_offset);
    bool checkHeader();

    void setOffset(const uint32_t& begin_offset, const uint32_t& end_offset);
    bool pushMessageUnLock(const std::string& topic
            , const std::string& from
            , const uint8_t* data
            , const size_t& length);

    struct AutoQueueLock {
        ShmMessageQueue& ref_;
        AutoQueueLock(ShmMessageQueue& ref)
            : ref_(ref) {
            ref.queue_thread_lock_.lock();
            ref.queue_process_lock_->lock();
        }

        ~AutoQueueLock() {
            ref_.queue_process_lock_->unlock();
            ref_.queue_thread_lock_.unlock();
        }
    };

    struct TMessageHeader {
        int32_t message_flag = MESSAGE_FLAG;
        uint32_t topic_length = 0;
        uint32_t data_length = 0;
        int32_t message_crc32 = 0;
        uint8_t variant[0];
    };


    bool checkMessage(const TMessageHeader* message);
    TMessageHeader* getTopMessage(uint32_t& origin_offset_begin, uint32_t& origin_offset_end);

    soldier::base::ShmObject shm_object_;
    ShmQueueHeader* header_ = nullptr;
    uint32_t shm_size_ = 0;
    std::string queue_lock_file_;

    std::mutex queue_thread_lock_; // 线程级别锁
    std::unique_ptr<boost::interprocess::file_lock> queue_process_lock_;  //进程级别锁
};



} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_SHM_MESSAGE_QUEUE_H_ */
