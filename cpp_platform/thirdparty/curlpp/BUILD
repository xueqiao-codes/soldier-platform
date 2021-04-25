cc_library(
	name='curl',
	export_incs = [
		'curl-7.53.1/include'
	],
	prebuilt = True
)

cc_library(
    name='curlpp',
    export_incs = [
        'curlpp-0.8.1/include',
    ],
	deps = [
		':curl'
	],
    prebuilt = True
)

#cc_library(
#    name='curlpp_shared',
#    export_incs = [
#        'curl-7.53.1/include',
#        'curlpp-0.8.1/include',
#    ],
#    deps = [
#        '#curlpp_shared',
#        '#curl_shared',
#    ]
#)