/*
 * file_watcher_swig.cpp
 *
 *  Created on: 2017年6月3日
 *      Author: 44385
 */

#include "watcher/swig/file_watcher_swig.h"

#include <mutex>
#include "watcher/file_watcher_module.h"
#include "base/app_log.h"

namespace soldier {
namespace watcher {

static std::shared_ptr<IFileListener> s_global_listener;
static std::mutex s_lock;


void FileWatcherSwig::initNativeLogger(const std::string& log_module) {
    soldier::base::AppLog::Init("FileWatcher/" + log_module);
}

void FileWatcherSwig::init(IFileListener* global_listener) {
    std::unique_lock<std::mutex> auto_lock(s_lock);
    if (s_global_listener) {
        throw new std::logic_error("double init for FileWatcherSwig");
    }
    s_global_listener = std::shared_ptr<IFileListener>(global_listener);
}

void FileWatcherSwig::addWatchFile(const std::string& file_path) {
    FileWatcherModule::Instance().addWatchFile(file_path, s_global_listener);
}

void FileWatcherSwig::removeWatchFile(const std::string& file_path) {
    FileWatcherModule::Instance().removeWatchFile(file_path);
}

void FileWatcherSwig::destroy() {
    std::unique_lock<std::mutex> auto_lock(s_lock);
    s_global_listener.reset();
    FileWatcherModule::Destroy();
}

} // end namespace watcher
} // end namespace soldier


