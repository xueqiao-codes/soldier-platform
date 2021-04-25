/*
 * attr_reporter_swig.h
 *
 *  Created on: 2017年3月23日
 *      Author: wileywang
 */

#ifndef ATTR_ATTR_REPORTER_SWIG_H_
#define ATTR_ATTR_REPORTER_SWIG_H_

#include <map>
#include <string>

namespace soldier {
namespace attr {

enum ReporterType {
    REPORTER_TYPE_MINUTE,
    REPORTER_TYPE_THIRTYSECONDS
};

int requireKey(ReporterType type
        , const std::string& metric, const std::map<std::string, std::string>& tags);
void inc(ReporterType type, int key, long long value);
void set(ReporterType type, int key, long long value);
void average(ReporterType type, int key, long long value);
void keep(ReporterType type, int key, long long value);

} // end namespace attr
} // end namespace




#endif /* ATTR_ATTR_REPORTER_SWIG_H_ */
