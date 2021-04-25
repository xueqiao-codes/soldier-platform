/*
 * file_util.cc
 *
 *  Created on: 2017年4月19日
 *      Author: wileywang
 */

#include "base/file_util.h"

#include <stdio.h>
#include <stdexcept>

namespace soldier {
namespace base {

std::string FileUtil::readFileAll(const std::string& file_path) {
    FILE * file = fopen(file_path.c_str(), "rb");
    if (file == nullptr) {
        throw std::invalid_argument("file not found for " + file_path );
    }
    if (0 != fseek (file , 0 , SEEK_END)) {
        fclose(file);
        throw std::runtime_error("can not access end for " + file_path);
    }
    long file_size = ftell(file);
    printf("file_size=%ld\n", file_size);
    if (file_size <= 0) {
        fclose(file);
        if (file_size == 0) {
            return "";
        } else {
            throw std::runtime_error("access file size failed for " + file_path);
        }
    }
    rewind(file);

    std::string content;
    content.resize(file_size);
    size_t read_size = fread((void*)content.data(), 1, (size_t)file_size, file);
    if (read_size != (size_t)file_size) {
        fclose(file);
        throw std::runtime_error("read file failed for " + file_path);
    }

    fclose(file);
    return content;
}

} // end namespace base
} // end namespace soldier


