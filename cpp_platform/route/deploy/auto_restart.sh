#!/bin/bash

if [ -L "$0" ];then
	THIS_SCRIPT_REAL_DIR=$( dirname $( readlink -f $0 ) )
else
	# run this script directly, can be run everywhere
	THIS_SCRIPT_REAL_DIR="$(dirname "${BASH_SOURCE:-$0}")"
	if [ "$( echo ${THIS_SCRIPT_REAL_DIR} | grep '^/' | wc -l )" = "0" ];then
		THIS_SCRIPT_REAL_DIR="$( pwd )/${THIS_SCRIPT_REAL_DIR}"
	fi
fi

INSTALL_PATH=${THIS_SCRIPT_REAL_DIR}

count=$( ps -ef | grep "route_agent_daemon" | egrep jar | grep -v grep | wc -l )
if [ $count -eq 0 ];then
	${INSTALL_PATH}/bin/route_agent_daemon.sh start
	if [ $? != 0 ];then
		echo ""
	fi	
	echo "[$(date +'%Y-%m-%d %H:%M:%S')]restart route_agent_daemon" >> ${INSTALL_PATH}/log/auto_restart.log
fi


