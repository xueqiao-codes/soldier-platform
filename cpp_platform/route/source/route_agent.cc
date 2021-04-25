/*
 * config_agent.cpp
 *
 *  Created on: 2012-5-16
 *      Author: Xairy
 */
#include "route_agent.h"
#include "v1/error_define.h"
#include <string.h>

using namespace platform::config;

CRouteAgent::CRouteAgent()
	:m_bInit(false)
{}

CRouteAgent::~CRouteAgent()
{}

uint32_t CRouteAgent::InitCreate(int iShmKey)
{
	uint32_t dwResult = CShmBase::Create(iShmKey, sizeof(CConfigShm), 0666);
	if(0 == dwResult)
	{
		CConfigShm* pszShm = static_cast<CConfigShm*>(this->m_pszShmAddr);
		pszShm->m_dwMagicNum = MAGIC_NUM;
		pszShm->m_dwStartTimeSec = time(NULL);
		pszShm->m_dwLastModifyTimeSec = time(NULL);
		pszShm->m_vIndex = 0;
		memset(pszShm->m_vIpArrays0, 0, sizeof(IpItem) * SERVICE_KEY_MAX_NUM);
		memset(pszShm->m_vIpArrays1, 0, sizeof(IpItem) * SERVICE_KEY_MAX_NUM);
		m_bInit = true;
	}
	return dwResult;
}

uint32_t CRouteAgent::InitAttach(int iShmKey)
{
	uint32_t dwResult = CShmBase::Attach(iShmKey);
	if(0 == dwResult){
		m_bInit = true;
	}
	return dwResult;
}

const IpItem* CRouteAgent::Query(const uint16_t& wServiceKey)
{
	if(wServiceKey >= SERVICE_KEY_MAX_NUM || !m_bInit){
		return NULL;
	}

	IpItem* pszArray = GetQueryArrays();
	return &(pszArray[wServiceKey]);
}

uint32_t CRouteAgent::SetUpdate(const uint16_t& wServiceKey, const IpItem& oItem)
{
	if(!m_bInit){
		return ERROR_NotInit;
	}
	if(wServiceKey >= SERVICE_KEY_MAX_NUM){
		return ERROR_Param;
	}

	IpItem& pszArray = GetUpdateArrays()[wServiceKey];
	pszArray.m_dwIpCount = 0;
	for(int index = 0; index < (int)oItem.m_dwIpCount; ++index){
		pszArray.m_dwIpAddr[index] = oItem.m_dwIpAddr[index];
	}
	pszArray.m_dwIpCount = oItem.m_dwIpCount;
	pszArray.m_routeType = oItem.m_routeType;
	return 0;
}

void CRouteAgent::DumpHeader()
{
	if(!m_bInit) return;

	CConfigShm* pszShm = static_cast<CConfigShm*>(m_pszShmAddr);
	printf("================================================\n");
	printf("Magic:%08x\n", pszShm->m_dwMagicNum);
	printf("StartTimeSec:%d\n", pszShm->m_dwStartTimeSec);
	printf("LastModifyTimeSec:%d\n", pszShm->m_dwStartTimeSec);
	printf("vIndex:%d\n", pszShm->m_vIndex);
	printf("================================================\n");
}

void CRouteAgent::ClearUpdate() {
	memset(GetUpdateArrays(), 0, SERVICE_KEY_MAX_NUM * sizeof(IpItem));
}


