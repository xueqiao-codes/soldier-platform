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
	echo "$0 start|stop|show|restart|uninstall ServiceName|all(special for all services)"
	exit 0
}

function MyExit() {
	exit $1
}

if [ $# -lt 2 ]; then
	help
	MyExit 0
fi

function getServiceCount(){
    if [ "$IsInCygwin" = "true" ];then
        echo "$( ps -ef | grep -v grep | grep "services/$1/java" | wc -l )"
    else
        echo "$( ps -ef | grep -v grep | grep java | grep "services/$1/service.properties" | wc -l )"
    fi
}

function getJarPath(){
	if [ "/" == "$( echo $1 | cut -c 1-1 )" ];then
		echo $1
	else
		echo $2/$1
	fi
}

function uninstallService() {
	local serviceName=$1
	local serviceDir="${InstallPath}/services/$serviceName"
	local serviceZipPath="${InstallPath}/services/$serviceName.zip"
	if [ ! -d ${serviceDir} ];then
		echo "No Service Named $serviceName"
		MyExit 1
	fi
	
	echo "removing ${serviceDir}"
	rm -rf ${serviceDir}
	if [ -f "$serviceZipPath" ];then
		echo "removing ${serviceZipPath}"
		rm -f ${serviceZipPath}
	fi
	
	$0 stop $serviceName
}

TIME=$( date +"%Y-%m-%d %H:%M:%S" )
case "$1" in
	"start")
	    if [ "$2" == "all" ];then
	       for service in $( ls -l "${InstallPath}/services" | grep ^d | awk '{print $NF;}' )
           do
                $0 start $service
           done
           MyExit 0
	    fi
	
		if [ ! -d "${InstallPath}/services/$2" ];then
			echo "Service is not existed!"
			MyExit 1
		fi
		
		if [ "$( getServiceCount $2 )" -gt "0" ];then
		    echo "Service is already running, pleasee use restart or stop"
		    MyExit 0
		fi
		

		JAR_FILES_PLATFORMS="${InstallPath}/PLATFORM_JARS_$2"
		JAR_FILES_EXT="${InstallPath}/EXT_JARS_$2"
		JAR_FILES_LPC_MODULES="${InstallPath}/LPC_MODULES_$2"

		# 构建Platform和EXT的JAR文件列表, EXT目录下的jar包优先加载,方便灰度升级
		PlatformJars=""

		# 提取服务自生特殊依赖的JAR包
		if [ -d "${InstallPath}/services/$2/ext_jar" ];then
			ls -l "${InstallPath}/services/$2/ext_jar" | grep 'jar$' | grep -v grep | awk '{print $NF}' > ${JAR_FILES_EXT}
			while read ext_jar
			do
				PlatformJars="${PlatformJars}:$( getJarPath ${ext_jar} ${InstallPath}/services/$2/ext_jar )"
			done < ${JAR_FILES_EXT}
		else
			printf "" > ${JAR_FILES_EXT}
		fi

		# 提取所有公共目录下的Jar包
		ls -l "${InstallPath}/lib" | grep 'jar$' | grep -v grep | awk '{print $NF}' > ${JAR_FILES_PLATFORMS}
		while read jar
		do
			if [ $( cat ${JAR_FILES_EXT} | grep "$( basename $jar )" | wc -l ) -eq "0" ];then
				PlatformJars="${PlatformJars}:$( getJarPath $jar ${InstallPath}/lib )"
			fi
		done < ${JAR_FILES_PLATFORMS}

		LpcModuleJars=""
		# 提取LPC模块目录下的Jar包
		#ls -l "${InstallPath}/plugins/lpc" | grep 'jar$' | grep -v grep | awk '{print $NF}' > ${JAR_FILES_LPC_MODULES}
		#while read lpc_jar
		#do
		#	if [ "$LpcModuleJars" == "" ];then
		#		LpcModuleJars="$( getJarPath ${lpc_jar} ${InstallPath}/plugins/lpc )"
		#	else
		#		LpcModuleJars="$LpcModuleJars:$( getJarPath ${lpc_jar} ${InstallPath}/plugins/lpc )"
		#	fi
		#done < ${JAR_FILES_LPC_MODULES}
		
		rm -f ${JAR_FILES_EXT}
		rm -f ${JAR_FILES_PLATFORMS}
		rm -f ${JAR_FILES_LPC_MODULES}

		RUNNABLE_JAR_PATH="${InstallPath}/services/$2/$( ls -l ${InstallPath}/services/$2 | egrep $2.*jar | awk '{print $NF}' | head -1 )"
		if [ "z${RUNNABLE_JAR_PATH}" == "z"  ];then
			echo "Service Jar is not existed!"
			MyExit 2
		fi

		if [ ! -f "${InstallPath}/services/$2/service.properties" ];then
			echo "service.properties is not existed!"
			MyExit 3
		fi
		
		JVM_OPTION=""
		if [ -f "${InstallPath}/services/$2/jvm.config" ];then
			JVM_OPTION=$( cat "${InstallPath}/services/$2/jvm.config" )
		fi
		echo "JVM-OPTION is ${JVM_OPTION}"
		
		ADAPTOR_CLASS=""
		if [ -f "${InstallPath}/services/$2/adaptor" ];then
			ADAPTOR_CLASS=$(cat "${InstallPath}/services/$2/adaptor" )
			export ADAPTOR_CLASS
		fi

		CONFIG_ARGS=" ${InstallPath}/services/$2/service.properties $2"

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
	   
		echo "[${TIME}] start service $2"
		JAVA_CMD_ARGS="-cp ${CLASSPATH_ARG} -server -ea -Dfile.encoding=UTF-8 ${JVM_OPTION} org.soldier.platform.svr_platform.container.ServiceContainer ${CONFIG_ARGS}"
		if [ "${IsInCygwin}" = "false" ];then
		  TMP_NO_HUP_OUT=/tmp/$$_svr_platform_no_hup.out
		  nohup java ${JAVA_CMD_ARGS} > $TMP_NO_HUP_OUT 2>&1 & 
		  
		   # 睡2s，防止进程启动了，但是shell未检测到
		  sleep 2
		  echo
		  cat $TMP_NO_HUP_OUT
		  rm -f $TMP_NO_HUP_OUT
		  
		else
		  cd ${InstallPath}/services/$2
		  #找到Java的路径 并且软链用于标识
		  if [ "$JAVA_HOME" = "" ];then
		      echo "JAVA_HOME is not set"
		      MyExit 10
		  fi 
		  
		  JAVA_PATH=$( cygpath -up "$JAVA_HOME/bin/java.exe" )
		  if [ ! -f "$JAVA_PATH" ];then
		      echo "Java.exe is not exit"
		      MyExit 11
		  fi
		  
		  #拷贝java
		  if [ ! -f "java.exe" ];then
		      cp -f $JAVA_PATH java 
		  fi
	
		  ${InstallPath}/services/$2/java ${JAVA_CMD_ARGS} &
		  cd -
		  
		  #cygwin下启动较慢，睡2s
		  sleep 2
		fi
	   
		count=$( getServiceCount $2 )
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
            for service in $( ls -l "${InstallPath}/services" | grep ^d | awk '{print $NF;}' )
            do
                $0 stop $service
            done
            MyExit 0
        fi
	
		echo "[${TIME}] stop service $2"
		count=$( getServiceCount $2 )
		if [ "$count" -eq "0" ];then
			echo "no service $2 is running"
			MyExit 0
		fi
		if [ "$IsInCygwin" = "true" ];then
		    ps -ef | grep java | grep "services/$2/java" | grep -v grep | awk '{print $2}' | xargs kill -9 
		else
		    ps -ef | grep java | grep "services/$2/service.properties" | grep -v grep | awk '{print $2}' | xargs kill -9
	    fi
		sleep 1
	;;

    "show" )
		if [ "$2" == "all" ];then
	      ps -ef | grep "${InstallPath}/services" | grep -v grep
		else 
		  if [ "$IsInCygwin" = "true" ];then
		      ps -ef | grep "${InstallPath}/services/$2/java" | grep -v grep
		  else
		      ps -ef | grep java | grep "${InstallPath}/services/$2/service.properties" | grep -v grep
		  fi
		fi
    ;;
	
    "restart" )
		if [ "$2" == "all" ];then
			for service in $( ls -l "${InstallPath}/services" | grep ^d | awk '{print $NF;}' )
			do
				$0 restart $service
			done
		else
			$0 stop $2
			$0 start $2
		fi
    ;;
	
	"uninstall" )
		uninstallService $2
	;;	
    * )
	help
    ;;
esac

MyExit 0


