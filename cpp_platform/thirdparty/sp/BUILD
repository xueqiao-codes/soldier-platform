cc_library(
	name = 'sp',
	export_incs = [
		'include',
	],
	
	defs = [
		'BOOST_MODE=1',
	],
	deps = [
		':spapiwrapper',
	]
)

cc_library(
	name = 'spapiwrapper',
	prebuilt = True,
)