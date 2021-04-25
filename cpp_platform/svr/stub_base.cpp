/*
 * stub_base.cpp
 *
 *  Created on: 2018年2月4日
 *      Author: wangli
 */
#include "stub_base.h"
#include "route_finder.h"
#include <boost/lexical_cast.hpp>
#include <thrift/protocol/TCompactProtocol.h>
#include <thrift/transport/TSocket.h>
#include <thrift/transport/TTransportUtils.h>

using namespace apache::thrift;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;
using namespace soldier::svr_platform;

TStubBase::TStubBase(int service_key)
	: service_key_(service_key) {
}

void TStubBase::prepareSyncCall(const TPrepareSyncCallArgs& args
		, TPrepareSyncCallResp& resp) const throw(TException) {
	std::string source_desc;
	if (args.file_name != nullptr) {
		source_desc.append(args.file_name);
	}
	if (args.line > 0) {
		source_desc.append(":").append(boost::lexical_cast<std::string>(args.line));
	}
	if (args.pretty_function_name != nullptr) {
		source_desc.append("[").append(args.pretty_function_name).append("]");
	} else {
		if (args.function_name != nullptr) {
			source_desc.append("[").append(args.function_name).append("]");
		}
	}

	resp.platform_args.__set_sourceDesc(source_desc);
	if (!xforward_address_.empty()) {
		resp.platform_args.__set_xForwardAddress(xforward_address_);
	}

	boost::shared_ptr<TSocket> socket;
	if (!socket_file_.empty()) {
		socket.reset(new TSocket(socket_file_));
	} else {
		int choosed_port = peer_port_;
		std::string choosed_addr = peer_addr_;
		if (choosed_port <= 0) {
			choosed_port = 10000 + service_key_;
		}
		if (choosed_addr.empty()) {
		    choosed_addr = platform::GetRouteIp(service_key_, "", args.route_key);
		}
		if (choosed_addr.empty()) {
            throw TException("no address for service");
		}
		socket.reset(new TSocket(choosed_addr, choosed_port));
	}

	if (timeout_ms_ > 0) {
	    socket->setConnTimeout(timeout_ms_);
	    socket->setSendTimeout(timeout_ms_);
	    socket->setRecvTimeout(timeout_ms_);
	}

	resp.transport.reset(new TFramedTransport(socket));
	resp.protocol.reset(new TCompactProtocol(resp.transport));

	resp.transport->open();
}


