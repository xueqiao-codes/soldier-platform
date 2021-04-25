#!/bin/bash

#扫描services目录下的所有目录

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

ls -l ${InstallPath}/services | grep ^d | awk '{print $NF}' | \
while read service
do
	count=$( ps -ef | grep "${InstallPath}/services/${service}/service.properties" | grep -v grep | wc -l )
	if [ -f "${InstallPath}/services/${service}/no_restart" ];then
		continue
	fi
	if [ ! -f "${InstallPath}/services/${service}/service.properties" ];then
		continue
	fi
	if [ $count -eq 0 ];then
		${InstallPath}/service.sh start ${service}
		echo "[$( date +'%Y-%m-%d %H:%M:%S')] restart service ${service} " >> ${InstallPath}/log/auto_restart.log
	fi
done

ls -l ${InstallPath}/daemon | grep ^d | awk '{print $NF}' | \
while read daemon
do
	if [ -f "${InstallPath}/daemon/${daemon}/no_restart" ];then
		continue 
	fi
	if [ ! -f "${InstallPath}/daemon/${daemon}/daemon.properties" ];then
		continue
	fi
	count=$( ps -ef | grep "${InstallPath}/daemon/${daemon}/daemon.properties" | grep -v grep | wc -l )
	if [ $count -eq 0 ];then
		${InstallPath}/daemon.sh start ${daemon}
		echo "[$( date +'%Y-%m-%d %H:%M:%S')] restart daemon ${daemon} " >> ${InstallPath}/log/auto_restart.log
	fi
done

