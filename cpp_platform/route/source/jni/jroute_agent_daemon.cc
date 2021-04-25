/*
 * config_agent_daemon.cpp
 *
 *  Created on: 2012-5-17
 *      Author: Xairy
 */
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include "route_agent.h"
#include "jni/org_soldier_platform_route_agent_daemon_RouteUpdateRunner.h"

using namespace COMM_NAMESPACE;
using namespace platform::config;

static CRouteAgent oAgent;

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

int writeContent(const char* filePath, const char* content) {
	FILE* file = fopen(filePath, "w+");
	if (file == NULL ) {
		fprintf(stderr, "open file %s failed!", filePath);
		return -1;
	}
	
	if( -1 == lockFile(fileno(file))) {
		fprintf(stderr, "lock file failed!");
		fclose(file);
		return -2;
	}
	
	size_t len = strlen(content);
	fwrite(content, len, 1, file);
	
	unLockFile(fileno(file));
	fclose(file);
	return 0;	
}


JNIEXPORT jint JNICALL Java_org_soldier_platform_route_1agent_1daemon_RouteUpdateRunner_writeFile
  (JNIEnv *env, jclass clazz, jstring jpath, jstring jcontent) {
    const char* path = env->GetStringUTFChars(jpath, NULL);
    if(path == NULL) {  
       return 1; /* OutOfMemoryError already thrown */  
    }  	
    const char* content = env->GetStringUTFChars(jcontent, NULL);
    if (content == NULL) {
       env->ReleaseStringUTFChars(jpath, path);
       return 2;
    }
   
    int result = writeContent(path, content);
    env->ReleaseStringUTFChars(jpath, path);
    env->ReleaseStringUTFChars(jcontent, content);
    return result; 
}


JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    if (0 != oAgent.InitAttach()) {
    	int iRet = oAgent.InitCreate();
    	if(iRet != 0){
       	     fprintf(stderr, "init config agent failed %d, errMsg=%s",
                	                 iRet, oAgent.GetLastErrMsg());
             return -1;
    	}
    }

    return JNI_VERSION_1_4;
}

JNIEXPORT void JNI_OnUnload(JavaVM* vm, void* reserved)
{
}
	
