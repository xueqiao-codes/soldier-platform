/*
 * attr_reporter.h
 *
 *  Created on: 2017年3月14日
 *      Author: wileywang
 */

#ifndef INTERFACE_ATTR_REPORTER_H_
#define INTERFACE_ATTR_REPORTER_H_

#include <map>
#include <string>
#include <map>

namespace soldier {
namespace attr {

class IAttrReporter {
public:
    virtual ~IAttrReporter() = default;

    /**
     *  request a key for upload, user should use the same key to report
     * @return -1, can not alloc key
     */
    virtual int64_t requireKey(const std::string& metric,
            const std::map<std::string, std::string>& tags) noexcept = 0;

    virtual void inc(const int64_t& key
            , const int64_t& value) noexcept = 0;

    virtual void set(const int64_t& key
            , const int64_t& value) noexcept= 0;

    virtual void average(const int64_t& key
            , const int64_t& value) noexcept = 0;

    virtual void keep(const int64_t& key
            , const int64_t& value) noexcept = 0;

protected:
    IAttrReporter() = default;

};

} // end namespace attr
} // end namespace soldier


#define INC_METRIC(NAME, reporter, metric, tag_key, tag_value, value) \
{ \
    static thread_local std::map<std::string, int64_t> NAME##_REGISTER_MAP; \
    int64_t key = -1; \
    auto it = NAME##_REGISTER_MAP.find(tag_value); \
    if (it == NAME##_REGISTER_MAP.end()) { \
        key = reporter.requireKey(metric, {{tag_key, tag_value}}); \
        NAME##_REGISTER_MAP[tag_value] = key; \
    } else { \
        key = it->second; \
    } \
    reporter.inc(key, value); \
}

#define SET_METRIC(NAME, reporter, metric, tag_key, tag_value, value) \
{ \
    static thread_local std::map<std::string, int64_t> NAME##_REGISTER_MAP; \
    int64_t key = -1; \
    auto it = NAME##_REGISTER_MAP.find(tag_value); \
    if (it == NAME##_REGISTER_MAP.end()) { \
        key = reporter.requireKey(metric, {{tag_key, tag_value}}); \
        NAME##_REGISTER_MAP[tag_value] = key; \
    } else { \
        key = it->second; \
    } \
    reporter.set(key, value); \
}

#define AVERAGE_METRIC(NAME, reporter, metric, tag_key, tag_value, value) \
{ \
    static thread_local std::map<std::string, int64_t> NAME##_REGISTER_MAP; \
    int64_t key = -1; \
    auto it = NAME##_REGISTER_MAP.find(tag_value); \
    if (it == NAME##_REGISTER_MAP.end()) { \
        key = reporter.requireKey(metric, {{tag_key, tag_value}}); \
        NAME##_REGISTER_MAP[tag_value] = key; \
    } else { \
        key = it->second; \
    } \
    reporter.average(key, value); \
}

#endif /* INTERFACE_ATTR_REPORTER_H_ */
