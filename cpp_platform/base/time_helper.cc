/*
 * time_helper.cc
 *
 *  Created on: 2017年3月11日
 *      Author: 44385
 */
#include "base/time_helper.h"

#include <chrono>
#include <iomanip>
#include <time.h>
#include <sstream>

namespace soldier {
namespace base {

int64_t NowInMilliSeconds() {
	return std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
}

int32_t NowInSeconds() {
	return (int32_t)std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
}

std::string Timestamp2String(int32_t timestamp, const char* format) {
	char buffer[128];
	time_t t = timestamp;
	struct tm rtm;
	localtime_r(&t, &rtm);
	strftime(buffer, sizeof(buffer)/sizeof(buffer[0]), format, &rtm);
	return buffer;
}

int32_t String2Timestamp(const std::string& date_time, const char* format) {
    struct tm insert_tm = {0};
    if ((date_time.c_str() + date_time.length()) != strptime(date_time.c_str(), format, &insert_tm)) {
        return -1;
    }
    return std::mktime(&insert_tm);
}

BlockTimeRecoder::BlockTimeRecoder(uint64_t& time_escaped_ns, BlockTimerRecoderOperationType op_type)
    : time_escaped_ns_(time_escaped_ns), op_type_(op_type){
    start_clock_ = std::chrono::high_resolution_clock::now();
}

BlockTimeRecoder::~BlockTimeRecoder() {
    uint64_t block_time_ns = std::chrono::duration_cast<std::chrono::nanoseconds>(std::chrono::high_resolution_clock::now() - start_clock_).count();
    if (op_type_ == BlockTimerRecoderOperationType::EQUAL) {
        time_escaped_ns_ = block_time_ns;
    } else {
        time_escaped_ns_ += block_time_ns;
    }
}


} // end namespace base
} // end namespace soldier



