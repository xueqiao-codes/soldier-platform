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
CURRENT_SCRIPT_DIR=$( pwd )
cd - > /dev/null


pushd ${THIS_SCRIPT_REAL_DIR}/.. > /dev/null
CPP_PLATFORM_DIR=$( pwd )
popd >/dev/null

pushd ${THIS_SCRIPT_REAL_DIR} > /dev/null
if [ ! -L cpp_platform ];then
	ln -sf ${CPP_PLATFORM_DIR}  cpp_platform || exit 1
fi
source ${THIS_SCRIPT_REAL_DIR}/cpp_platform/envsetup.sh
popd > /dev/null
