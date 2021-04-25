/*
 * string_util.cc
 *
 *  Created on: 2017年2月19日
 *      Author: 44385
 */
#include "base/string_util.h"
#include <iconv.h>

#include <memory>
#include "base/code_defense.h"

namespace soldier {
namespace base {


void StringUtil::tokenize(const std::string& str, std::vector<std::string>& tokens,
              const std::string& delimiters
              , bool trimEmpty) {
   std::string::size_type pos, lastPos = 0, length = str.length();

   while(lastPos < length + 1) {
      pos = str.find_first_of(delimiters, lastPos);
      if(pos == std::string::npos) {
         pos = length;
      }

      if(pos != lastPos || !trimEmpty)
         tokens.push_back(std::string(str.data()+lastPos, pos-lastPos ));

      lastPos = pos + 1;
   }
}

std::string StringUtil::join(const std::vector<std::string>& tokens
        , const std::string& delimiter) {
    std::string result;
    for (auto& token : tokens) {
        if (!result.empty()) {
            result.append(delimiter);
        }
        result.append(token);
    }
    return result;
}

bool StringUtil::startsWith(const std::string& longstr, const std::string& substr) {
    return 0 == longstr.find(substr);
}

bool StringUtil::endsWith(const std::string& longstr, const std::string& substr) {
    if (longstr.size() < substr.size()) {
        return false;
    }

    return longstr.rfind(substr) == (longstr.length() - substr.length());
}

static int codeConvert(const char *from_charset
		, const char *to_charset
		, const std::string& from
		, std::string& to) {
	iconv_t cd = NULL;
	int rc = -1;
	char* in = (char*)from.data();
	size_t in_len = from.length();
	size_t out_len = from.length()*3 + 1;
	size_t out_left = out_len;
	std::unique_ptr<char[]> buffer(new char[out_len]);

	char* out = buffer.get();
	cd = iconv_open(to_charset,from_charset);
	if (cd==0) {
		return -1;
	}
	rc = iconv(cd, &in, &in_len, &out ,&out_left);
	iconv_close(cd);
	if (rc == 0) {
		CHECK(out_left <= out_len);
		buffer[out_len - out_left] = 0;
		to = buffer.get();
	}
	return rc;
}

bool StringUtil::gbkToUTF8(const std::string& gbkstr, std::string& utf8str) {
	if (0 == codeConvert("gb2312", "utf-8", gbkstr, utf8str)) {
		return true;
	}
	return false;
}

bool StringUtil::utf8ToGBK(const std::string& utf8str, std::string& gbkstr) {
	if (0 == codeConvert("utf-8", "gb2312", utf8str, gbkstr)) {
		return true;
	}
	return false;
}


} // end namespace base
} // end namespae soldier
