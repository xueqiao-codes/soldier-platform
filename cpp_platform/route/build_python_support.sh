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

pushd ${ROUTE_SCRIPT_DIR} > /dev/null
mkdir -p out/route_finder_python

pushd ${ROUTE_SCRIPT_DIR}/../projects > /dev/null
source build_env.sh
blade build cpp_platform/route --generate-python || exit 

# copy libs to java resource
cp -f build64_release/cpp_platform/route/source/swig/route_finder.py  ${ROUTE_SCRIPT_DIR}/out/route_finder_python/ || exit 
cp -f build64_release/cpp_platform/route/_route_finder_swig.so    ${ROUTE_SCRIPT_DIR}/out/route_finder_python/_route_finder.so || exit
cp -f ${ROUTE_SCRIPT_DIR}/source/swig/python/*.py   ${ROUTE_SCRIPT_DIR}/out/route_finder_python/

popd > /dev/null
popd > /dev/null
