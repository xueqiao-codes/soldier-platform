/*
 * atomic_test.cc
 *
 *  Created on: 2017年3月13日
 *      Author: wileywang
 */


#include <atomic>
#include <iostream>
#include <map>
#include <random>
#include <sstream>
#include <chrono>
#include "attr/attr_reporter_impl.h"
#include "attr/backend/attr_reporter_backend_falcon.h"

#include "base/app_log.h"

using namespace soldier::attr;

class SimpleBackend : public IAttrUploadBackend {
public:
    void uploadItems(const std::shared_ptr<std::vector<AttrItem>>& items, int period_seconds) noexcept {
        for (auto& item: *items) {
            std::cout << "metric:" << item.getMetric()
                      << ", tags=";
            for (auto& pair : item.getTags()) {
                std::cout << pair.first << "=" << pair.second << "&";
            }
            std::cout << ", timestamp=" << item.getTimestamp()
                      << ", value=" << item.getValue()
                      << std::endl;
        }

    }
};

static std::unique_ptr<AttrReporterImpl> attr_reporter;


void onTest() {
    std::chrono::high_resolution_clock::time_point start = std::chrono::high_resolution_clock::now();
    int count = 30000000;

    std::map<std::string, int64_t> tag_map;
    for (int i = 0; i < count; ++i) {
//        INC_METRIC(INC, (*attr_reporter), "attr.benchmark.test.inc", "tag1", "value1", 1)
//        SET_METRIC(SET, (*attr_reporter), "attr.benchmark.set", "set1", "value1", 1000)
//        AVERAGE_METRIC(AVERAGE, (*attr_reporter), "attr.benchmark.test.average", "tag2", "value2", i)

        int64_t key2 = attr_reporter->requireKey("tencent.test", {{"tag2", "value2"}, {"tag3", "value3"}});
        attr_reporter->inc(key2, 2);
        int64_t key3 = attr_reporter->requireKey("next", {});
        attr_reporter->set(key3, 1000);
        int64_t key4 = attr_reporter->requireKey("average", {});
        attr_reporter->average(key4, i);
    }

    std::chrono::high_resolution_clock::time_point end = std::chrono::high_resolution_clock::now();
    std::cout <<"time escaped="
              << std::chrono::duration_cast<std::chrono::milliseconds>(end -start).count()
              << "ms"
              << ",every operation time="
              <<  std::chrono::duration_cast<std::chrono::nanoseconds>(end - start).count()/(3*count)
              << "ns"
              << std::endl;
}


int main() {
    soldier::base::AppLog::Init("attr_benchmark");

    attr_reporter.reset(
        new AttrReporterImpl(std::shared_ptr<IAttrUploadBackend>(new SimpleBackend()), 5));

    attr_reporter->keep(attr_reporter->requireKey("tencent.test.keep", {}), 1);

    std::thread thread1(onTest);
    std::thread thread2(onTest);
//    std::thread thread3(onTest);
//    std::thread thread4(onTest);

    thread1.join();
    thread2.join();
//    thread3.join();
//    thread4.join();

    attr_reporter.reset();

    return 0;
}



