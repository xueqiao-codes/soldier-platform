__all__=['route_finder']

import route_finder
if 0 != route_finder.InitRouteFinder():
	raise Exception("InitRouteFinder Failed")