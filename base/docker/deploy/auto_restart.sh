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


ls -l | egrep "^d" | awk '{print $NF}' | while read dir
do
	pushd ${dir} > /dev/null
	if [ -f auto_restart.sh ];then
		chmod u+x auto_restart.sh
		./auto_restart.sh >> ../log/auto_restart.sh.log 2>&1
	fi
	popd > /dev/null
done

cd - > /dev/null

