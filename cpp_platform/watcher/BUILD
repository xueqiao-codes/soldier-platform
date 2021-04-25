cc_library(
    name = 'file_watcher_module',
    srcs = [
        'file_watcher_module.cpp',
        'qconf_file_installer.cpp',
    ],
    deps = [
        '//cpp_platform/inotify:inotifycpp',
        '//cpp_platform/base:soldier_basecpp',
        '//cpp_platform/thirdparty/qconf:qconf',
    ]
)

cc_library(
	name = 'file_watcher_swig',
	srcs = [
		'swig/file_watcher_swig.cpp',
	],
	deps = [
		':file_watcher_module',
		'//cpp_platform/thirdparty/java/jni:jni_headers',
	]
)

swig_library(
    name = 'FileWatcher',
    srcs = [
        'swig/FileWatcher.i',
    ],
    java_package = 'org.soldier.watcher.file.swig',
    java_lib_packed = True,
    deps = [
        ':file_watcher_swig'
    ]
)