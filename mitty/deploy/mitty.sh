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
InstallPath=$( pwd )
cd - > /dev/null

flock -xo ${InstallPath}/lock/mitty_sh.lock -c "${InstallPath}/func/mitty_func.sh $*"
