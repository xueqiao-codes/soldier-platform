/*
 * attr_reporter_backend_log.h
 *
 *	数据在LOG中进行打印
 *
 *  Created on: 2017年3月18日
 *      Author: 44385
 */

#ifndef ATTR_BACKEND_ATTR_REPORTER_BACKEND_LOG_H_
#define ATTR_BACKEND_ATTR_REPORTER_BACKEND_LOG_H_

#include "attr/attr_reporter_backend.h"

namespace soldier {
namespace attr {


class AttrReporterBackendLog : public IAttrUploadBackend {
public:
	virtual void uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items, int period_seconds) noexcept;
};


} // end namespace attr
} // end namespace soldier



#endif /* ATTR_BACKEND_ATTR_REPORTER_BACKEND_LOG_H_ */
