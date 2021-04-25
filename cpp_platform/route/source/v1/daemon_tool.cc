/*
 * daemon_tool.cpp
 *
 *  Created on: 2012-5-17
 *      Author: Xairy
 */
#include "v1/daemon_tool.h"
#include <signal.h>
#include <stdlib.h>
#include <sys/stat.h>
#include "v1/error_define.h"

COMM_NAMESPACE_BEGIN

int init_daemon(){
	pid_t pid = fork();
	if(pid < 0){
		return ERROR_Fork;
	}else if(pid > 0){
		exit(0);
	}

	if(-1 == setsid()){
		return ERROR_SetSid;
	}

	pid = fork();
	if(pid < 0){
		return ERROR_Fork;
	}else if(pid > 0){
		exit(0);
	}

	umask(0);

	signal(SIGHUP, SIG_IGN);
	signal(SIGPIPE, SIG_IGN);
	signal(SIGIO, SIG_IGN);
	signal(SIGBUS, SIG_IGN);
	//todo 完善信号屏蔽

	return 0;
}


COMM_NAMESPACE_END
