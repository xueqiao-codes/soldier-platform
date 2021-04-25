/*
 * app_log.cc
 *
 *  Created on: 2017年2月19日
 *      Author: 44385
 */
#include "base/app_log.h"
#include "base/string_util.h"

#include <set>
#include <stdio.h>
#include <algorithm>
#include <stdlib.h>
#include <sys/stat.h>
#include <stdexcept>

#include "qconf.h"

namespace soldier {
namespace base {

std::mutex AppLog::g_log_instance_lock;
AppLog* AppLog::g_log_instance = nullptr;

static const std::string DEFAULT_LOGGER_BASE_DIR = "/data/applog";

void AppLog::Init(const std::string& log_module) {
	Init(log_module, DEFAULT_LOGGER_BASE_DIR);
}

void AppLog::Init(const std::string& log_module, const std::string& log_basedir) {
	if (log_module.empty() || log_basedir.empty()) {
		return ;
	}

	if (log_module[0] == '/') {
		throw new std::invalid_argument("log_module should not start with /");
	}

	if (g_log_instance == nullptr) {
		std::unique_lock<std::mutex> auto_lock(g_log_instance_lock);
		if (g_log_instance == nullptr) {
			g_log_instance = new AppLog(log_module, log_basedir);
			std::atexit(&AppLog::AtExit);
		}
	}
}

bool AppLog::IsTraceEnabled() {
    if (!g_log_instance) {
        return false;
    }

    return (int)LogLevel::LOG_TRACE >= g_log_instance->current_loglevel_;
}

bool AppLog::IsDebugEnabled() {
	if (!g_log_instance) {
		return false;
	}

	return (int)LogLevel::LOG_DEBUG >= g_log_instance->current_loglevel_;
}

bool AppLog::IsInfoEnabled() {
	if (!g_log_instance) {
		return false;
	}

	return (int)LogLevel::LOG_INFO >= g_log_instance->current_loglevel_;
}

bool AppLog::IsWarnEnabled() {
    if (!g_log_instance) {
        return false;
    }

    return (int)LogLevel::LOG_WARNING >= g_log_instance->current_loglevel_;
}

bool AppLog::IsErrorEnabled() {
    if (!g_log_instance) {
        return false;
    }

    return (int)LogLevel::LOG_ERROR >= g_log_instance->current_loglevel_;
}

void AppLog::FlushLog() {
    if (!g_log_instance) {
        return ;
    }
    g_log_instance->Flush();
}

void AppLog::AtExit() {
	if (g_log_instance != nullptr) {
		APPLOG_INFO("Flush logging...");
		g_log_instance->Flush();
		AppLog* log_instance = g_log_instance;
		g_log_instance = NULL;
		delete log_instance;
	}
}

AppLog::AppLog(const std::string& log_module, const std::string& log_basedir)
	  : log_basedir_(log_basedir)
        , log_module_(log_module)
	    , log_module_dir_(log_basedir)
	    , watch_thread_(new std::thread(&AppLog::OnWatch, this)) {

	std::vector<std::string> sub_log_modules;
	StringUtil::tokenize(log_module, sub_log_modules, "/", true);

	for (auto& sub_log_module : sub_log_modules) {
		log_module_dir_.append("/");
		log_module_dir_.append(sub_log_module);

		if (0 != access(log_module_dir_.c_str(), 0)) {
			mkdir(log_module_dir_.c_str(), 0755);
		}
	}

	spdlog::set_pattern("[%Y-%m-%d %H:%M:%S.%e %t:%l]%v");

	spdlog::set_async_mode(1024 * 1024);

	if (isLocalFileMode()) {
	    spdlog::sink_ptr trace_sink(
	        new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/trace", "log", 10*1024*1024, 10));
	    trace_sink->set_level(spdlog::level::trace);

	    spdlog::sink_ptr debug_sink(
			new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/debug", "log", 10*1024*1024, 10));
	    debug_sink->set_level(spdlog::level::debug);

	    spdlog::sink_ptr info_sink(
			new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/info", "log", 10*1024*1024, 10));
	    info_sink->set_level(spdlog::level::info);

	    spdlog::sink_ptr warn_sink(
			new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/warn", "log", 10*1024*1024, 10));
	    warn_sink->set_level(spdlog::level::warn);

	    spdlog::sink_ptr error_sink(
			new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/error", "log", 10*1024*1024, 10));
	    error_sink->set_level(spdlog::level::err);

	    spdlog::sink_ptr fatal_sink(
			new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/fatal", "log", 10*1024*1024, 10));
	    fatal_sink->set_level(spdlog::level::critical);

	    spdlog::sink_ptr statics_sink(
			new spdlog::sinks::rotating_file_sink_st(log_module_dir_ + "/statics", "log", 10*1024*1024, 10));
	    statics_sink->set_level(spdlog::level::trace);

	    logger_ = spdlog::create("AppLog_" + log_module, {trace_sink, debug_sink, info_sink, warn_sink, error_sink, fatal_sink});
	    logger_->set_level(spdlog::level::trace);
	    logger_->flush_on(spdlog::level::trace);

	    slogger_ = spdlog::create("StaticLog_" + log_module, {statics_sink});
	    slogger_->set_level(spdlog::level::trace);
	    slogger_->flush_on(spdlog::level::trace);

	} else {
	    if (0 != qconf_init()) {
	        throw std::runtime_error("init qconf failed!");
	    }

	    logger_ = spdlog::create("AppLog_" + log_module, {spdlog::sinks::stdout_sink_st::instance()});
	    logger_->set_level(spdlog::level::trace);
	    logger_->flush_on(spdlog::level::trace);

	    slogger_ = spdlog::create("StaticLog_" + log_module, {spdlog::sinks::stdout_sink_st::instance()});
	    slogger_->set_level(spdlog::level::trace);
	    slogger_->flush_on(spdlog::level::trace);

	}

	checkLogLevel(true);
}

AppLog::~AppLog() {
	stop_watch_ = true;
	watch_thread_->join();
}

void AppLog::checkLogLevel(bool first) {
    int old_loglevel = current_loglevel_;

    if (isLocalFileMode()) {
        current_loglevel_ = checkLogLevelByFile();
    } else {
        current_loglevel_ = checkLogLevelByQconf();
    }

    if (first) {
        printf("applog start, current_loglevel_=%d\n", current_loglevel_);
    } else if (current_loglevel_ != old_loglevel) {
        printf("switch applog level, current_loglevel_=%d, old_loglevel=%d\n", current_loglevel_, old_loglevel);
    }
}

int AppLog::checkLogLevelByQconf() {
    std::stringstream qconf_path;
    qconf_path << "logger/";

    std::string module_key;
    if (log_basedir_ == (DEFAULT_LOGGER_BASE_DIR)) {
        module_key = log_module_;
    } else {
        module_key = log_module_dir_;
    }

    std::replace(module_key.begin(), module_key.end(), '/', '|');
    qconf_path << module_key;

    string_vector_t log_switch_keys;
    int ret = init_string_vector(&log_switch_keys);
    if (ret != QCONF_OK) {
        return (int)LogLevel::LOG_WARNING;
    }

    ret = qconf_aget_batch_keys(qconf_path.str().c_str(), &log_switch_keys, NULL);
    if (ret != QCONF_OK) {
        destroy_string_vector(&log_switch_keys);
        return (int)LogLevel::LOG_WARNING;
    }

    std::set<std::string> keys;
    for (int index = 0; index < log_switch_keys.count; ++index) {
        keys.insert(log_switch_keys.data[index]);
    }
    destroy_string_vector(&log_switch_keys);

    if (keys.end() != keys.find("no_fatal")) {
        return (int)LogLevel::LOG_OFF;
    }
    if (keys.end() != keys.find("no_error")) {
        return (int)LogLevel::LOG_FALAL;
    }
    if (keys.end() != keys.find("no_warn")) {
        return (int)LogLevel::LOG_ERROR;
    }
    if (keys.end() != keys.find("no_info")) {
        return (int)LogLevel::LOG_WARNING;
    }
    if (keys.end() != keys.find("no_debug")) {
        return (int)LogLevel::LOG_INFO;
    }
    if (keys.end() != keys.find("no_trace")) {
        return (int)LogLevel::LOG_DEBUG;
    }
    return (int)LogLevel::LOG_TRACE;
}

int AppLog::checkLogLevelByFile() {
    if (0 == access((log_module_dir_ + "/no_fatal").c_str(), 0)) {
        return (int)LogLevel::LOG_OFF;
    }

    if (0 == access((log_module_dir_ + "/no_error").c_str(), 0)) {
        return (int)LogLevel::LOG_FALAL;
    }

    if (0 == access((log_module_dir_ + "/no_warn").c_str(), 0)) {
        return (int)LogLevel::LOG_ERROR;
    }

    if (0 == access((log_module_dir_ + "/no_info").c_str(), 0)) {
        return (int)LogLevel::LOG_WARNING;
    }

    if (0 == access((log_module_dir_ + "/no_debug").c_str(), 0)) {
        return (int)LogLevel::LOG_INFO;
    }

    if (0 == access((log_module_dir_ + "/no_trace").c_str(), 0)) {
        return (int)LogLevel::LOG_DEBUG;
    }

    return (int)LogLevel::LOG_TRACE;
}

void AppLog::OnWatch() {
	while(!stop_watch_) {
		// check every 5 seconds
	    for (int count = 0; count < 100; ++count) {
	        std::this_thread::sleep_for(std::chrono::milliseconds(50));
	        if (stop_watch_) {
	            break;
	        }
	    }

	    checkLogLevel(false);
	}
}

void AppLog::Flush(){
	logger_->flush();
}

bool AppLog::isLocalFileMode() {
    char* stdout_mode_env = getenv("APPLOG_STDOUT_MODE");
    if (stdout_mode_env != NULL && 0 == strcmp("true", stdout_mode_env)) {
        return false;
    }
    return true;
}



} // end namespace base
} // end namespace soldier



