/*
 * attr_reporter_factory.cc
 *
 *  Created on: 2017年3月18日
 *      Author: 44385
 */
#include "attr/attr_reporter_factory.h"
#include "attr/attr_reporter_impl.h"
#include "attr/backend/attr_reporter_backend_log.h"
#include "attr/backend/attr_reporter_backend_falcon.h"

namespace soldier {
namespace attr {

std::mutex AttrReporterFactory::global_lock_;
std::unique_ptr<AttrReporterFactory> AttrReporterFactory::global_factory_;

AttrReporterFactory& AttrReporterFactory::Global() {
	if (!global_factory_.get()) {
		global_lock_.lock();
		if (!global_factory_.get()) {
			global_factory_.reset(new AttrReporterFactory());
		}
		global_lock_.unlock();
	}
	return *global_factory_;
}

AttrReporterFactory::AttrReporterFactory() {
	std::unique_ptr<LinkedAttrUploadBackend> linked_backend(new LinkedAttrUploadBackend());

	// register all backend
	linked_backend->add(std::shared_ptr<IAttrUploadBackend>(new AttrReporterBackendLog()));
	linked_backend->add(std::shared_ptr<IAttrUploadBackend>(new AttrReporterBackendFalcon()));

	// transfer the own to backend_
	backend_.reset(linked_backend.release());
}

IAttrReporter& AttrReporterFactory::minute() {
	if (!reporter_1min_.get()) {
		global_lock_.lock();
		if (!reporter_1min_.get()) {
			reporter_1min_.reset(new AttrReporterImpl(backend_, 60));
		}
		global_lock_.unlock();
	}
	return *reporter_1min_;
}

IAttrReporter& AttrReporterFactory::thirtySecs() {
	if (!reporter_30secs_.get()) {
		global_lock_.lock();
		if (!reporter_30secs_.get()) {
			reporter_30secs_.reset(new AttrReporterImpl(backend_, 30));
		}
		global_lock_.unlock();
	}
	return *reporter_30secs_;
}

IAttrReporter& AttrReporterFactory::fifteenSecs() {
	if (!reporter_15secs_.get()) {
		global_lock_.lock();
		if (!reporter_15secs_.get()) {
			reporter_15secs_.reset(new AttrReporterImpl(backend_, 15));
		}
		global_lock_.unlock();
	}
	return *reporter_15secs_;
}

} // end namespace attr
} // end namespace soldier


