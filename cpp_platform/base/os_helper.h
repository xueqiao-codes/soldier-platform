/*
 * os_helper.h
 *
 *  Created on: 2017年4月23日
 *      Author: 44385
 */

#ifndef BASE_OS_HELPER_H_
#define BASE_OS_HELPER_H_

#include <string>

namespace soldier {
namespace base {

class ProcessCommandArgs {
public:
	ProcessCommandArgs(const std::string& exe_path
			, const char* args[]) ;

	ProcessCommandArgs(const std::string& exe_path
			, const char* args[]
			, const char* envp[]);

	~ProcessCommandArgs();

	inline const std::string& getExePath() const {
		return exe_path_;
	}

	inline char** getArgv() const {
		return argv_;
	}

	inline char** getEnvp() const {
		return envp_;
	}

	inline bool isDiedWithParent() const {
		return died_with_parent_;
	}

	inline void setDiedWithParent(bool died) {
		died_with_parent_ = died;
	}

private:
	std::string exe_path_;
	char** argv_ = nullptr;
	char** envp_ = nullptr;
	bool died_with_parent_ = true; // when parent process died, child process die
};

class OSHelper {
public:
	static std::string getProcessExePath();

    static std::string getProcessName();

    static std::string getProcessDir();

    /**
     *  开启子进程
     */
    static int startChildProcess(const ProcessCommandArgs& args);

    /**
     *  杀死子进程，并等待退出
     */
    static void killChildProcess(int pid);


    static bool touchFile(const std::string& file_path);
};


} // end namespace base
} // end namespace soldier



#endif /* BASE_OS_HELPER_H_ */
