/*
 * listener_container.h
 *
 *  Created on: 2017年4月22日
 *      Author: 44385
 */

#ifndef BASE_LISTENER_CONTAINER_H_
#define BASE_LISTENER_CONTAINER_H_

#include <memory>
#include <mutex>
#include <vector>

namespace soldier {
namespace base {

template<class Listener>
class ListenerContainer {
public:
    ListenerContainer() = default;
    virtual ~ListenerContainer() = default;

    void addListener(const std::shared_ptr<Listener>& listener) {
        if (!listener) {
            return ;
        }

        std::unique_lock<std::mutex> auto_lock(lock_);
        for (auto& it : listeners_) {
            if (it.lock().get() == listener.get()) {
                return ;
            }
        }

        listeners_.push_back(listener);
    }

    void removeListener(Listener* listener) {
        if (!listener) {
            return ;
        }

        std::unique_lock<std::mutex> auto_lock(lock_);
        for (auto it = listeners_.begin(); it != listeners_.end(); ++it) {
            if (it->lock().get() == listener) {
                listeners_.erase(it);
                return ;
            }
        }
    }

    void getListeners(std::vector<std::shared_ptr<Listener>>& listeners) {
        std::unique_lock<std::mutex> auto_lock(lock_);
        auto it = listeners_.begin();
        while (it != listeners_.end()) {
            std::shared_ptr<Listener> l = it->lock();
            if (!l) {
                it = listeners_.erase(it);
            } else {
                listeners.push_back(l);
                ++it;
            }
        }
    }

private:
    std::vector<std::weak_ptr<Listener>> listeners_;
    std::mutex lock_;
};


} // end namespace base
} // end namespace soldier



#endif /* BASE_LISTENER_CONTAINER_H_ */
