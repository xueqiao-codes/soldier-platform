/*
 * shm_base.cpp
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */
#include "v1/shm_base.h"
#include <sys/shm.h>
#include <sys/ipc.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>
#include "base/string_util.h"
#include "v1/error_define.h"

COMM_NAMESPACE_BEGIN

CShmBase::CShmBase()
	:m_iShmKey(0),m_iShmId(-1),m_pszShmAddr(NULL)
{}

CShmBase::~CShmBase()
{
	if(m_pszShmAddr){
		shmdt(m_pszShmAddr);
		m_pszShmAddr = NULL;
	}
	m_iShmKey = 0;
	m_iShmId = -1;
}

uint32_t CShmBase::Create(int iShmKey, uint32_t dwSize, uint32_t mode)
{
	m_iShmId = shmget(iShmKey, dwSize, IPC_CREAT | mode);
	if(-1 == m_iShmId){
		m_strLastErrMsg = "shmget failed," + std::string(strerror(errno));
		ERROR_OUTPUT("shmget failed, %s", strerror(errno));
		return ERROR_ShmGet;
	}

	m_pszShmAddr = shmat(m_iShmId, NULL, 0);
	if(m_pszShmAddr == (void*) -1){
		m_strLastErrMsg = "shmat failed, " + std::string(strerror(errno));
		ERROR_OUTPUT("shmat failed, %s", strerror(errno));
		return ERROR_ShmAt;
	}

	m_iShmKey = iShmKey;
	return 0;
}

uint32_t CShmBase::Attach(int iShmKey)
{
	int iTmpShmId = shmget(iShmKey, sizeof(uint32_t), IPC_CREAT|IPC_EXCL);
	if(-1 != iTmpShmId){
		m_strLastErrMsg = "shm " + soldier::base::StringUtil::boostCast(iShmKey) + "is not existed";
		ERROR_OUTPUT("shmkey %08x is not existed", iShmKey);
		shmctl(iTmpShmId, IPC_RMID, NULL);
		return ERROR_NoShm;
	}

	m_iShmId = shmget(iShmKey, 0, IPC_CREAT);
	if(-1 == m_iShmId){
		m_strLastErrMsg = "shmget failed," + std::string(strerror(errno));
		ERROR_OUTPUT("shmget failed, %s", strerror(errno));
		return ERROR_ShmGet;
	}

	m_pszShmAddr = shmat(m_iShmId, NULL, 0);
	if(m_pszShmAddr == (void*) -1){
		m_strLastErrMsg = "shmat failed, " + std::string(strerror(errno));
		ERROR_OUTPUT("shmat failed, %s", strerror(errno));
		return ERROR_ShmAt;
	}

	m_iShmKey = iShmKey;
	return 0;
}

COMM_NAMESPACE_END



