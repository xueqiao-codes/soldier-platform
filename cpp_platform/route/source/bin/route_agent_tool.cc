/*
 * config_agent_tool.cpp
 *
 *  Created on: 2012-5-17
 *      Author: Xairy
 */

#include <chrono>
#include <iostream>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <memory>
#include <fcntl.h> 
#include "v1/error_define.h"
#include "route_agent.h"
#include "route_finder.h"
#include "base/string_util.h"
#include "base/smart_ptr_helper.h"
#include "rapidjson/document.h"
#include "rapidjson/reader.h"
#include "rapidjson/filereadstream.h"
#include "rapidjson/error/en.h"

using namespace platform::config;


CRouteAgent oAgent;

void help()
{
	printf("usage:\n");
	printf("query  servicekey[123]\n");
	printf("queryOneIp serviceKey[123] optional(routeKey[123])\n");
	printf("set    servicekey[123] iplist(xxx.xxx.xxx.xxx,xxx.xxx.xxx.xxx)\n");
	printf("load   file\n");
	printf("switch\n");
}

int lockFile(int fd) {
	struct flock lock;
	memset(&lock, 0, sizeof(lock));
	lock.l_type = F_WRLCK;
	
	return fcntl(fd, F_SETLKW,  &lock); 
		
}

void unLockFile(int fd) {
	struct flock lock;
	memset(&lock, 0, sizeof(lock));
	lock.l_type = F_UNLCK;

	fcntl(fd, F_SETLKW, &lock);
}

uint32_t SliceItem(IpItem& oItem, const std::string& strIpList)
{
	size_t posBefore = 0;
	size_t posAfter = 0;
	while((posAfter = strIpList.find(',', posBefore)) != strIpList.npos){
		std::string strIp = strIpList.substr(posBefore, posAfter-posBefore);
		in_addr_t addr = inet_addr(strIp.c_str());
		if(addr == INADDR_NONE){
			fprintf(stderr, "iplist %s[%d-%d] is invalied\n", strIpList.c_str(), (uint32_t)posBefore, (uint32_t)posAfter);
			return ERROR_Param;
		}
		oItem.m_dwIpAddr[oItem.m_dwIpCount] = addr;
		++oItem.m_dwIpCount;
		posBefore = posAfter + 1;
	}
	if(posBefore < posAfter){
		std::string strIpLast = strIpList.substr(posBefore);
		in_addr_t addr = inet_addr(strIpLast.c_str());
		if(addr == INADDR_NONE){
			fprintf(stderr, "iplist %s[%d-] is invalied\n", strIpList.c_str(), (uint32_t)posBefore);
			return ERROR_Param;
		}
		oItem.m_dwIpAddr[oItem.m_dwIpCount] = addr;
		++oItem.m_dwIpCount;
	}
	return 0;
}

uint32_t DoQuery(int argc, char* argv[])
{
	if(argc < 3){
		fprintf(stderr, "please input the servicekey!\n");
		return ERROR_Param;
	}

	uint32_t dwServiceKey = 0;
	if(!soldier::base::StringUtil::boostCast(argv[2], dwServiceKey)){
		fprintf(stderr, "servicekey input error \n");
		return ERROR_Param;
	}

	const IpItem* pszItem = oAgent.Query((uint16_t)dwServiceKey);
	if(pszItem){
		printf("ServiceKey:%d, RouteType:%d, IpNum:%d\n", dwServiceKey, pszItem->m_routeType, pszItem->m_dwIpCount);
		for(int index = 0; index < (int)pszItem->m_dwIpCount; ++index){
			struct in_addr addr;
			addr.s_addr = pszItem->m_dwIpAddr[index];
			printf("[%d]%s\n", index, inet_ntoa(addr));
		}
	}else{
		fprintf(stderr, "Not Found!");
	}

	return 0;
}

uint32_t DoQueryOneIp(int argc, char* argv[]) {
    if(argc < 3){
        fprintf(stderr, "please input the servicekey!\n");
        return ERROR_Param;
    }

    int32_t serviceKey = 0;
    if(!soldier::base::StringUtil::boostCast(argv[2], serviceKey)){
        fprintf(stderr, "servicekey input error \n");
        return ERROR_Param;
    }

    int64_t routeKey = 0;
    if (argc >= 4) {
        if (!soldier::base::StringUtil::boostCast(argv[3], routeKey)) {
            fprintf(stderr, "routeKey input error \n");
            return ERROR_Param;
        }
    }

    platform::InitRouteFinder();

    auto start_clock = std::chrono::high_resolution_clock::now();
    std::string resultIp = platform::GetRouteIp(serviceKey, "", routeKey);
    std::cout << "serviceKey=" << serviceKey
              << ", routeKey=" << routeKey
              << ", resultIp=" << resultIp
              << ", escaped="
              << std::chrono::duration_cast<std::chrono::microseconds>(
                      std::chrono::high_resolution_clock::now() - start_clock).count()
              << "us" << std::endl;
    return 0;
}

