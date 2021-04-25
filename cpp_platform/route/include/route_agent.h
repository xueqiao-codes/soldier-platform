/*
 * config_agent.h
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 *
 *  基于共享内存的配置信息查找
 */

#ifndef ROUTE_CONFIG_SHM_H_XAIRY
#define ROUTE_CONFIG_SHM_H_XAIRY

#include <unistd.h>
#include <sys/types.h>
#include <stdint.h>
#include "v1/shm_base.h"

#define MAGIC_NUM 0x22232425
#define SERVICE_KEY_MAX_NUM 3000
#define SERVICE_IP_MAX_NUM  16
#define CONFIG_SHM_KEY 0x1000

using namespace COMM_NAMESPACE;

namespace platform{
namespace config {

struct IpItem{
	int32_t m_routeType;
	uint32_t m_dwIpCount;
	uint32_t m_dwIpAddr[SERVICE_IP_MAX_NUM];

	IpItem(): m_routeType(0), m_dwIpCount(0){}
};

struct CConfigShm{
	uint32_t m_dwMagicNum;
	uint32_t m_dwStartTimeSec;
	uint32_t m_dwLastModifyTimeSec;
	uint32_t m_vIndex;

	IpItem m_vIpArrays0[SERVICE_KEY_MAX_NUM];
	IpItem m_vIpArrays1[SERVICE_KEY_MAX_NUM];
};

class CRouteAgent : public CShmBase
{
public:
	CRouteAgent();
	~CRouteAgent();

	uint32_t InitCreate(int iShmKey = CONFIG_SHM_KEY);
	uint32_t InitAttach(int iShmKey = CONFIG_SHM_KEY);

	const IpItem* Query(const uint16_t& wServiceKey);
	uint32_t SetUpdate(const uint16_t& wServiceKey, const IpItem& oItem);
	inline bool Switch();
	void DumpHeader();
	void ClearUpdate();

private:
	inline IpItem* GetQueryArrays();
	inline IpItem* GetUpdateArrays();

private:
	bool m_bInit;
};

inline IpItem* CRouteAgent::GetQueryArrays()
{
	CConfigShm* pszShm = static_cast<CConfigShm*>(m_pszShmAddr);
	return (pszShm->m_vIndex % 2 == 0) ? pszShm->m_vIpArrays0 : pszShm->m_vIpArrays1;
}

inline IpItem* CRouteAgent::GetUpdateArrays()
{
	CConfigShm* pszShm = static_cast<CConfigShm*>(m_pszShmAddr);
	return (pszShm->m_vIndex % 2 != 0 ) ? pszShm->m_vIpArrays0 : pszShm->m_vIpArrays1;
}

inline bool CRouteAgent::Switch()
{
	if(!m_bInit) return false;
	++static_cast<CConfigShm*>(m_pszShmAddr)->m_vIndex;
	static_cast<CConfigShm*>(m_pszShmAddr)->m_dwLastModifyTimeSec = time(NULL);
	return true;
}

} // namespace config
} // namespace platform

#endif
