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

IsInCygwin="false"
if [ "0" != "$( uname -s | grep '^CYGWIN' | wc -l )" ];then
    IsInCygwin="true"
fi

function help() {
	echo "$0 start|stop|show|restart|uninstall DaemonName|all(special for all daemon)"
	exit 0
}

function MyExit() {
	exit $1
}

if [ $# -lt 2 ]; then
	help
	MyExit 0
fi

function getDaemonCount(){
    if [ "$IsInCygwin" = "true" ];then
        echo "$( ps -ef | grep -v grep | grep "daemon/$1/java" | wc -l )"
    else
        echo "$( ps -ef | grep -v grep | grep java | grep "daemon/$1/daemon.properties" | wc -l )"
    fi
}

function getJarPath(){
	if [ "/" == "$( echo $1 | cut -c 1-1 )" ];then
		echo $1
	else
		echo $2/$1
	fi
}

function uninstallDaemon() {
	local daemonName=$1
	local daemonDir="${InstallPath}/daemon/$daemonName"
	local daemonZipPath="${InstallPath}/daemon/$daemonName.zip"
	if [ ! -d ${daemonDir} ];then
		echo "No Daemon Named $daemonName"
		MyExit 1
	fi
	
	echo "removing ${daemonDir}"
	rm -rf ${daemonDir}
	if [ -f "$daemonZipPath" ];then
		echo "removing ${daemonZipPath}"
		rm -f ${daemonZipPath}
	fi
	
	$0 stop $daemonName
}

TIME=$( date +"%Y-%m-%d %H:%M:%S" )
case "$1" in
	"start")
	    if [ "$2" == "all" ];then
	       for daemon in $( ls -l "${InstallPath}/daemon" | grep ^d | awk '{print $NF;}' )
           do
                $0 start $daemon
           done
           MyExit 0
	    fi
	
		if [ ! -d "${InstallPath}/daemon/$2" ];then
			echo "Daemon is not existed!"
			MyExit 1
		fi
		
		if [ "$( getDaemonCount $2 )" -gt "0" ];then
		    echo "Daemon is already running, pleasee use restart or stop"
		    MyExit 0
		fi
		

		JAR_FILES_PLATFORMS="${InstallPath}/PLATFORM_JARS_$2"
		JAR_FILES_EXT="${InstallPath}/EXT_JARS_$2"
		JAR_FILES_LPC_MODULES="${InstallPath}/LPC_MODULES_$2"

		PlatformJars=""

		if [ -d "${InstallPath}/daemon/$2/ext_jar" ];then
			ls -l "${InstallPath}/daemon/$2/ext_jar" | grep 'jar$' | grep -v grep | awk '{print $NF}' > ${JAR_FILES_EXT}
			while read ext_jar
			do
				PlatformJars="${PlatformJars}:$( getJarPath ${ext_jar} ${InstallPath}/daemon/$2/ext_jar )"
			done < ${JAR_FILES_EXT}
		else
			printf "" > ${JAR_FILES_EXT}
		fi
		
		ls -l "${InstallPath}/lib" | grep 'jar$' | grep -v grep | awk '{print $NF}' > ${JAR_FILES_PLATFORMS}
		while read jar
		do
			if [ $( cat ${JAR_FILES_EXT} | grep "$( basename $jar )" | wc -l ) -eq "0" ];then
				PlatformJars="${PlatformJars}:$( getJarPath $jar ${InstallPath}/lib )"
			fi
		done < ${JAR_FILES_PLATFORMS}

		LpcModuleJars=""
		
		rm -f ${JAR_FILES_EXT}
		rm -f ${JAR_FILES_PLATFORMS}
		rm -f ${JAR_FILES_LPC_MODULES}

		RUNNABLE_JAR_PATH="${InstallPath}/daemon/$2/$( ls -l ${InstallPath}/daemon/$2 | egrep $2.*jar | awk '{print $NF}' | head -1 )"
		if [ "z${RUNNABLE_JAR_PATH}" == "z"  ];then
			echo "Daemon Jar is not existed!"
			MyExit 2
		fi

		if [ ! -f "${InstallPath}/daemon/$2/daemon.properties" ];then
			echo "daemon.properties is not existed!"
			MyExit 3
		fi
		
		JVM_OPTION=""
		if [ -f "${InstallPath}/daemon/$2/jvm.config" ];then
			JVM_OPTION=$( cat "${InstallPath}/daemon/$2/jvm.config" )
		fi
		echo "JVM-OPTION is ${JVM_OPTION}"
		
		ADAPTOR_CLASS=""
		if [ -f "${InstallPath}/daemon/$2/adaptor" ];then
			ADAPTOR_CLASS=$(cat "${InstallPath}/daemon/$2/adaptor" )
			export ADAPTOR_CLASS
		fi

		CONFIG_ARGS=" ${InstallPath}/daemon/$2/daemon.properties $2"

		CLASSPATH_ARG="${PlatformJars}:${LpcModuleJars}:${RUNNABLE_JAR_PATH}"
		LPC_MODULES="${LpcModuleJars}"
		DAL_SET_PATH="${InstallPath}/../dal_set/config/dal_set.xml"
	   
		if [ "${IsInCygwin}" = "true" ];then
			LD_LIBARAY_PATH=$( cygpath -wp ${LD_LIBARAY_PATH} )
			CLASSPATH_ARG=$( cygpath -wp ${CLASSPATH_ARG} )
			LPC_MODULES=$( cygpath -wp ${LPC_MODULES} )
			CONFIG_ARGS=$( cygpath -wp ${CONFIG_ARGS} | tr '\n' ' ' ) 
			DAL_SET_PATH=$( cygpath -wp ${DAL_SET_PATH} )
		fi
		export LD_LIBARAY_PATH
		export LPC_MODULES
		export DAL_SET_PATH
	   
		echo "[${TIME}] start daemon $2"
		JAVA_CMD_ARGS="-cp ${CLASSPATH_ARG} -server -ea -Dfile.encoding=UTF-8 ${JVM_OPTION} org.soldier.platform.svr_platform.container.DaemonContainer ${CONFIG_ARGS}"
		if [ "${IsInCygwin}" = "false" ];then
		  TMP_NO_HUP_OUT=/tmp/$$_daemon_svr_platform_no_hup.out
		  nohup java ${JAVA_CMD_ARGS} > $TMP_NO_HUP_OUT 2>&1 & 
		  
		  sleep 2
		  echo
		  cat $TMP_NO_HUP_OUT
		  rm -f $TMP_NO_HUP_OUT
		  
		else
		  cd ${InstallPath}/daemon/$2
		  
		  if [ "$JAVA_HOME" = "" ];then
		      echo "JAVA_HOME is not set"
		      MyExit 10
		  fi 
		  
		  JAVA_PATH=$( cygpath -up "$JAVA_HOME/bin/java.exe" )
		  if [ ! -f "$JAVA_PATH" ];then
		      echo "Java.exe is not exit"
		      MyExit 11
		  fi
		  
		  if [ ! -f "java.exe" ];then
		      cp -f $JAVA_PATH java 
		  fi
	
		  ${InstallPath}/daemon/$2/java ${JAVA_CMD_ARGS} &
		  cd -
		  
		  sleep 2
		fi
	   
		count=$( getDaemonCount $2 )
		echo 
		echo 
		if [ "$count" -eq "0" ];then
			echo "=============== $2 STARTED FAILED================="
		else
			$0 show $2
		fi
		MyExit 0
		
	;;

	"stop" )
	    if [ "$2" == "all" ];then
            for daemon in $( ls -l "${InstallPath}/daemon" | grep ^d | awk '{print $NF;}' )
            do
                $0 stop $daemon
            done
            MyExit 0
        fi
	
		echo "[${TIME}] stop daemon $2"
		count=$( getDaemonCount $2 )
		if [ "$count" -eq "0" ];then
			echo "no daemon $2 is running"
			MyExit 0
		fi
		if [ "$IsInCygwin" = "true" ];then
		    ps -ef | grep java | grep "daemon/$2/java" | grep -v grep | awk '{print $2}' | xargs kill -9 
		else
		    ps -ef | grep java | grep "daemon/$2/daemon.properties" | grep -v grep | awk '{print $2}' | xargs kill -9
	    fi
	;;

    "show" )
		if [ "$2" == "all" ];then
	      ps -ef | grep "${InstallPath}/daemon" | grep -v grep
		else 
		  if [ "$IsInCygwin" = "true" ];then
		      ps -ef | grep "${InstallPath}/daemon/$2/java" | grep -v grep
		  else
		      ps -ef | grep java | grep "${InstallPath}/daemon/$2/daemon.properties" | grep -v grep
		  fi
		fi
    ;;
	
    "restart" )
		if [ "$2" == "all" ];then
			for daemon in $( ls -l "${InstallPath}/daemon" | grep ^d | awk '{print $NF;}' )
			do
				$0 restart $daemon
			done
		else
			$0 stop $2
			$0 start $2
		fi
    ;;
	
	"uninstall" )
		uninstallDaemon $2
	;;
    * )
	help
    ;;
esac

MyExit 0


