/*
 * shm_base.cpp
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */
#include <boost/lexical_cast.hpp>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>
#include "base/shm_object.h"
#include "base/app_log.h"

namespace soldier {
namespace base {


ShmObject::~ShmObject()
{
	if(shm_addr_){
		shmdt(shm_addr_);
		shm_addr_ = nullptr;
	}
	shm_key_ = 0;
	shm_id_ = -1;
}

bool ShmObject::initShmAddr() {
    shm_addr_ = shmat(shm_id_, NULL, 0);
    if(shm_addr_ == (void*) -1) {
        last_err_msg_ = "shmat failed, " + std::string(strerror(errno));
        shm_addr_ = nullptr;
        return false;
    }
    return true;
}

bool ShmObject::create(int shm_key, size_t  size, uint32_t mode)
{
    shm_id_ = shmget(shm_key, size, IPC_CREAT | IPC_EXCL | mode);
	if(-1 == shm_id_){
	    last_err_msg_ = "shmget failed, shm_key="
	            + boost::lexical_cast<std::string>(shm_key)
	            + ", detail=" + std::string(strerror(errno));
		return false;
	}

	if (!initShmAddr()) {
	    return false;
	}

	shm_key_ = shm_key;
	return true;
}

bool ShmObject::attach(int shm_key)
{
    shm_id_= shmget(shm_key, sizeof(size_t), 0);
	if(-1 == shm_id_){
	    last_err_msg_ = "shmget failed, shm_key="
	                    + boost::lexical_cast<std::string>(shm_key)
	                    + ", detail=" + std::string(strerror(errno));
	    return false;
	}

	if (!initShmAddr()) {
	    return false;
	}

	shm_key_ = shm_key;
	return true;
}

bool ShmObject::createOrAttach(int shm_key, size_t  size, uint32_t mode) {
    shm_id_ = shmget(shm_key, size, IPC_CREAT|mode);
    if (-1 == shm_id_) {
        last_err_msg_ = "shmget failed, shm_key="
                        + boost::lexical_cast<std::string>(shm_key)
                        + ", detail=" + std::string(strerror(errno));
        return false;
    }

    if (!initShmAddr()) {
        return false;
    }

    shm_key_ = shm_key;
    return true;
}

} // end namespace base
} // end namespace soldier



