/*
 * attr_reporter_backend_falcon.h
 *
 *  Created on: 2017年3月21日
 *      Author: wileywang
 */

#ifndef ATTR_BACKEND_ATTR_REPORTER_BACKEND_FALCON_H_
#define ATTR_BACKEND_ATTR_REPORTER_BACKEND_FALCON_H_

#include "base/thread_pool.h"
#include "attr/attr_reporter_backend.h"

#include <curlpp/cURLpp.hpp>

namespace soldier {
namespace attr {

class AttrReporterBackendFalcon : public IAttrUploadBackend {
public:
    AttrReporterBackendFalcon();
    virtual ~AttrReporterBackendFalcon() = default;

    virtual void uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items, int period_seconds) noexcept;

private:
    std::string tagsFalconDescription(const std::map<std::string, std::string>& tags);

    void onHttpUpload(const std::shared_ptr<std::vector<AttrItem>>& items, int period_seconds);

    std::unique_ptr<soldier::base::TaskThread> upload_thread_;
    curlpp::Cleanup cleaner_;
};


} // end namespace attr
} // end namespace soldier



#endif /* ATTR_BACKEND_ATTR_REPORTER_BACKEND_FALCON_H_ */
