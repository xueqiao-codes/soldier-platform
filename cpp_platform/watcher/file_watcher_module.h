/*
 * file_watcher_module.h
 *
 *  Created on: 2017年4月19日
 *      Author: wileywang
 */

#ifndef WATCHER_FILE_WATCHER_MODULE_H_
#define WATCHER_FILE_WATCHER_MODULE_H_

#include <map>
#include <memory>
#include <mutex>
#include <string>
#include <thread>
#include "base/thread_pool.h"
#include "inotify/Inotify.h"
#include "watcher/file_listener.h"

namespace soldier {
namespace watcher {

/**
 * 文件监听
 */
class FileWatcherModule {
public:
    ~FileWatcherModule();
    static FileWatcherModule& Instance();
    static void Destroy();

    /**
     *  增加一个监听文件，同时返回文件的内容
     */
    void addWatchFile(const std::string& file_path
            , const std::shared_ptr<IFileListener>& listener);

    /**
     *  移除文件的监听
     */
    void removeWatchFile(const std::string& file_path);

private:
    static std::unique_ptr<FileWatcherModule> s_module;
    struct Entry{
        std::weak_ptr<IFileListener> listener;
        time_t lastmodify_time;
        int inotify_id;
        std::shared_ptr<std::mutex> lock;

        Entry()
            : lastmodify_time(0)
              , inotify_id(-1)
              , lock(new std::mutex()) {
        }
    } ;

    FileWatcherModule();

    void onCheckWorking() noexcept;
    void onInotifyListen() noexcept;
    void signalInotify();
    void onCheckFilesOnce() noexcept;
    void checkFile(const std::string& file_path, const std::shared_ptr<Entry>& entry);
    void removeWatch(int watch_id);
    std::string getProcessNotifyFile();
    void onCheckQConfOnce() noexcept;

    std::unique_ptr<Inotify> inotify_;
    std::unique_ptr<std::thread> check_thread_;
    std::unique_ptr<std::thread> inotify_thread_;
    std::unique_ptr<soldier::base::TaskThread> work_thread_;

    std::mutex watch_lock_;
    std::vector<int> removed_watches_;
    std::map<std::string, std::shared_ptr<Entry>> watch_entries_;

    std::atomic_bool ending_ = {false};
    std::atomic_bool signal_ = {false};
};


} // end namespace watcher
} // end namespace soldier



#endif /* WATCHER_FILE_WATCHER_MODULE_H_ */
