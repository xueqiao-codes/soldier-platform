/*
 * app_log.h
 *
 *  Created on: 2017年2月19日
 *      Author: 44385
 */

#ifndef BASE_APP_LOG_H_
#define BASE_APP_LOG_H_

#include <stdlib.h>
#include "spdlog/spdlog.h"

namespace soldier {
namespace base {

enum class LogLevel {
    LOG_TRACE = 1,
	LOG_DEBUG = 2,
	LOG_INFO = 3,
	LOG_WARNING = 5,
	LOG_ERROR = 6,
	LOG_FALAL = 7,
	LOG_OFF = 8
};

class AppLog {
public:
	static void Init(const std::string& log_module);
	static void Init(const std::string& log_module, const std::string& log_basedir);

	static bool IsTraceEnabled();
	static bool IsDebugEnabled();
	static bool IsInfoEnabled();
	static bool IsWarnEnabled();
	static bool IsErrorEnabled();
	static void FlushLog();

	template <typename... Args> static void T(const char* fmt, const Args&... args);
	template <typename... Args> static void D(const char* fmt, const Args&... args);
	template <typename... Args> static void I(const char* fmt, const Args&... args);
	template <typename... Args> static void W(const char* fmt, const Args&... args);
	template <typename... Args> static void E(const char* fmt, const Args&... args);
	template <typename... Args> static void F(const char* fmt, const Args&... args);

	template <typename... Args> static void S(const char* fmt, const Args&... args);

protected:
	AppLog(const std::string& log_module, const std::string& log_basedir);
	virtual ~AppLog();

	template <typename... Args> void Log(LogLevel level, const char* fmt, const Args&... args);
	template <typename... Args> void SLog(const char* fmt, const Args&... args);

private:
	static void AtExit();
	void OnWatch();
	void Flush();
	void checkLogLevel(bool first);
	int checkLogLevelByQconf();
	int checkLogLevelByFile();

	bool isLocalFileMode();

	static std::mutex g_log_instance_lock;
	static AppLog* g_log_instance;

	std::string log_basedir_;
	std::string log_module_;
	std::string log_module_dir_;

	volatile int current_loglevel_ = (int)LogLevel::LOG_TRACE;
	volatile bool stop_watch_ = false;
	std::shared_ptr<std::thread> watch_thread_;
	std::shared_ptr<spdlog::logger> logger_;
	std::shared_ptr<spdlog::logger> slogger_; // for statics
};

template<typename... Args> void AppLog::Log(LogLevel level, const char* fmt, const Args&... args) {
	if ((int)level < current_loglevel_) {
		return ;
	}

	spdlog::level::level_enum spd_level = spdlog::level::trace;
	if (level == LogLevel::LOG_TRACE) {
	    spd_level = spdlog::level::trace;
	} else if (level == LogLevel::LOG_DEBUG) {
		spd_level = spdlog::level::debug;
	} else if (level == LogLevel::LOG_INFO) {
		spd_level = spdlog::level::info;
	} else if (level == LogLevel::LOG_WARNING) {
		spd_level = spdlog::level::warn;
	} else if (level == LogLevel::LOG_ERROR) {
		spd_level = spdlog::level::err;
	} else if (level == LogLevel::LOG_FALAL) {
		spd_level = spdlog::level::critical;
	}
	logger_->log(spd_level, fmt, args...);
}

template <typename... Args> void AppLog::SLog(const char* fmt, const Args&... args) {
    if ((int)LogLevel::LOG_INFO < current_loglevel_) {
        return ;
    }
	slogger_->log(spdlog::level::info, fmt, args...);
}

template <typename... Args> void AppLog::T(const char* fmt, const Args&... args) {
    if (g_log_instance == nullptr) {
        return ;
    }
    g_log_instance->Log(LogLevel::LOG_TRACE, fmt, args...);
}

template <typename... Args> void AppLog::D(const char* fmt, const Args&... args) {
	if (g_log_instance == nullptr) {
		return ;
	}
	g_log_instance->Log(LogLevel::LOG_DEBUG, fmt, args...);
}

template <typename... Args> void AppLog::I(const char* fmt, const Args&... args) {
	if (g_log_instance == nullptr) {
		return ;
	}
	g_log_instance->Log(LogLevel::LOG_INFO, fmt, args...);
}

template <typename... Args> void AppLog::W(const char* fmt, const Args&... args) {
	if (g_log_instance == nullptr) {
		return ;
	}
	g_log_instance->Log(LogLevel::LOG_WARNING, fmt, args...);
}

template <typename... Args> void AppLog::E(const char* fmt, const Args&... args) {
	if (g_log_instance == nullptr) {
		return ;
	}
	g_log_instance->Log(LogLevel::LOG_ERROR, fmt, args...);
}

template <typename... Args> void AppLog::F(const char* fmt, const Args&... args) {
	if (g_log_instance == nullptr) {
		return ;
	}
	g_log_instance->Log(LogLevel::LOG_FALAL, fmt, args...);
	g_log_instance->Flush();
	std::terminate();
}

template <typename... Args> void AppLog::S(const char* fmt, const Args&... args) {
	if (g_log_instance == nullptr) {
		return ;
	}

	g_log_instance->SLog(fmt, args...);
}

} // end namespace base
} // end namespace soldier

#define APPLOG_STR_H(x) #x
#define APPLOG_STR_HELPER(x) APPLOG_STR_H(x)
#define APPLOG_TRACE(...) if (::soldier::base::AppLog::IsTraceEnabled()) ::soldier::base::AppLog::T("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)
#define APPLOG_DEBUG(...) if (::soldier::base::AppLog::IsDebugEnabled()) ::soldier::base::AppLog::D("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)
#define APPLOG_WARN(...)  if (::soldier::base::AppLog::IsWarnEnabled()) ::soldier::base::AppLog::W("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)
#define APPLOG_INFO(...)  if (::soldier::base::AppLog::IsInfoEnabled()) ::soldier::base::AppLog::I("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)
#define APPLOG_ERROR(...) if (::soldier::base::AppLog::IsErrorEnabled()) ::soldier::base::AppLog::E("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)
#define APPLOG_FATAL(...) ::soldier::base::AppLog::F("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)
#define APPLOG_STATICS(...) ::soldier::base::AppLog::S("[" __FILE__ ":" APPLOG_STR_HELPER(__LINE__) "] " __VA_ARGS__)

#define APPLOG_DEBUG_OPENED() ::soldier::base::AppLog::IsDebugEnabled()
#define APPLOG_INFO_OPENED()  ::soldier::base::AppLog::IsInfoEnabled()


#endif /* BASE_APP_LOG_H_ */
