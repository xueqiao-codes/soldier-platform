#!/usr/bin/env python
# test for service RouteDao
import random
from route_dao.ttypes import *
from route_dao.constants import *
from route_dao.client.RouteDaoStub import *

stub=RouteDaoStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...
print(stub.getLastRouteVersion(0, 3000))
