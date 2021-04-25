cc_library(
    name = 'message_bus_api',
    srcs = [
        'guard/shm_message_guard_storage.cpp',
        'interface/framework_topics.cpp',
        'interface/message_agent.cpp',
        'interface/message_graph_holder.cpp',
        'interface/message_factory.cpp',
        'queue/shm_message_queue.cpp',
    ],
    deps = [
        '//cpp_platform/base:soldier_basecpp',
        '//cpp_platform/thirdparty/rapidjson:rapidjson',
        '//cpp_platform/watcher:file_watcher_module',
    ]
)

cc_library(
    name = 'message_bus_swig',
    srcs = [
        'swig/message_agent_swig.cpp',
    ],
    deps = [
        ':message_bus_api',
    ]
)

swig_library(
    name = 'MessageBus',
    srcs = [
        'swig/MessageAgent.i',
    ],
    java_package = 'org.soldier.framework.message_bus.swig',
    java_lib_packed = True,
    deps = [
        ':message_bus_swig',
        '//cpp_platform/thirdparty/java/jni:jni_headers',
    ]
)