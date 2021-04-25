/*
 * qconf_file_installer.h
 *
 *  Created on: 2017年9月18日
 *      Author: wileywang
 */

#ifndef WATCHER_QCONF_FILE_INSTALLER_H_
#define WATCHER_QCONF_FILE_INSTALLER_H_

#include <string>

namespace soldier {
namespace watcher {

class QConfFileInstaller {
public:
    QConfFileInstaller(const std::string& qconf_path
            , const std::string& abs_file_path);
    ~QConfFileInstaller() = default;

    void install();

private:
    std::string getQconfScript();
    void ensureDirectories();

    std::string qconf_path_;
    std::string abs_file_path_;
};

} // end namespace watcher
} // end namespace soldier



#endif /* WATCHER_QCONF_FILE_INSTALLER_H_ */
