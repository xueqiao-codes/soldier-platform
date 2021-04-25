/*
 * shm_base.h
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */

#ifndef SHM_BASE_H_XAIRY
#define SHM_BASE_H_XAIRY

#include "v1/comm_define.h"
#include <string>

COMM_NAMESPACE_BEGIN


class CShmBase
{
public:
	CShmBase();
	~CShmBase();

	uint32_t Create(int iShmKey, uint32_t dwSize, uint32_t mode = 0666);
	uint32_t Attach(int iShmKey);

	inline const char* GetLastErrMsg()const;

protected:
	int m_iShmKey;
	int m_iShmId;
	void* m_pszShmAddr;
	std::string m_strLastErrMsg;
};

inline const char* CShmBase::GetLastErrMsg()const
{
	return m_strLastErrMsg.c_str();
}

COMM_NAMESPACE_END

#endif
