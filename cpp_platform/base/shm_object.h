/*
 * shm_base.h
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */

#ifndef SHM_BASE_H_XAIRY
#define SHM_BASE_H_XAIRY

#include <string>

namespace soldier {
namespace base {

class ShmObject
{
public:
    ShmObject() = default;
	~ShmObject();

	bool create(int shm_key, size_t  size, uint32_t mode = 0666);
	bool attach(int shm_key);
	bool createOrAttach(int shm_key, size_t size, uint32_t mode = 0666);

	inline const char* getLastErrMsg() const;
	inline void* getShmAddr() const;
	inline int getShmKey() const;
	inline int getShmId() const;

private:
	bool initShmAddr();

	int shm_key_ = 0;
	int shm_id_ = -1;
	void* shm_addr_ = nullptr;
	std::string last_err_msg_;
};

inline const char* ShmObject::getLastErrMsg()const
{
	return last_err_msg_.c_str();
}

inline void* ShmObject::getShmAddr() const {
    return shm_addr_;
}

inline int ShmObject::getShmKey() const {
    return shm_key_;
}

inline int ShmObject::getShmId() const {
    return shm_id_;
}

} // end namespace base
} // end namespace soldier

#endif
