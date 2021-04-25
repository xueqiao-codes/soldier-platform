cc_library(
    name = 'route_agent',
    srcs = [
        'source/route_finder.cc',
        'source/route_agent.cc',
        'source/router/k8smod_router.cc',
        'source/router/k8ssvr_router.cc',
        'source/router/mod_router.cc',
        'source/router/router_factory.cc',
        'source/router/router.cc',
        'source/router/rr_router.cc',
        'source/router/xl_router.cc',
        'source/v1/daemon_tool.cc',
        'source/v1/shm_base.cc',
    ],
    export_incs = [
        'include',
    ],
    deps = [
        '//cpp_platform/base:soldier_basecpp',
        '//cpp_platform/thirdparty/qconf:qconf',
    ]
)

cc_library(
    name ='jroute_agent',
    srcs = [
        'source/jni/jroute_agent.cc',
    ],
    deps = [
        ':route_agent',
        '//cpp_platform/thirdparty/java/jni:jni_headers',
    ]
)

cc_library(
    name = 'jroute_agent_daemon',
    srcs = [
        'source/jni/jroute_agent_daemon.cc',
    ],
    deps = [
        ':route_agent',
        '//cpp_platform/thirdparty/java/jni:jni_headers',
    ]
)

cc_library(
   name = 'route_finder_swig_comm',
   srcs = [
        'source/swig/swig_interface.cc',
   ],
   deps = [
        ':route_agent'
   ]
)

swig_library(
    name = 'route_finder_swig',
    srcs = [
        'source/swig/route_finder.i',
    ],
    deps = [
        ':route_finder_swig_comm'
    ]
)


cc_binary(
    name = 'route_agent_tool2',
    srcs = [
        'source/bin/route_agent_tool.cc',
    ],
    deps = [
        ':route_agent',
        '//cpp_platform/thirdparty/rapidjson:rapidjson',
    ]
)
