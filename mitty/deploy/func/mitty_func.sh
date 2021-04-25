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
InstallPath=$( pwd )
cd - > /dev/null

MITTY_HOME=$InstallPath

function help() {
	echo "$0 (start|start-force|stop|show|restart|restart-force|uninstall WebAppName|all) or (install|install-force war)"
	echo 
	echo " force is used for update mitty.ini from war"
	MyExit 0
}

function MyExit() {
	exit $1
}

if [ $# -lt 2 ]; then
	help
	MyExit 0
fi


function parseWebAppName() {
# $1 for war path
	if [ 0 -ne $( echo $1 | egrep "^/" | wc -l ) ];then
		WAR_PATH=$1
	else
		WAR_PATH=$(pwd)/$1
	fi
	WAR_NAME=$( basename $WAR_PATH )

	echo "$( echo "$WAR_NAME" | awk -F '.' '
			{
				web_app_name=$1;
				for(i=2;i<=NF-1;++i)
					web_app_name=web_app_name"."$i;
				print web_app_name;
			}' | \
	awk -F '-' '{print $1}' )"
} 

function fromWebAppNameToWar() {
	echo "${MITTY_HOME}/webapps/$1/$( ls -l ${MITTY_HOME}/webapps/$1 | egrep "*.war" | awk '{print $NF}' | head -1 )"
}

function getJarPath(){
	if [ "/" == "$( echo $1 | cut -c 1-1 )" ];then
		echo $1
	else
		echo $2/$1
	fi
}

function startWebApp() {
	if [ ! -d ${MITTY_HOME}/webapps/$1 ];then
		echo "No WebApp Named $1"
		MyExit 1
	fi
	
	count=$( ps -ef | egrep "${MITTY_HOME}/webapps/$1"  | grep -v grep | wc -l )
	if [ $count -ne 0 ];then
		echo "WebApp Named $1 is Already Running"
		MyExit 0
	fi
	
	MITTY_JAR_FILES=${MITTY_HOME}/log/$$_$2_JARS
	
	MITTY_JARS=""
	ls -l "${MITTY_HOME}/lib" | grep 'jar$' | grep -v grep | awk '{print $NF}' > ${MITTY_JAR_FILES}
	while read jar
	do
		MITTY_JARS="${MITTY_JARS}:$( getJarPath $jar ${MITTY_HOME}/lib )"
	done < ${MITTY_JAR_FILES}
	rm -f ${MITTY_JAR_FILES}

	JVM_OPTIONS=""
	if [ -f ${MITTY_HOME}/webapps/$1/jvm.config ];then
		JVM_OPTIONS=$( cat ${MITTY_HOME}/webapps/$1/jvm.config )
	fi
	
	WEBAPP_TEMP_DIR=$MITTY_HOME/work/$1
	echo "removing ${WEBAPP_TEMP_DIR}"
	rm -rf ${WEBAPP_TEMP_DIR}
	
	JAVA_CMD_ARGS="-cp ${MITTY_JARS} -server -ea -Dfile.encoding=UTF-8 ${JVM_OPTIONS} org.soldier.platform.web.mitty.MittyServer -dmitty.home=$MITTY_HOME -dmitty.webapp.tmpdir=${WEBAPP_TEMP_DIR} $( fromWebAppNameToWar $1 ) "
	if [ $# -gt 1 ];then
		if [ "$2"  = "--force" ];then
			JAVA_CMD_ARGS="$JAVA_CMD_ARGS -f "
		fi
	fi
	
	NO_HUP_FILE="/tmp/$$_mitty_no_hup.out"
	nohup java ${JAVA_CMD_ARGS} > ${NO_HUP_FILE} 2>&1 & 
	
	sleep 2
	cat $NO_HUP_FILE
	rm -f $NO_HUP_FILE
	
	echo 
	count=$( ps -ef | egrep "${MITTY_HOME}/webapps/$1"  | grep -v grep | wc -l )
	if [ $count -eq 0 ];then
		echo "======================Start Failed!=================================="
	else
		$0 show $1
	fi
}

function backupWebApp() {
	local webAppName=$1
	
	if [ ! -d ${MITTY_HOME}/webapps/${webAppName} ];then
		echo "no ${webAppName} need to backup"
		return 
	fi
	
	pushd ${MITTY_HOME}/webapps/${webAppName} > /dev/null
	
	local warName=$( ls -l | egrep "\.war$" | awk '{print $NF}' )
	if [ "$warName" = "" ];then
		echo "no war for ${webAppName} need to backup"
		popd > /dev/null
		return ;
	fi
	
	currentTime=$( date +%Y-%m-%d_%H-%M-%S )
	local backupPackage=${MITTY_HOME}/backup/${webAppName}_$currentTime.war
	echo "copy $( pwd )/${warName} to ${backupPackage}"
    cp -f $warName  ${backupPackage}
    if [ $? -ne 0 ];then
		echo "backup $( pwd )/${warName} failed"
		popd > /dev/null
		return 
    fi
	
	popd > /dev/null
}

function installWebApp() {
	warFilePath=$1
	if [ ! -f $warFilePath ];then
		echo "warFile $warFilePath is not exist! please check!"
		MyExit 1
	fi
	
	count=$( echo $warFilePath | egrep *.war$ | wc -l )
	if [ $count -eq 0 ];then
		echo "please using the war file to install"
		MyExit 1
	fi

	webAppName=$( parseWebAppName $warFilePath )
	echo "webAppName=$webAppName"
		
	pushd ${MITTY_HOME}/webapps > /dev/null
	if [ ! -d "$webAppName" ];then
		mkdir -p $webAppName
	else
		rm -f '$webAppName/*.war'
	fi
	popd > /dev/null
	
	backupWebApp ${webAppName}
	
	cp -f ${warFilePath} ${MITTY_HOME}/webapps/$webAppName
	
	if [ $# -gt 1 ];then
		if [ "$2"  = "--force" ];then
			$0 restart-force $webAppName
			return
		fi
	fi
	$0 restart $webAppName
}

function uninstallWebApp() {
	local webAppName=$1
	local webAppDir="${MITTY_HOME}/webapps/$webAppName"
	if [ ! -d ${webAppDir} ];then
		echo "No WebApp Named $webAppName"
		MyExit 1
	fi
	
	echo "removing ${webAppDir}"
	rm -rf ${webAppDir}
	
	$0 stop $webAppName
}

TIME=$( date +"%Y-%m-%d %H:%M:%S" )
case "$1" in
	"start")
		if [ $2 = "all" ];then
			ls -l ${MITTY_HOME}/webapps | grep ^d | awk '{print $NF}' | \
			while read webapp
			do
				$0 start $webapp
			done
		else
			startWebApp $2
		fi
	;;
	"start-force")
		if [ $2 = "all" ];then
			ls -l ${MITTY_HOME}/webapps | grep ^d | awk '{print $NF}' | \
			while read webapp
			do
				$0 start-force $webapp
			done
		else
			startWebApp $2 --force
		fi
	;;
	"stop" )
		if [ $2 = "all" ];then
			ls -l ${MITTY_HOME}/webapps | grep ^d | awk '{print $NF}' | \
			while read webapp
			do
				$0 stop $webapp
			done
		else
			count=$( ps -ef | egrep "${MITTY_HOME}/webapps/$2" | grep -v grep  | wc -l )
			if [ $count -ne 0 ];then
				ps -ef | egrep  "${MITTY_HOME}/webapps/$2" | grep -v grep | awk '{print $2}' | xargs kill -9
				echo "Stopping WebApp $2"
				WEBAPP_TEMP_DIR=$MITTY_HOME/work/$2
				echo "removing ${WEBAPP_TEMP_DIR}"
				rm -rf ${WEBAPP_TEMP_DIR}
				$0 show $2
				sleep 1
			else
				echo "No WebApp Named $2 Running"
			fi
		fi
	;;
    "show" )
		if [ $2 = "all" ];then
			ps -ef | egrep "${MITTY_HOME}/webapps/" | grep -v grep
		else
			ps -ef | egrep "${MITTY_HOME}/webapps/$2"  | grep -v grep 
		fi
    ;;
    "restart" )
		if [ $2 = "all" ];then
			ls -l ${MITTY_HOME}/webapps | grep ^d | awk '{print $NF}' | \
			while read webapp
			do
				$0 stop $webapp
				$0 start $webapp
			done
		else
			$0 stop $2
			$0 start $2
		fi
    ;;
	"restart-force" )
		if [ $2 = "all" ];then
			ls -l ${MITTY_HOME}/webapps | grep ^d | awk '{print $NF}' | \
			while read webapp
			do
				$0 stop $webapp
				$0 start-force $webapp
			done
		else
			$0 stop $2
			$0 start-force $2
		fi
	;;
	"install" )
		installWebApp $2
	;;
	"install-force" )
		installWebApp $2 --force
	;;
	"uninstall" )
		uninstallWebApp $2
	;;
    * )
		help
    ;;
esac
