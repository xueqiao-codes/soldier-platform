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
SNginxAgentPath=$( pwd )
cd - > /dev/null

CONFIG_PATH=${SNginxAgentPath}/config/snginx_agent_config.properties

function help() {
	echo "$0 start|stop|show|restart"
	exit 0
}

function MyExit() {
	exit $1
}
if [ $# -lt 1 ]; then
	help
	MyExit 0
fi

function getProcessCount() {
	echo "$( ps -ef | egrep "snginx_agent" | egrep jar | egrep -v egrep | wc -l )"
}

pushd ${THIS_SCRIPT_REAL_DIR}/bin > /dev/null
RUNNABLE_JAR=$(pwd)/$( ls -l | egrep *.jar | egrep snginx_agent | awk '{print $NF}' )
popd > /dev/null

case "$1" in
	"start")
		count=$( getProcessCount )
		if [ $count -eq 0 ];then
			pushd ${THIS_SCRIPT_REAL_DIR} > /dev/null
			TMP_OUT=/tmp/snginx_agent_$$.out
			nohup java -jar $RUNNABLE_JAR $CONFIG_PATH >${TMP_OUT} 2>&1 &
			sleep 2
			cat $TMP_OUT
			rm -f $TMP_OUT
			popd > /dev/null
			
			echo ""
			echo ""	
			if [ $( getProcessCount ) -ne 0 ];then
				$0 show
			else
				echo "===============Start Failed!===================="
			fi
		else
			echo "Already started!"
		fi
	;;
	
	"stop" )
		if [ $( getProcessCount ) -eq 0 ];then
			echo "Process is not running"
		else
			ps -ef | egrep "snginx_agent" | egrep jar | egrep -v egrep | awk '{print $2}' | xargs kill -9
		fi
	;;
	
	"show" )
		ps -ef | egrep "snginx_agent" | egrep jar | egrep -v egrep
	;;
	
	"restart" )
		$0 stop
		$0 start
	;;
	
	* )
		help
    ;;
esac
