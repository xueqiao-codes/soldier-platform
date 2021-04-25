/*
 * protocol_util.cpp
 *
 *  Created on: 2018年2月5日
 *      Author: wangli
 */

#include "protocol_util.h"

#include "thrift/protocol/TCompactProtocol.h"
#include "thrift/protocol/TJSONProtocol.h"

using namespace soldier::svr_platform;

boost::shared_ptr<::apache::thrift::protocol::TProtocolFactory>
ProtocolUtil::COMPACT_PROTOCOLFACTORY(new ::apache::thrift::protocol::TCompactProtocolFactory());

boost::shared_ptr<::apache::thrift::protocol::TProtocolFactory>
ProtocolUtil::JSON_PROTOCOLFACTORY(new ::apache::thrift::protocol::TJSONProtocolFactory());





