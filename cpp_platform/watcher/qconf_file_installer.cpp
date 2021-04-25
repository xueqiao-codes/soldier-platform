/*
 * qconf_file_installer.cpp
 *
 *  Created on: 2017年9月18日
 *      Author: wileywang
 */

#include "watcher/qconf_file_installer.h"

#include <boost/algorithm/string/replace.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/filesystem.hpp>
#include <chrono>
#include <stdio.h>
#include <fstream>
#include <thread>
#include "base/time_helper.h"
#include "qconf.h"

namespace soldier {
namespace watcher {

static const unsigned int MAX_QCONF_BUFFER_SIZE = 1024*1024;

QConfFileInstaller::QConfFileInstaller(const std::string& qconf_path, const std::string& abs_file_path)
        : qconf_path_(qconf_path), abs_file_path_(abs_file_path) {
}

void QConfFileInstaller::install() {
    int ret = qconf_init();
    if (ret != 0) {
        throw std::runtime_error("qconf init failed");
    }

    std::unique_ptr<char[]> buffer(new char[MAX_QCONF_BUFFER_SIZE]);
    ret = qconf_get_conf(qconf_path_.c_str(), buffer.get(), MAX_QCONF_BUFFER_SIZE, NULL);
    if (ret != 0) {
        throw std::runtime_error("qconf get " + qconf_path_ + " content failed!");
    }

    ensureDirectories();

    std::string qconf_shell_name = boost::replace_all_copy(qconf_path_, "/", "#") + ".sh";
    std::string tmp_qconf_shell_path = "/usr/local/soldier/qconf_agent/script/" + boost::lexical_cast<std::string>(soldier::base::NowInMilliSeconds())
            + "_" +  boost::lexical_cast<std::string>(getpid())
            + qconf_shell_name + ".tmp";
    std::string qconf_shell_path = "/usr/local/soldier/qconf_agent/script/" + qconf_shell_name;

    std::ofstream of(tmp_qconf_shell_path);
    if (!of.good()) {
        throw std::runtime_error("open " + tmp_qconf_shell_path + " failed!");
    }

    std::string qconf_script_content = getQconfScript();
    of.write(qconf_script_content.c_str(), qconf_script_content.length());
    of.flush();
    if (!of.good()){
        throw std::runtime_error("write " + tmp_qconf_shell_path + " failed!");
    }
    of.close();

    boost::filesystem::rename(boost::filesystem::path(tmp_qconf_shell_path)
            , boost::filesystem::path(qconf_shell_path));
    boost::filesystem::permissions(boost::filesystem::path(qconf_shell_path)
            , boost::filesystem::add_perms | boost::filesystem::owner_exe| boost::filesystem::group_exe | boost::filesystem::others_exe);

    std::ofstream config_of(abs_file_path_);
    if (!config_of.good()) {
        throw std::runtime_error("open " + abs_file_path_ + " failed!");
    }
    ret = qconf_get_conf(qconf_path_.c_str(), buffer.get(), MAX_QCONF_BUFFER_SIZE, NULL);
    if (ret != 0) {
        throw std::runtime_error("qconf get " + qconf_path_ + " content failed!");
    }
    config_of.write(buffer.get(), strlen(buffer.get()));
    config_of.flush();
    if (!config_of.good()) {
        throw std::runtime_error("write " + abs_file_path_ + "failed!");
    }
    config_of.close();

//    ret = system(("/bin/bash " + qconf_shell_path).c_str());
//    if (ret != 0) {
//        throw std::runtime_error("update qconf failed, ret=" + boost::lexical_cast<std::string>(ret));
//    }
}

void QConfFileInstaller::ensureDirectories() {
    boost::filesystem::path parent_dir = boost::filesystem::path(abs_file_path_).parent_path();
    if (boost::filesystem::exists(parent_dir)) {
        return ;
    }
    if (!boost::filesystem::create_directories(parent_dir)) {
        throw std::runtime_error("create directorys for " + abs_file_path_ + " failed!");
    }
}

std::string QConfFileInstaller::getQconfScript() {
    std::stringstream script_builder;
    script_builder << "#!/bin/bash" << std::endl
                   << "NODE_ADD=0" << std::endl
                   << "NODE_CHANGE=1" << std::endl
                   << "NODE_DEL=2" << std::endl
                   << "qconf_path=\"" <<  qconf_path_  << "\"" << std::endl
                   << "target_file=\"" << abs_file_path_ + "\"" << std::endl
                   << "if [ \"$qconf_event\" != \"$NODE_DEL\" ]; then" << std::endl
                   << "  value=$(/usr/local/soldier/qconf_agent/bin/qconf get_conf $qconf_path )" << std::endl
                   << "  if [ $? -ne 0 ];then" << std::endl
                   << "   echo \"get_conf  $qconf_path failed!!!\"" << std::endl
                   << "   exit 1" << std::endl
                   << "  fi" << std::endl
                   << "  if [ -f \"$target_file\" ];then" << std::endl
                   << "    old_conf_value=$( cat $target_file )" << std::endl
                   << "    if [ \"$value\" != \"$old_conf_value\" ]; then" << std::endl
                   << "      cp -f $target_file $target_file.bak || exit 1" << std::endl
                   << "      echo \"$value\" > $target_file || exit 2" << std::endl
                   << "    fi" << std::endl
                   << "  else" << std::endl
                   << "    echo \"$value\" > $target_file || exit 1" << std::endl
                   << "  fi" << std::endl
                   << "else" << std::endl
                   << "  rm -f $target_file" << std::endl
                   << "fi" << std::endl;
    return script_builder.str();
}

} // end namespace watcher
} // end namespace soldier


