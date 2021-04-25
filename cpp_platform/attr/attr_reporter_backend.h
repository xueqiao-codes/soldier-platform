/*
 * attr_reporter_backend.h
 *
 *  Created on: 2017年3月18日
 *      Author: 44385
 */

#ifndef ATTR_ATTR_REPORTER_BACKEND_H_
#define ATTR_ATTR_REPORTER_BACKEND_H_

#include <map>
#include <memory>
#include <string>
#include <vector>


namespace soldier {
namespace attr {

class AttrItem {
public:
    AttrItem() = default;
    ~AttrItem() = default;

    AttrItem(AttrItem&& item)
        : metric_(std::move(item.metric_))
         , tags_(std::move(item.tags_))
         , timestamp_(item.timestamp_)
         , value_(item.value_) {
    }

    const std::string& getMetric() const {
        return metric_;
    }
    void setMetric(const std::string& metric) {
        metric_ = metric;
    }

    const std::map<std::string, std::string>& getTags() const {
        return tags_;
    }

    void setTags(const std::map<std::string, std::string>& tags) {
        this->tags_ = tags;
    }

    std::string getTagsUrlStr() const {
    	std::string tags_url_str;
    	for (auto& tag_pair : tags_) {
    		if (!tags_url_str.empty()) {
    			tags_url_str.append("&");
    		}
    		tags_url_str.append(tag_pair.first);
    		tags_url_str.append("=");
    		tags_url_str.append(tag_pair.second);
    	}
    	return tags_url_str;
    }

    const int64_t& getTimestamp() const {
        return timestamp_;
    }
    void setTimestamp(int64_t timestamp) {
        timestamp_ = timestamp;
    }

    const int64_t& getValue() const {
        return value_;
    }

    void setValue(const int64_t& value) {
        value_ = value;
    }



private:
    std::string metric_;
    std::map<std::string, std::string> tags_;
    int64_t timestamp_ = 0;
    int64_t value_ = 0;
};


class IAttrUploadBackend {
public:
    virtual ~IAttrUploadBackend() = default;

    virtual void uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items
    		, int period_seconds) noexcept = 0;

protected:
    IAttrUploadBackend() = default;
};


class LinkedAttrUploadBackend : public IAttrUploadBackend {
public:
	LinkedAttrUploadBackend() = default;
	virtual ~LinkedAttrUploadBackend() = default;

	void add(const std::shared_ptr<IAttrUploadBackend>& backend) {
		backend_list_.push_back(backend);
	}

	virtual void uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items, int period_seconds) noexcept {
		for (auto& backend : backend_list_) {
			backend->uploadItems(items, period_seconds);
		}
	}

private:
	std::vector<std::shared_ptr<IAttrUploadBackend>> backend_list_;

};


} // end namespace attr
} // end namespace soldier



#endif /* ATTR_ATTR_REPORTER_BACKEND_H_ */
