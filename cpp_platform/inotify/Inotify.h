/** 
 * @file      Inotify.h
 * @author    Erik Zenker
 * @date      02.11.2012
 * @copyright Gnu Public License
 **/
#ifndef INOTIFY_INOTIFY_CPP_INCLUDE_
#define INOTIFY_INOTIFY_CPP_INCLUDE_

#include <atomic>
#include <sys/inotify.h>
#include <string>
#include <queue>
#include <map>
#include <vector>
#include <boost/filesystem.hpp>
#include <assert.h>
#include <errno.h>
#include <time.h>
#include <errno.h>
#include <string>
#include <exception>
#include <sstream>

#include "inotify/FileSystemEvent.h"

#define MAX_EVENTS     32
#define EVENT_SIZE     (sizeof (inotify_event))
#define EVENT_BUF_LEN  (MAX_EVENTS * (EVENT_SIZE + 32))

namespace fs = boost::filesystem;

/**
 * @brief C++ wrapper for linux inotify interface
 * @class Inotify
 *        Inotify.h
 *        "include/Inotify.h"
 *
 * folders will be watched by watchFolderRecursively or
 * files by watchFile. If there are changes inside this
 * folder or files events will be raised. This events
 * can be get by getNextEvent.
 *
 * @eventMask
 *
 * IN_ACCESS         File was accessed (read) (*).
 * IN_ATTRIB         Metadata changed—for example, permissions,
 *                   timestamps, extended attributes, link count
 *                   (since Linux 2.6.25), UID, or GID. (*).
 * IN_CLOSE_WRITE    File opened for writing was closed (*).
 * IN_CLOSE_NOWRITE  File not opened for writing was closed (*).
 * IN_CREATE         File/directory created in watched directory(*).
 * IN_DELETE         File/directory deleted from watched directory(*).
 * IN_DELETE_SELF    Watched file/directory was itself deleted.
 * IN_MODIFY         File was modified (*).
 * IN_MOVE_SELF      Watched file/directory was itself moved.
 * IN_MOVED_FROM     Generated for the directory containing the old
 *                   filename when a file is renamed (*).
 * IN_MOVED_TO       Generated for the directory containing the new
 *                   filename when a file is renamed (*).
 * IN_OPEN           File was opened (*).
 * IN_ALL_EVENTS     macro is defined as a bit mask of all of the above
 *                   events
 * IN_MOVE           IN_MOVED_FROM|IN_MOVED_TO
 * IN_CLOSE          IN_CLOSE_WRITE | IN_CLOSE_NOWRITE
 *
 * See inotify manpage for more event details
 *
 */
class Inotify {
public:
    Inotify();
    Inotify(uint32_t eventMask);
    Inotify(std::vector<std::string> ignoredDirectories, unsigned eventTimeout,
            uint32_t eventMask);
    ~Inotify();
    void watchDirectoryRecursively(fs::path path);
    int watchFile(fs::path file);
    void removeWatch(int wd);
    void ignoreFileOnce(fs::path file);
    FileSystemEvent getNextEvent();
    int getLastErrno();

private:
    fs::path wdToPath(int wd);
    bool isIgnored(std::string file);
    void init();

    // Member
    int mError;
    uint32_t mEventMask;
    std::vector<std::string> mIgnoredDirectories;
    std::vector<std::string> mOnceIgnoredDirectories;
    std::queue<FileSystemEvent> mEventQueue;
    std::map<int, fs::path> mDirectorieMap;
    int mInotifyFd;
};

inline Inotify::Inotify()
    : mError(0)
      , mEventMask(IN_ALL_EVENTS)
      , mIgnoredDirectories(std::vector<std::string>())
      , mInotifyFd(0) {
    init();
}

inline Inotify::Inotify(uint32_t eventMask)
    : mError(0)
      , mEventMask(eventMask)
      , mIgnoredDirectories(std::vector<std::string>())
      , mInotifyFd(0) {
    init();
}

inline Inotify::Inotify(
        std::vector<std::string> ignoredDirectories,
        unsigned eventTimeout, uint32_t eventMask)
    : mEventMask(eventMask)
      , mIgnoredDirectories(ignoredDirectories)
      , mInotifyFd(0) {
    init();
}

inline Inotify::~Inotify() {
    if (!close(mInotifyFd)) {
        mError = errno;
    }
}

inline void Inotify::init() {
    mInotifyFd = inotify_init();
    if (mInotifyFd == -1) {
        mError = errno;
        std::stringstream errorStream;
        errorStream << "Can't initialize inotify ! " << strerror(mError) << ".";
        throw std::runtime_error(errorStream.str());
    }

}

/**
 * @brief Adds the given path and all files and subdirectories
 *        to the set of watched files/directories.
 *        Symlinks will be followed!
 *
 * @param path that will be watched recursively
 *
 */
