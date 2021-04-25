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
cd ${THIS_SCRIPT_REAL_DIR} > /dev/null
InstallPath=$( pwd )
cd - > /dev/null

ls -l ${InstallPath}/webapps | grep ^d | awk '{print $NF}' | \
while read webapp
do
	count=$( ps -ef | grep "${InstallPath}/webapps/${webapp}" | grep -v grep | wc -l )
	if [ -f "${InstallPath}/webapps/${webapp}/no_restart" ];then
		continue
	fi
	if [ ! -f "${InstallPath}/webapps/${webapp}/mitty.ini" ];then
		continue
	fi
	if [ $count -eq 0 ];then
		${InstallPath}/mitty.sh start ${webapp}
		echo "[$( date +'%Y-%m-%d %H:%M:%S')] restart webapp ${webapp} " >> ${InstallPath}/log/auto_restart.log
	fi
done