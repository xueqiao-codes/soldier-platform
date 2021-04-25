/*
 * attr_reporter_swig.cpp
 *
 *  Created on: 2017年3月23日
 *      Author: wileywang
 */
#include "attr/attr_reporter_swig.h"

#include "attr/attr_reporter_factory.h"

namespace soldier {
namespace attr {

int requireKey(ReporterType type
        , const std::string& metric, const std::map<std::string, std::string>& tags) {
    if (type == ReporterType::REPORTER_TYPE_MINUTE) {
        return AttrReporterFactory::Global().minute().requireKey(metric, tags);
    } else if (type == ReporterType::REPORTER_TYPE_THIRTYSECONDS) {
        return AttrReporterFactory::Global().thirtySecs().requireKey(metric, tags);
    }

    return -1;
}

void inc(ReporterType type, int key, long long value) {
    if (type == ReporterType::REPORTER_TYPE_MINUTE) {
        AttrReporterFactory::Global().minute().inc(key, value);
    } else if (type == ReporterType::REPORTER_TYPE_THIRTYSECONDS) {
        AttrReporterFactory::Global().thirtySecs().inc(key, value);
    }
}

void set(ReporterType type, int key, long long value) {
    if (type == ReporterType::REPORTER_TYPE_MINUTE) {
        AttrReporterFactory::Global().minute().set(key, value);
    } else if (type == ReporterType::REPORTER_TYPE_THIRTYSECONDS) {
        AttrReporterFactory::Global().thirtySecs().set(key, value);
    }
}

void average(ReporterType type, int key, long long value) {
    if (type == ReporterType::REPORTER_TYPE_MINUTE) {
        AttrReporterFactory::Global().minute().average(key, value);
    } else if (type == ReporterType::REPORTER_TYPE_THIRTYSECONDS) {
        AttrReporterFactory::Global().thirtySecs().average(key, value);
    }
}

void keep(ReporterType type, int key, long long value) {
    if (type == ReporterType::REPORTER_TYPE_MINUTE) {
        AttrReporterFactory::Global().minute().keep(key, value);
    } else if (type == ReporterType::REPORTER_TYPE_THIRTYSECONDS) {
        AttrReporterFactory::Global().thirtySecs().keep(key, value);
    }
}

} // end namespace attr
} // end namespace soldier



