/*
 * file_watcher_module.cpp
 *
 *  Created on: 2017年4月20日
 *      Author: wileywang
 */

#include "watcher/file_watcher_module.h"

#include "base/app_log.h"
#include "base/string_util.h"
#include "watcher/qconf_file_installer.h"
#include "qconf.h"

namespace soldier {
namespace watcher {

std::unique_ptr<FileWatcherModule> FileWatcherModule::s_module;
static std::mutex s_module_lock;

FileWatcherModule& FileWatcherModule::Instance() {
    if (!s_module) {
        s_module_lock.lock();
        if (!s_module) {
            s_module.reset(new FileWatcherModule());
        }
        s_module_lock.unlock();
    }
    return *s_module;
}

void FileWatcherModule::Destroy() {
    if (s_module) {
        s_module_lock.lock();
        if (s_module) {
            s_module.reset();
        }
        s_module_lock.unlock();
    }
}

FileWatcherModule::FileWatcherModule(){
    int ret = qconf_init();
    if (ret != 0) {
        throw std::runtime_error("qconf init failed");
    }

    inotify_thread_.reset(new std::thread(&FileWatcherModule::onInotifyListen, this));
    check_thread_.reset(new std::thread(&FileWatcherModule::onCheckWorking, this));
    work_thread_.reset(new soldier::base::TaskThread());
}

FileWatcherModule::~FileWatcherModule() {
    ending_ = true;

    signalInotify();
    inotify_thread_->join();
    check_thread_->join();
}

std::string FileWatcherModule::getProcessNotifyFile() {
    std::stringstream ss;
    ss << "/proc/" << getpid() << "/limits";
    return ss.str();
}

void FileWatcherModule::signalInotify() {
    signal_ = false;
    int retryCount = 2;
    while(!signal_ && (retryCount--) > 0) {
        std::string file_path = getProcessNotifyFile();
        APPLOG_DEBUG("signalInotify {}, retryCount={}", file_path, retryCount);
        int fd = open (file_path.c_str(), O_RDONLY);
        if (fd < 0) {
            APPLOG_WARN("signalInotify, open {} failed, errno={}", file_path, errno);
            break;
        }
        close(fd);

        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

static const std::string QCONF_PATH_PREFIX = "/data/configs/qconf/";

void FileWatcherModule::addWatchFile(const std::string& file_path
            , const std::shared_ptr<IFileListener>& listener) {
    boost::filesystem::path fp(boost::filesystem::absolute(file_path, boost::filesystem::current_path()));
    if (!boost::filesystem::exists(fp)) {
        if (soldier::base::StringUtil::startsWith(fp.string(), QCONF_PATH_PREFIX)) {
            std::string qconf_path = fp.string().substr(QCONF_PATH_PREFIX.length());
            QConfFileInstaller installer(qconf_path, fp.string());
            installer.install();
        }

        if (!boost::filesystem::exists(fp)) {
            throw std::invalid_argument(fp.string() + " is not existed");
        }
    }
    if (!boost::filesystem::is_regular_file(fp)) {
        throw std::invalid_argument(fp.string() + " is not file");
    }

    std::shared_ptr<Entry> watch_entry;
    {
        std::unique_lock<std::mutex> auto_lock(watch_lock_);
        auto it = watch_entries_.find(fp.string());
        if (it != watch_entries_.end()) {
            return ;
        }

        watch_entry.reset(new Entry());
        watch_entry->listener = listener;
    }

    struct stat file_stat;
    if (0 == stat(fp.string().c_str(), &file_stat)) {
        watch_entry->lastmodify_time = file_stat.st_mtime;
    }

    {
        std::unique_lock<std::mutex> auto_lock(watch_lock_);
        watch_entries_.insert(std::pair<std::string, std::shared_ptr<Entry>>(fp.string(), watch_entry));
    }

    signalInotify();
}

void FileWatcherModule::removeWatchFile(const std::string& file_path) {
    boost::filesystem::path fp(boost::filesystem::absolute(file_path, boost::filesystem::current_path()));
    {
        std::unique_lock<std::mutex> auto_lock(watch_lock_);
        auto it = watch_entries_.find(fp.string());
        if (it == watch_entries_.end()) {
            return ;
        }

        if (it->second->inotify_id >= 0) {
            removed_watches_.push_back(it->second->inotify_id);
        }
        watch_entries_.erase(it);
    }
    signalInotify();
}

void FileWatcherModule::checkFile(
        const std::string& file_path
        , const std::shared_ptr<Entry>& entry) {
    std::unique_lock<std::mutex> auto_entry_lock(*entry->lock);
    time_t reserved_lastmodify_time = entry->lastmodify_time;
    APPLOG_DEBUG("checkFile file_path={}, reserved_lastmodify_time={}"
            , file_path, entry->lastmodify_time);

    struct stat file_stat;
    if (0 != stat(file_path.c_str(), &file_stat)) {
        APPLOG_ERROR("can not stat {}, should check ", file_path);
        return ;
    }
    entry->lastmodify_time = file_stat.st_mtime;

    if (reserved_lastmodify_time == entry->lastmodify_time)
        return ;

    APPLOG_WARN("[NOTICE]on {} changed...", file_path);
    std::shared_ptr<IFileListener> listener = entry->listener.lock();
    if (listener) {
        try {
            listener->onFileChanged(file_path);
        } catch (...) {
            APPLOG_ERROR("onFileChanged for {} callback throws unexpected Exception"
                    , file_path);
        }
    }
}

void FileWatcherModule::removeWatch(int watch_id) {
    try {
        inotify_->removeWatch(watch_id);
        APPLOG_DEBUG("remove watch {}", watch_id);
    } catch (std::exception& e) {
        APPLOG_ERROR("remove watch {} failed, {}", watch_id, e.what());
    }
}

void FileWatcherModule::onCheckWorking() noexcept {
    while(!ending_) {
        int sleep_million_seconds = 100;
        int count = 15*1000/sleep_million_seconds;
        for (int index = 0; index < count; ++index) {
            if (ending_) {
                return;
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(sleep_million_seconds));
        }

        onCheckFilesOnce();
        onCheckQConfOnce();
    }
}

void FileWatcherModule::onCheckFilesOnce() noexcept {
    APPLOG_DEBUG("onCheckFilesOnce run...");

    watch_lock_.lock();
    std::map<std::string, std::shared_ptr<Entry>> cp_watch_entries(watch_entries_);
    watch_lock_.unlock();

    for (auto& it : cp_watch_entries) {
        checkFile(it.first, it.second);
        if (it.second->inotify_id == -1) {
            signalInotify();
        }
    }
}

void FileWatcherModule::onInotifyListen() noexcept {
    inotify_.reset(new Inotify());
    int process_notify_id = inotify_->watchFile(getProcessNotifyFile());
    APPLOG_DEBUG("onInotifyListen start, process_notify_id={}...", process_notify_id);

    while(!ending_) {
        FileSystemEvent event = inotify_->getNextEvent();
        APPLOG_DEBUG("Inotify Event, wd={}, path={}, event_mask={}", event.wd, event.path.string(), event.getMaskString());

        if (event.wd == process_notify_id && (event.mask & IN_OPEN)) {
            signal_ = true;

            {
                std::unique_lock<std::mutex> auto_lock(watch_lock_);
                for (auto& it : watch_entries_) {
                    try {
                        if (it.second->inotify_id < 0) {
                            it.second->inotify_id = inotify_->watchFile(it.first);
                            APPLOG_DEBUG("AddWatch {} to wd {}", it.first, it.second->inotify_id);
                        }
                    } catch (std::exception& e) {
                        APPLOG_ERROR("add watch {} failed, {}", it.first, e.what());
                    }
                }
                for (auto& watch_id : removed_watches_) {
                    removeWatch(watch_id);
                }
                removed_watches_.clear();
            }

            continue;
        }

        if (event.wd != -1) {
            if ((event.mask & IN_CLOSE_WRITE) || (event.mask & IN_IGNORED)) {
                std::string file_path;
                std::shared_ptr<Entry> watch_entry;

                watch_lock_.lock();
                auto it = watch_entries_.find(event.path.string());
                if (it != watch_entries_.end()) {
                    file_path = it->first;
                    watch_entry = it->second;
                }
                watch_lock_.unlock();

                if (!file_path.empty()) {
                    if (event.mask & IN_CLOSE_WRITE) {
                        checkFile(file_path, watch_entry);
                    } else {
//                        if (watch_entry->inotify_id >= 0) {
//                            removeWatch(watch_entry->inotify_id);
                            watch_entry->inotify_id = -1;
//                        }
                    }
                } else {
                    APPLOG_WARN("Not found file event for watch id {} to {}", event.wd, event.path.string());
                }

                continue;
            }
        }
    }
}

void FileWatcherModule::onCheckQConfOnce() noexcept {
    APPLOG_DEBUG("onCheckQConfOnce run...");
    std::unique_ptr<char[]> buffer(new char[1024*1024]);

    watch_lock_.lock();
    std::map<std::string, std::shared_ptr<Entry>> cp_watch_entries(watch_entries_);
    watch_lock_.unlock();

    for (auto& it : cp_watch_entries) {
        if (soldier::base::StringUtil::startsWith(it.first, QCONF_PATH_PREFIX)) {
            std::string qconf_path = it.first.substr(QCONF_PATH_PREFIX.length());
            int ret = qconf_aget_conf(qconf_path.c_str(), buffer.get(), 1024*1024, NULL);
            if (ret != 0) {
                APPLOG_ERROR("qconf_aget_conf {} failed, ret={}", qconf_path, ret);
            } else {
                APPLOG_DEBUG("qconf_aget_conf {} success", qconf_path);
            }
        }
    }
}


} // end namespace watcher
} // end namespace soldier

