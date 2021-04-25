cc_binary(
    name = 'shm_master',
    srcs = [
        'shm_master.cpp',
    ],
    deps = [
        '//cpp_platform/base:soldier_basecpp',
    ]
)

cc_binary(
    name = 'shm_worker',
    srcs = [
        'shm_worker.cpp',
    ],
    deps = [
        '//cpp_platform/base:soldier_basecpp',
    ]
)

cc_binary(
    name = 'shm_message_queue_master',
    srcs = [
        'shm_message_queue_master.cpp',
    ],
    deps = [
        '//cpp_platform/message_bus:message_bus_api',
    ]
)

cc_binary(
    name = 'shm_message_queue_receivor',
    srcs = [
        'shm_message_queue_receivor.cpp',
    ],
    deps = [
        '//cpp_platform/message_bus:message_bus_api',
    ]
)

cc_binary(
    name = 'message_agent_receivor',
    srcs = [
        'message_agent_receivor.cpp',
    ],
    deps = [
        '//cpp_platform/message_bus:message_bus_api',
    ]
)

cc_binary(
    name = 'message_agent_sender',
    srcs = [
        'message_agent_sender.cpp',
    ],
    deps = [
        '//cpp_platform/message_bus:message_bus_api',
    ]
)

cc_binary(
    name = 'shm_message_guard_storage_test',
    srcs = [
        'shm_message_guard_storage_test.cpp',
    ],
    deps = [
        '//cpp_platform/message_bus:message_bus_api',
    ]
)