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
cd ${THIS_SCRIPT_REAL_DIR}/../.. > /dev/null
DalSetAgentPath=$( pwd )
cd - > /dev/null

CONFIG_PATH=${DalSetAgentPath}/config/config.properties

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
	echo "$( ps -ef | egrep "dal_set_agent_daemon" | egrep jar | egrep -v egrep | wc -l )"
}

pushd ${DalSetAgentPath}/bin > /dev/null
RUNNABLE_JAR=$( ls -l | egrep *.jar | egrep dal_set_agent_daemon | awk '{print $NF}' )
popd > /dev/null

case "$1" in
	"start")
		echo "starting"
		count=$( getProcessCount )
		if [ $count -eq 0 ];then
			pushd ${DalSetAgentPath}/bin > /dev/null
			TMP_NO_HUP=/tmp/$$_dal_set_agent_daemon.nohup
			nohup java -jar $RUNNABLE_JAR $CONFIG_PATH >${TMP_NO_HUP} 2>&1 &
			sleep 2
			cat ${TMP_NO_HUP}
			rm -f ${TMP_NO_HUP}
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
		echo "stoping..."
		if [ $( getProcessCount ) -eq 0 ];then
			echo "Process is not running"
		else
			ps -ef | egrep "dal_set_agent_daemon" | egrep jar | egrep -v egrep | awk '{print $2}' | xargs kill -9
		fi
		sleep 1
	;;
	
	"show" )
		ps -ef | egrep "dal_set_agent_daemon" | egrep jar | egrep -v egrep
	;;
	
	"restart" )
		$0 stop
		$0 start
	;;
	
	* )
		help
    ;;
esac