inline void Inotify::watchDirectoryRecursively(fs::path path) {
    if (fs::exists(path)) {
        if (fs::is_directory(path)) {
            fs::recursive_directory_iterator it(path,
                    fs::symlink_option::recurse);
            fs::recursive_directory_iterator end;

            while (it != end) {
                fs::path currentPath = *it;

                if (fs::is_directory(currentPath)) {
                    watchFile(currentPath);
                }
                if (fs::is_symlink(currentPath)) {
                    watchFile(currentPath);
                }
                ++it;

            }

        }
        watchFile(path);
    } else {
        throw std::invalid_argument(
                "Can´t watch Path! Path does not exist. Path: "
                        + path.string());
    }

}

/**
 * @brief Adds a single file/directorie to the list of
 *        watches. Path and corresponding watchdescriptor
 *        will be stored in the directorieMap. This is done
 *        because events on watches just return this
 *        watchdescriptor.
 *
 * @param path that will be watched
 *
 */
inline int Inotify::watchFile(fs::path filePath) {
    if (fs::exists(filePath)) {
        mError = 0;
        int wd = 0;
        if (!isIgnored(filePath.string())) {
            wd = inotify_add_watch(mInotifyFd, filePath.string().c_str(),
                    mEventMask);
        }

        if (wd == -1) {
            mError = errno;
            std::stringstream errorStream;
            if (mError == 28) {
                errorStream << "Failed to watch! " << strerror(mError)
                        << ". Please increase number of watches in \"/proc/sys/fs/inotify/max_user_watches\".";
                throw std::runtime_error(errorStream.str());
            }

            errorStream << "Failed to watch! " << strerror(mError) << ". Path: "
                    << filePath.string();
            throw std::runtime_error(errorStream.str());

        }
        mDirectorieMap[wd] = filePath;
        return wd;
    } else {
        throw std::invalid_argument(
                        "Can´t watch Path! Path does not exist. Path: "
                                + filePath.string());
    }

}

inline void Inotify::ignoreFileOnce(fs::path file) {
    mOnceIgnoredDirectories.push_back(file.string());

}

/**
 * @brief Removes watch from set of watches. This
 *        is not done recursively!
 *
 * @param wd watchdescriptor
 *
 */
inline void Inotify::removeWatch(int wd) {
    int result = inotify_rm_watch(mInotifyFd, wd);
    if (result == -1) {
        mError = errno;
        std::stringstream errorStream;
        errorStream << "Failed to remove watch! " << strerror(mError) << ".";
        throw std::runtime_error(errorStream.str());
    }
    mDirectorieMap.erase(wd);
}

inline fs::path Inotify::wdToPath(int wd) {
    return mDirectorieMap[wd];

}

/**
 * @brief Blocking wait on new events of watched files/directories 
 *        specified on the eventmask. FileSystemEvents
 *        will be returned one by one. Thus this
 *        function can be called in some while(true)
 *        loop.
 *
 * @return A new FileSystemEvent
 *
 */
inline FileSystemEvent Inotify::getNextEvent() {
    int length = 0;
    char buffer[EVENT_BUF_LEN];

    // Read Events from fd into buffer
    while (mEventQueue.empty()) {
        length = 0;
        memset(&buffer, 0, EVENT_BUF_LEN);
        while (length <= 0) {
            length = read(mInotifyFd, buffer, EVENT_BUF_LEN);
            if (length == -1) {
                mError = errno;
                if (mError != EINTR) {
                    continue;
                }
            }
        }

        // Read events from buffer into queue
        int i = 0;
        while (i < length) {
            inotify_event *event = ((struct inotify_event*) &buffer[i]);
            std::string eventName;
            if (event->len > 1) {
                eventName.append(event->name);
            }
            fs::path path(wdToPath(event->wd));
            if (!eventName.empty()) {
                path /= eventName;
            }
            if (fs::is_directory(path)) {
                event->mask |= IN_ISDIR;
            }
            FileSystemEvent fsEvent(event->wd, event->mask, path);

            i += EVENT_SIZE + event->len;

            if (fsEvent.path.empty()) {
                continue;
            }

            if (isIgnored(fsEvent.path.string())) {
                continue;
            }
            mEventQueue.push(fsEvent);
        }
    }
    // Return next event
    FileSystemEvent event = mEventQueue.front();
    mEventQueue.pop();
    return event;

}

inline int Inotify::getLastErrno() {
    return mError;

}

inline bool Inotify::isIgnored(std::string file) {
    if (mIgnoredDirectories.empty() and mOnceIgnoredDirectories.empty()) {
        return false;
    }

    for (unsigned i = 0; i < mOnceIgnoredDirectories.size(); ++i) {
        size_t pos = file.find(mOnceIgnoredDirectories[i]);
        if (pos != std::string::npos) {
            mOnceIgnoredDirectories.erase(mOnceIgnoredDirectories.begin() + i);
            return true;
        }
    }

    for (unsigned i = 0; i < mIgnoredDirectories.size(); ++i) {
        size_t pos = file.find(mIgnoredDirectories[i]);
        if (pos != std::string::npos) {
            return true;
        }
    }

    return false;
}

#endif // INOTIFY_INOTIFY_CPP_INCLUDE_
