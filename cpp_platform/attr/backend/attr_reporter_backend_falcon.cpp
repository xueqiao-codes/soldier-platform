/*
 * attr_reporter_backend_falcon.cpp
 *
 *  Created on: 2017年3月21日
 *      Author: wileywang
 */
#include "attr/backend/attr_reporter_backend_falcon.h"

#include <unistd.h>
#include <errno.h>
#include <sstream>
#include <list>
#include "base/app_log.h"
#include "curlpp/Easy.hpp"
#include "curlpp/Options.hpp"
#include "curlpp/Exception.hpp"
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"

namespace soldier {
namespace attr {

AttrReporterBackendFalcon::AttrReporterBackendFalcon() {
    upload_thread_.reset(new soldier::base::TaskThread());
}

void AttrReporterBackendFalcon::uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items
        , int period_seconds) noexcept {
    upload_thread_->postTask(&AttrReporterBackendFalcon::onHttpUpload, this, items, period_seconds);
}

void AttrReporterBackendFalcon::onHttpUpload(const std::shared_ptr<std::vector<AttrItem>>& items
        , int period_seconds) {
    char hostname[HOST_NAME_MAX + 1];
    if (0 != gethostname(hostname, HOST_NAME_MAX + 1)) {
        APPLOG_ERROR("gethostname failed, errno={}", errno);
        return ;
    }

    rapidjson::StringBuffer s;
    rapidjson::Writer<rapidjson::StringBuffer> writer(s);

    writer.StartArray();
    for (auto& item : *items) {
        writer.StartObject();

        writer.Key("endpoint");
        writer.String(hostname);

        writer.Key("metric");
        writer.String(item.getMetric());

        writer.Key("timestamp");
        writer.Int64(item.getTimestamp());

        writer.Key("step");
        writer.Int(period_seconds);

        writer.Key("value");
        writer.Int64(item.getValue());

        writer.Key("counterType");
        writer.String("GAUGE");

        writer.Key("tags");
        writer.String(tagsFalconDescription(item.getTags()));

        writer.EndObject();
    }
    writer.EndArray();

    try {
        curlpp::Easy request;

        request.setOpt(new curlpp::options::Url("http://127.0.0.1:1988/v1/push"));
//        request.setOpt(new curlpp::options::Verbose(true));

        std::list<std::string> header;
        header.push_back("Content-Type: application/json");

        request.setOpt(new curlpp::options::HttpHeader(header));
        request.setOpt(new curlpp::options::PostFields(s.GetString()));
        request.setOpt(new curlpp::options::PostFieldSize(s.GetSize()));
        request.setOpt(new curlpp::options::ConnectTimeout(3));
        request.setOpt(new curlpp::options::Timeout(30));

        std::stringstream output_stream;
        request.setOpt(new curlpp::options::WriteStream(&output_stream));

        request.perform();

        APPLOG_DEBUG("Upload {} reponse {} ", s.GetString(), output_stream.str());

    } catch (std::exception& e) {
        APPLOG_ERROR("attr reporter upload {} to falcon agent exception {}", s.GetString(), e.what());
    }
}

std::string AttrReporterBackendFalcon::tagsFalconDescription(const std::map<std::string, std::string>& tags) {
    std::string description;
    for (auto& tag_pair : tags) {
        if (!description.empty()) {
            description.append(",");
        }
        description.append(tag_pair.first);
        description.append("=");
        description.append(tag_pair.second);
    }
    return description;
}

} // end namespace attr
} // end namespace soldier



