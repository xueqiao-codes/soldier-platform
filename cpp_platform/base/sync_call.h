/*
 * sync_call.h
 *
 *  Created on: 2017年4月14日
 *      Author: wileywang
 */

#ifndef BASE_SYNC_CALL_H_
#define BASE_SYNC_CALL_H_

#include <condition_variable>
#include <memory>
#include <mutex>
#include <vector>

namespace soldier {
namespace base {

class SyncCall {
private:
    std::shared_ptr<std::mutex> wait_lock_;
    std::shared_ptr<std::condition_variable> wait_variable_;
    std::shared_ptr<bool> ready_;

    std::vector<void*> parameters_;

public:
    SyncCall()
        : wait_lock_(new std::mutex())
          , wait_variable_(new std::condition_variable())
          , ready_(new bool(false)){
    }
    ~SyncCall() = default;

    void push(void* p) {
        parameters_.push_back(p);
    }

    template<class T>
    T* at(int index) {
        if (index >= 0 && index <= parameters_.size()) {
            return (T*)(parameters_[index]);
        }
        return nullptr;
    }

    void wait() {
        std::unique_lock<std::mutex> auto_lock(*wait_lock_);
        while(!(*ready_)) {
            wait_variable_->wait(auto_lock);
        }
    }

    bool waitFor(uint64_t timeout_ms) {
        std::unique_lock<std::mutex> auto_lock(*wait_lock_);
        while(!(*ready_)) {
            wait_variable_->wait_for(auto_lock, std::chrono::milliseconds(timeout_ms));
            return *ready_;
        }
        return true;
    }

    void notify() {
        std::unique_lock<std::mutex> auto_lock(*wait_lock_);
        *ready_ = true;
        wait_variable_->notify_one();
    }
};

class AutoSyncCallNotify {
public:
	AutoSyncCallNotify(SyncCall& sync_call)
		: sync_call_(sync_call){
	}
	~AutoSyncCallNotify() {
		sync_call_.notify();
	}
private:
	SyncCall& sync_call_;
};

} // end namespace base
} // end namespace soldier



#endif /* BASE_SYNC_CALL_H_ */
