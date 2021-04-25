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

cd ${THIS_SCRIPT_REAL_DIR} > /dev/null


count=$(ps -ef | grep "$(pwd)/bin/qconf_agent" | grep -v grep | wc -l)
if [ $count -eq 0 ];then
	chmod u+x $(pwd)/bin/agent-cmd.sh
	$(pwd)/bin/agent-cmd.sh start
	echo "[$(date +'%Y-%m-%d %H:%M:%S')]restart qconf_agent" >> $(pwd)/logs/auto_restart.log
	sleep 2
	pushd script > /dev/null
	ls -al | awk '{print $NF}' | grep ".sh$" | while read script
	do
		if [ "$script" != "nginx_template.sh" ];then
			chmod u+x $script
			./$script >> ${THIS_SCRIPT_REAL_DIR}/logs/auto_restart.log
		fi
	done
	popd > /dev/null
fi

cd - > /dev/null

