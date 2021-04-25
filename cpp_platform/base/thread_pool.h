#ifndef __THREAD_POOL_H__
#define __THREAD_POOL_H__

#include <vector>
#include <queue>
#include <memory>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <future>
#include <functional>
#include <stdexcept>

namespace soldier {
namespace base {

class ThreadPool {
public:
    ThreadPool(size_t maxNumberOfThreads = (std::thread::hardware_concurrency() + 1));

    template<class F, class... Args>
        auto postTask(F&& f, Args&&... args)
            -> std::future<typename std::result_of<F(Args...)>::type>;
    virtual ~ThreadPool();

    void clearTasks();

protected:
    virtual void onThreadAttached() {};
    virtual void onThreadDetached() {}

private:
    void taskLoop();

    ThreadPool(const ThreadPool&) = delete;
    ThreadPool(ThreadPool&&) = delete;
    ThreadPool& operator=(const ThreadPool&) = delete;
    ThreadPool& operator=(ThreadPool&&) = delete;

protected:
    // need to keep track of threads so we can join them
    std::vector<std::thread> mThreads;
    // the task queue
    std::queue<std::packaged_task<void(void)> > mTasks;

    // synchronization
    std::mutex mQueueMutex;
    std::condition_variable mCv;
    volatile bool mStop;
};

// add new work item to the pool
template<class F, class... Args>
auto ThreadPool::postTask(F&& f, Args&&... args) 
    -> std::future<typename std::result_of<F(Args...)>::type>
{
    using return_type = typename std::result_of<F(Args...)>::type;

    auto task = std::packaged_task<return_type(void)>(
            std::bind(std::forward<F>(f), std::forward<Args>(args)...));

    std::future<return_type> res = task.get_future();
    {
//        TIMEPRINT_BEGIN(ThreadPool_PostTask)
        std::unique_lock<std::mutex> lock(mQueueMutex);

        // don't allow enqueueing after stopping the pool
        if(mStop)
            throw std::runtime_error("enqueue on stopped ThreadPool");

        mTasks.emplace(std::move(task));
//        TIMEPRINT_END(ThreadPool_PostTask, "")
    }
    mCv.notify_one();
    return res;
}

// ====================================================
// a task executor
class TaskThread : public ThreadPool {
public:
    TaskThread() : ThreadPool(1) {
    }

    bool isRunningInTaskThread() const ;
};
// ====================================================

} // end namespace base
} // end namespace soldier

#endif // __THREAD_POOL_H__
