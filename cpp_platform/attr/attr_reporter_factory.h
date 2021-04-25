/*
 * attr_reporter_factory.h
 *
 *  Created on: 2017年3月18日
 *      Author: 44385
 */

#ifndef ATTR_ATTR_REPORTER_FACTORY_H_
#define ATTR_ATTR_REPORTER_FACTORY_H_

#include <memory>
#include <mutex>
#include "attr/attr_reporter.h"
#include "attr/attr_reporter_backend.h"

namespace soldier {
namespace attr {

class AttrReporterFactory {
public:
	~AttrReporterFactory() = default;

	static AttrReporterFactory& Global();

	/**
	 *  1min一次上报
	 */
	IAttrReporter& minute();

	/**
	 *  30s上报一次
	 */
	IAttrReporter& thirtySecs();

	/**
	 *  15s上报一次
	 */
	IAttrReporter& fifteenSecs();

private:
	AttrReporterFactory();

	static std::mutex global_lock_;
	static std::unique_ptr<AttrReporterFactory> global_factory_;

	std::shared_ptr<IAttrUploadBackend> backend_;

	std::unique_ptr<IAttrReporter> reporter_1min_;
	std::unique_ptr<IAttrReporter> reporter_30secs_;
	std::unique_ptr<IAttrReporter> reporter_15secs_;
};

} // end namespace attr
} // end namespace soldier




#endif /* ATTR_ATTR_REPORTER_FACTORY_H_ */
