/*
 * message_guard_storage.h
 *
 *  Created on: 2018年1月2日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_INTERFACE_MESSAGE_GUARD_STORAGE_H_
#define MESSAGE_BUS_INTERFACE_MESSAGE_GUARD_STORAGE_H_

#include "message_bus/interface/message_sender.h"

namespace soldier {
namespace message_bus {

struct GuardStaticsData {
    int32_t free_num_num = 0;
};

class IMessageGuardStorage {
public:
    virtual ~IMessageGuardStorage() = default;

    virtual std::string prepareGuardMessage(const std::string& topic
            , const std::string& from
            , const uint8_t* data
            , const size_t& length
            , const GuardPolicy& policy) = 0;

    virtual bool getGuardMessage(const std::string& guard_id
            , std::string& topic
            , std::string& message_data) = 0;

    virtual bool rmGuardMessage(const std::string& guard_id) = 0;

    virtual bool isBroken() = 0;
    virtual void reset() = 0;

    class IIterator {
    public:
        virtual ~IIterator() = default;

        virtual bool hasNext() = 0;
        virtual std::string next() = 0;
        virtual bool shouldSend(const int32_t& current_timestamp_s) = 0;

    protected:
        IIterator() = default;
    };

    virtual std::shared_ptr<IIterator> iterator() = 0;
    virtual void getStatics(GuardStaticsData& data) const = 0;

protected:
    IMessageGuardStorage() = default;

};

} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_INTERFACE_MESSAGE_GUARD_STORAGE_H_ */
