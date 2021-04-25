/*
 * jconfig_agent.cpp
 *
 *  Created on: 2012-5-17
 *      Author: Xairy
 */
#include "route_agent.h"

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

#include "jni/org_soldier_platform_route_impl_RouteFinderBySoImpl.h"
#include "route_finder.h"

JNIEXPORT jstring JNICALL Java_org_soldier_platform_route_impl_RouteFinderBySoImpl_nativeGetRouteIp
  (JNIEnv *env, jclass jclazz, jlong jServiceKey, jstring jMethodName, jlong jRouteKey) 
{
	const char* methodName = env->GetStringUTFChars(jMethodName, NULL);
    if(methodName == NULL) {  
       return env->NewStringUTF(""); /* OutOfMemoryError already thrown */  
    }
	
	std::string ip = platform::GetRouteIp((int)jServiceKey, methodName, jRouteKey);
	env->ReleaseStringUTFChars(jMethodName, methodName);
	
	return env->NewStringUTF(ip.c_str());
}

JNIEXPORT void JNICALL Java_org_soldier_platform_route_impl_RouteFinderBySoImpl_nativeUpdateCallInfo
  (JNIEnv *, jclass, jlong, jstring, jstring, jint) 
{
}

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
	if(platform::InitRouteFinder()){
		fprintf(stderr, "route finder failed!");
		return -1;
	}
	
    return JNI_VERSION_1_4;
}

JNIEXPORT void JNI_OnUnload(JavaVM* vm, void* reserved)
{
}
