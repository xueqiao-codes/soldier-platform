/*
 * file_watcher_test.cpp
 *
 *  Created on: 2017年4月20日
 *      Author: wileywang
 */
#include "watcher/file_watcher_module.h"

#include "base/app_log.h"
#include <iostream>

using namespace soldier::watcher;

class PrintListener : public IFileListener {
public:
    virtual void onFileChanged(const std::string& file_path) noexcept {
        std::cout << "onFileChanged " << file_path << std::endl;
    }
};

int main(int argc, char* argv[]) {
    if (argc <= 1) {
        std::cout << "please input the watch files" << std::endl;
        return 1;
    }

    soldier::base::AppLog::Init("file_watcher_test");

    std::shared_ptr<IFileListener> print_listener(new PrintListener());

    for (int index = 1; index < argc; ++index) {
        FileWatcherModule::Instance().addWatchFile(argv[index], print_listener);
    }

    getchar();
    for (int index = 1; index < argc; ++index) {
        FileWatcherModule::Instance().removeWatchFile(argv[index]);
    }
//    getchar();

    return 0;
}

