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

app_name="fast_thrift_proxy"
install_dir="/usr/local/soldier/apps/"+ app_name

def _install(env, fabfile_dir):
    run("mkdir -p " + install_dir )
    if not files.exists("/data/applog/apps/" + app_name):
        run("mkdir -p /data/applog/apps/" + app_name)
    run('cp -f ' + fabfile_dir + "/target/fast_thrift_proxy-0.0.1-SNAPSHOT-jar-with-dependencies.jar " + install_dir)
    run('cp -f ' + fabfile_dir + '/supervisor.conf ' + install_dir)
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
    