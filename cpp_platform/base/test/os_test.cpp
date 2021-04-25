/*
 * os_test.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include "base/os_helper.h"
#include <iostream>


using namespace soldier::base;

int main(int argc, char* argv[]) {

	std::cout << "exe path=" << OSHelper::getProcessExePath() << std::endl;
	std::cout << "process name=" << OSHelper::getProcessName() << std::endl;
	std::cout << "process dir=" << OSHelper::getProcessDir() << std::endl;

	const char* args[] = {"-al", NULL};
	ProcessCommandArgs processArgs("/bin/ls", args);
	std::cout << "sub pid=" << OSHelper::startChildProcess(processArgs) << std::endl;

	getchar();

	return 0;
}

