/*
 * applog_test.cpp
 *
 *  Created on: 2018年11月20日
 *      Author: wangli
 */

#include "base/app_log.h"

#include <chrono>
#include <thread>

using namespace soldier::base;

int main() {
    AppLog::Init("test/applog_test");

    while(true) {
        APPLOG_TRACE("this is a {}", "trace");
        APPLOG_DEBUG("this is a {}", "debug");
        APPLOG_INFO("this is a {}", "info");
        APPLOG_WARN("this is a {}", "warn");
        APPLOG_ERROR("this is a {}", "error");

        std::this_thread::sleep_for(std::chrono::seconds(10));
    }


    return 0;
}


