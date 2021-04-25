/*
 * attr_reporter_impl.h
 *
 *  Created on: 2017年3月14日
 *      Author: wileywang
 */
#include "attr/attr_reporter.h"

#include <atomic>
#include <thread>
#include <vector>
#include <sstream>
#include <unordered_map>

#include "attr/attr_reporter_backend.h"

namespace soldier {
namespace attr {

class AttrReporterImpl : public IAttrReporter {
public:
    AttrReporterImpl(const std::shared_ptr<IAttrUploadBackend>& backend
            , int period_seconds = 60
            , int hashtable_size = 10000);
    virtual ~AttrReporterImpl();

    virtual int64_t requireKey(const std::string& metric,
                const std::map<std::string, std::string>& tags) noexcept;

    virtual void inc(const int64_t& key
                , const int64_t& value) noexcept;

    virtual void set(const int64_t& key
                , const int64_t& value) noexcept;

    virtual void average(const int64_t& key
                , const int64_t& value) noexcept;

    virtual void keep(const int64_t& key
                , const int64_t& value) noexcept;

private:

    class AttrKeyInfo {
    public:
        AttrKeyInfo() {
        }

        AttrKeyInfo(const std::string& metric
                , const std::map<std::string, std::string>& tags)
            : metric_(metric)
             , tags_(new std::map<std::string, std::string>(tags)){
            updateKey();
        }

        void updateKeyInfo(const std::string& metric
                , const std::shared_ptr<std::map<std::string, std::string>>& tags) {
            metric_ = metric;
            tags_ = tags;
            updateKey();
        }

        const std::string& getKey() const {
            return key_;
        }

        const std::string& getMetric() const {
            return metric_;
        }

        const std::shared_ptr<std::map<std::string, std::string>>& getTags() const {
            return tags_;
        }

        bool operator==(const AttrKeyInfo& key_info) const {
            return key_ == key_info.getKey();
        }

        size_t getHashCode() const { return hash_code_; }

    private:
        void updateKey();

        std::string metric_;
        std::shared_ptr<std::map<std::string, std::string>> tags_;
        std::string key_;
        size_t hash_code_ = 0;
    };

    struct AttrKeyInfoHash {
        std::size_t operator()(const AttrKeyInfo& k) const;
    };

    struct AttrNode {
        AttrNode():value_(0), count_(0), keep_value_(false) {
        }

        std::atomic_int_fast64_t value_;
        std::atomic_int_fast64_t count_;
        std::atomic_bool keep_value_;
    };

    struct AttrHashBlock {
        AttrNode* nodes_;

        AttrHashBlock(size_t hashtable_size) {
            nodes_ = new AttrNode[hashtable_size];
        }

        ~AttrHashBlock() {
        	delete[] nodes_;
        }

    };

    void onUpload() noexcept;
    void getAttrKeyInfo(const int64_t& key, AttrKeyInfo& key_info);

    const int period_seconds_;
    const int hashtable_size_;
    std::unique_ptr<AttrHashBlock> block1_;
    std::unique_ptr<AttrHashBlock> block2_;
    std::atomic_bool ending_upload_ = {false};

    std::shared_ptr<IAttrUploadBackend> backend_;
    std::atomic<AttrHashBlock*> front_block_;

    std::atomic_int_fast64_t last_key_id_ = {0};
    std::unordered_map<AttrKeyInfo, int64_t, AttrKeyInfoHash> info_key_maps_;
    std::atomic_flag key_lock_ = ATOMIC_FLAG_INIT;

    std::map<int64_t, AttrKeyInfo> key_info_maps_;
    std::atomic_flag info_lock_ = ATOMIC_FLAG_INIT;
    std::thread upload_thread_;
};



} // end namespace attr
} // end namespace soldier



