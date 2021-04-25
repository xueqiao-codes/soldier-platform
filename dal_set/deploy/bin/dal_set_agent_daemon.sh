#!/bin/bash
if [ -L "$0" ];then
	THIS_SCRIPT_REAL_DIR=$( dirname $0 )
else
	# run this script directly, can be run everywhere
	THIS_SCRIPT_REAL_DIR="$(dirname "${BASH_SOURCE:-$0}")"
	if [ "$( echo ${THIS_SCRIPT_REAL_DIR} | grep '^/' | wc -l )" = "0" ];then
		THIS_SCRIPT_REAL_DIR="$( pwd )/${THIS_SCRIPT_REAL_DIR}"
	fi
fi
cd ${THIS_SCRIPT_REAL_DIR}/.. > /dev/null
DalSetAgentPath=$( pwd )
cd - > /dev/null


flock -ox ${DalSetAgentPath}/bin/dal_set_agent_daemon_sh.lock -c "${DalSetAgentPath}/bin/func/dal_set_agent_daemon_func.sh $*"
