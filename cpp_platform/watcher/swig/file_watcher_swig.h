/*
 * file_watcher_swig.h
 *
 *  Created on: 2017年6月3日
 *      Author: 44385
 */

#ifndef WATCHER_SWIG_FILE_WATCHER_SWIG_H_
#define WATCHER_SWIG_FILE_WATCHER_SWIG_H_

#include <string>
#include "watcher/file_listener.h"

namespace soldier {
namespace watcher {

class FileWatcherSwig {
public:
    static void init(IFileListener* global_listener);

    static void addWatchFile(const std::string& file_path);
    static void removeWatchFile(const std::string& file_path);

    static void initNativeLogger(const std::string& log_module);

    static void destroy();
};

} // end namespace watcher
} // end namespace soldier



#endif /* WATCHER_SWIG_FILE_WATCHER_SWIG_H_ */
