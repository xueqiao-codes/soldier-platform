/*
 * stub_base.h
 *
 *  Created on: 2018年2月4日
 *      Author: wangli
 */

#ifndef SVR_STUB_BASE_H_
#define SVR_STUB_BASE_H_

#include <boost/shared_ptr.hpp>
#include <string>
#include "comm_types.h"
#include "thrift/Thrift.h"
#include "thrift/protocol/TProtocol.h"
#include "thrift/transport/TTransport.h"


namespace soldier {
namespace svr_platform {

struct TPrepareSyncCallArgs {
	const char* file_name = nullptr;
	const char* function_name = nullptr;
	const char* pretty_function_name = nullptr;
	int line = 0;
	long route_key = 0;
};

struct TPrepareSyncCallResp {
	boost::shared_ptr<::apache::thrift::transport::TTransport> transport;
	boost::shared_ptr<::apache::thrift::protocol::TProtocol> protocol;
	platform::comm::PlatformArgs platform_args;
};


class TStubBase {
public:
	virtual ~TStubBase() = default;

	inline void setPeerAddr(const std::string& peer_addr) {
		peer_addr_ = peer_addr;
	}
	inline const std::string& getPeerAddr() const {
		return peer_addr_;
	}

	inline void setPeerPort(int port) {
		peer_port_ = port;
	}
	inline int getPeerPort() const {
		return peer_port_;
	}

	inline void setSocketFile(const std::string& socket_file) {
		socket_file_ = socket_file;
	}
	const std::string& getSocketFile() const {
		return socket_file_;
	}

	void setXForwardAddress(const std::string& xforward_addresss) {
		xforward_address_ = xforward_addresss;
	}
	const std::string& getXForwardAddress() const {
		return xforward_address_;
	}

	void setTimeoutMs(int timeout_ms) {
	    timeout_ms_ = timeout_ms;
	}

	void prepareSyncCall(const TPrepareSyncCallArgs& args, TPrepareSyncCallResp& resp)
		const throw(::apache::thrift::TException);

protected:
	TStubBase(int service_key);

private:
	std::string peer_addr_;
	int  peer_port_ = 0;
	std::string socket_file_;
	std::string xforward_address_;
	int service_key_;

	int timeout_ms_ = 0;
};


} // end namespace svr_platform
} // end namespace soldier

#define STUB_SYNC_INVOKE_NOARGS(stub, func_name) \
{ \
	::soldier::svr_platform::TPrepareSyncCallArgs platformCallArgs; \
	platformCallArgs.file_name = __FILE__; \
	platformCallArgs.line = __LINE__; \
	platformCallArgs.function_name = __FUNCTION__; \
	stub.func_name(platformCallArgs); \
}

#define STUB_SYNC_INVOKE(stub, func_name, args...) \
{ \
	::soldier::svr_platform::TPrepareSyncCallArgs platformCallArgs; \
	platformCallArgs.file_name = __FILE__; \
	platformCallArgs.line = __LINE__; \
	platformCallArgs.function_name = __FUNCTION__; \
	stub.func_name(platformCallArgs, ##args); \
}

#define STUB_SYNC_INVOKE_NOARGS_RETURN(stub, func_name) \
{ \
    ::soldier::svr_platform::TPrepareSyncCallArgs platformCallArgs; \
    platformCallArgs.file_name = __FILE__; \
    platformCallArgs.line = __LINE__; \
    platformCallArgs.function_name = __FUNCTION__; \
    return stub.func_name(platformCallArgs); \
}

#define STUB_SYNC_INVOKE_RETURN(stub, func_name, args...) \
{ \
    ::soldier::svr_platform::TPrepareSyncCallArgs platformCallArgs; \
    platformCallArgs.file_name = __FILE__; \
    platformCallArgs.line = __LINE__; \
    platformCallArgs.function_name = __FUNCTION__; \
    return stub.func_name(platformCallArgs, ##args); \
}



#endif /* SVR_STUB_BASE_H_ */
