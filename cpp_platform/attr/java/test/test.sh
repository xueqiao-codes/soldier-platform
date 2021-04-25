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

pushd ${THIS_SCRIPT_REAL_DIR} > /dev/null

#javac -cp ../target/libattr-1.0-SNAPSHOT.jar TestMain.java || exit 1
#java -cp ../target/libattr-1.0-SNAPSHOT.jar:.  TestMain || exit 1

javac -cp ../target/libattr-1.0.jar TestFactory.java || exit 1
java -cp ../target/libattr-1.0.jar:. TestFactory || exit 1

popd > /dev/null