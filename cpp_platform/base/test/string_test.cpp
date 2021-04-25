/*
 * string_test.cpp
 *
 *  Created on: 2018年2月5日
 *      Author: wangli
 */

#include "base/string_util.h"

#include <iostream>

using namespace soldier::base;

int main(int argc, char* argv[]) {

	std::string utf8str = "我是中国人";

	std::string gbkstr;
	if (StringUtil::utf8ToGBK(utf8str, gbkstr)) {
		std::cout << "gbk:" << gbkstr << std::endl;
	} else {
		std::cout << "convert failed!" << std::endl;
	}

	int right100 = 0;
	std::cout << "boostCast:" <<  StringUtil::boostCast("100", right100);
	std::cout << ", value=" << right100 << std::endl;
	int wrong100 = 0;
	std::cout << "boostCast:" << StringUtil::boostCast("a100", wrong100);
	std::cout << ", value=" << wrong100 << std::endl;

	return 0;
}


