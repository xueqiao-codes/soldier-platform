#!/usr/bin/env python
# test for service MachineManageBo
import random
from platform.machine.ttypes import *
from platform.machine.constants import *
from platform.machine.client.MachineManageBoStub import *

stub=MachineManageBoStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...


# newMachine=Machine()
# newMachine.hostName = "dev1.fund"
# newMachine.hostInnerIP = "172.16.131.115"
# newMachine.hostDesc = "dev_machine"
# newMachine.hostAdmin = "wangli"
# 
# print(stub.addMachine(0, 3000, newMachine))

# option = QueryMachineOption()
# option.hostNames = ['dev1.fund', "gaservice1"]
# 
# print(stub.queryMachineList(0, 3000, option, 0, 10))

# uMachine=Machine()
# uMachine.hostName = "dev1.fund"
# uMachine.hostDesc = "dev machine for users"

# print(stub.updateMachine(0, 3000, uMachine))

# print(stub.deleteMachine(0, 3000, 'dev1.fund'))

print(stub.updateMachineRelatedMonitor(0, 3000, "dev1.fund"))
# print(stub.deleteMachineRelatedMonitor(0, 3000, "test"));