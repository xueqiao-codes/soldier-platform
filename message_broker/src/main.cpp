/*
 * main.cpp
 *
 *  Created on: 2017年12月26日
 *      Author: wileywang
 */
#include <boost/program_options.hpp>

#include <chrono>
#include <iostream>
#include <thread>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "message_broker.h"
#include "message_bus/interface/message_graph_holder.h"

namespace po = boost::program_options;
using namespace soldier::message_bus;

int main(int argc, char* argv[]) {
    po::options_description desc("Allowed options");
    desc.add_options()
                ("help", "produce help message")
                ("name", po::value<std::string>(), "broker name for brokers")
                ("graph", po::value<std::string>(), "graph file for brokers");

    po::variables_map vm;
    po::store(po::parse_command_line(argc, argv, desc), vm);
    po::notify(vm);

    if(vm.count("help")){
        std::cout<< desc << std::endl;
        return 0;
    }

    if(!vm.count("graph")) {
        std::cerr << "please input graph file path" << std::endl;
        return 1;
    }
    if(!vm.count("name")) {
        std::cerr << "please input name for broker" << std::endl;
    }

    std::string graph_path = vm["graph"].as<std::string>();
    std::string name = vm["name"].as<std::string>();

    soldier::base::AppLog::Init("apps/message_broker_" + name);


    std::shared_ptr<MessageGraphHolder> message_graph_holder = MessageGraphHolder::create(graph_path);
    CHECK(message_graph_holder);
    std::shared_ptr<MessageGraph> current_graph = message_graph_holder->current();

    std::vector<std::shared_ptr<MessageBroker>> message_brokers;
    for (auto broker_it : current_graph->getBrokers()) {
        std::shared_ptr<MessageBroker> broker = MessageBroker::create(broker_it.first, message_graph_holder);
        CHECK(broker);
        message_brokers.push_back(broker);
    }

    while(true) {
        std::this_thread::sleep_for(std::chrono::seconds(10));
    }

    return 0;
}



