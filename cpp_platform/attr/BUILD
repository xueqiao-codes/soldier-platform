cc_library(
    name = 'soldier_attr',
    srcs = [
        'backend/attr_reporter_backend_falcon.cpp',
        'backend/attr_reporter_backend_log.cpp',
        'attr_reporter_factory.cpp',
        'attr_reporter_impl.cpp',
        'attr_reporter_swig.cpp',
    ],
    deps = [
        '//cpp_platform/base:soldier_basecpp',
        '//cpp_platform/thirdparty/rapidjson:rapidjson',
        '//cpp_platform/thirdparty/curlpp:curlpp',
    ]
)

#cc_library(
#    name = 'soldier_attr_shared',
#    srcs = [
#        'backend/attr_reporter_backend_falcon.cpp',
#        'backend/attr_reporter_backend_log.cpp',
#        'attr_reporter_factory.cpp',
#        'attr_reporter_impl.cpp',
#        'attr_reporter_swig.cpp',
#    ],
#    deps = [
#        '//cpp_platform/base:soldier_basecpp',
#        '//cpp_platform/thirdparty/rapidjson:rapidjson',
#        '//cpp_platform/thirdparty/curlpp:curlpp_shared',
#    ]
#)

swig_library(
    name = 'AttrReporter',
    srcs = [
        'AttrReporter.i',
    ],
    java_package = 'org.soldier.platform.attr',
    java_lib_packed = True,
    deps = [
        ':soldier_attr'
    ]
)

