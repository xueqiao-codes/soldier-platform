/*
 * protocol_util.h
 *
 *  Created on: 2018年2月5日
 *      Author: wangli
 */

#ifndef SVR_PROTOCOL_UTIL_H_
#define SVR_PROTOCOL_UTIL_H_

#include <stdint.h>

#include <boost/shared_ptr.hpp>
#include "thrift/Thrift.h"
#include "thrift/protocol/TProtocol.h"
#include "thrift/transport/TTransport.h"
#include "thrift/transport/TBufferTransports.h"

namespace soldier {
namespace svr_platform {


class ProtocolUtil {
public:
	static boost::shared_ptr<::apache::thrift::protocol::TProtocolFactory> COMPACT_PROTOCOLFACTORY;
	static boost::shared_ptr<::apache::thrift::protocol::TProtocolFactory> JSON_PROTOCOLFACTORY;

	template<class TBase>
	static void unserialize(TBase& result
			, ::apache::thrift::protocol::TProtocolFactory& protocolFactory
			, const uint8_t* mem
			, const uint32_t& length) {
		boost::shared_ptr<::apache::thrift::transport::TMemoryBuffer> read_buffer(
				new ::apache::thrift::transport::TMemoryBuffer((uint8_t*)mem, length));
		result.read(protocolFactory.getProtocol(read_buffer).get());
	}

	template<class TBase>
	static void unserializeCompact(TBase& result
			, const uint8_t* mem
			, const uint32_t& length) {
		unserialize<TBase>(result, *COMPACT_PROTOCOLFACTORY, mem, length);
	}

	template<class TBase>
	static void unserializeJson(TBase& result
			, const uint8_t* mem
			, const uint32_t& length) {
		unserialize<TBase>(result, *JSON_PROTOCOLFACTORY, mem, length);
	}

	template<class TBase>
	static void unserializeJson(TBase& result, const std::string& json) {
		unserialize<TBase>(result, *JSON_PROTOCOLFACTORY
				, (const uint8_t*)json.data(), (uint32_t)json.length());
	}

	template<class TBase>
	static void serialize(const TBase& obj
			, ::apache::thrift::protocol::TProtocolFactory& protocolFactory
			, boost::shared_ptr<::apache::thrift::transport::TMemoryBuffer>& write_buffer) {
		obj.write(*(protocolFactory.getProtocol(write_buffer)));
	}

	template<class TBase>
	static void serializeCompact(const TBase& obj
			, boost::shared_ptr<::apache::thrift::transport::TMemoryBuffer>& write_buffer) {
		serialize(obj, *COMPACT_PROTOCOLFACTORY, write_buffer);
	}

	template<class TBase>
	static void serializeJson(const TBase& obj
			, boost::shared_ptr<::apache::thrift::transport::TMemoryBuffer>& write_buffer) {
		serialize(obj, *JSON_PROTOCOLFACTORY, write_buffer);
	}

	template<class TBase>
	static std::string serializeJson(const TBase& obj) {
		boost::shared_ptr<::apache::thrift::transport::TMemoryBuffer> write_buffer(
				new ::apache::thrift::transport::TMemoryBuffer(1024));
		return write_buffer->getBufferAsString();
	}
};


} // end namespace svr_platform
} // end namespace soldier



#endif /* SVR_PROTOCOL_UTIL_H_ */
