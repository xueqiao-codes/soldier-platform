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


pushd ${THIS_SCRIPT_REAL_DIR}/../.. > /dev/null

echo "script dir is ${THIS_SCRIPT_REAL_DIR}"

blade build cpp_platform/attr --generate-java || exit 1

cp -rf build64_release/cpp_platform/attr/org  ${THIS_SCRIPT_REAL_DIR}/java/src/main/java || exit 1
cp -f  build64_release/cpp_platform/attr/libAttrReporter_java.so ${THIS_SCRIPT_REAL_DIR}/java/src/main/resources/linux || exit 1

pushd ${THIS_SCRIPT_REAL_DIR}/java
mvn package || exit 1
popd > /dev/null

echo "runing test...."
chmod u+x ${THIS_SCRIPT_REAL_DIR}/java/test/test.sh
${THIS_SCRIPT_REAL_DIR}/java/test/test.sh || exit 1

echo "Finished..."

popd > /dev/null