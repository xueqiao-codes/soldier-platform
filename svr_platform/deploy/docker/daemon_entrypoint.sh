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

function MyExit() {
	exit $1
}

function getClassPath(){
	local classpath=$( ls -l "$1" | grep 'jar$' | grep -v grep | awk -v jar_dir="$1" $'{print jar_dir"/"$NF}' | tr '\n' ':' )
	echo ${classpath%?}
}

function startDaemon() {
	local daemon_name=$1
	local TIME=$( date +"%Y-%m-%d %H:%M:%S" )
	
	PlatformJars=$( getClassPath $InstallPath/lib )
	DaemonExtJars=$( getClassPath $InstallPath/daemon/$daemon_name/ext_jar )

	RUNNABLE_JAR_PATH="${InstallPath}/daemon/$daemon_name/$( ls -l ${InstallPath}/daemon/$daemon_name | egrep $daemon_name.*jar | awk '{print $NF}' | head -1 )"
	if [ "z${RUNNABLE_JAR_PATH}" == "z"  ];then
		echo "Daemon Jar is not existed!"
		MyExit 2
	fi
	
	if [ ! -f "${InstallPath}/daemon/$daemon_name/daemon.properties" ];then
		echo "daemon.properties is not existed!"
		MyExit 3
	fi
		
	JVM_OPTION=""
	if [ -f "${InstallPath}/daemon/$daemon_name/jvm.config" ];then
		JVM_OPTION=$( cat "${InstallPath}/daemon/$daemon_name/jvm.config" )
	fi
	echo "JVM-OPTION is ${JVM_OPTION}"
		
	ADAPTOR_CLASS=""
	if [ -f "${InstallPath}/daemon/$daemon_name/adaptor" ];then
		ADAPTOR_CLASS=$(cat "${InstallPath}/daemon/$daemon_name/adaptor" )
		export ADAPTOR_CLASS
	fi

	CONFIG_ARGS=" ${InstallPath}/daemon/$daemon_name/daemon.properties $daemon_name"

	CLASSPATH_ARG="${PlatformJars}:${DaemonExtJars}:${RUNNABLE_JAR_PATH}"
	DAL_SET_PATH="${InstallPath}/../dal_set/config/dal_set.xml"

	export DAL_SET_PATH
	   
	echo "[${TIME}] start daemon $daemon_name"
	JAVA_CMD_ARGS="-cp ${CLASSPATH_ARG} -server -ea -Dfile.encoding=UTF-8 ${JVM_OPTION} org.soldier.platform.svr_platform.container.DaemonContainer ${CONFIG_ARGS}"
	
	JAVA_START_CMD=""
	if [ "$JAVA_HOME" = "" ];then
		JAVA_START_CMD="java ${JAVA_CMD_ARGS}"
	else
		JAVA_START_CMD="$JAVA_HOME/bin/java ${JAVA_CMD_ARGS}"
	fi
	echo "$JAVA_START_CMD"
	$JAVA_START_CMD
}

function main() {
  for daemon in $( ls -l "${InstallPath}/daemon" | grep ^d | awk '{print $NF;}' )
  do
    # acture only one will run
    startDaemon $daemon
    exit $?
  done
}

main


