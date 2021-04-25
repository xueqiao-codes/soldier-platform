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

export PATH=$CURRENT_SCRIPT_DIR/.bin:$CURRENT_SCRIPT_DIR/.bin/swig/bin:$CURRENT_SCRIPT_DIR/.bin/typhoon-blade:$CURRENT_SCRIPT_DIR/.bin/automake/bin:$PATH
export LIBRARY_PATH=$CURRENT_SCRIPT_DIR/.libs
export LD_LIBRARY_PATH=$CURRENT_SCRIPT_DIR/.libs:$LD_LIBRARY_PATH
