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

blade build --generate-java || exit 1

rm -f java/src/main/java/org/soldier/watcher/file/swig/*.java
cp -f ${THIS_SCRIPT_REAL_DIR}/../projects/build64_release/cpp_platform/watcher/org/soldier/watcher/file/swig/*.java java/src/main/java/org/soldier/watcher/file/swig || exit 1
cp -f ${THIS_SCRIPT_REAL_DIR}/../projects/build64_release/cpp_platform/watcher/libFileWatcher_java.so java/src/main/resources/linux || exit 1

pushd java
mvn package || exit 1
popd > /dev/null

echo "Finished..."

popd > /dev/null