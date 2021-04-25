/*
 * string_util.h
 *
 *  Created on: 2017年2月19日
 *      Author: 44385
 */

#ifndef BASE_STRING_UTIL_H_
#define BASE_STRING_UTIL_H_

#include "base/code_defense.h"
#include <boost/lexical_cast.hpp>

#include <string>
#include <vector>

namespace soldier {
namespace base {

class StringUtil{
public:
    static void tokenize(const std::string& str
		, std::vector<std::string>& tokens
		, const std::string& delimiters
		, bool trimEmpty);

    static std::string join(const std::vector<std::string>& tokens
        , const std::string& delimiters);

    static bool startsWith(const std::string& longstr, const std::string& substr);
    static bool endsWith(const std::string& longstr, const std::string& substr);

    static bool gbkToUTF8(const std::string& gbkstr, std::string& utf8str);
    static bool utf8ToGBK(const std::string& utf8str, std::string& gbkstr);

    template<class T>
    static bool boostCast(const std::string& str, T& convert_value) {
        try {
            convert_value = boost::lexical_cast<T>(str);
            return true;
        } catch (boost::bad_lexical_cast& e) {
            return false;
        }
    }

    template<class T>
    static std::string boostCast(const T& convert_value) {
        return boost::lexical_cast<std::string>(convert_value);
    }
};


} // end namespace base
} // end namespace soldier



#endif /* BASE_STRING_UTIL_H_ */
