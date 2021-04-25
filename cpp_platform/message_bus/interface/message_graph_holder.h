/*
 * message_graph_holder.h
 *
 *  Created on: 2017年12月25日
 *      Author: wileywang
 */

#ifndef MESSAGE_BUS_INTERFACE_MESSAGE_GRAPH_HOLDER_H_
#define MESSAGE_BUS_INTERFACE_MESSAGE_GRAPH_HOLDER_H_

#include "base/listener_container.h"
#include "message_bus/interface/message_graph.h"
#include "watcher/file_watcher_module.h"

namespace soldier {
namespace message_bus {

class IMessageGraphListener {
public:
    virtual ~IMessageGraphListener() = default;
    virtual void onMessageGraphChanged() = 0;

protected:
    IMessageGraphListener() = default;
};

class MessageGraphHolder :
        public soldier::base::ListenerContainer<IMessageGraphListener>
        , public soldier::watcher::IFileListener {
public:
    static std::shared_ptr<MessageGraphHolder> create(const std::string& graph_file);
    virtual ~MessageGraphHolder() = default;

    std::shared_ptr<MessageGraph> current() {
        return current_graph_;
    }

    virtual void onFileChanged(const std::string& file_path);

private:
    MessageGraphHolder(const std::string& graph_file);
    std::shared_ptr<MessageGraph> consturctMessageGraph();

    std::string graph_file_;
    std::shared_ptr<MessageGraph> current_graph_;
    std::mutex  lock_;
};



} // end namespace message_bus
} // end namespace soldier



#endif /* MESSAGE_BUS_INTERFACE_MESSAGE_GRAPH_HOLDER_H_ */
