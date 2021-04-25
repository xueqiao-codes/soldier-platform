cc_library(
	name = 'soldier_basecpp',
	srcs = [
		'app_log.cc',
		'base64.c',
		'file_util.cc',
		'hash.cc',
		'net_helper.cc',
		'os_helper.cc',
		'shm_object.cc',
		'string_util.cc',
		'thread_pool.cc',
		'time_helper.cc',
		'url_util.cc',
	],
	deps = [
		'//cpp_platform/thirdparty/spdlog:spdlog',
		'//cpp_platform/thirdparty/qconf:qconf',
		'//cpp_platform:cpp_platform',
		'#boost_system',
		'#boost_filesystem',
	],
)