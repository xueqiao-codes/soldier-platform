#
# install script
#

import sys
sys.path.append("/public/scripts/machine")

import os

from fabric.contrib import project, console, files
from common import getEnv
from supervisord import *
from epower import *

app_name="message_broker_hosting"
build_dir="../../build64_release/cpp_platform/message_bus/broker"
install_dir="/usr/local/soldier/apps/"+ app_name

def _analysis_dependencies(build_target_dir):
    return local("ldd " + build_target_dir + app_name + " | grep " +  build_dir + " | awk '{print $1}'", capture=True).split()


def _install(env, fabfile_dir):
    build_target_dir= fabfile_dir + "/" + build_dir + "/";
    run("mkdir -p " + install_dir )
    if not files.exists("/data/applog/apps/" + app_name):
        run("mkdir -p /data/applog/apps/" + app_name)
    if not files.exists("/data/message_bus/locks"):
        run("mkdir -p /data/message_bus/locks")

    for dependency in _analysis_dependencies(build_target_dir):
        dependency_dir = os.path.dirname(install_dir + "/" + dependency)
        if not files.exists(dependency_dir):
            run("mkdir -p " + dependency_dir)
        run('cp -f ' + fabfile_dir + '/' + dependency + ' ' + dependency_dir)

    run('cp -f ' + build_target_dir  + "message_broker " + install_dir)
    run('cp -f ' + fabfile_dir + "/supervisor.conf " + install_dir)
    addSupervisorConfig(install_dir)
    run("supervisorctl update")
    restart()
    

def install():
    _install(getEnv(), os.path.dirname(__file__))

def restart():
    run("supervisorctl restart " + app_name)

def uninstall():
    removeSupervisorConfig(install_dir)
    run("supervisorctl update")
    run("rm -rf " + install_dir)
    