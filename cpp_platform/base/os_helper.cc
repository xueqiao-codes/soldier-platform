/*
 * os_helper.cc
 *
 *  Created on: 2017年4月23日
 *      Author: 44385
 */
#include "base/os_helper.h"

#include <fstream>
#include <memory>
#include <sstream>
#include <unistd.h>
#include <limits.h>
#include <signal.h>
#include <sys/prctl.h>
#include <sys/wait.h>
#include "code_defense.h"

namespace soldier {
namespace base {

ProcessCommandArgs::ProcessCommandArgs(
		const std::string& exe_path
		, const char* args[])
	: exe_path_(exe_path) {
	int args_length = 0;
	while(args[args_length] != NULL) {
		++args_length;
	}

	argv_ = (char**)malloc(sizeof(char*) * (args_length + 2));
	argv_[0] = (char*)exe_path.c_str();
	for (int index = 0; index < args_length; ++index) {
		argv_[index + 1] = (char*)args[index];
	}
	argv_[args_length+1] = NULL;
}

ProcessCommandArgs::ProcessCommandArgs(
		const std::string& exe_path
		, const char* args[]
		, const char* envp[])
	: ProcessCommandArgs(exe_path, args){

	int envp_length = 0;
	while(args[envp_length] != NULL) {
		++envp_length;
	}

	envp_ = (char**)malloc(sizeof(char*) * (envp_length + 1));
	for (int index = 0; index < envp_length; ++index) {
		envp_[index] = (char*)envp[index];
	}
	envp_[envp_length] = NULL;
}

ProcessCommandArgs::~ProcessCommandArgs() {
	if (argv_ != nullptr) {
		free(argv_);
	}
	if (envp_ != nullptr) {
		free(envp_);
	}
}

std::string OSHelper::getProcessExePath() {
	 pid_t pid = getpid();
	 std::stringstream file_path;
	 file_path << "/proc/" << pid << "/exe";

	 std::unique_ptr<char[]> buffer(new char[PATH_MAX + 1]);
	 int result = readlink(file_path.str().c_str(), buffer.get(), PATH_MAX);
	 if (result <= 0) {
		 return "";
	 }
	 return std::string(buffer.get());
}

std::string OSHelper::getProcessName() {
    std::string exe_path = getProcessExePath();
    CHECK(!exe_path.empty());
    size_t index = exe_path.rfind('/');
    CHECK(index != std::string::npos);
    CHECK(exe_path.length() > index + 1);
    return exe_path.substr(index+1);
}

std::string OSHelper::getProcessDir() {
	std::string exe_path = getProcessExePath();
	CHECK(!exe_path.empty());
	size_t index = exe_path.rfind('/');
	CHECK(index != std::string::npos);
	CHECK(index > 0);
	return exe_path.substr(0, index);
}

bool OSHelper::touchFile(const std::string& file_path) {
    std::ofstream o(file_path, std::ios::out);
    if (!o) {
        return false;
    }
    o.flush();
    o.close();
    return true;
}

int OSHelper::startChildProcess(const ProcessCommandArgs& args) {
	int pid = fork();
	if (pid < 0) {
		return -1;   // create sub process failed;
	}

	if (pid == 0) {
		// sub process
		if (args.isDiedWithParent()) {
			prctl(PR_SET_PDEATHSIG, SIGKILL);
		}
		int ret = 0;
		if (args.getEnvp() == nullptr) {
			execv(args.getExePath().c_str(), args.getArgv());
		} else {
			execve(args.getExePath().c_str(), args.getArgv(), args.getEnvp());
		}
		if (ret < 0) {
			perror("execve error");
		}
		return getpid();
	}
	return pid;
}

void OSHelper::killChildProcess(int pid) {
	kill(pid, SIGKILL);
	waitpid(pid, NULL, 0);
}

} // end namespace base
} // end namespace soldier



