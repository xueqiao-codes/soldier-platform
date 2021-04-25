%module AttrReporter
%{

#include "attr/attr_reporter_swig.h"
using namespace soldier::attr;

%}

%include "std_string.i"
%include "std_map.i"

%template(StringMap) std::map<std::string, std::string>;

enum ReporterType {
    REPORTER_TYPE_MINUTE,
    REPORTER_TYPE_THIRTYSECONDS
};

extern int requireKey(ReporterType type
    , const std::string& metric
    , const std::map<std::string, std::string>& tags);
extern void inc(ReporterType type, int key, long long value);
extern void set(ReporterType type, int key, long long value);
extern void average(ReporterType type, int key, long long value);
extern void keep(ReporterType type, int key, long long value);

