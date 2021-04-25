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
ROUTE_SCRIPT_DIR="$( pwd )"
cd - > /dev/null

mkdir -p build/

pushd ${ROUTE_SCRIPT_DIR} > /dev/null

pushd ${ROUTE_SCRIPT_DIR}/../projects > /dev/null
source build_env.sh
blade build cpp_platform/route --generate-dynamic || exit 

# copy libs to java resource
cp -f build64_release/cpp_platform/route/libjroute_agent_daemon.so ${ROUTE_SCRIPT_DIR}/java/route_agent_daemon/java/src/main/resources/linux/ || exit 
cp -f build64_release/cpp_platform/route/libjroute_agent.so ${ROUTE_SCRIPT_DIR}/java/api/finder/src/main/resources/linux/ || exit
cp -f build64_release/cpp_platform/route/route_agent_tool2 ${ROUTE_SCRIPT_DIR}/build

popd > /dev/null

pushd ${ROUTE_SCRIPT_DIR}/java/route_agent_daemon/java > /dev/null
mvn clean package || exit 
popd > /dev/null

pushd ${ROUTE_SCRIPT_DIR}/java/api/finder
mvn clean package || exit 
popd > /dev/null

popd > /dev/null
