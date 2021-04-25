/*
 * reset_queue.cpp
 *
 *  Created on: 2018年2月26日
 *      Author: wileywang
 */

#include <boost/program_options.hpp>

#include <chrono>
#include <iostream>
#include <thread>

#include "message_bus/queue/shm_message_queue.h"

namespace po = boost::program_options;
using namespace soldier::message_bus;

int main(int argc, char* argv[]) {
    po::options_description desc("Allowed options");
    desc.add_options()
            ("help", "produce help message")
            ("shm_key", po::value<int64_t>(), "share memory message queue key");

    po::variables_map vm;
    po::store(po::parse_command_line(argc, argv, desc), vm);
    po::notify(vm);

    if(vm.count("help")){
        std::cout<< desc << std::endl;
        return 0;
    }

    if (!vm.count("shm_key")) {
        std::cerr << "please input shm_key option" << std::endl;
        return 1;
    }

    int64_t shm_key = vm["shm_key"].as<int64_t>();
    std::unique_ptr<ShmMessageQueue> queue(ShmMessageQueue::Get(shm_key));
    if (!queue) {
        std::cerr << "failed to get queue for shm_key=" << shm_key << std::endl;
        return 1;
    }

    queue->broken();
    std::cout << "broken queue shm_key=" << shm_key << " finished!" << std::endl;

    return 0;
}

