/*
 * filesystem_test.cpp
 *
 *  Created on: 2017年9月18日
 *      Author: wileywang
 */
#include <boost/filesystem.hpp>

#include <iostream>

int main() {

    boost::filesystem::path path1("test");
    boost::filesystem::path path2("/data/home/wangli/test");

    std::cout << boost::filesystem::absolute(path1, boost::filesystem::current_path()).string() << std::endl;
    std::cout << boost::filesystem::absolute(path2, boost::filesystem::current_path()).string() << std::endl;
    return 0;
}


