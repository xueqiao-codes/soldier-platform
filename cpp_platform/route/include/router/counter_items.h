/*
 * counter_items.h
 *
 *  Created on: 2017年8月8日
 *      Author: wileywang
 */

#ifndef SOURCE_ROUTER_COUNTER_ITEMS_H_
#define SOURCE_ROUTER_COUNTER_ITEMS_H_

#include <sys/time.h>
#include "route_agent.h"

namespace platform {

struct CounterItems {
    uint16_t counter[SERVICE_KEY_MAX_NUM];

    CounterItems() {
        struct  timeval    tv;
        struct  timezone   tz;
        gettimeofday(&tv,&tz);

        uint16_t init_value = (uint16_t)((tv.tv_sec * 1000 + tv.tv_usec/1000) % 10240);
        for (int index = 0; index < SERVICE_KEY_MAX_NUM; ++index) {
            counter[index] = init_value;
        }
    }
};

extern CounterItems s_oCounterItems;

} // end namespace platform;



#endif /* SOURCE_ROUTER_COUNTER_ITEMS_H_ */
