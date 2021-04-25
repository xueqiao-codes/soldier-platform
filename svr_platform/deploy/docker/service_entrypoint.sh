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

function startService() {
  local service_name=$1
  local TIME=$( date +"%Y-%m-%d %H:%M:%S" )
  
  PlatformJars=$( getClassPath $InstallPath/lib )
  ServiceExtJars=$( getClassPath $InstallPath/services/$service_name/ext_jar )
  
  RUNNABLE_JAR_PATH="${InstallPath}/services/$service_name/$( ls -l ${InstallPath}/services/$service_name | egrep $service_name.*jar | awk '{print $NF}' | head -1 )"
  if [ "z${RUNNABLE_JAR_PATH}" == "z"  ];then
    echo "Service Jar is not existed!"
    MyExit 2
  fi

  if [ ! -f "${InstallPath}/services/$service_name/service.properties" ];then
    echo "service.properties is not existed!"
    MyExit 3
  fi
  
  JVM_OPTION=""
  if [ -f "${InstallPath}/services/$service_name/jvm.config" ];then
    JVM_OPTION=$( cat "${InstallPath}/services/$service_name/jvm.config" )
  fi
  echo "JVM-OPTION is ${JVM_OPTION}"
  
  ADAPTOR_CLASS=""
  if [ -f "${InstallPath}/services/$service_name/adaptor" ];then
    ADAPTOR_CLASS=$(cat "${InstallPath}/services/$service_name/adaptor" )
    export ADAPTOR_CLASS
  fi

  CONFIG_ARGS=" ${InstallPath}/services/$service_name/service.properties $service_name"

  export CLASSPATH_ARG="${PlatformJars}:${ServiceExtJars}:${RUNNABLE_JAR_PATH}"
  export LPC_MODULES="${LpcModuleJars}"
  export DAL_SET_PATH="${InstallPath}/../dal_set/config/dal_set.xml"
  
  echo "[${TIME}] start service $service_name"
  JAVA_CMD_ARGS="-cp ${CLASSPATH_ARG} -server -ea -Dfile.encoding=UTF-8 ${JVM_OPTION} org.soldier.platform.svr_platform.container.ServiceContainer ${CONFIG_ARGS}"
  
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
  for service in $( ls -l "${InstallPath}/services" | grep ^d | awk '{print $NF;}' )
  do
    # acture only one will run
    startService $service
    exit $?
  done
}

main