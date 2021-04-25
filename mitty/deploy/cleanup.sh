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

pushd ${InstallPath}/backup > /dev/null
if [ $? -ne 0 ];then
	echo "no backup directory"
	exit 1
fi
echo "[$( date +'%Y-%m-%d %H:%M:%S')]cleanup $(pwd)"

find -mtime +5 -exec rm -f {} \;

popd > /dev/null