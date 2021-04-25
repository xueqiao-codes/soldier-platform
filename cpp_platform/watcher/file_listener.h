/*
 * file_listener.h
 *
 *  Created on: 2017年6月3日
 *      Author: 44385
 */

#ifndef WATCHER_FILE_LISTENER_H_
#define WATCHER_FILE_LISTENER_H_

#include <string>

namespace soldier {
namespace watcher {

class IFileListener {
public:
    virtual ~IFileListener() {}

    virtual void onFileChanged(const std::string& file_path) = 0;

protected:
    IFileListener() {}
};

} // end namespace watcher
} // end namespace soldier

#endif /* WATCHER_FILE_LISTENER_H_ */
