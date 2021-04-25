/*
 * attr_reporter_backend_log.cpp
 *
 *  Created on: 2017年3月18日
 *      Author: 44385
 */
#include "attr/backend/attr_reporter_backend_log.h"

#include "base/app_log.h"


namespace soldier {
namespace attr {

void AttrReporterBackendLog::uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items, int period_seconds) noexcept {
	APPLOG_STATICS("start reporter[{}]...", period_seconds);
	for (auto& item : *items) {
		APPLOG_STATICS("metric({}), tags({}), timestamp({}), value({})"
				, item.getMetric(), item.getTagsUrlStr(), item.getTimestamp(), item.getValue());
	}
	APPLOG_STATICS("end reporter...");
}


} // end namespace attr
} // end namespace soldier



