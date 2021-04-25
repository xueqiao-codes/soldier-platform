/*
 * attr_reporter_impl.cc
 *
 *  Created on: 2017年3月14日
 *      Author: wileywang
 */
#include "attr/attr_reporter_impl.h"

#include "base/app_log.h"
#include "base/time_helper.h"
#include <sstream>
#include <random>
#include <chrono>
#include <deque>

namespace soldier {
namespace attr {

AttrReporterImpl::AttrReporterImpl(const std::shared_ptr<IAttrUploadBackend>& backend, int period_seconds, int hashtable_size)
    : period_seconds_(period_seconds)
      , hashtable_size_(hashtable_size)
      , block1_(new AttrHashBlock(hashtable_size))
      , block2_(new AttrHashBlock(hashtable_size))
      , backend_(backend)
      , front_block_(block1_.get())
      , upload_thread_(&AttrReporterImpl::onUpload, this) {
}

AttrReporterImpl::~AttrReporterImpl() {
    ending_upload_ = true;
    upload_thread_.join();
}

int64_t AttrReporterImpl::requireKey(const std::string& metric,
               const std::map<std::string, std::string>& tags) noexcept {
    if (metric.empty()) {
        return -2;
    }

    int64_t key = -1;
    AttrKeyInfo key_info(metric, tags);

    while(std::atomic_flag_test_and_set_explicit(
                            &key_lock_, std::memory_order_seq_cst));

    auto it = info_key_maps_.find(key_info);
    if (it == info_key_maps_.end()) {
        key = last_key_id_.fetch_add(1);
        if (key >= hashtable_size_) {
            key_lock_.clear();
            return -1;
        }

        info_key_maps_.insert(std::pair<AttrKeyInfo, int64_t>(key_info, key));
        key_lock_.clear();

        while(std::atomic_flag_test_and_set_explicit(
                                    &info_lock_, std::memory_order_seq_cst));
        key_info_maps_.insert(std::pair<int64_t, AttrKeyInfo>(key, key_info));
        info_lock_.clear();
        return key;
    }
    key = it->second;
    key_lock_.clear();

    return key;
}

void AttrReporterImpl::inc(const int64_t& key
        , const int64_t& value) noexcept {
    if (key < 0 || key >= hashtable_size_) {
        return ;
    }

    front_block_.load()->nodes_[key].value_.fetch_add(value);
}

void AttrReporterImpl::set(const int64_t& key
        , const int64_t& value) noexcept {
    if (key < 0 || key >= hashtable_size_) {
        return ;
    }

    front_block_.load()->nodes_[key].value_.store(value);
}

void AttrReporterImpl::average(const int64_t& key
                , const int64_t& value) noexcept {
    if (key < 0 || key >= hashtable_size_) {
        return ;
    }

    AttrNode& attr_node = front_block_.load()->nodes_[key];
    attr_node.value_.fetch_add(value);
    attr_node.count_.fetch_add(1);
}

void AttrReporterImpl::keep(const int64_t& key
                , const int64_t& value) noexcept {
    if (key < 0 || key >= hashtable_size_) {
        return ;
    }

    AttrNode& block1_attr_node = block1_->nodes_[key];
    AttrNode& block2_attr_node = block2_->nodes_[key];

    block1_attr_node.value_.store(value);
    block2_attr_node.value_.store(value);
    block1_attr_node.keep_value_.store(true);
    block2_attr_node.keep_value_.store(true);
}

void AttrReporterImpl::AttrKeyInfo::updateKey() {
    static std::hash<std::string> key_hash;

    key_.clear();
    key_.append(metric_);
    if (tags_.get() != nullptr && tags_->size() > 0) {
        key_.append("?");
        for (auto& pair : *tags_) {
            key_.append(pair.first);
            key_.append("=");
            key_.append(pair.second);
            key_.append("&");
        }
    }
    hash_code_ = key_hash(key_);
}

std::size_t AttrReporterImpl::AttrKeyInfoHash::operator()(const AttrKeyInfo& k) const {
    return k.getHashCode();
}


void AttrReporterImpl::getAttrKeyInfo(const int64_t& key, AttrKeyInfo& key_info) {
    while(std::atomic_flag_test_and_set_explicit(
                               &info_lock_, std::memory_order_seq_cst));
    auto it = key_info_maps_.find(key);
    if (it == key_info_maps_.end()) {
        key_info.updateKeyInfo("", std::shared_ptr<std::map<std::string, std::string>>());
    } else {
        key_info = it->second;
    }
    info_lock_.clear();
}


void AttrReporterImpl::onUpload()noexcept {
    const int sleep_meta_milliseconds = 100;
    int64_t backend_escaped_time_ms = 0;

    AttrKeyInfo attr_key_info;
    while(!ending_upload_) {
        int64_t sleep_count = (period_seconds_ * 1000 - backend_escaped_time_ms)/sleep_meta_milliseconds;

        APPLOG_DEBUG("OnUpload period_seconds_ ={}", period_seconds_);

        for (int64_t count = 0; count < sleep_count; ++count) {
            if (ending_upload_) {
               return ;
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(sleep_meta_milliseconds));
        }

        AttrHashBlock* upload_block = front_block_.load();
        if (upload_block == block1_.get()) {
            front_block_.store(block2_.get());
        } else {
//            CHECK(upload_block == block2_.get());
            front_block_.store(block1_.get());
        }

        if (backend_.get() == nullptr) {
            continue;
        }

        int64_t backend_start = soldier::base::NowInMilliSeconds();

        // let old_block value refresh the values
        std::this_thread::sleep_for(std::chrono::milliseconds(10));

        int64_t timestamp = std::chrono::duration_cast<std::chrono::seconds>(
                    std::chrono::system_clock::now().time_since_epoch()).count();
        std::shared_ptr<std::vector<AttrItem>> upload_attr_items(new std::vector<AttrItem>());

        for(int64_t key = 0; key < last_key_id_.load(); ++key) {
            AttrItem item;
            getAttrKeyInfo(key, attr_key_info);
            if (attr_key_info.getMetric().empty()) {
                continue;
            }

            AttrNode& attr_node =  upload_block->nodes_[key];
            item.setMetric(attr_key_info.getMetric());
            if (attr_key_info.getTags().get() != nullptr) {
                item.setTags(*(attr_key_info.getTags()));
            }
            item.setTimestamp(timestamp);
            if (attr_node.count_.load() > 0) {
                item.setValue(attr_node.value_.load() / attr_node.count_.load());
            } else {
                item.setValue(attr_node.value_.load() );
            }
            upload_attr_items->push_back(std::move(item));

            if (!attr_node.keep_value_)  {
                attr_node.value_.store(0);
                attr_node.count_.store(0);
            }
        }

        backend_->uploadItems(upload_attr_items, period_seconds_);
        backend_escaped_time_ms = soldier::base::NowInMilliSeconds() - backend_start;
        if (backend_escaped_time_ms >= period_seconds_ * 1000 ) {
            APPLOG_WARN("too slow for backend to update the value, please check the implementions!");
        }
    }
}



} // end namespace attr
} // end namespace soldier