uint32_t DoSet(int argc, char* argv[]){
	if(argc < 4){
		fprintf(stderr, "please input enough parameters\n");
		return ERROR_Param;
	}

	uint32_t dwResult = 0;
	uint32_t dwServiceKey = 0;
	if(!soldier::base::StringUtil::boostCast(argv[2], dwServiceKey)){
		fprintf(stderr, "servicekey input error \n");
		return ERROR_Param;
	}

	IpItem oItem;
	RETURN_IF_ERROR(SliceItem(oItem, argv[3]));

	dwResult = oAgent.SetUpdate((uint16_t)dwServiceKey, oItem);
	if(dwResult){
		fprintf(stderr, "SetUpdate Failed %d\n", dwResult);
		return dwResult;
	}
	return 0;
}

uint32_t DoLoad(int argc, char* argv[])
{
	if(argc < 3){
		fprintf(stderr, "please input the file path\n");
		return ERROR_Param;
	}
	
	printf("begin load file %s\n", argv[2]);

	std::unique_ptr<FILE, soldier::base::FileDeleter> file(fopen(argv[2], "rb"));
	if (!file.get()) {
	    printf("failed to open file %s\n", argv[2]);
	    return ERROR_Param;
	}

	char buffer[1024];
	rapidjson::FileReadStream stream(file.get(), buffer, sizeof(buffer)/sizeof(char));

	rapidjson::Document root;
	root.ParseStream(stream);

	if (root.HasParseError()) {
	    printf("parse error %s, offset=%lu"
	            , rapidjson::GetParseError_En(root.GetParseError()), root.GetErrorOffset());
	}

	try {
	    oAgent.ClearUpdate();
	    const rapidjson::Value& itemsValueList = root["items"];

	    for(rapidjson::SizeType index = 0; index < itemsValueList.Size(); ++index) {
	        const rapidjson::Value& itemValue = itemsValueList[index];

	        int serviceKey = itemValue["serviceKey"].GetInt();
	        std::string ipList = itemValue["ipList"].GetString();
	        int routeType = itemValue["routeType"].GetInt();

	        printf("load serviceKey=%d, ipList=%s, routeType=%d\n", serviceKey, ipList.c_str(), routeType);
	        if (serviceKey < 0 || serviceKey >= SERVICE_KEY_MAX_NUM ) {
	            printf("warning: get unused servicekey %d\n", serviceKey);
	            continue;
	        }

	        IpItem oItem;
	        oItem.m_routeType = routeType;
	        if(0 == SliceItem(oItem, ipList)){
	            oAgent.SetUpdate((uint16_t)serviceKey, oItem);
	        }else{
	            printf("warning: serviceKey %d iplist error\n", serviceKey);
	        }
	    }

	    printf("end load, switch the agent\n");
	    oAgent.Switch();
	} catch (std::exception& e) {
	    printf("ParseConfig Error! %s\n", e.what());
	    return ERROR_Format;
	}

	sleep(1);
	return 0;
}

int main(int argc, char* argv[]){
	if(argc < 2){
		printf("please input your choice\n");
		help();
		return -1;
	}

	if(oAgent.InitAttach()){
		printf("Attach Shm Failed, errorMsg=%s\n", oAgent.GetLastErrMsg());
		return -2;
	}

	uint32_t dwResult = 0;
	if(0 == strcmp(argv[1], "query")){
		dwResult = DoQuery(argc, argv);
	}else if(0 == strcmp(argv[1], "set")){
		//	dwResult = DoSet(argc, argv);
		printf("it is not allowed now!");
	}else if(0 == strcmp(argv[1], "load")){
		dwResult = DoLoad(argc, argv);
	}else if(0 == strcmp(argv[1], "switch")){
		if(!oAgent.Switch()){
			printf("switch failed, not init!\n");
		}
	}else if(0 == strcmp(argv[1], "header")){
		oAgent.DumpHeader();
	}else if(0 == strcmp(argv[1], "queryOneIp")) {
	    dwResult = DoQueryOneIp(argc, argv);
	} else{
		printf("unkown choice!\n");
		help();
	}
	if(dwResult){
		help();
	}
	return dwResult;
}



