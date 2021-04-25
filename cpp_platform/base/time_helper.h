/*
 * time_helper.h
 *
 *  Created on: 2017年3月11日
 *      Author: 44385
 */

#ifndef BASE_TIME_HELPER_H_
#define BASE_TIME_HELPER_H_

#include <chrono>
#include <stdint.h>
#include <string>

namespace soldier {
namespace base {


int64_t NowInMilliSeconds();
int32_t NowInSeconds();

std::string Timestamp2String(int32_t timestamp, const char* format);
int32_t String2Timestamp(const std::string& date_time, const char* format);

enum class BlockTimerRecoderOperationType {
    EQUAL,
    PLUS
};

class BlockTimeRecoder {
public:
    BlockTimeRecoder(uint64_t& time_escaped_ns, BlockTimerRecoderOperationType op_type);
    ~BlockTimeRecoder();

private:
    uint64_t& time_escaped_ns_;
    BlockTimerRecoderOperationType op_type_;
    std::chrono::time_point<std::chrono::high_resolution_clock> start_clock_;
};


} // end namespace base
} // end namespace soldier

#define S_BLOCK_TIMER(time_escaped_ns) ::soldier::base::BlockTimeRecoder time_escaped_ns##_recoder_inner(time_escaped_ns, ::soldier::base::BlockTimerRecoderOperationType::EQUAL)
#define S_BLOCK_TIMER_PLUS(time_escaped_ns) ::soldier::base::BlockTimeRecoder time_escaped_ns##_recoder_inner(time_escaped_ns, ::soldier::base::BlockTimerRecoderOperationType::PLUS)

#endif /* BASE_TIME_HELPER_H_ */
