#include "base/thread_pool.h"

#include <algorithm>

using namespace soldier::base;

// the constructor just launches some amount of workers
ThreadPool::ThreadPool(size_t maxNumberOfThreads) : mStop(false) {
    for(size_t i = 0; i < maxNumberOfThreads; ++i)
        mThreads.emplace_back(&ThreadPool::taskLoop, this);
}

// the destructor joins all threads
ThreadPool::~ThreadPool() {
    {
        std::unique_lock<std::mutex> lock(mQueueMutex);
        mStop = true;
    }
    mCv.notify_all();
    std::for_each(mThreads.begin(), mThreads.end(),
                  std::bind(&std::thread::join, std::placeholders::_1));
}

void ThreadPool::clearTasks() {
    std::unique_lock<std::mutex> lock(this->mQueueMutex);
    while(!this->mTasks.empty()) {
        mTasks.pop();
    }
}

void ThreadPool::taskLoop() {
    onThreadAttached();
    for(;;) {
        std::packaged_task<void(void)> task;
        {
            std::unique_lock<std::mutex> lock(this->mQueueMutex);
            this->mCv.wait(lock,
                [this]{ return this->mStop || !this->mTasks.empty(); });
            if(this->mStop) {
                onThreadDetached();
                return;
            }
            task = std::move(this->mTasks.front());
            this->mTasks.pop();
        }

        task();
    }
}

bool TaskThread::isRunningInTaskThread() const {
	if (mThreads[0].get_id() == std::this_thread::get_id()) {
		return true;
	}
	return false;
}
